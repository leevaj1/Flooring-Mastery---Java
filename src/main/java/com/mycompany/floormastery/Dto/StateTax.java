/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dto;

import java.math.BigDecimal;

/**
 *
 * @author leeva
 */
public class StateTax {

    private String stateAbbrv;
    private BigDecimal taxRate;

    public StateTax() {
        stateAbbrv = "";
        taxRate = new BigDecimal(0);
    }

    /**
     * @return the state
     */
    public String getState() {
        return stateAbbrv;
    }

    /**
     * @param stateAbbrv the state to set
     */
    public void setState(String stateAbbrv) {
        this.stateAbbrv = stateAbbrv;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
