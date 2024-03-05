package org.example.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Clock implements Runnable {
    Thread clockThread;
     int time;
     BufferedWriter writer;
     private boolean isRunning;
    public Clock(BufferedWriter writer) {
        this.writer=writer;
        time = 0;
    }
    public void startClock(){

        clockThread.start();
    }
    public void stopClock(){
        isRunning=false;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void run() {


        while(true){
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time++;
            String text=printClock();
            System.out.println(text);

        }
    }

    public synchronized String printClock(){
        return "clock"+time;
    }
}
