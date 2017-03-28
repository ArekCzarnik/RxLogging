package com.infinity.rxlogging;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogRxProceedingJoinPoint extends LogRxJoinPoint {

  private final ProceedingJoinPoint proceedingJoinPoint;

  public LogRxProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
    super(proceedingJoinPoint);
    this.proceedingJoinPoint = proceedingJoinPoint;
  }

  /**
   * {@link ProceedingJoinPoint#proceed()}
   */
  public Object proceed() throws Throwable {
    return proceedingJoinPoint.proceed();
  }

  /**
   * {@link ProceedingJoinPoint#proceed(Object[])}
   */
  public Object proceed(Object[] args) throws Throwable {
    return proceedingJoinPoint.proceed(args);
  }
}
