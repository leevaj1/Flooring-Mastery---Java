/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author leeva
 */
public class ProductDaoFile implements ProductDao {


        
    private static final String PRODUCTS_FILE = "Products.txt";
    private static final String DELIMITER = ",";

    @Override
    public Product getProduct(String productType) throws OrderPersistenceException {
        List<Product> products = loadProducts();
        if (products == null) {
            return null;
        } else {
            Product getProduct = products.stream()
                    .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                    .findFirst().orElse(null);
            return getProduct;
        }
    }

    private List<Product> loadProducts() throws OrderPersistenceException {
        Scanner scanner;
        List<Product> products = new ArrayList<>();

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException(
                    "Could not load products.", e);
        }

        String currentLine;
        String[] currentTokens;
        scanner.nextLine();      
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 3) {
                Product currentProduct = new Product();
                currentProduct.setProductType(currentTokens[0]);
                currentProduct.setMaterialCostPerSqFoot(new BigDecimal(currentTokens[1]));
                currentProduct.setLaborCostPerSqFoot(new BigDecimal(currentTokens[2]));
                
                products.add(currentProduct);
            } else {
               
            }
        }
        scanner.close();

        if (!products.isEmpty()) {
            return products;
        } else {
            return null;
        }
    }
}


