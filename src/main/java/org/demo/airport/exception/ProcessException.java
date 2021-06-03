package org.demo.airport.exception;

public class ProcessException extends RuntimeException {

    public ProcessException(String exceptionMessage){
        super(exceptionMessage);
    }
}
