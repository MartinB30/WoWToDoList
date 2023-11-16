package io.everyonecodes.WoWToDoList.customExceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
