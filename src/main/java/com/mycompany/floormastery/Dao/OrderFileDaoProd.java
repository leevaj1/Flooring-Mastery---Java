/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;

/**
 *
 * @author leeva
 */
public class OrderFileDaoProd implements OrderDao {

//    Map<Integer, Order> orderMap = new HashMap<>();
    String folder;

    public static String DELIMETER = ",";

    public OrderFileDaoProd(String folder) {

        this.folder = folder;

    }

    private void writeOrder(LocalDate date, List<Order> orders) throws OrderPersistenceException, IOException {
        PrintWriter out;

        try {
            //System.out.println(convertDateToPath(date));

            //out = new PrintWriter(new FileWriter("OrdersNumber.txt"));
            out = new PrintWriter(new FileWriter(convertDateToPath(date)));
        } catch (FileNotFoundException e) {

            throw new OrderPersistenceException("File could not be written");

            //TODO - create new file
        } catch (IOException e) {
            throw new OrderPersistenceException("File could not be written");
        }

        for (Order currentOrderList : orders) {
            out.println(currentOrderList.getOrderNumber() + DELIMETER
                    + currentOrderList.getCustomerName() + DELIMETER
                    + currentOrderList.getTaxInfo().getState() + DELIMETER
                    + currentOrderList.getTaxInfo().getTaxRate() + DELIMETER
                    + currentOrderList.getProductInfo().getProductType() + DELIMETER
                    + currentOrderList.getAreaOfProduct() + DELIMETER
                    + currentOrderList.getTotalMaterialCost() + DELIMETER
                    + currentOrderList.getTotalLaborCost() + DELIMETER
                    + currentOrderList.getMaterialCost() + DELIMETER
                    + currentOrderList.getLaborCost() + DELIMETER
                    + currentOrderList.getTotalTax() + DELIMETER
                    + currentOrderList.getTotal());

            out.flush();
        }

        out.close();
    }

    private List<Order> loadOrder(LocalDate date) throws OrderPersistenceException {

        String path = convertDateToPath(date);

        List<Order> orders = new ArrayList<>();

        Scanner scanner = null;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException ex) {
            throw new OrderPersistenceException("");
        }
        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMETER);
            Order currentOrder = new Order(Integer.parseInt(currentTokens[0]));
            currentOrder.setCustomerName(currentTokens[1]);
            currentOrder.getTaxInfo().setState(currentTokens[2]);
            currentOrder.getTaxInfo().setTaxRate(new BigDecimal(currentTokens[3]));
            currentOrder.getProductInfo().setProductType(currentTokens[4]);
            currentOrder.setAreaOfProduct(new BigDecimal(currentTokens[5]));
            currentOrder.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal(currentTokens[6]));
            currentOrder.getProductInfo().setLaborCostPerSqFoot(new BigDecimal(currentTokens[7]));
//            currentOrder.getProductInfo().setMaterialCost(new BigDecimal(currentTokens[8]));
//            currentOrder.getProductInfo().setLaborCost(new BigDecimal(currentTokens[9]));
            currentOrder.setDate(date);
            orders.add(currentOrder);

        }
        scanner.close();

        return orders;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws OrderPersistenceException {

        return loadOrder(date);

    }

    @Override
    public Order addOrder(Order toAdd) throws OrderPersistenceException {

        try {
            List<Order> orders = getOrdersForDate(toAdd.getDate());
            int orderNum = computeNewOrderNum(orders);

            toAdd.setOrderNumber(orderNum);
            orders.add(toAdd);

            writeOrder(toAdd.getDate(), orders);

        } catch (IOException e) {
            throw new OrderPersistenceException("Could not be added");
        }
        return toAdd;
    }

    @Override
    public Order editOrder(Order newData) throws OrderPersistenceException {

//        try {
        removeOrder(newData.getDate(), newData.getOrderNumber());
        addOrder(newData);

//            newData.setOrderNumber(orderNum);
//            newData.setDate(mapOrder.getDate());
//            orderMap.put(newData.getOrderNumber(), newData);
//            List<Order> order = getOrdersForDate(newData.getDate());
//
//            writeOrder(newData.getDate(), order);
        return newData;
//
//        } catch (IOException e) {
//            throw new OrderPersistenceException(e.getMessage());
//        } 

    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        try {

            List<Order> ordersList = getOrdersForDate(date);
            Order removedOrder = null;
            for (Order order : ordersList) {
                if (order.getOrderNumber() == orderNumber) {
                    removedOrder = order;

                }
            }

            ordersList.remove(removedOrder);

            writeOrder(date, ordersList);

            return removedOrder;

        } catch (IOException e) {
            throw new OrderPersistenceException("Order could not be removed");
        }

    }

    private String convertDateToPath(LocalDate date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

        String fileName = "Orders_" + date.format(formatter) + ".txt";

        Path generatedPath = Paths.get(folder, fileName);

        return generatedPath.toString();

    }

//    @Override
//    public List<Order> getAllOrders() throws OrderPersistenceException {
//        
//        List<Order> allOrders = new ArrayList<>();
//        try{
//            Scanner fileReader = new Scanner(new FileReader(Path));
//            
//            while(fileReader.hasNextLine()) {
//                
//            }
//        }
//        
//    private void writeFile() throws OrderPersistenceException {
//        PrintWriter out;
//        try {
//            out = new PrintWriter(new FileWriter(ORDERNUM_FILE));
//        } catch (IOException e) {
//            throw new OrderPersistenceException("Can not write to File");
//        }
//        out.print(orderNum + 1);
//        out.close();
//
//    }
//
//    private void loadFile() throws OrderPersistenceException {
//
//        Scanner sc;
//        try {
//            sc = new Scanner(new BufferedReader(new FileReader(ORDERNUM_FILE)));
//        } catch (IOException e) {
//            throw new OrderPersistenceException("Can not load file");
//
//        }
//        orderNum = Integer.parseInt(sc.nextLine());
//        sc.close();
//    }
    private int computeNewOrderNum(List<Order> orders) {
        
        int numOrder = 0;

        for (Order toCheck : orders) {

            if (toCheck.getOrderNumber() > numOrder) {

                numOrder = toCheck.getOrderNumber();

            }

        }
        numOrder++;
        
        return numOrder;

    }

}
