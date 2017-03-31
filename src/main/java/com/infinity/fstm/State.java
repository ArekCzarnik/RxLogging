package com.infinity.fstm;

import rx.functions.Action2;

import java.util.HashMap;
import java.util.Map;

public class State<T, E> {
    private String name;
    private Action2<T, State<T, E>> enter;
    private Action2<T, State<T, E>> exit;
    private Map<E, State<T, E>> transitions = new HashMap<>();

    public State(String name) {
        this.name = name;
    }

    public State<T, E> onEnter(Action2<T, State<T, E>> func) {
        this.enter = func;
        return this;
    }

    public State<T, E> onExit(Action2<T, State<T, E>> func) {
        this.exit = func;
        return this;
    }

    public void enter(T context) {
        enter.call(context, this);
    }

    public void exit(T context) {
        exit.call(context, this);
    }

    public State<T, E> transition(E event, State<T, E> state) {
        transitions.put(event, state);
        return this;
    }

    public State<T, E> next(E event) {
        return transitions.get(event);
    }

    public String toString() {
        return name;
    }
}