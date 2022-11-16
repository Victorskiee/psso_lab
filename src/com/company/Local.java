package com.company;

public class Local extends Connection {
    Local(Customer a, Customer b) {
        super(a, b);
        System.out.println("[new local connection from " + a + " to " + b + "]");
    }
}
