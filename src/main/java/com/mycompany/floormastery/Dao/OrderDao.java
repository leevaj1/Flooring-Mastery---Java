/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author leeva
 */
public interface OrderDao {
    
    public List<Order> getOrdersForDate (LocalDate date) throws OrderPersistenceException;
    public Order addOrder(Order toAdd)throws OrderPersistenceException;
    public Order editOrder (Order newData)throws OrderPersistenceException;
    public Order removeOrder (LocalDate date, int orderNumber)throws OrderPersistenceException;

//    public List<Order> getAllOrders() throws OrderPersistenceException;
    
    
    
    
}
