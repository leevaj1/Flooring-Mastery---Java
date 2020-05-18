/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Controller;

import com.mycompany.floormastery.Dto.Order;
import com.mycompany.floormastery.Service.OrderService;
import com.mycompany.floormastery.Service.Response;
import com.mycompany.floormastery.UserIO.View;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author leeva
 */
public class Controller {

    View view;
    OrderService service;

    public Controller(OrderService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

//        try{
        while (keepGoing) {
            menuSelection = displayMenu();

            switch (menuSelection) {
                case 1:
                    displayOrderByDate();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    keepGoing = false;
                    break;
                default:
                    UnknownCommand();

            }
        }
        exitMessage();

//        }catch () {
//            
//            view.displayErrorMessage(e.getMessage());
    }

//    private int getMenuSelection() {
//        return view.printMenuAndGetSelection();
//    }
    private int displayMenu() {
        return view.displayMenu();
    }

    private void addOrder() {
        Order toAdd = view.addOrder();
        Response<Order> addOrder = service.addOrder(toAdd);
        view.addOrderComplete(addOrder);

    }

    private void editOrder() {
        displayOrderByDate();
        LocalDate orderDate = view.getOrderByDate();
        int orderNum = view.getOrderNumber();
        Response<List<Order>> editOrders = service.getOrdersForDate(orderDate);
        //get single existing order
        Order toEdit = null;
        
        
                
                
               toEdit  = view.editOrder(toEdit);
        String answer = view.saveData();

        if (answer.equalsIgnoreCase("y")) {
            Response<Order> editedOrder = service.editOrder(toEdit);
            view.displaySuccessfulEdit(editedOrder);
        }

    }//LocalDate date, int orderNum

    private void removeOrder() {
        LocalDate date = view.getOrderByDate();
        int orderNum = view.getOrderNumber();
        view.displayOrdersByDate(service.getOrdersForDate(date));
        Response<Order> order = service.removeOrder(date, orderNum);
        view.displayRemoveOrderCompleted(order);
    }

    private void UnknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void displayOrderByDate() {
        LocalDate date = view.getOrderByDate();

        Response<List<Order>> orders = service.getOrdersForDate(date);

        view.displayOrdersByDate(orders);
    }

}
