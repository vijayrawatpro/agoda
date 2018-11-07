package com.agoda.domain;

public class Rate {
    private int requests;
    private int seconds;

    public Rate(int requests, int seconds) {
        this.requests = requests;
        this.seconds = seconds;
    }

    public Rate() {
    }

    public int getRequests() {
        return requests;
    }

    public int getSeconds() {
        return seconds;
    }

    public static interface RequestsStep {
        SecondsStep withRequests(int requests);
    }

    public static interface SecondsStep {
        BuildStep withSeconds(int seconds);
    }

    public static interface BuildStep {
        Rate build();
    }

    public static class Builder implements RequestsStep, SecondsStep, BuildStep {
        private int requests;
        private int seconds;

        private Builder() {
        }

        public static RequestsStep rate() {
            return new Builder();
        }

        @Override
        public SecondsStep withRequests(int requests) {
            this.requests = requests;
            return this;
        }

        @Override
        public BuildStep withSeconds(int seconds) {
            this.seconds = seconds;
            return this;
        }

        @Override
        public Rate build() {
            return new Rate(
                this.requests,
                this.seconds
            );
        }
    }
}
