
package com.epam.task3.entity;

import com.epam.task3.exception.HubException;
import com.epam.task3.util.LorryIdGenerator;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Lorry extends Thread implements Serializable {
    
    private static final Logger LOG = LogManager.getLogger();
    
    public static final int MAX_LOAD_SPEED_PER_UNIT = 25;
    public static final int MIN_LOAD_SPEED_PER_UNIT = 10;
    public static final int DEFAULT_LOAD_CAPACITY = 25000;
    
    private final int lorryId;
    private final int loadCapacity;
    private State state;
    
    //private TerminalPool<Terminal> pool;
    private boolean isPerischable;
    private boolean toUpload;
    private int cargoWeight;
    
    
    
    //private Termina
    
    public enum State {
        CREATED, WAITING, PROCESSING, COMPLETED
    }
    
    public Lorry(boolean isPerischable, boolean toUpload, int loadCapacity, int cargoWeight)
    {
        lorryId = LorryIdGenerator.generate();
        state = State.CREATED;
        this.loadCapacity = loadCapacity;
        this.cargoWeight = cargoWeight;
        this.isPerischable = isPerischable;
        this.toUpload = toUpload;
        if(isPerischable) {
            this.setPriority(Thread.MAX_PRIORITY);
        } 
        else {
            this.setPriority(Thread.NORM_PRIORITY);
        }
    }
 
    @Override
    public void run() {
        Hub hub = Hub.getInstance();
        Terminal terminal = null;
        try {
            terminal = hub.assignTerminalToLorry(this);
            if(terminal!=null)
            {
                LOG.info("Lorry#" + this.getLorryId() + "occupied the terminal" + terminal.getTerminalId());
                this.setState(State.PROCESSING);
                terminal.using(this.getCargoWeight(), this.isToUpload());
                this.setState(State.COMPLETED);
            }
            
        } catch ( HubException e) {
            LOG.warn("Lorry#" + getLorryId() + " is interrupted\t\t\t" + this, e);
        } finally {
            if (terminal != null) {
                hub.releaseTerminal(terminal);
                LOG.info("Lorry#" + this.getLorryId() + " left the terminal" + terminal.getTerminalId());
            }
        }
        
    }
    

    public int getLorryId() {
        return lorryId;
    }

    /**
     * @return the loadCapacity
     */
    public int getLoadCapacity() {
        return loadCapacity;
    }

    /**
     * @return the isPerischable
     */
    public boolean isIsPerischable() {
        return isPerischable;
    }

    /**
     * @param isPerischable the isPerischable to set
     */
    public void setPerischable(boolean isPerischable) {
        this.isPerischable = isPerischable;
    }

    /**
     * @return the toUpload
     */
    public boolean isToUpload() {
        return toUpload;
    }

    /**
     * @param toUpload the toUpload to set
     */
    public void setUpload(boolean toUpload) {
        this.toUpload = toUpload;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return the cargoWeight
     */
    public int getCargoWeight() {
        return cargoWeight;
    }


    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
    
}
