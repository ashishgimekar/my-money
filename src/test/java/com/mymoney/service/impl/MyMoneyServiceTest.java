package com.mymoney.service.impl;

import com.mymoney.model.PortfolioTracker;
import com.mymoney.service.IPortfolioService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyMoneyServiceTest {

    @Test
    public void testExecuteCommands() {
        List<String> actions = new ArrayList<>();
        actions.add("ALLOCATE 6000 3000 1000");
        actions.add("SIP 2000 1000 500");
        actions.add("CHANGE 4.00% 10.00% 2.00% JANUARY");
        actions.add("CHANGE -10.00% 40.00% 0.00% FEBRUARY");
        actions.add("CHANGE 12.50% 12.50% 12.50% MARCH");
        actions.add("CHANGE 8.00% -3.00% 7.00% APRIL");
        actions.add("CHANGE 13.00% 21.00% 10.50% MAY");
        actions.add("CHANGE 10.00% 8.00% -5.00% JUNE");
        actions.add("BALANCE MARCH");
        actions.add("REBALANCE");

        IPortfolioService portfolioService = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        MyMoneyService myMoneyService = new MyMoneyService();

        myMoneyService.executeCommands(actions, portfolioService, portfolioTracker);

        PortfolioTracker expectedPortfolioTracker = new PortfolioTracker();
        List<String> rebalanceList = new ArrayList<>();
        rebalanceList.add("23619 11809 3936");
        expectedPortfolioTracker.setRebalancedBalances(rebalanceList);
        assertEquals(expectedPortfolioTracker.getRebalancedBalances(), portfolioTracker.getRebalancedBalances());
    }

    @Test
    public void testExecuteCommandsWithEarlyRebalance() {
        List<String> actions = new ArrayList<>();
        actions.add("ALLOCATE 8000 6000 3500");
        actions.add("SIP 3000 2000 1000");
        actions.add("CHANGE 11.00% 9.00% 4.00% JANUARY");
        actions.add("CHANGE -10.00% 40.00% 0.00% FEBRUARY");
        actions.add("CHANGE 12.50% 18.00% 12.50% MARCH");
        actions.add("BALANCE MARCH");
        actions.add("REBALANCE");

        IPortfolioService portfolioService = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        MyMoneyService myMoneyService = new MyMoneyService();

        myMoneyService.executeCommands(actions, portfolioService, portfolioTracker);

        PortfolioTracker expectedPortfolioTracker = new PortfolioTracker();
        List<String> rebalanceList = new ArrayList<>();
        expectedPortfolioTracker.setRebalancedBalances(rebalanceList);
        assertEquals(expectedPortfolioTracker.getRebalancedBalances(), portfolioTracker.getRebalancedBalances());
    }

}
