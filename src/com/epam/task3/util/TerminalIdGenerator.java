
package com.epam.task3.util;


public class TerminalIdGenerator {
    
    public static int currentTerminalId = 0;
    
    private TerminalIdGenerator() {
        
    }
    
    public static int generate() {
        return ++currentTerminalId;
    }
    
}
