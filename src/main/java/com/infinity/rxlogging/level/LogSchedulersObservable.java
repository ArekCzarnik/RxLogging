package com.infinity.rxlogging.level;

import com.infinity.rxlogging.LogRxProceedingJoinPoint;
import com.infinity.rxlogging.LoggableObservable;
import com.infinity.rxlogging.MessageManager;
import com.infinity.rxlogging.ObservableInfo;
import rx.Observable;

@SuppressWarnings("unchecked")
public class LogSchedulersObservable extends LoggableObservable {
    public LogSchedulersObservable(LogRxProceedingJoinPoint joinPoint,
                                   MessageManager messageManager, ObservableInfo observableInfo) {
        super(joinPoint, messageManager, observableInfo);
    }

    @Override
    public <T> Observable<T> get(T type) throws Throwable {
        return ((Observable<T>) joinPoint.proceed())
                .doOnEach(notification -> {
                    if (!observableInfo.getSubscribeOnThread().isPresent()
                            && (notification.isOnNext() || notification.isOnError())) {
                        observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
                    }
                })
                .doOnUnsubscribe(() -> {
                    if (!observableInfo.getObserveOnThread().isPresent()) {
                        observableInfo.setObserveOnThread(Thread.currentThread().getName());
                    }
                    messageManager.printObservableThreadInfo(observableInfo);
                });
    }
}
