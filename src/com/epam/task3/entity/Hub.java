
package com.epam.task3.entity;

import com.epam.task3.exception.HubException;
import com.epam.task3.property.LogisticHubProperties;
import com.epam.task3.util.RandomValuesGenerator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class Hub implements Serializable {
    
    public static Hub instance; 

    private final Semaphore semaphore;
    private final List<Terminal> terminals; 
    
    public static Hub getInstance() {
        if (instance == null) {
            instance = new Hub();
        }
        return instance;
    }
    private Hub(){
        LogisticHubProperties properties = LogisticHubProperties.getInstance();
        semaphore = new Semaphore(properties.getTerminalQuantity(), true);
        terminals = new LinkedList<>();
        
        for (int i=0; i<properties.getTerminalQuantity(); i++){
            int terminalCapacity = (int) RandomValuesGenerator.getInstance().generateRandomIntRange(0, properties.getTerminalCapacity());
            Terminal terminal = new Terminal(terminalCapacity);
            terminal.setCurrentCargoAmount(RandomValuesGenerator.getInstance().generateRandomIntRange(0, terminalCapacity));
            terminals.add(terminal);
        }
            
    }
    public Terminal assignTerminalToLorry(Lorry lorry) throws HubException {
        Terminal terminal;
        lorry.setState(Lorry.State.WAITING);
        try{
                semaphore.acquire();
                terminal = getTerminal(lorry);
                return terminal;
        } catch (InterruptedException e) {
            throw new HubException(e);
        }
    }
    
    public void releaseTerminal(Terminal terminal) {
        terminal.setBisy(false);
        semaphore.release();
    }
    
    private Terminal getTerminal(Lorry lorry){
        Terminal terminal = null;
        if(lorry.isToUpload()){//it is possible to unload to the warehouse the amount of cargo not exceeding the maximum load of the warehouse.
            
            terminal = terminals.stream().filter(Terminal::getBisy)
                    .filter(t -> t.checkUploadPossibility(lorry.getCargoWeight()))
                    .findFirst()
                    .orElse(null);
        } else {
            terminal = terminals.stream().filter(Terminal::getBisy)
                    .filter(t -> t.checkCargoAmount(lorry.getCargoWeight()))
                    .findFirst()
                    .orElse(null);
            
        }
        return terminal; 
    }
}
