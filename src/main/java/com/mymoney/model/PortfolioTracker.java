package com.mymoney.model;

import com.mymoney.model.MonthEnum;
import com.mymoney.model.Portfolio;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PortfolioTracker {

    private List<String> rebalancedBalances;

    /*
        This map is maintaining the balance for an year only and if we want to keep this data
        for more than 1 year then we will have to maintain another map for year and
        balance map as Map<Integer, Map<MonthEnum, String>>
     */
    private Map<MonthEnum, Portfolio> balance;

    public PortfolioTracker() {
        this.rebalancedBalances = new ArrayList<>();
        this.balance = new HashMap<>();
    }
}
