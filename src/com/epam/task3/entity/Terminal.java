package com.epam.task3.entity;

import com.epam.task3.util.TerminalIdGenerator;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

// в терминале есть максимальная загрузка. 
// в терминале есть текущая загрузка.
// если текущая загрузка = 0; только грузим в терминал
// если текущая загрузка = max_size; только грузим из терминал
public class Terminal implements Serializable {
    
    private final int terminalId;
    private final int terminalCapacity;
    private final AtomicBoolean isBisy;
    private AtomicInteger currentCargoAmount;
    
    
    
    public Terminal(int terminalCapacity) {
        terminalId = TerminalIdGenerator.generate();
        this.terminalCapacity = terminalCapacity;
        isBisy = new AtomicBoolean(false);
        currentCargoAmount = new AtomicInteger(0);
    }


    public long getTerminalId() {
        return terminalId;
    }
    
    public boolean getBisy() {
        return !isBisy.get();
    }

    public void setBisy(boolean bisy){
       isBisy.set(bisy);
    }
    
    public void using(int cargoWeight, boolean toUpload) {
        try {
            if(toUpload)
            {
                currentCargoAmount.getAndAdd(cargoWeight);
            } else {
                int value = currentCargoAmount.get() - cargoWeight;
                currentCargoAmount.set(value);
            }
            TimeUnit.MILLISECONDS.sleep(cargoWeight);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Terminal terminal = (Terminal) o;
        return terminalId == terminal.terminalId && isBisy.get() == terminal.isBisy.get() && currentCargoAmount.get() == terminal.currentCargoAmount.get();
    }

    @Override
    public int hashCode() {
        
        final int prime = 31;
        int result = Integer.hashCode(terminalId);
        result = result*prime + isBisy.hashCode();
        result = result*prime + currentCargoAmount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append("{terminalId=").append(terminalId).
                append("{isBisy=").append(isBisy.toString()).
                append("{currentCargoAmount=").append(currentCargoAmount.toString()).
                append('}');
        return builder.toString();
    }


    public int getCurrentCargoAmount() {
        return currentCargoAmount.get();
    }

    public void setCurrentCargoAmount(int value) {
        this.currentCargoAmount.set(value);
    }

    public int getTerminalCapacity() {
        return terminalCapacity;
    }
    
    public boolean checkUploadPossibility(int value){
        return  currentCargoAmount.get() + value <= terminalCapacity;
    }
    
    public boolean checkCargoAmount(int value){
        return  currentCargoAmount.get() >= value;
    }
}
