
package com.epam.task3.property;

import com.epam.task3.exception.HubException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogisticHubProperties {
     private static final Logger LOG = LogManager.getLogger();
     private static LogisticHubProperties instance;
     private static final String PROPERTIES_FILE_NAME = "resources/hub.properties";
     
    private static final int DEFAULT_TERMINAL_CAPACITY = 500000;
    private static final int DEFAULT_TERMINAL_QUANTITY = 10;
    private static final int DEFAULT_LORRY_QUANTITY = 50;
     
     
    private int terminalCapacity;
    private int terminalQuantity;
    private int lorryQuantity;
     
    public static LogisticHubProperties getInstance() {
        if (instance == null) {
            instance = new LogisticHubProperties();
        }
        return instance;
    }
    
    private LogisticHubProperties() {
        try {
            loadFromFile();
            LOG.info("Properties loaded. " + this);
        } catch (IOException | HubException exception) {
            LOG.warn("Properties not loaded! Default values are used. " + this, exception);
            defaultInitialize();
        }
    }

    public int getTerminalCapacity() {
        return terminalCapacity;
    }

    public int getTerminalQuantity() {
        return terminalQuantity;
    }

    public int getLorryQuantity() {
        return lorryQuantity;
    }
    
    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append("{terminalCapacity=").append(terminalCapacity).
                append(", terminalQuantity=").append(terminalQuantity).
                append(", lorryQuantity=").append(lorryQuantity).append('}');
        return builder.toString();
    }
    
    private void defaultInitialize() {
        terminalCapacity = DEFAULT_TERMINAL_CAPACITY;
        terminalQuantity = DEFAULT_TERMINAL_QUANTITY;
        lorryQuantity = DEFAULT_LORRY_QUANTITY;
    }
    
    private void loadFromFile() throws HubException, IOException {
        
        Properties properties = new Properties();
        try { 
            properties.load(new FileReader(PROPERTIES_FILE_NAME));
            lorryQuantity = Integer.parseInt(properties.getProperty("lorryQuantity"));
            terminalCapacity = Integer.parseInt(properties.getProperty("terminalCapacity"));
            terminalQuantity = Integer.parseInt(properties.getProperty("terminalQuantity"));
        }
        catch(IOException e){
            LOG.error("IOException in loadFromFile method");
        }
    }
    
    
}
