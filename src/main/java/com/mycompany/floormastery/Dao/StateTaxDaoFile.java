/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.floormastery.Dao;

import com.mycompany.floormastery.Dto.StateTax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.Thread.State;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author leeva
 */
public class StateTaxDaoFile implements StateTaxDao {

    private static final String STATES_FILE = "Taxes.txt";
    private static final String DELIMITER = ",";

    @Override
    public StateTax getStateTax(String stateAbbrv) throws OrderPersistenceException {
        List<StateTax> states = loadStates();
        if (states == null) {
            return null;
        } else {
            StateTax getState = states.stream()
                    .filter(s -> s.getState().equalsIgnoreCase(stateAbbrv))
                    .findFirst().orElse(null);
            return getState;
        }
    }

    private List<StateTax> loadStates() throws OrderPersistenceException {
        Scanner scanner;
        List<StateTax> states = new ArrayList<>();

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(STATES_FILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException(
                    "Could not load state.", e);
        }

        String currentLine;
        String[] currentTokens;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 2) {
                StateTax currentState = new StateTax();
                currentState.setState(currentTokens[0]);
                currentState.setTaxRate(new BigDecimal(currentTokens[1]));

                states.add(currentState);
            } else {

            }
        }
        scanner.close();

        if (!states.isEmpty()) {
            return states;
        } else {
            return null;
        }
    }

}
