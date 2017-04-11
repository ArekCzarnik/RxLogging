package com.infinity.fstm;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.functions.Action2;


public class StateMachineTest {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachineTest.class);

    public static enum Event {
        IDLE,
        CONNECT,
        CONNECTED,
        FAILED,
        UNQUARANTINE,
        REMOVE
    }

    public static Action2<SomeContext, State<SomeContext, Event>> log(final String text) {
        return (t1, state) -> LOG.info("" + t1 + ":" + state + ":" + text);
    }

    public static class SomeContext {
        @Override
        public String toString() {
            return "Foo []";
        }
    }

    public static State<SomeContext, Event> IDLE        = new State<>("IDLE");
    public static State<SomeContext, Event> CONNECTING  = new State<>("CONNECTING");
    public static State<SomeContext, Event> CONNECTED   = new State<>("CONNECTED");
    public static State<SomeContext, Event> QUARANTINED = new State<>("QUARANTINED");
    public static State<SomeContext, Event> REMOVED     = new State<>("REMOVED");

    @BeforeClass
    public static void beforeClass() {
        IDLE
                .onEnter(log("enter"))
                .onExit(log("exit"))
                .transition(Event.CONNECT, CONNECTING)
                .transition(Event.REMOVE,  REMOVED);

        CONNECTING
                .onEnter(log("enter"))
                .onExit(log("exit"))
                .transition(Event.CONNECTED, CONNECTED)
                .transition(Event.FAILED,  QUARANTINED)
                .transition(Event.REMOVE,  REMOVED);

        CONNECTED
                .onEnter(log("enter"))
                .onExit(log("exit"))
                .transition(Event.IDLE,    IDLE)
                .transition(Event.FAILED,  QUARANTINED)
                .transition(Event.REMOVE,  REMOVED);

        QUARANTINED
                .onEnter(log("enter"))
                .onExit(log("exit"))
                .transition(Event.IDLE,    IDLE)
                .transition(Event.REMOVE,  REMOVED);

        REMOVED
                .onEnter(log("enter"))
                .onExit(log("exit"))
                .transition(Event.CONNECT, CONNECTING);
    }
    @Test
    public void test() {
        StateMachine<SomeContext, Event> sm = new StateMachine<>(new SomeContext(), IDLE);

        sm.connect().subscribe();

        sm.call(Event.CONNECT);
        sm.call(Event.CONNECTED);
        sm.call(Event.FAILED);
        sm.call(Event.REMOVE);
    }
}