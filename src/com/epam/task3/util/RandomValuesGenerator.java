
package com.epam.task3.util;

import java.util.Random;


public class RandomValuesGenerator {
    private static RandomValuesGenerator instance;
    private Random random;
    
    public static RandomValuesGenerator getInstance() {
        if (instance == null) {
            instance = new RandomValuesGenerator();
        }
        return instance;
    }
    
    private RandomValuesGenerator() {
        random = new Random();
    }
    
    public boolean generateRandomBoolean(){
        return random.nextBoolean();
    }
    
    public int generateRandomIntRange(int bottomBound,int upperBound){
        int value = random.nextInt(upperBound -bottomBound) + bottomBound;
        return value;
    }
    
}
