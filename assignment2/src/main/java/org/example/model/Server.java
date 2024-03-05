package org.example.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger time;
    private int queueNr;
    private int nrOfTasks;
    private Thread serverThread;
    BufferedWriter writer;

    Clock clock;

    public Server(int queueNr,Clock clock,BufferedWriter writer){

        this.clock=clock;
        this.nrOfTasks=0;
        this.tasks=new ArrayBlockingQueue<>(100);
        this.queueNr=queueNr;
        this.writer=writer;
       // this.serverThread=new Thread(this);
      //  serverThread.start();
    }
    public synchronized void addTask(Task newTask)throws InterruptedException{
        nrOfTasks++;
        tasks.add(newTask);
        notifyAll();
    }
    public synchronized void removeTask()throws InterruptedException{

        while(tasks.size()==0)
            wait();
        tasks.remove();
        nrOfTasks--;
        notifyAll();

    }
    public int getIndex(){
        return this.queueNr;
    }

    public synchronized int getWaitingTime() {
        int waitT=0;
        if(tasks.size()!=0) {
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task element = iterator.next();
                waitT = waitT + element.serviceTime;

            }
        }
        return waitT;
    }
    @Override
    public void run(){
        while(true) {
          //  System.out.println("server " +this.getIndex()+" with clock time "+clock.getTime());
         //  System.out.println("Clock: "+clock.getTime());
            if(tasks.isEmpty()==false){
                if(tasks.peek().serviceTime>1){
                    int service=tasks.peek().getServiceTime();
                    service--;
                    tasks.peek().setServiceTime(service);
                }
                else{
                    tasks.remove();
                }
            }
            String text=displayQueue();
            System.out.println(text);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public synchronized String displayQueue(){

        StringBuilder stringBuilder=new StringBuilder();

        if(!tasks.isEmpty()){
           // System.out.print("Server: "+getIndex()+" has : ");
            stringBuilder.append("Server: "+getIndex()+" has : ");
            for(Task task:tasks){
                //System.out.print("( "+task.id+" , "+task.arrivalTime+" , "+task.serviceTime+" )");
                stringBuilder.append("( "+task.id+" , "+task.arrivalTime+" , "+task.serviceTime+" )");
            }

            //System.out.println();
        }
        else{
            //System.out.println("Server: "+getIndex()+": empty");
            stringBuilder.append("Server: "+getIndex()+": empty");
        }

        return stringBuilder.append("\n").toString();

    }
    public synchronized void displayInTxt(String text){

    }
    public BlockingQueue<Task> getTasks(){
        return this.tasks;
    }
}

