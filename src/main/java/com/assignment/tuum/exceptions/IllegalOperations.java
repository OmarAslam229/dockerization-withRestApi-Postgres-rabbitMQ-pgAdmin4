package com.assignment.tuum.exceptions;

public class IllegalOperations extends RuntimeException{

    public IllegalOperations(String message)
    {
        super(message);
    }

    public IllegalOperations(Exception e)
    {
        super(e);
    }
}
