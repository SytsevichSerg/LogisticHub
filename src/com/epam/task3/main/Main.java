/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.task3.main;

import com.epam.task3.creator.LorryCreator;
import com.epam.task3.entity.Lorry;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) {
        LorryCreator creator = LorryCreator.getInstance();
        List<Lorry> lorries = creator.createLorriesList();

        
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (Lorry lorry: lorries) {
            executorService.execute(lorry);
        }
        executorService.shutdown();
    }
        
       
}
