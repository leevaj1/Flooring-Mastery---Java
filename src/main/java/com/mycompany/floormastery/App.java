/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery;

import com.mycompany.floormastery.Controller.Controller;
import com.mycompany.floormastery.Dao.OrderAuditDao;
import com.mycompany.floormastery.Dao.OrderAuditDaoFile;
import com.mycompany.floormastery.Dao.OrderDao;
import com.mycompany.floormastery.Dao.OrderFileDaoProd;
import com.mycompany.floormastery.Dao.ProductDao;
import com.mycompany.floormastery.Dao.ProductDaoFile;
import com.mycompany.floormastery.Dao.StateTaxDao;
import com.mycompany.floormastery.Dao.StateTaxDaoFile;
import com.mycompany.floormastery.Service.OrderService;
import com.mycompany.floormastery.UserIO.UserIO;
import com.mycompany.floormastery.UserIO.UserIOConsole;
import com.mycompany.floormastery.UserIO.View;

/**
 *
 * @author leeva
 */
public class App {
    public static void main(String[] args) {
        
        UserIO myIO = new UserIOConsole();
        
        View myView = new View(myIO);
        
        OrderDao myDao = new OrderFileDaoProd("Data");
        
        ProductDao prodDao = new ProductDaoFile();
        
        StateTaxDao stateDao = new StateTaxDaoFile();
        
        OrderAuditDao myAudit = new OrderAuditDaoFile();
        
        OrderService service = new OrderService(myDao, myAudit, prodDao,stateDao);
        
        Controller controller = new Controller(service, myView);
        
        controller.run();
        
        
        
    }
}
