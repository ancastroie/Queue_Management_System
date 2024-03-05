package org.example.logic;

import org.example.model.Clock;
import org.example.model.Server;
import org.example.model.Task;
import org.example.view.GUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    public GUI gui;
    public int timeLimit;
    public int maxServiceTime;
    public int minServiceTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    Thread tClock;

    private Clock clock ;
    // private SimulationFrame frame;
    private ArrayList<Task> clients = new ArrayList<Task>();
    private Server[] queues;
    private Thread[] t;
    BufferedWriter writer;

    public void setInfo(int numberOfClients, int numberOfServers, int timeLimit, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, GUI gui) {
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.gui=gui;
    }

    public void generateNRandomTasks() {

        Random random = new Random();
        int arrivalTime, processTime;
        for (int i = 0; i < numberOfClients; i++) {
            arrivalTime = random.nextInt(minArrivalTime, maxArrivalTime + 1);
            processTime = random.nextInt(minServiceTime, maxServiceTime + 1);
            Task client = new Task(i + 1, arrivalTime, processTime);
            clients.add(client);
            //   System.out.println("client "+client.id+" "+client.arrivalTime+" "+client.serviceTime);

        }

        for (int i = 0; i < numberOfClients - 1; i++) {
            for (int j = i + 1; j < numberOfClients; j++) {
                if (clients.get(i).arrivalTime > clients.get(j).arrivalTime) Collections.swap(clients, i, j);
            }
        }
    }

    public void startSimulation(int numberOfClients, int numberOfServers, int timeLimit, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, GUI gui) throws IOException {
        this.setInfo(numberOfClients, numberOfServers, timeLimit, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, gui);
        this.generateNRandomTasks();
        String info=displayInfo();
        System.out.println(info);
        writer=new BufferedWriter(new FileWriter("test3.txt"));
        writer.write(info);
        Thread simT = new Thread(this);
        clock = new Clock(writer);
        tClock = new Thread(clock);
        queues = new Server[numberOfServers];
        t=new Thread[numberOfServers];
        tClock.start();
        for (int i = 0; i < numberOfServers; i++) {
            queues[i] = new Server(i, clock,writer);
             t[i] = new Thread(queues[i]);
            t[i].start();
        }
        simT.start();



    }

    public String displayInfo() {
        StringBuilder text=new StringBuilder();
        text.append("nr of clients: " + numberOfClients+"\n");
        text.append("nr of queues: " + numberOfServers+"\n");
        text.append("simulation time: " + timeLimit+"\n");
        text.append("min arr time: " + minArrivalTime + "   max arr time:" + maxArrivalTime+"\n");
        text.append("min serv time: " + minServiceTime + "  max serv time: " + maxServiceTime+"\n");


        //  generateNRandomTasks();
        for (Task task : clients) {
            text.append(" ( " + task.id + " , " + task.arrivalTime + " , " + task.serviceTime+" ) ");
        }
        text.append("\n");
        return text.toString();
    }

    public synchronized String displayWaitingClients() {

        StringBuilder text=new StringBuilder();
        for (Task task : clients) {
            text.append("( " + task.id + " , " + task.arrivalTime + " , " + task.serviceTime + " )");
        }
        return text.toString();
    }

    public synchronized int getQueueWithMinWaitingT() {
        int min = 500;
        int index = 0;
        for (Server server : queues) {
            if (server.getWaitingTime() < min) {
                min = server.getWaitingTime();
                index = server.getIndex();
            }
        }
        return index;
    }

//    public String getCurrentState(){
//        StringBuilder stringBuilder=
//    }

    @Override
    public void run() {

        int i = 0, minTimeQueue;
        while (clock.getTime() <= timeLimit) {

            // System.out.println("clock in sim manager " + clock.getTime());
            for (i = 0; i < clients.size(); i++) {
                if (clients.get(i).arrivalTime == clock.getTime()) {

                    minTimeQueue = getQueueWithMinWaitingT();
                    // System.out.println(getQueueWithMinWaitingT());
                    try {
                        queues[minTimeQueue].addTask(clients.get(i));
                        clients.remove(i);
                        i--;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            StringBuilder stringBuilder=new StringBuilder(" ");
            stringBuilder.append("Current time:"+clock.getTime()+" , waiting clients: ").append(displayWaitingClients()).append("\n");
            for(Server s: queues){
                stringBuilder.append(s.displayQueue());
            }
            gui.update(stringBuilder.toString());
            try {
                writer.write(stringBuilder.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        tClock.stop();
        for(Thread thread: t){
            thread.stop();
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
