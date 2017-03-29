package com.infinity.rxlogging;


public abstract class LoggableObservable {

  public final LogRxProceedingJoinPoint joinPoint;
  public final MessageManager messageManager;
  public final ObservableInfo observableInfo;

  public LoggableObservable(LogRxProceedingJoinPoint joinPoint, MessageManager messageManager,
                     ObservableInfo observableInfo) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
  }

  public abstract <T> rx.Observable<T> get(T type) throws Throwable;

  public ObservableInfo getInfo() {
    return observableInfo;
  }
}
