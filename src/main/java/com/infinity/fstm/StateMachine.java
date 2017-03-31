package com.infinity.fstm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class StateMachine<T, E> implements Action1<E> {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachine.class);


    private volatile State<T, E> state;
    private final T context;
    private final PublishSubject<E> events = PublishSubject.create();

    protected StateMachine(T context, State<T, E> initial) {
        this.context = context;
        this.state = initial;
    }


    public Observable<Void> connect() {

        return Observable.create(sub -> {
            state.enter(context);

            sub.add(events.collect(() -> context, (context, event) -> {

                final State<T, E> next = state.next(event);

                if (next != null) {
                    state.exit(context);
                    state = next;
                    next.enter(context);
                } else {
                    LOG.info("Invalid event : " + event);
                }
            })
                    .subscribe());
        });
    }

    @Override
    public void call(E event) {
        events.onNext(event);
    }

    public State<T, E> getState() {
        return state;
    }

}