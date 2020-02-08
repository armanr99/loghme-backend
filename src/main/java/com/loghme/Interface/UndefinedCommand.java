package com.loghme.Interface;

public class UndefinedCommand extends Exception {
    private String command;

    UndefinedCommand(String command) {
        this.command = command;
    }

    public String toString() {
        return String.format("UndefinedCommand Exception %s command is not defined", command);
    }
}
