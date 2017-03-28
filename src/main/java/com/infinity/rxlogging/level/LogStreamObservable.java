package com.infinity.rxlogging.level;

import com.infinity.rxlogging.*;
import org.springframework.util.StopWatch;
import rx.Observable;

@SuppressWarnings("unchecked")
public class LogStreamObservable extends LoggableObservable {
    public LogStreamObservable(LogRxProceedingJoinPoint joinPoint,
                               MessageManager messageManager, ObservableInfo observableInfo) {
        super(joinPoint, messageManager, observableInfo);
    }

    @Override
    public <T> Observable<T> get(T type) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        final Counter emittedItems = new Counter(joinPoint.getMethodName());
        return ((Observable<T>) joinPoint.proceed())
                .doOnSubscribe(() -> stopWatch.start())
                .doOnNext(value -> {
                    emittedItems.increment();
                    messageManager.printObservableOnNextWithValue(observableInfo, value);
                })
                .doOnError(throwable -> messageManager.printObservableOnError(observableInfo, throwable))
                .doOnTerminate(() -> {
                    stopWatch.stop();
                    observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
                    observableInfo.setTotalEmittedItems(emittedItems.tally());
                    messageManager.printObservableItemTimeInfo(observableInfo);
                });
    }
}
