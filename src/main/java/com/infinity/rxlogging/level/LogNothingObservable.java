package com.infinity.rxlogging.level;

import com.infinity.rxlogging.LogRxProceedingJoinPoint;
import com.infinity.rxlogging.LoggableObservable;
import com.infinity.rxlogging.MessageManager;
import com.infinity.rxlogging.ObservableInfo;
import rx.Observable;

@SuppressWarnings("unchecked")
public class LogNothingObservable extends LoggableObservable {

  public LogNothingObservable(LogRxProceedingJoinPoint joinPoint,
                              MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override
  public <T> Observable<T> get(T type) throws Throwable {
    return ((Observable<T>) joinPoint.proceed());
  }
}
