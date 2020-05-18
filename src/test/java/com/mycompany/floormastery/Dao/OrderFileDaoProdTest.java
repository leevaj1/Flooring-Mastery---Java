/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author leeva
 */
public class OrderFileDaoProdTest {

    OrderDao toTest = new OrderFileDaoProd("Data\\Test");

    public OrderFileDaoProdTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {

        File testFolder = new File("Data\\Test");
        File seedFolder = new File("Data\\Seed");

        for (File testFile : testFolder.listFiles()) {
            testFile.delete();
        }

        for (File seedFile : seedFolder.listFiles()) {
            Files.copy(seedFile.toPath(), Paths.get("Data\\" + testFolder.getName(),
                    seedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            //Files.copy(seedFile.toPath(), testFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);

        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getOrdersForDate method, of class OrderFileDaoProd.
     */
    @Test
    public void testGetOrdersForDate() throws Exception {
        try{ 
            OrderDao dao = new OrderFileDaoProd("Data\\Seed");
        
        
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));
        List<Order> toCheck = dao.getOrdersForDate(date);
        Assert.assertEquals(1 ,toCheck.size());
        }catch (OrderPersistenceException e) {
            Assert.fail();
        }

    }

    /**
     * Test of addOrder method, of class OrderFileDaoProd.
     */
    @Test
    public void testAddOrder() throws Exception {
        try {
            LocalDate date = LocalDate.parse("06012013",
                    DateTimeFormatter.ofPattern("MMddyyyy"));
            List<Order> initialOrders = toTest.getOrdersForDate(date);

            Order order = new Order();
            order.setDate(date);
            order.setCustomerName("Walmart");
            order.getTaxInfo().setState("Oh");
            order.getTaxInfo().setTaxRate(new BigDecimal("6.00"));
            order.getProductInfo().setProductType("Tile");
            order.setAreaOfProduct(new BigDecimal("35"));
            order.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.75"));
            order.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

            order = toTest.addOrder(order);

            List<Order> fromDao = toTest.getOrdersForDate(order.getDate());

            //Testing to see if a row was added to the test file.
            assertEquals(1, (fromDao.size() - initialOrders.size()));

        } catch (OrderPersistenceException e) {
            Assert.fail();
        }
    }

    /**
     * Test of editOrder method, of class OrderFileDaoProd.
     */
    @Test
    public void testEditOrder() throws Exception {
        //OrderDao dao = new OrderFileDaoProd("Data//Seed");
        try {

            LocalDate date = LocalDate.parse("06012013",
                    DateTimeFormatter.ofPattern("MMddyyyy"));
            List<Order> toEdit = toTest.getOrdersForDate(date);

            int orderNum = toEdit.size() + 1;

            Order order = new Order();
            order.setOrderNumber(orderNum);
            order.setDate(date);
            order.setCustomerName("Walmart");
            order.getTaxInfo().setState("Oh");
            order.getTaxInfo().setTaxRate(new BigDecimal("6.00"));
            order.getProductInfo().setProductType("Tile");
            order.setAreaOfProduct(new BigDecimal("35"));
            order.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.75"));
            order.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

            order = toTest.addOrder(order);

            order.setCustomerName("Target");
            order.getTaxInfo().setState("OH");
            order.getTaxInfo().setTaxRate(new BigDecimal("3.00"));
            order.getProductInfo().setProductType("Wood");
            order.setAreaOfProduct(new BigDecimal("20"));
            order.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("0.75"));
            order.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("2.00"));

            order = toTest.editOrder(order);

            Assert.assertEquals("Target", order.getCustomerName());
            Assert.assertEquals("OH", order.getTaxInfo().getState());
            Assert.assertEquals(new BigDecimal("3.00"), order.getTaxInfo().getTaxRate());
            Assert.assertEquals("Wood", order.getProductInfo().getProductType());
            Assert.assertEquals(new BigDecimal("20"), order.getAreaOfProduct());
            Assert.assertEquals(new BigDecimal("0.75"), order.getProductInfo().getMaterialCostPerSqFoot());
            Assert.assertEquals(new BigDecimal("2.00"), order.getProductInfo().getLaborCostPerSqFoot());

        } catch (OrderPersistenceException e) {

            Assert.fail();
        }

    }

    /**
     * Test of removeOrder method, of class OrderFileDaoProd.
     */
    @Test
    public void testRemoveOrder() throws Exception {

        //OrderDao dao = new OrderFileDaoProd("Data\\Seed");
        int orderNum = 1;

        try {
            LocalDate date = LocalDate.parse("06012013",
                    DateTimeFormatter.ofPattern("MMddyyyy"));

            Order toRemove = toTest.removeOrder(date, orderNum);
            //OrderNumber,CustomerName,State,
            //TaxRate,ProductType,Area,CostPerSquareFoot,
            //LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
//1,Wise,OH,6.25,Wood,100.00,5.15,4.75,515.00,475.00,61.88,1051.88

            Assert.assertEquals(1, toRemove.getOrderNumber());
            Assert.assertEquals("Wise", toRemove.getCustomerName());
            Assert.assertEquals("OH", toRemove.getTaxInfo().getState());
            Assert.assertEquals("Wood", toRemove.getProductInfo().getProductType());

        } catch (OrderPersistenceException e) {
            Assert.fail();
        }

    }

}
