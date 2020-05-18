/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Product;

/**
 *
 * @author leeva
 */
public interface ProductDao {
    Product getProduct(String productType) throws OrderPersistenceException;
}
