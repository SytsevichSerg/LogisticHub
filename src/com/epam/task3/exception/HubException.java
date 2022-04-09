package com.epam.task3.exception;

public class HubException extends Exception {
    public HubException() {
        super();
    }
    
    public HubException(String message, Throwable cause) {
        super(message, cause);
    }

    public HubException(String message) {
        super(message);
    }
    
    public HubException(Throwable cause) {
        super(cause);
    }


}
