package database;

import common.collection.*;

import java.io.Serializable;
import java.util.Stack;

public class LoginObj implements Serializable {
    String message;
    Stack<Vehicle> stack;

    public String getMessage() {
        return message;
    }

    public Stack<Vehicle> getStack() {
        return stack;
    }

    public LoginObj(String message, Stack<Vehicle> stack) {
        this.message = message;
        this.stack = stack;
    }
}
