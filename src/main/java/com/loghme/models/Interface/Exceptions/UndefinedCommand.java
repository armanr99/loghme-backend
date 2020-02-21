package com.loghme.models.Interface.Exceptions;

public class UndefinedCommand extends Exception {
    private String command;

    public UndefinedCommand(String command) {
        this.command = command;
    }

    public String toString() {
        return String.format("Command %s is not defined", command);
    }
}
