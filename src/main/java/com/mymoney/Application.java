package com.mymoney;

import com.mymoney.exception.MyMoneyException;
import com.mymoney.model.PortfolioTracker;
import com.mymoney.service.IMyMoneyService;
import com.mymoney.service.IPortfolioService;
import com.mymoney.service.impl.MyMoneyService;
import com.mymoney.service.impl.PortfolioService;
import com.mymoney.utils.Constants;
import com.mymoney.utils.FileReaderService;
import java.util.List;

public class Application {

    public static void main( String[] args ) {
        if(args.length != 1) {
            throw new MyMoneyException(Constants.FILEPATH_NOT_PASSED);
        }
        List<String> actions = FileReaderService.readFileIntoString(args[0]);
        IPortfolioService portfolioService = new PortfolioService();
        PortfolioTracker portfolioTracker = new PortfolioTracker();
        IMyMoneyService myMoneyService = new MyMoneyService();
        myMoneyService.executeCommands(actions, portfolioService, portfolioTracker);
    }
}
