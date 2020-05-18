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
public class Product {
    
    private String productType;
    private BigDecimal materialCostPerSqFoot;
    private BigDecimal laborCostPerSqFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;

public Product () {
     materialCostPerSqFoot = new BigDecimal(0);
     laborCostPerSqFoot = new BigDecimal(0);

    }
    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return the materialCostPerSqFoot
     */
    public BigDecimal getMaterialCostPerSqFoot() {
        
        return materialCostPerSqFoot;
    }

    /**
     * @param materialCostPerSqFoot the materialCostPerSqFoot to set
     */
    public void setMaterialCostPerSqFoot(BigDecimal materialCostPerSqFoot) {
        this.materialCostPerSqFoot = materialCostPerSqFoot;
    }

    /**
     * @return the laborCostPerSqFoot
     */
    public BigDecimal getLaborCostPerSqFoot() {
        return laborCostPerSqFoot;
    }

    /**
     * @param laborCostPerSqFoot the laborCostPerSqFoot to set
     */
    public void setLaborCostPerSqFoot(BigDecimal laborCostPerSqFoot) {
        this.laborCostPerSqFoot = laborCostPerSqFoot;
    }


    
    
}
