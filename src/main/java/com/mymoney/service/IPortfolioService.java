package com.mymoney.service;

import com.mymoney.model.PortfolioTracker;
import com.mymoney.model.Portfolio;

public interface IPortfolioService {
    Portfolio allocate(double equity, double debt, double gold);

    void assignSIP(Portfolio portfolio, double equitySIP, double debtSIP, double goldSIP);

    void monthlyChange(Portfolio portfolio, double equityChangePercent, double debtChangePercent, double goldChangePercent, String month, PortfolioTracker portfolioTracker);

    default void addChangeValues(Portfolio portfolio, double equityChangePercent, double debtChangePercent, double goldChangePercent) {
        double equityChange = portfolio.getEquity() * equityChangePercent / 100;
        double debtChange = portfolio.getDebt() * debtChangePercent / 100;
        double goldChange = portfolio.getGold() * goldChangePercent / 100;

        portfolio.setEquity(Math.floor(portfolio.getEquity() + equityChange));
        portfolio.setDebt(Math.floor(portfolio.getDebt() + debtChange));
        portfolio.setGold(Math.floor(portfolio.getGold() + goldChange));
    }

    void printBalanceForMonth(PortfolioTracker portfolioTracker, String month);

    void rebalance(Portfolio portfolio, PortfolioTracker portfolioTracker);

    void getLastRebalancedValue(PortfolioTracker portfolioTracker);
}
