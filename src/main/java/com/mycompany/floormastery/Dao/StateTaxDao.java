/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.StateTax;

/**
 *
 * @author leeva
 */
public interface StateTaxDao {
        StateTax getStateTax(String stateAbbr) throws OrderPersistenceException;

}
