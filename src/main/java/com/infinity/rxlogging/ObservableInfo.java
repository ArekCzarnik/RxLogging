package com.infinity.rxlogging;

import java.util.Optional;

public class ObservableInfo {
    private final LogRxJoinPoint joinPoint;

    private String subscribeOnThread;
    private String observeOnThread;
    private long totalExecutionTime;
    private int totalEmittedItems;

    public ObservableInfo(LogRxJoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public String getClassSimpleName() {
        return joinPoint.getClassSimpleName();
    }

    public String getMethodName() {
        return joinPoint.getMethodName();
    }

    public LogRxJoinPoint getJoinPoint() {
        return joinPoint;
    }

    public Optional<String> getSubscribeOnThread() {
        return Optional.ofNullable(subscribeOnThread);
    }

    public Optional<String> getObserveOnThread() {
        return Optional.ofNullable(observeOnThread);
    }

    public Optional<Long> getTotalExecutionTime() {
        if (totalExecutionTime == 0) {
            return Optional.empty();
        }
        return Optional.of(totalExecutionTime);
    }

    public Optional<Integer> getTotalEmittedItems() {
        if (totalEmittedItems == 0) {
            return Optional.empty();
        }
        return Optional.of(totalEmittedItems);
    }

    public void setSubscribeOnThread(String subscribeOnThread) {
        this.subscribeOnThread = subscribeOnThread;
    }

    public void setObserveOnThread(String observeOnThread) {
        this.observeOnThread = observeOnThread;
    }

    public void setTotalExecutionTime(long totalExecutionTime) {
        this.totalExecutionTime = totalExecutionTime;
    }

    public void setTotalEmittedItems(int totalEmittedItems) {
        this.totalEmittedItems = totalEmittedItems;
    }
}
