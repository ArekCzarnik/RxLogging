package com.infinity.rxlogging;

@SuppressWarnings("unchecked")
public class LogRxObservable {

    private final LogRxProceedingJoinPoint joinPoint;
    private final MessageManager messageManager;
    private final ObservableInfo observableInfo;
    private final LoggableObservableFactory observableFactory;

    public LogRxObservable(LogRxProceedingJoinPoint joinPoint, MessageManager messageManager,
                           LoggableObservableFactory observableFactory) {
        this.joinPoint = joinPoint;
        this.messageManager = messageManager;
        this.observableInfo = new ObservableInfo(joinPoint);
        this.observableFactory = observableFactory;
    }

    public rx.Observable getObservable() throws Throwable {
        messageManager.printObservableInfo(observableInfo);
        final Class observableType = joinPoint.getGenericReturnTypes().get(0);
        return observableFactory.create().get(observableType);
    }
}
