package com.company;

public abstract class Connection {

    public static final int PENDING = 0;
    public static final int COMPLETE = 1;
    public static final int DROPPED = 2;

    Customer caller, receiver;
    private int state = PENDING;

    /**
     * Create a new Connection between a and b
     */
    Connection(Customer a, Customer b) {
        this.caller = a;
        this.receiver = b;
    }

    /**
     * what is the state of the connection?
     */
    public int getState(){
        return state;
    }

    /**
     * get the customer who initiated this connection
     */
    public Customer getCaller() { return caller; }

    /**
     * get the customer who received this connection
     */
    public Customer getReceiver() { return receiver; }

    /**
     * Called when a call is picked up.  This means the b side has picked up
     * and the connection should now complete itself and start passing data.
     */
    void complete() {
        state = COMPLETE;
        System.out.println("connection from "+ caller+ " to "+ receiver +" completed");
    }

    /**
     * Called when the connection is dropped from a call.  Is intended to
     * free up any resources the connection was consuming.
     */
    void drop() {
        state = DROPPED;
        System.out.println("connection from "+ caller+ " to "+ receiver +" dropped");
    }

    /**
     * Is customer c connected by this connection?
     */
    public boolean connects(Customer c){
        return (caller == c || receiver == c);
    }

}
