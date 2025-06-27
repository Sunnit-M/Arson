package net.justsunnit.arson.util;

import java.util.function.Consumer;

public class CountdownBuilder {
    public ArgRunnable onFinish;
    public ArgRunnable onTick;

    public Object[] args;

    public int seconds;

    @FunctionalInterface
    public interface ArgRunnable {
        void run(long ticksLeft,Object... args);
    }

    public static class Builder {
        public ArgRunnable onFinish;
        public ArgRunnable onTick;

        public Object[] args;

        public int seconds;

        public Builder setDuration(int seconds) {
            this.seconds = seconds;
            return this;
        }

        public Builder setOnFinish(ArgRunnable onFinish) {
            this.onFinish = onFinish;
            return this;
        }

        public Builder setOnTick(ArgRunnable onTick) {
            this.onTick = onTick;
            return this;
        }

        public Builder setArgs(Object... args) {
            this.args = args;
            return this;
        }

        public CountdownBuilder build() {
            CountdownBuilder countdownBuilder = new CountdownBuilder();
            countdownBuilder.seconds = this.seconds;
            countdownBuilder.onFinish = this.onFinish;
            countdownBuilder.onTick = this.onTick;
            countdownBuilder.args = this.args;
            return countdownBuilder;
        }
    }

    public void exe() {
        Thread thread = new Thread(() -> {
            for (int i = seconds; i >= 0; i--) {
                if (onTick != null) {
                    onTick.run(i,args);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if (onFinish != null) {
                onFinish.run(0,args);
            }
        });
        thread.start();
    }
}