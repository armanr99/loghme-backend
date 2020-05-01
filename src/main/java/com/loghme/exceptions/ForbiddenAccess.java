package com.loghme.exceptions;

public class ForbiddenAccess extends Throwable {
    public String toString() {
        return "Forbidden access to resources";
    }
}
