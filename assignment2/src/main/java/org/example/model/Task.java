package org.example.model;

public class Task {
    public int arrivalTime;
    public int serviceTime;
    public int id;

    public Task(int id,int arrivalTime,int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime= serviceTime;
        this.id=id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
