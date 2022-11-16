package com.company;

public aspect TimeAspect {

    declare parents: Customer implements ICustomer;
    declare parents: Connection implements IConnection;

    private final int LOCAL_RATE = 3;
    private final int LONG_DISTANCE_RATE = 10;
    public int ICustomer.totalConnectTime = 0;
    public long ICustomer.totalBillRate = 0;
    public Timer IConnection.timer;

    pointcut creationOfConnection(Connection conn) : initialization(IConnection+.new(..)) && this(conn);

    after(Connection conn) : creationOfConnection(conn) {
        System.out.println("Connection has been created");
        conn.timer = new Timer();
    }

    pointcut startTimer(Connection conn) : execution(* Connection.complete(..)) && this(conn);

    after(Connection conn) : startTimer(conn) {
        System.out.println("Timer has started");
        conn.timer.start();
    }

    pointcut stopTimer(Connection conn) : execution(* Connection.drop(..)) && this(conn);

    after(Connection conn) : stopTimer(conn) {
        System.out.println("Timer has stopped");
        conn.timer.stop();
        System.out.println("Connection time: " + conn.timer.getTime() + " seconds");
        conn.caller.totalConnectTime += conn.timer.getTime();
        conn.receiver.totalConnectTime += conn.timer.getTime();
        System.out.println(conn.caller.toString() + " has been connected (in total) for " + conn.caller.totalConnectTime + " seconds");
        System.out.println(conn.receiver.toString() + " has been connected (in total) for " + conn.receiver.totalConnectTime + " seconds");
    }

    pointcut calculateLocalBilling(Local conn) : execution(* Connection.drop(..)) && this(conn);

    after(Local conn) : calculateLocalBilling(conn){
        long durationOfCall = conn.timer.getTime();
        long billRate = durationOfCall * LOCAL_RATE;
        conn.caller.totalBillRate += billRate;
        System.out.println(conn.caller.toString() + " has been charged (for call) by " + billRate + " with local call");
        System.out.println(conn.caller.toString() + " has been charged (in total) by " + conn.caller.totalBillRate);
    }

    pointcut calculateLongDistanceBilling(LongDistance conn) : execution(* Connection.drop(..)) && this(conn);

    after(LongDistance conn) : calculateLongDistanceBilling(conn){
        long durationOfCall = conn.timer.getTime();
        long billRate = durationOfCall * LONG_DISTANCE_RATE;
        conn.caller.totalBillRate += billRate;
        System.out.println(conn.caller.toString() + " has been charged (for call) by " + billRate + " with long-distance call");
        System.out.println(conn.caller.toString() + " has been charged (in total) by " + conn.caller.totalBillRate);
    }
}
