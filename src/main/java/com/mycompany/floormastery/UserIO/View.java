/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.UserIO;

import com.mycompany.floormastery.Dto.Order;
import com.mycompany.floormastery.Service.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author leeva
 */
public class View {

    UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    //input date, custumer name, state abr, prodtype, area
    public int displayMenu() {
        io.print("<< -------------->> ");
        io.print("1. Display Orders ");
        io.print("2. Add an Order ");
        io.print("3. Edit an Order ");
        io.print("4. Remove an order ");
        io.print("5. Exit and Quit ");
        io.print("<< -------------->> ");
        int getMenuSelection = io.readInt("Please Select From The Above Choices", 1, 5);

        
        return getMenuSelection;
    }

    public void displayExitBanner() {
        io.print("<< EXIT >>");
    }

    public void displayUnknownCommandBanner() {
        io.print("<< UNKNOWN COMMAND >>");
        displayContinue();
    }

    public LocalDate editLocalDate() {

        return io.readDate("Please enter date (MM-DD-YYYY)",
                 LocalDate.of(2000, 1, 31), LocalDate.of(2100, 1, 31));
    }
    public String editCustomerName() {
        return io.readString("Please enter customer's name");
        
    }
    public String editStateAbbrv() {
        return io.readString("Please enter your state in abbreviation" , 2);
    }
    public String editProductType () {
        return io.readString("Please enter your product type" , 15);
    }
    public BigDecimal editArea() {
        return io.readBigDecimal("Please enter in the area of your product",
                2, BigDecimal.ZERO);
        
    }
    
    public void displayOrdersByDate(Response<List<Order>> orders){
        for (Order order : orders.getData()) {
            io.print(order.getOrderNumber() + " , " + order.getCustomerName()
                    + " , " + io.formatCurrency(order.getTotal()));
        }
        
    }
    
    public Order addOrder() {
        Order order = new Order();
        order.setDate(editLocalDate());
        order.setCustomerName(editCustomerName());
        order.setAreaOfProduct(editArea());
        order.getProductInfo().setProductType(editProductType());
//        
//        Product product = new Product();
//        product.setProductType(editProductType());
        
//        StateTax stateTax = new StateTax();
//        stateTax.setState(editStateAbbrv());
        order.getTaxInfo().setState(editStateAbbrv());
        
//        order.setProductInfo(product);
//        order.setTaxInfo(stateTax);
        return order;
    }
    public void displaySingleOrder(Order order) {
        io.print("Date: " + order.getDate());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getTaxInfo().getState());
        io.print("Tax Rate: " + order.getTaxInfo().getTaxRate() + "%");
        io.print("Product Type: " + order.getProductInfo().getProductType());
        io.print("Material cost per Sq Ft: $" + 
                order.getProductInfo().getMaterialCostPerSqFoot());
        io.print("Labor cost per Sq Ft: $" + 
                order.getProductInfo().getLaborCostPerSqFoot());
        io.print("Total cost of Material: $" + 
                io.formatCurrency(order.getTotalMaterialCost()));
        io.print("Total cost of Labor: $" + 
                io.formatCurrency(order.getTotalLaborCost()));
        io.print("Area of product: " + order.getAreaOfProduct());
        io.print("Total Tax: $" + io.formatCurrency(order.getTotalTax()));
        io.print("<< TOTAL COST >> \n" +
                "$" + io.formatCurrency(order.getTotal()));
        
    }
    public String saveData(){
        return io.readString("Save this order? \n (Y/N)", 1);
    }
    
    
    public void addOrderComplete(Response<Order> order) {
        if (order.isSuccess()) {
            io.print("Order was successfully completed!");
        } else {
            io.print("Order was not saved");
            displayContinue();
        }
    }

    private void displayContinue() {
        io.readString("Please hit enter to continue");
    }
    
    public int getOrderNumber() {
        return io.readInt("Please enter an order number.");
        
    }
    
    public LocalDate getOrderByDate() {
        return io.readDate("Please enter date (MM-DD-YYYY)", LocalDate.MIN, LocalDate.MAX);
    }

    public Order editOrder(Order order) {
        
        Order editOrder = new Order();
        
        io.print("Edit Your Order\n");
        
        //io.print("Customer Name: " + order.getCustomerName());
        editOrder.setCustomerName(editCustomerName());
        //io.print("State : " + order.getTaxInfo().getState());
        editOrder.getTaxInfo().setState(editStateAbbrv());
        //io.print("Product Type : " + order.getProductInfo().getProductType());
        editOrder.getProductInfo().setProductType(editProductType());
        //io.print("Area of Product : " +order.getAreaOfProduct() + " Sq Ft");
        editOrder.setAreaOfProduct(editArea());
        
        return editOrder;
    }
    
    public void displaySuccessfulEdit(Response<Order> order)
    {
        if(order.isSuccess()){
            io.print("Order successfully edited.");
        } else{
            io.print("Order unsuccessfully edited.");
        }
    }
    
    public String removeOrder(LocalDate date) {
        
        return io.readString
        ("Would you like to remove this order? \n (Y/N)" , 1);
                
    }
    public void displayRemoveOrderCompleted(Response<Order> order) {
        if (order.isSuccess()) {
            
            io.print("Order was successfully removed!");
            
        }else {
            io.print("Order was not successfully removed.");
        }
        
    }
    public void displayErrorMessage(String error){
        io.print("*Error*");
        io.print(error);
        displayContinue();
    }
    
    
}
