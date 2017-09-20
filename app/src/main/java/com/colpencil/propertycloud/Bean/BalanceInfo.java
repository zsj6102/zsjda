package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

public class BalanceInfo implements Serializable {

    public String transaction_type;
    public int transaction_type_code;
    public String type;
    public int type_code;
    public String create_time;
    public double amount;
    public double account_amount;
    public int id;

}
