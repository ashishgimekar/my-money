package com.mymoney.service.impl;

import com.mymoney.model.PortfolioTracker;
import com.mymoney.exception.MyMoneyException;
import com.mymoney.model.MonthEnum;
import com.mymoney.model.Portfolio;
import com.mymoney.service.IPortfolioService;
import com.mymoney.utils.Constants;

public class PortfolioService implements IPortfolioService {

    @Override
    public Portfolio allocate(double equity, double debt, double gold) {
        return new Portfolio(equity, debt, gold);
    }

    @Override
    public void assignSIP(Portfolio portfolio, double equitySIP, double debtSIP, double goldSIP) {
        if(portfolio != null) {
            portfolio.setSIPEnabled(true);
            portfolio.setEquitySIP(equitySIP);
            portfolio.setDebtSIP(debtSIP);
            portfolio.setGoldSIP(goldSIP);
        } else {
            throw new MyMoneyException("Portfolio is not initialized yet with initial allocation");
        }
    }

    @Override
    public void monthlyChange(Portfolio portfolio, double equityChangePercent, double debtChangePercent, double goldChangePercent, String month, PortfolioTracker portfolioTracker) {
        MonthEnum monthEnum;
        try {
            monthEnum = MonthEnum.valueOf(month);
        } catch(IllegalArgumentException e) {
            throw new MyMoneyException(Constants.INVALID_MONTH + " " + month);
        }

        if(portfolio == null) {
            throw new MyMoneyException("Portfolio is not initialized yet with initial allocation");
        }

        if(portfolio.isSIPEnabled() && monthEnum != MonthEnum.JANUARY) {
            addSIPValues(portfolio);
        }

        addChangeValues(portfolio, equityChangePercent, debtChangePercent, goldChangePercent);

        if(monthEnum == MonthEnum.JUNE || monthEnum == MonthEnum.DECEMBER) {
            rebalance(portfolio, portfolioTracker);
        }
        Portfolio monthlyPortFolio;
        try {
            monthlyPortFolio = (Portfolio) portfolio.clone();
        } catch (CloneNotSupportedException e) {
            throw new MyMoneyException("Error occurred while creating copy of the portfolio");
        }
        portfolioTracker.getBalance().put(monthEnum, monthlyPortFolio);
    }

    @Override
    public void printBalanceForMonth(PortfolioTracker portfolioTracker, String month) {
        MonthEnum monthEnum;
        try {
            monthEnum = MonthEnum.valueOf(month);
        } catch(IllegalArgumentException e) {
            throw new MyMoneyException(Constants.INVALID_MONTH + " " + month);
        }

        if(!portfolioTracker.getBalance().containsKey(monthEnum)) {
            throw new MyMoneyException("There is no balance present for " + month);
        }

        String balance = generateBalanceString(portfolioTracker.getBalance().get(monthEnum));
        System.out.println(balance);
    }

    @Override
    public void rebalance(Portfolio portfolio, PortfolioTracker portfolioTracker) {
        double currentSum = portfolio.getEquity() + portfolio.getDebt() + portfolio.getGold();
        double rebalancedEquity = (currentSum / 100 ) * portfolio.getInitialEquityPercent();
        double rebalancedDebt = (currentSum / 100 ) * portfolio.getInitialDebtPercent();
        double rebalancedGold = (currentSum / 100) * portfolio.getInitialGoldPercent();

        portfolio.setEquity(Math.floor(rebalancedEquity));
        portfolio.setDebt(Math.floor(rebalancedDebt));
        portfolio.setGold(Math.floor(rebalancedGold));

        String rebalancedValue = generateBalanceString(portfolio);
        portfolioTracker.getRebalancedBalances().add(rebalancedValue);
    }

    private String generateBalanceString(Portfolio portfolio) {
        if(portfolio == null) {
            return null;
        }

        return Math.round(portfolio.getEquity()) + " " + Math.round(portfolio.getDebt()) + " " + Math.round(portfolio.getGold());
    }

    @Override
    public void getLastRebalancedValue(PortfolioTracker portfolioTracker) {
        if(portfolioTracker.getRebalancedBalances().isEmpty()) {
            System.out.println(Constants.CANNOT_REBALANCE);
        } else {
            int lastIndex = portfolioTracker.getRebalancedBalances().size() - 1;
            System.out.println(portfolioTracker.getRebalancedBalances().get(lastIndex));
        }
    }

    private void addSIPValues(Portfolio portfolio) {
        portfolio.setEquity(portfolio.getEquity() + portfolio.getEquitySIP());
        portfolio.setDebt(portfolio.getDebt() + portfolio.getDebtSIP());
        portfolio.setGold(portfolio.getGold() + portfolio.getGoldSIP());
    }

}
