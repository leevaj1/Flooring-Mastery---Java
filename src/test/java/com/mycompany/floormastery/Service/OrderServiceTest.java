/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Service;

import com.mycompany.floormastery.Dao.OrderAuditDao;
import com.mycompany.floormastery.Dao.OrderAuditDaoStub;
import com.mycompany.floormastery.Dao.OrderDao;
import com.mycompany.floormastery.Dao.OrderInMemory;
import com.mycompany.floormastery.Dao.ProductDao;
import com.mycompany.floormastery.Dao.ProductDaoFile;
import com.mycompany.floormastery.Dao.StateTaxDao;
import com.mycompany.floormastery.Dao.StateTaxDaoFile;
import com.mycompany.floormastery.Dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author leeva
 */
public class OrderServiceTest {

    OrderService toTest;

    public OrderServiceTest() {
        OrderDao dao = new OrderInMemory();
        OrderAuditDao auditDao = new OrderAuditDaoStub();
        ProductDao prodDao = new ProductDaoFile();
        StateTaxDao taxDao = new StateTaxDaoFile();

        toTest = new OrderService(dao, auditDao, prodDao, taxDao);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
//        private final OrderDao dao;
//    private final OrderAuditDao audit;
//    private final ProductDao productDao;
//    private final StateTaxDao stateTaxDao;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrderService.
     */
    @Test
    public void testAddOrder() {
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));
        Order toAdd = new Order();

        toAdd.setDate(date);
        toAdd.setCustomerName("Walmart");
        toAdd.getTaxInfo().setState("OH");
        toAdd.getTaxInfo().setTaxRate(new BigDecimal("6.00"));
        toAdd.getProductInfo().setProductType("Tile");
        toAdd.setAreaOfProduct(new BigDecimal("35"));
        toAdd.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("1.75"));
        toAdd.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("1.00"));

        Response<Order> response = toTest.addOrder(toAdd);

        Assert.assertTrue(response.isSuccess());

        Assert.assertEquals("Walmart", response.getData().getCustomerName());
        Assert.assertEquals("OH", response.getData().getTaxInfo().getState());
        Assert.assertEquals(new BigDecimal("6.00"), response.getData().getTaxInfo().getTaxRate());
        Assert.assertEquals("Tile", response.getData().getProductInfo().getProductType());
        Assert.assertEquals(new BigDecimal("35"), response.getData().getAreaOfProduct());
        Assert.assertEquals(new BigDecimal("1.75"), response.getData().getProductInfo().getMaterialCostPerSqFoot());
        Assert.assertEquals(new BigDecimal("1.00"), response.getData().getProductInfo().getLaborCostPerSqFoot());
    }

    /**
     * Test of editOrder method, of class OrderService.
     */
    @Test
    public void testEditOrder() {
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));

        Order toAdd = new Order();

        toAdd.setDate(date);
        toAdd.setCustomerName("Walmart");
        toAdd.getTaxInfo().setState("OH");
        toAdd.getTaxInfo().setTaxRate(new BigDecimal("6.00"));
        toAdd.getProductInfo().setProductType("Tile");
        toAdd.setAreaOfProduct(new BigDecimal("20"));
        toAdd.getProductInfo().setMaterialCostPerSqFoot(new BigDecimal("3.00"));
        toAdd.getProductInfo().setLaborCostPerSqFoot(new BigDecimal("2.00"));

        Response<Order> response = toTest.editOrder(toAdd);

        Assert.assertTrue(response.isSuccess());

        Assert.assertEquals("Walmart", response.getData().getCustomerName());
        Assert.assertEquals("OH", response.getData().getTaxInfo().getState());
        Assert.assertEquals(new BigDecimal("6.00"), response.getData().getTaxInfo().getTaxRate());
        Assert.assertEquals("Tile", response.getData().getProductInfo().getProductType());
        Assert.assertEquals(new BigDecimal("20"), response.getData().getAreaOfProduct());
        Assert.assertEquals(new BigDecimal("3.00"), response.getData().getProductInfo().getMaterialCostPerSqFoot());
        Assert.assertEquals(new BigDecimal("2.00"), response.getData().getProductInfo().getLaborCostPerSqFoot());

    }

    /**
     * Test of removeOrder method, of class OrderService.
     */
    @Test
    public void testRemoveOrder() {
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));

        Response<Order> order = toTest.removeOrder(date, 1);

        Assert.assertTrue(order.isSuccess());

        Assert.assertEquals("Wise", order.getData().getCustomerName());
        Assert.assertEquals("OH", order.getData().getTaxInfo().getState());
        Assert.assertEquals(new BigDecimal("1.00"), order.getData().getTaxInfo().getTaxRate());
        Assert.assertEquals("Tile", order.getData().getProductInfo().getProductType());
        Assert.assertEquals(new BigDecimal("20"), order.getData().getAreaOfProduct());
        Assert.assertEquals(new BigDecimal("1.00"), order.getData().getProductInfo().getMaterialCostPerSqFoot());
        Assert.assertEquals(new BigDecimal("1.00"), order.getData().getProductInfo().getLaborCostPerSqFoot());

    }

    /**
     * Test of getOrdersForDate method, of class OrderService.
     */
    @Test
    public void testGetOrdersForDate() {
        LocalDate date = LocalDate.parse("06012013",
                DateTimeFormatter.ofPattern("MMddyyyy"));
        Response<List<Order>> response = toTest.getOrdersForDate(date);

        Assert.assertTrue(response.isSuccess());

        Assert.assertEquals(3, response.getData().size());

    }

}
