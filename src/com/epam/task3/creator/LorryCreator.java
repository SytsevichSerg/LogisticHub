
package com.epam.task3.creator;

import com.epam.task3.entity.Lorry;
//import com.epam.task3.entity.Terminal;
import com.epam.task3.property.LogisticHubProperties;
import com.epam.task3.util.RandomValuesGenerator;
import java.util.LinkedList;
import java.util.List;
//import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LorryCreator {
     private static final Logger LOG = LogManager.getLogger();
     
     private static LorryCreator instance;
     private final int lorriesQuantity;
     
     public static LorryCreator getInstance(){
         if(instance == null) {
             instance = new LorryCreator();
         }
         return instance;
     }
     
     private LorryCreator() {
         LogisticHubProperties properties = LogisticHubProperties.getInstance();
         lorriesQuantity = properties.getLorryQuantity();
         
     }
     
     public List<Lorry> createLorriesList(){
         List<Lorry> lorries = new LinkedList<>();
         for (int i=0; i< lorriesQuantity; i++) {
             Lorry lorry = createLorry();
             lorries.add(lorry);
         }
         LOG.info("Created " + lorriesQuantity + "lorries");
         return lorries;    
     }
     
     private Lorry createLorry(){
        boolean isPerischable = RandomValuesGenerator.getInstance().generateRandomBoolean();
        boolean toUpload= RandomValuesGenerator.getInstance().generateRandomBoolean();
        int loadCapacity = 25000;
        int cargoWeight = (int) RandomValuesGenerator.getInstance().generateRandomIntRange(0, loadCapacity);
         
        return new Lorry(isPerischable,toUpload, loadCapacity, cargoWeight);     
     }
     
}
