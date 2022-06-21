package org.dkmakain.common.multithreading.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.time.Duration;
import java.util.function.Supplier;

public class ThreadConfig {

    private String             name;
    private Boolean            daemon;
    private Runnable           runnable;
    private Supplier<Duration> interval;

    public Supplier<Duration> getInterval() {
        return interval;
    }

    public void setInterval(Supplier<Duration> interval) {
        this.interval = interval;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(Boolean daemon) {
        this.daemon = daemon;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("name", name)
                          .add("daemon", daemon)
                          .add("runnable", runnable)
                          .add("interval", interval)
                          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThreadConfig that = (ThreadConfig) o;
        return Objects.equal(name, that.name) &&
            Objects.equal(daemon, that.daemon) &&
            Objects.equal(runnable, that.runnable) &&
            Objects.equal(interval, that.interval);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, daemon, runnable, interval);
    }

}
