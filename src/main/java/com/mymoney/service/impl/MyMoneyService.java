package com.mymoney.service.impl;

import com.mymoney.model.PortfolioTracker;
import com.mymoney.exception.MyMoneyException;
import com.mymoney.model.CommandEnum;
import com.mymoney.model.Portfolio;
import com.mymoney.service.IMyMoneyService;
import com.mymoney.service.IPortfolioService;
import com.mymoney.utils.Constants;
import com.mymoney.utils.ValidatorService;

import java.util.List;

public class MyMoneyService implements IMyMoneyService {

    @Override
    public void executeCommands(List<String> actions, IPortfolioService portfolioService, PortfolioTracker portfolioTracker) {
        try {
            Portfolio pf = null;
            for (String action : actions) {
                String[] inputs = action.split(" ");
                CommandEnum command;
                try {
                    command = CommandEnum.valueOf(inputs[0]);
                } catch(IllegalArgumentException e) {
                    throw new MyMoneyException(Constants.INVALID_COMMAND + " " + inputs[0]);
                }

                if (!ValidatorService.isValidInput(command, inputs)) {
                    throw new MyMoneyException(Constants.INVALID_INPUT + " for " + inputs[0]);
                }

                switch (command) {
                    case ALLOCATE:
                        pf = portfolioService.allocate(
                                Double.parseDouble(inputs[1]),
                                Double.parseDouble(inputs[2]),
                                Double.parseDouble(inputs[3]));
                        break;

                    case SIP:
                        portfolioService.assignSIP(pf,
                                Double.parseDouble(inputs[1]),
                                Double.parseDouble(inputs[2]),
                                Double.parseDouble(inputs[3]));
                        break;

                    case CHANGE:
                        portfolioService.monthlyChange(pf,
                                Double.parseDouble(inputs[1].substring(0,inputs[1].length()-1)),
                                Double.parseDouble(inputs[2].substring(0,inputs[2].length()-1)),
                                Double.parseDouble(inputs[3].substring(0,inputs[3].length()-1)),
                                inputs[4], portfolioTracker);
                        break;

                    case BALANCE:
                        portfolioService.printBalanceForMonth(portfolioTracker, inputs[1]);
                        break;

                    case REBALANCE:
                        portfolioService.getLastRebalancedValue(portfolioTracker);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
