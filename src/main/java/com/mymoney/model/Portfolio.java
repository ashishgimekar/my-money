package com.mymoney.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio implements Cloneable {
    private double equity;
    private double debt;
    private double gold;

    private double initialEquityPercent;
    private double initialDebtPercent;
    private double initialGoldPercent;

    private boolean isSIPEnabled;

    private double equitySIP;
    private double debtSIP;
    private double goldSIP;

    public Portfolio(double equity, double debt, double gold) {
        this.equity = equity;
        this.debt = debt;
        this.gold = gold;

        this.equitySIP = 0;
        this.debtSIP = 0;
        this.goldSIP = 0;

        this.isSIPEnabled = false;

        double totalInvestment = equity + debt + gold;
        this.initialEquityPercent = (equity/totalInvestment) * 100;
        this.initialDebtPercent = (debt/totalInvestment) * 100;
        this.initialGoldPercent = (gold/totalInvestment) * 100;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "equity=" + equity +
                ", debt=" + debt +
                ", gold=" + gold +
                ", initialEquityPercent=" + initialEquityPercent +
                ", initialDebtPercent=" + initialDebtPercent +
                ", initialGoldPercent=" + initialGoldPercent +
                ", isSIPEnabled=" + isSIPEnabled +
                ", equitySIP=" + equitySIP +
                ", debtSIP=" + debtSIP +
                ", goldSIP=" + goldSIP +
                '}';
    }
}
