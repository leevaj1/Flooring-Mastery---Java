/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

/**
 *
 * @author leeva
 */
public class OrderInMemory implements OrderDao {

    private List<Order> orderList = new ArrayList<>();

    public OrderInMemory() {
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));
        
        
        Order first = new Order();
        first.setDate(date);
        first.setOrderNumber(1);
        first.setCustomerName("Wise");
        first.getTaxInfo().setState("OH");
        first.getTaxInfo().setTaxRate(new BigDecimal("1.00"));
        first.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.00"));
        first.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

        orderList.add(first);

        Order second = new Order();
        second.setDate(date);
        second.setOrderNumber(2);
        second.setCustomerName("Data");
        second.getTaxInfo().setState("OH");
        second.getTaxInfo().setTaxRate(new BigDecimal("1.00"));
        second.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.00"));
        second.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

        orderList.add(second);

        Order third = new Order();
        third.setDate(date);
        third.setOrderNumber(3);
        third.setCustomerName("Folder");
        third.getTaxInfo().setState("OH");
        third.getTaxInfo().setTaxRate(new BigDecimal("1.00"));
        third.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.00"));
        third.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

        orderList.add(third);

    }
    //OrderNumber,CustomerName,State,
    //TaxRate,ProductType,Area,CostPerSquareFoot,
    //LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws OrderPersistenceException {
        //make new empty list
        //go through orderList and add any orders with a matching date to the new list
        //return the new list

        List<Order> orders = new ArrayList<>();
        
        for(Order order : orderList) {
            if (order.getDate().equals(date)) {
                orders.add(order);
                
            }
        }return orders;

      

    }

    @Override
    public Order addOrder(Order toAdd) throws OrderPersistenceException {
        orderList.add(toAdd);
        return toAdd;
    }

    @Override
    public Order editOrder(Order newData) throws OrderPersistenceException {
        for(Order order : orderList) {
            if(order.getOrderNumber()== newData.getOrderNumber()){
                orderList.remove(order);
                
            }
        } 
        orderList.add(newData);
        return newData;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        
        Order toRemove = new Order();
        for(Order order : orderList) {
            if(order.getDate() == date ) {
                if(order.getOrderNumber() == orderNumber){
                    toRemove = order;
                    orderList.remove(order);
                    
                }
            }
            
                
        }return toRemove;
    }

}
