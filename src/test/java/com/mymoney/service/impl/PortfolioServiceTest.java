package com.mymoney.service.impl;

import com.mymoney.exception.MyMoneyException;
import com.mymoney.model.MonthEnum;
import com.mymoney.model.Portfolio;
import com.mymoney.model.PortfolioTracker;
import com.mymoney.utils.Constants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PortfolioServiceTest {

    @Test
    public void testAllocate() {
        Portfolio expected = new Portfolio(100, 200, 300);
        PortfolioService service = new PortfolioService();
        Portfolio actual = service.allocate(100, 200, 300);

        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
    }

    @Test
    public void testAssignSIP() {
        Portfolio expected = new Portfolio(100, 200, 300);
        expected.setSIPEnabled(true);
        expected.setEquitySIP(100);
        expected.setDebtSIP(200);
        expected.setGoldSIP(300);

        PortfolioService service = new PortfolioService();
        Portfolio actual = new Portfolio(100, 200,300);
        service.assignSIP(actual, 100, 200, 300);

        assertEquals(expected.getEquitySIP(), actual.getEquitySIP());
        assertEquals(expected.getDebtSIP(), actual.getDebtSIP());
        assertEquals(expected.getGoldSIP(), actual.getGoldSIP());
    }

    @Test
    public void testAssignSIPWithException() {
        Throwable exception = assertThrows(MyMoneyException.class, () -> {
            PortfolioService service = new PortfolioService();
            service.assignSIP(null, 100, 200, 300);
        });
        assertEquals("Portfolio is not initialized yet with initial allocation", exception.getMessage());
    }

    @Test
    public void testMonthlyChange() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        Portfolio actual = new Portfolio(100, 200, 300);
        Portfolio expected = new Portfolio(110, 220, 330);

        service.monthlyChange(actual, 10, 10, 10, "JANUARY", portfolioTracker);
        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
    }

    @Test
    public void testMonthlyChangeWithSIPOnJanuary() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        Portfolio actual = new Portfolio(100, 200, 300);
        actual.setSIPEnabled(true);
        Portfolio expected = new Portfolio(110, 220, 330);

        service.monthlyChange(actual, 10, 10, 10, "JANUARY", portfolioTracker);
        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
    }

    @Test
    public void testMonthlyChangeWithSIPFromFebruary() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        Portfolio actual = new Portfolio(100, 200, 300);
        actual.setSIPEnabled(true);
        actual.setEquitySIP(10);
        Portfolio expected = new Portfolio(121, 220, 330);

        service.monthlyChange(actual, 10, 10, 10, "FEBRUARY", portfolioTracker);
        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
    }

    @Test
    public void testMonthlyChangeWithRebalancingOnJune() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        Portfolio actual = new Portfolio(100, 200, 300);
        actual.setSIPEnabled(true);
        actual.setEquitySIP(10);
        Portfolio expected = new Portfolio(111, 223, 335);
        PortfolioTracker expectedPortfolioTracker = new PortfolioTracker();
        List<String> rebalanceList = new ArrayList<>();
        rebalanceList.add("111 223 335");
        expectedPortfolioTracker.setRebalancedBalances(rebalanceList);

        service.monthlyChange(actual, 10, 10, 10, "JUNE", portfolioTracker);
        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
        assertEquals(expectedPortfolioTracker.getRebalancedBalances(), portfolioTracker.getRebalancedBalances());
    }

    @Test
    public void testMonthlyChangeWithRebalancingOnDecember() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        Portfolio actual = new Portfolio(100, 200, 300);
        actual.setSIPEnabled(true);
        actual.setEquitySIP(10);
        Portfolio expected = new Portfolio(111, 223, 335);
        PortfolioTracker expectedPortfolioTracker = new PortfolioTracker();
        List<String> rebalanceList = new ArrayList<>();
        rebalanceList.add("111 223 335");
        expectedPortfolioTracker.setRebalancedBalances(rebalanceList);

        service.monthlyChange(actual, 10, 10, 10, "DECEMBER", portfolioTracker);
        assertEquals(expected.getEquity(), actual.getEquity());
        assertEquals(expected.getDebt(), actual.getDebt());
        assertEquals(expected.getGold(), actual.getGold());
        assertEquals(expectedPortfolioTracker.getRebalancedBalances(), portfolioTracker.getRebalancedBalances());
    }

    @Test
    public void testMonthlyChangeWithInvalidMonth() {
        PortfolioService service = new PortfolioService();
        Portfolio actual = new Portfolio(100, 200, 300);

        Throwable exception = assertThrows(MyMoneyException.class, () -> service.monthlyChange(actual, 10, 10, 10, "TEST", null));
        assertEquals(Constants.INVALID_MONTH + " " + "TEST", exception.getMessage());
    }

    @Test
    public void testMonthlyChangeWithNullPortfolio() {
        PortfolioService service = new PortfolioService();

        Throwable exception = assertThrows(MyMoneyException.class, () -> service.monthlyChange(null, 10, 10, 10, "JANUARY", null));
        assertEquals("Portfolio is not initialized yet with initial allocation", exception.getMessage());
    }

    @Test
    public void testPrintBalanceForMonth() {
        PortfolioService service = new PortfolioService();
        Portfolio actual = new Portfolio(100, 200, 300);
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        portfolioTracker.getBalance().put(MonthEnum.JANUARY, actual);

        service.printBalanceForMonth(portfolioTracker, "JANUARY");
    }

    @Test
    public void testPrintBalanceForMonthWithFutureMonth() {
        PortfolioService service = new PortfolioService();
        Portfolio actual = new Portfolio(100, 200, 300);
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        portfolioTracker.getBalance().put(MonthEnum.JANUARY, actual);

        Throwable exception = assertThrows(MyMoneyException.class, () -> service.printBalanceForMonth(portfolioTracker, "FEBRUARY"));
        assertEquals("There is no balance present for FEBRUARY", exception.getMessage());
    }

    @Test
    public void testPrintBalanceForMonthWithWrongMonth() {
        PortfolioService service = new PortfolioService();
        Portfolio actual = new Portfolio(100, 200, 300);
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        portfolioTracker.getBalance().put(MonthEnum.JANUARY, actual);

        Throwable exception = assertThrows(MyMoneyException.class, () -> service.printBalanceForMonth(portfolioTracker, "TEST"));
        assertEquals(Constants.INVALID_MONTH + " TEST", exception.getMessage());
    }

    @Test
    public void testGetLastRebalancedValue() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        portfolioTracker.getRebalancedBalances().add("100 200 300");
        service.getLastRebalancedValue(portfolioTracker);
    }

    @Test
    public void testGetLastRebalancedValueBeforeSixMonths() {
        PortfolioService service = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        portfolioTracker.getRebalancedBalances();
        service.getLastRebalancedValue(portfolioTracker);
    }

}
