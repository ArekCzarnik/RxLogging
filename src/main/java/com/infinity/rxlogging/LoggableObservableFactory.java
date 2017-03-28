package com.infinity.rxlogging;

import com.infinity.rxlogging.level.LogEverythingObservable;

@SuppressWarnings("unchecked")
public class LoggableObservableFactory {

    private final LogRxProceedingJoinPoint joinPoint;
    private final MessageManager messageManager;
    private final ObservableInfo observableInfo;

    public LoggableObservableFactory(LogRxProceedingJoinPoint joinPoint,
                                     MessageManager messageManager, ObservableInfo observableInfo) {
        this.joinPoint = joinPoint;
        this.messageManager = messageManager;
        this.observableInfo = observableInfo;
    }

    public LoggableObservable create() {
        return new LogEverythingObservable(joinPoint, messageManager, observableInfo);

    }
}
