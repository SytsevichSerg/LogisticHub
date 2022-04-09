
package com.epam.task3.util;


public class LorryIdGenerator {
    
    public static int currentLorryId = 0;
    
    private LorryIdGenerator() {
        
    }
    
    public static int generate() {
        return ++currentLorryId;
    }
}
