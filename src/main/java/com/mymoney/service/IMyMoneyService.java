package com.mymoney.service;

import com.mymoney.model.PortfolioTracker;
import com.mymoney.service.impl.PortfolioService;

import java.util.List;

public interface IMyMoneyService {
    void executeCommands(List<String> actions, IPortfolioService portfolioService, PortfolioTracker portfolioTracker);
}
