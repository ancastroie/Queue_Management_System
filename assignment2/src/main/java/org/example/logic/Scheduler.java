package org.example.logic;

import org.example.model.Server;

import java.util.*;

//public class ConcreteStrategyTime implements Strategy{
//    public void addTask(List<Server> servers,Task t);
//}

public class Scheduler {
    private List<Server> servers;
    private int maxNrServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public enum SelectionPolicy{
        SHORTEST_QUEUE,SHORTEST_TIME;
    }

    public Scheduler(int maxNrServers,int maxTasksPerServer){

    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy==SelectionPolicy.SHORTEST_QUEUE){
        //    strategy=new ConcreteStrategyTime();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
          //  strategy=new ConcreteStrategyTime();
        }
    }
}
