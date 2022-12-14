package com.company;

import java.util.Vector;
import java.util.Enumeration;

/**
 * A call supports the process of a customer trying to
 * connect to others.
 */
public class Call {

    //private Customer caller, receiver;
    protected Vector<Connection> connections = new Vector<>();

    /**
     * Create a new call connecting caller to receiver
     * with a new connection. This should really only be
     * called by Customer.call(...)
     */
    public Call(Customer caller, Customer receiver) {
        // this.caller = caller;
        // this.receiver = receiver;
        Connection c;
        if (receiver.localTo(caller)) {
            c = new Local(caller, receiver);
        } else {
            c = new LongDistance(caller, receiver);
        }
        connections.addElement(c);
    }

    /**
     * picking up a call completes the current connection
     * (this means that you shouldn't merge calls until
     * they are completed)
     */
    public void pickup() {
        Connection connection = connections.lastElement();
        connection.complete();
    }


    /**
     * Is the call in a connected state?
     */
    public boolean isConnected(){
        return (connections.lastElement()).getState() == Connection.COMPLETE;
    }

    /**
     * hanging up a call drops the connection
     */
    public void hangup(Customer c) {
        for(Enumeration<Connection> e = connections.elements(); e.hasMoreElements();) {
            (e.nextElement()).drop();
        }
    }

    /**
     * is Customer c one of the customers in this call?
     */
    public boolean includes(Customer c){
        boolean result = false;
        for(Enumeration<Connection> e = connections.elements(); e.hasMoreElements();) {
            result = result || (e.nextElement()).connects(c);
        }
        return result;
    }

    /**
     * Merge all connections from call 'other' into 'this'
     */
    public void merge(Call other){
        for(Enumeration<Connection> e = other.connections.elements(); e.hasMoreElements();){
            Connection conn = e.nextElement();
            other.connections.removeElement(conn);
            connections.addElement(conn);
        }
    }
}
