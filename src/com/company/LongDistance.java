package com.company;

public class LongDistance extends Connection {
    LongDistance(Customer a, Customer b) {
        super(a, b);
        System.out.println("[new long distance connection from " + a + " to " + b + "]");
    }
}
