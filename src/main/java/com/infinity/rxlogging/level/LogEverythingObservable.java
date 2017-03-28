package com.infinity.rxlogging.level;

import com.infinity.rxlogging.*;
import org.springframework.util.StopWatch;
import rx.Observable;

@SuppressWarnings("unchecked")
public class LogEverythingObservable extends LoggableObservable {

    public LogEverythingObservable(LogRxProceedingJoinPoint joinPoint, MessageManager messageManager,
                                   ObservableInfo observableInfo) {
        super(joinPoint, messageManager, observableInfo);
    }

    @Override
    public <T> Observable<T> get(T type) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        final Counter emittedItems = new Counter(joinPoint.getMethodName());
        return ((Observable<T>) joinPoint.proceed())
                .doOnSubscribe(() -> {
                    stopWatch.start();
                    messageManager.printObservableOnSubscribe(observableInfo);
                })
                .doOnEach(notification -> {
                    if (!observableInfo.getSubscribeOnThread().isPresent()
                            && (notification.isOnNext() || notification.isOnError())) {
                        observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
                    }
                })
                .doOnNext(value -> {
                    emittedItems.increment();
                    messageManager.printObservableOnNextWithValue(observableInfo, value);
                })
                .doOnError(throwable -> messageManager.printObservableOnError(observableInfo, throwable))
                .doOnCompleted(() -> messageManager.printObservableOnCompleted(observableInfo))
                .doOnTerminate(() -> {
                    stopWatch.stop();
                    observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
                    observableInfo.setTotalEmittedItems(emittedItems.tally());
                    messageManager.printObservableOnTerminate(observableInfo);
                    messageManager.printObservableItemTimeInfo(observableInfo);
                })
                .doOnUnsubscribe(() -> {
                    stopWatch.stop();
                    observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
                    observableInfo.setTotalEmittedItems(emittedItems.tally());


                    if (!observableInfo.getObserveOnThread().isPresent()) {
                        observableInfo.setObserveOnThread(Thread.currentThread().getName());
                    }
                    messageManager.printObservableThreadInfo(observableInfo);
                    messageManager.printObservableOnUnsubscribe(observableInfo);
                    messageManager.printObservableItemTimeInfo(observableInfo);
                });
    }
}
