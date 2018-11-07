package com.agoda.domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private Semaphore semaphore;
    private ScheduledExecutorService scheduler;
    private Boolean suspended;
    private ScheduledExecutorService suspendedScheduler;

    public RateLimiter(Rate rate) {
        this.semaphore = new Semaphore(rate.getRequests());
        //setup scheduler to clear used permits
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scheduler.schedule(() -> {
            //total permits minus availablePermits = acquired permits
            int acquiredPermits = rate.getRequests() - semaphore.availablePermits();
            semaphore.release(acquiredPermits);
        }, rate.getSeconds(), TimeUnit.SECONDS);
        this.suspended = Boolean.FALSE;
    }

    public synchronized boolean getAccess() {
        //if suspended simply return false
        if (suspended) {
            return false;
        }
        boolean gotPermit = semaphore.tryAcquire();
        if (gotPermit) {
            return true;
        }

        //if rate limited then suspend for 5 minutes
        suspended = true;
        suspendedScheduler = Executors.newScheduledThreadPool(1);
        suspendedScheduler.schedule(() -> {
            //clear suspended after 5 minutes
            suspended = false;
            //clear suspendedScheduler after running once
            suspendedScheduler.shutdown();
        }, 5, TimeUnit.MINUTES);

        return false;
    }

    public synchronized void cleanUp() {
        scheduler.shutdown();
        if (suspendedScheduler != null) {
            suspendedScheduler.shutdown();
        }
    }

    public synchronized void cleanUpNow() {
        scheduler.shutdownNow();
        if (suspendedScheduler != null) {
            suspendedScheduler.shutdownNow();
        }
    }
}
