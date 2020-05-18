/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Service;

import com.mycompany.floormastery.Dao.OrderAuditDao;
import com.mycompany.floormastery.Dao.OrderDao;
import com.mycompany.floormastery.Dao.OrderPersistenceException;
import com.mycompany.floormastery.Dao.ProductDao;
import com.mycompany.floormastery.Dao.StateTaxDao;
import com.mycompany.floormastery.Dto.Order;
import com.mycompany.floormastery.Dto.Product;
import com.mycompany.floormastery.Dto.StateTax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author leeva
 */
public class OrderService {
    private final OrderDao dao;
    private final OrderAuditDao audit;
    private final ProductDao productDao;
    private final StateTaxDao stateTaxDao;
    
    public OrderService(OrderDao dao, OrderAuditDao audit, ProductDao productDao, StateTaxDao  stateTaxDao){
        this.dao = dao;
        this.audit = audit;
        this.productDao = productDao;
        this.stateTaxDao = stateTaxDao;
    }
    
//    public Response<List<Order>> getAllOrders(){
//        Response<List<Order>> response = new Response<>();
//        
//        try {
//            List<Order> allOrders = dao.getAllOrders();
//            
//            response.setSuccess(true);
//            response.setData(allOrders);
//        }catch (OrderPersistenceException e) {
//            response.setMessage(e.getMessage());
//            
//        }return response;
//    }

    public Response<Order> addOrder(Order toAdd) {
        Response<Order> response = new Response<>();
            try {
                Product product = productDao.getProduct(toAdd.getProductInfo().getProductType());
                StateTax stateTax = stateTaxDao.getStateTax(toAdd.getTaxInfo().getState());
                toAdd.setProductInfo(product);
                toAdd.setTaxInfo(stateTax);

                Order addedOrder = dao.addOrder(toAdd);
                response.setSuccess(true);
                response.setData(addedOrder);
                
            }catch (OrderPersistenceException e) {
                response.setMessage(e.getMessage());
            }
        return response;
    }

    public Response<Order> editOrder(Order newData) {
        Response<Order> response = new Response<>();
            
            try {
                dao.editOrder(newData);
                
                response.setSuccess(true);
                response.setData(newData);
            }catch (OrderPersistenceException e ) {
                response.setMessage(e.getMessage());
            }
    
        
        return response;
        
    }

    public Response<Order> removeOrder(LocalDate date, int removedOrder) {
        Response<Order> response = new Response<>();
        Order order = new Order();
        try {
            order = dao.removeOrder(date, removedOrder);
            
            
            response.setSuccess(true);
            response.setData(order);
            
        }catch (OrderPersistenceException e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    public Response <List<Order>> getOrdersForDate(LocalDate date) {
        Response <List<Order>> response = new Response<>();
        try {
            List<Order> data = dao.getOrdersForDate(date);
            response.setSuccess(true);
            response.setData(data);
        }catch (OrderPersistenceException e) {
            response.setMessage(e.getMessage());
        }return response;
    }
}
