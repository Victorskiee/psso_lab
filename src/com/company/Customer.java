package com.company;

import java.util.Vector;

/**
 * Customers have a unique id (name in this case for didactic purposes,
 * but it could be telephone number) and area code.
 * They also have protocol for managing calls: call, pickup, etc.
 */
public class Customer {

    private final String name;
    private final int areaCode;
    private final Vector<Call> calls = new Vector<>();

    /**
     * unregister a call
     */
    protected void removeCall(Call c){
        calls.removeElement(c);
    }

    /**
     * register a call
     */
    protected void addCall(Call c){
        calls.addElement(c);
    }

    /**
     * Make a new customer with given name
     */
    public Customer(String name, int areaCode) {
        this.name = name;
        this.areaCode = areaCode;
    }

    /**
     * String rendition of customer
     */
    public String toString() {
        return name + "(" + areaCode + ")";
    }

    /**
     * what area is the customer in?
     */
    public int getAreaCode(){
        return areaCode;
    }

    /**
     * Is the other customer in the same area?
     */
    public boolean localTo(Customer other){
        return areaCode == other.areaCode;
    }

    /**
     * Make a new call to receiver
     */
    public Call call(Customer receiver) {
        Call call = new Call(this, receiver);
        addCall(call);
        return call;
    }

    /**
     * pick up a call
     */
    public void pickup(Call call) {
        call.pickup();
        addCall(call);
    }

    /**
     * hang up a call
     */
    public void hangup(Call call) {
        call.hangup(this);
        removeCall(call);
    }

    /**
     * Merge a pair of calls -- conference them
     * PRE: call1.includes(this)
     *      call2.includes(this)
     *      call1.connected()
     *      call2.connected()
     * POST: call1 includes all customers connected by call1@pre and call2@pre
     */
    public void merge(Call call1, Call call2){
        call1.merge(call2);
        removeCall(call2);
    }
}
