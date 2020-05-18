///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.floormastery.Dao;
//
//import com.mycompany.floormastery.Dto.Order;
//import com.mycompany.floormastery.Dto.Product;
//import com.mycompany.floormastery.Dto.StateTax;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
///**
// *
// * @author leeva
// */
//public class OrderFileDaoTraining implements OrderDao {
//
//
//    Map<Integer, Order> orderMap = new HashMap<>();
//    
//    int orderNum;
//    
//    public static String ORDERNUM_FILE = "orderNumber.txt";
//    public static String DELIMETER = ",";
//    
//    String folder;
//
//    public OrderFileDaoTraining(String folder) {
//
//        this.folder = folder;
//
//    }
//    
//        private void writeOrder(LocalDate date) throws OrderPersistenceException, IOException {
//        PrintWriter out;
//
//        try {
//
//            out = new PrintWriter(new FileWriter(convertDateToPath(date)));
//
//        } catch (IOException e) {
//            throw new OrderPersistenceException("file");
//        }
//
//        List<Order> listDate = new ArrayList();
//        
//        List<Order> orderList = new ArrayList(orderMap.values());
//        
//        for (Order order : orderList) {
//            if (order.getDate().equals(date)) {
//                listDate.add(order);
//
//            }
//
//        }
//
//
//        for (Order currentOrderList : listDate) {
//            out.println(currentOrderList.getOrderNumber() + DELIMETER
//                    + currentOrderList.getCustomerName() + DELIMETER
//                    + currentOrderList.getTaxInfo().getState()+ DELIMETER
//                    + currentOrderList.getTaxInfo().getTaxRate()+ DELIMETER
//                    + currentOrderList.getProductInfo().getProductType()+ DELIMETER
//                    + currentOrderList.getAreaOfProduct()+ DELIMETER
//                    + currentOrderList.getTotalMaterialCost()+ DELIMETER
//                    + currentOrderList.getTotalLaborCost()+ DELIMETER
////                    + currentOrderList.getProductInfo().getMaterialCost()+ DELIMETER
////                    + currentOrderList.getProductInfo().getLaborCost()+ DELIMETER
//                    + currentOrderList.getTotalTax() + DELIMETER
//                    + currentOrderList.getTotal());
//
//
//            out.flush();
//        }
//
//        out.close();
//    }
//            private void loadOrder(LocalDate date) throws OrderPersistenceException {
//
//        for (Order order : orderMap.values()) {
//            if (order.getDate().equals(date)) {
//               return;
//            }
//        }
//      
//        String path = convertDateToPath(date);
//        File filePath = new File(path);
//        if (!filePath.exists()) {
//            return;
//        }
//        
//        Scanner scanner = null;
//
//        try {
//            scanner = new Scanner(new BufferedReader(new FileReader(path)));
//        } catch (FileNotFoundException ex) {
//            throw new OrderPersistenceException("");
//        }
//        String currentLine;
//        String[] currentTokens;
//        while (scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentTokens = currentLine.split(DELIMETER);
//            Order currentOrder = new Order(Integer.parseInt(currentTokens[0]));
//            currentOrder.setCustomerName(currentTokens[1]);
//            currentOrder.getTaxInfo().setState(currentTokens[2]);
//            currentOrder.getTaxInfo().setTaxRate(new BigDecimal(currentTokens[3]));
//            currentOrder.getProductInfo().setProductType(currentTokens[4]);
//            currentOrder.setAreaOfProduct(new BigDecimal(currentTokens[5]));
//            currentOrder.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal(currentTokens[6]));
//            currentOrder.getProductInfo().setLaborCostPerSqFoot(new BigDecimal(currentTokens[7]));
////            currentOrder.getProductInfo().setMaterialCost(new BigDecimal(currentTokens[8]));
////            currentOrder.getProductInfo().setLaborCost(new BigDecimal(currentTokens[9]));
//            currentOrder.setDate(date);
//
//            orderMap.put(currentOrder.getOrderNumber(), currentOrder);
//            
//
//        }
//        scanner.close();
//
//    }
// 
//    
//
//
//    @Override
//    public List<Order> getOrdersForDate(LocalDate date) {
//
//        List<Order> ordersForDate = new ArrayList();
//
//        try {
//            String filePath = convertDateToPath(date);
//
//            Scanner sc = new Scanner(new BufferedReader(
//                    new FileReader(filePath)));
//
//            String line = sc.nextLine();
//
//            while (sc.hasNextLine()) {
//
//                line = sc.nextLine();
//
//                String[] cells = line.split(",");
//
//                Order toBuild = new Order();
//
//                toBuild.setDate(date);
//
//                toBuild.setOrderNumber(Integer.parseInt(cells[0]));
//                toBuild.setOrderNumber(Integer.parseInt(cells[1]));
//
//                StateTax taxInfo = new StateTax();
//                taxInfo.setState(cells[2]);
//                taxInfo.setTaxRate(new BigDecimal(cells[3]));
//                toBuild.setTaxInfo(taxInfo);
//
//                Product productInfo = new Product();
//                productInfo.setProductType(cells[4]);
//                productInfo.setMaterialCostPerSqFoot(new BigDecimal(cells[6]));
//                productInfo.setLaborCostPerSqFoot(new BigDecimal(cells[7]));
//
//                toBuild.setAreaOfProduct(new BigDecimal(cells[5]));
//
//                ordersForDate.add(toBuild);
//            }
//
//        } catch (FileNotFoundException ex) {
//            
//
//        }
//
//        return ordersForDate;
//
//    }
//
//    @Override
//    public Order addOrder(Order toAdd) throws OrderPersistenceException {
//        if (toAdd == null) {
//            throw new OrderPersistenceException("Tried to add Order");
//            
//            }
//        orderMap.put(orderNum, toAdd);
//        writeFile();
//        orderNum++;
//        
//        return toAdd;
//    }
//
//    @Override
//    public Order editOrder(Order newData, int orderNum)throws OrderPersistenceException {
//               
//        
//        if (newData == null){
//            throw new OrderPersistenceException(" Could not be edited ");
//        }
//        
//        Order mapOrder = orderMap.get(newData.getOrderNumber());
//       
//        orderMap.put(mapOrder.getOrderNumber(), newData);
//        
//        writeFile();
//        return mapOrder;
//    }
//
//    @Override
//    public Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException{
//
//        loadFile();
//        if (orderMap.get(orderNumber) == null) {
//            throw new OrderPersistenceException("Order could not be remove");
//        }
//        Order removedOrder = orderMap.remove(orderNum);
//        
//        writeFile();
//        
//        return removedOrder;
//    }
//
//    private String convertDateToPath(LocalDate date) {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
//
//        String fileName = "Orders_ " + date.format(formatter) + ".txt";
//
//        Path generatedPath = (Path) Paths.get(folder, fileName);
//
//        return generatedPath.toString();
//
//    }
//
////    @Override
////    public List<Order> getAllOrders() throws OrderPersistenceException {
////        
////        List<Order> allOrders = new ArrayList<>();
////        try{
////            Scanner fileReader = new Scanner(new FileReader(Path));
////            
////            while(fileReader.hasNextLine()) {
////                
////            }
////        }
////        
//
//    private void writeFile() throws OrderPersistenceException {
//                PrintWriter out;
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
//    
//     
//    
//}
