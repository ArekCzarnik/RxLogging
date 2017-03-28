package com.infinity.aspects;

import com.infinity.rxlogging.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {
    @Pointcut("execution(@com.infinity.annotations.Loggable * *(..))")
    public void loggableMethods() {
    }

    @Around("loggableMethods()")
    public Object logMethod(ProceedingJoinPoint jp) throws Throwable {
        final LogRxProceedingJoinPoint proceedingJoinPoint = new LogRxProceedingJoinPoint(jp);

        final MessageManager messageManager = new MessageManager();

        final LoggableObservableFactory observableFactory =
                new LoggableObservableFactory(proceedingJoinPoint, messageManager,
                        new ObservableInfo(proceedingJoinPoint));

        return new LogRxObservable(proceedingJoinPoint, messageManager,
                observableFactory).getObservable();
    }


}
