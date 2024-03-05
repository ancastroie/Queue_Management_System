package org.example.view;

import org.example.logic.SimulationManager;
import org.example.model.Clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
public class GUI extends JFrame implements ActionListener {
    JTextField n,queue,simTime,minArr,maxArr,minServ,maxServ;
    JTextArea text= new JTextArea(" ");
    JButton add;
    public GUI(){
        getContentPane().setBackground(Color.WHITE);
        this.setTitle("Queue management");
        this.setLayout(null);
        this.setBounds(300, 100, 800, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Number of clients:");
        l1.setBounds(40, 20, 150, 30);
        add(l1);

        n = new JTextField();
        n.setBounds(150, 20, 50, 30);
        add(n);

        JLabel l2 = new JLabel("Number of queues:");
        l2.setBounds(40, 70, 150, 30);
        add(l2);

        queue = new JTextField();
        queue.setBounds(150, 70, 50, 30);
        add(queue);

        JLabel l3=new JLabel("Simulation time:");
        l3.setBounds(40,120,150,30);
        add(l3);

        simTime=new JTextField();
        simTime.setBounds(150,120,50,30);
        add(simTime);

        JLabel l4=new JLabel("Min arrival time:");
        l4.setBounds(40,170,150,30);
        add(l4);

        minArr=new JTextField();
        minArr.setBounds(150,170,50,30);
        add(minArr);

        JLabel l5=new JLabel("Max arrival time:");
        l5.setBounds(210,170,150,30);
        add(l5);

        maxArr=new JTextField();
        maxArr.setBounds(320,170,50,30);
        add(maxArr);

        JLabel l6=new JLabel("Min service time:");
        l6.setBounds(40,220,150,30);
        add(l6);

        minServ=new JTextField();
        minServ.setBounds(150,220,50,30);
        add(minServ);

        JLabel l7=new JLabel("Max service time:");
        l7.setBounds(210,220,150,30);
        add(l7);

        maxServ=new JTextField();
        maxServ.setBounds(320,220,50,30);
        add(maxServ);

        add = new JButton("Add Info");
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.setBounds(70, 270, 150, 50);
        add.addActionListener(this);
        add(add);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Add Info")){

            int nrOfClients,nrOfQueues,simulatonTime,minArrival,maxArrival,minService,maxService;
            nrOfClients=Integer.parseInt(n.getText());
            nrOfQueues=Integer.parseInt(queue.getText());
            simulatonTime=Integer.parseInt(simTime.getText());
            minArrival=Integer.parseInt(minArr.getText());
            maxArrival=Integer.parseInt(maxArr.getText());
            minService=Integer.parseInt(minServ.getText());
            maxService=Integer.parseInt(maxServ.getText());

            SimulationManager simulation=new SimulationManager();
            try {
                simulation.startSimulation(nrOfClients,nrOfQueues,simulatonTime,minArrival,maxArrival,minService,maxService, this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            newFrame();
        }
    }

    public void newFrame(){
        JFrame frame=new JFrame();
        frame.setPreferredSize(new Dimension(400,400));
        frame.setMinimumSize(new Dimension(400,400));
        frame.setMaximumSize(new Dimension(400,400));
        frame.setSize(new Dimension(400,400));
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        GridBagConstraints gbc=new GridBagConstraints();
        text.setSize(new Dimension(400,400));
        text.setEditable(false);
        text.setPreferredSize(new Dimension(400,400));
        text.setMinimumSize(new Dimension(400,400));
        text.setMaximumSize(new Dimension(400,400));
        frame.add(text);
        frame.setVisible(true);
    }

    public void update(String text){
        this.text.setText(text);
    }
}

