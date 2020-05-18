/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.UserIO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author leeva
 */
public interface UserIO {

    void print(String msg);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);

    String readString(String prompt, int max);

    String formatCurrency(BigDecimal amount);

    public BigDecimal readBigDecimal(String prompt);

    public BigDecimal readBigDecimal(String prompt, int scale, BigDecimal min);

    public boolean readBoolean(String prompt);
    
    LocalDate readDate(String prompt);

    LocalDate readDate(String prompt, LocalDate min, LocalDate max);
}
