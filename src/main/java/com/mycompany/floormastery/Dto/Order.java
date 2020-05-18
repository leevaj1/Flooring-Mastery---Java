/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author leeva
 */
public class Order {

    private LocalDate date;
    private int orderNumber;
    private String customerName;
    private StateTax taxInfo;
    private Product productInfo;
    
    
    private BigDecimal areaOfProduct;


    public Order() {
        this(0);

    }

    public Order(int orderNum) {
        this.orderNumber = orderNum;
        date = LocalDate.now();
        customerName = "";
        taxInfo = new StateTax();
        productInfo = new Product();
        areaOfProduct = new BigDecimal(0);

    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the CustomerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the taxInfo
     */
    public StateTax getTaxInfo() {
        return taxInfo;
    }

    /**
     * @param taxInfo the taxInfo to set
     */
    public void setTaxInfo(StateTax taxInfo) {
        this.taxInfo = taxInfo;
    }

    /**
     * @return the productInfo
     */
    public Product getProductInfo() {
        return productInfo;
    }

    /**
     * @param productInfo the productInfo to set
     */
    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public BigDecimal getAreaOfProduct() {
        return areaOfProduct;
    }

    /**
     * @param areaOfProduct the areaOfProduct to set
     */
    public void setAreaOfProduct(BigDecimal areaOfProduct) {
        this.areaOfProduct = areaOfProduct;
    }

    public BigDecimal getTotalMaterialCost() {

        BigDecimal toReturn = getProductInfo().getMaterialCostPerSqFoot()
                .multiply(getAreaOfProduct())
                .setScale(2, RoundingMode.HALF_UP);

        return toReturn;

    }

    public BigDecimal getTotalLaborCost() {

        BigDecimal toReturn
                = getProductInfo().getLaborCostPerSqFoot()
                        .multiply(getAreaOfProduct())
                        .setScale(2, RoundingMode.HALF_UP);

        return toReturn;

    }

    public BigDecimal getTotalTax() {

        BigDecimal subTotal
                = getTotalMaterialCost()
                        .add(getTotalLaborCost());

        BigDecimal toReturn
                = subTotal.multiply(getTaxInfo().getTaxRate())
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        return toReturn;

    }

    public BigDecimal getTotal() {

        BigDecimal toReturn
                = getTotalMaterialCost()
                        .add(getTotalLaborCost())
                        .add(getTotalTax());

        return toReturn;

    }

    /**
     * @return the materialCost
     */
    public BigDecimal getMaterialCost() {

        return areaOfProduct.multiply(productInfo.getMaterialCostPerSqFoot());
        
    }

    /**
     * @return the laborCost
     */
    public BigDecimal getLaborCost() {
        
        return areaOfProduct.multiply(productInfo.getLaborCostPerSqFoot());
    }



}
