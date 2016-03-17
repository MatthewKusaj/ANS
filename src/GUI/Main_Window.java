/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agent_Management.ContainersManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Matthew
 */
public class Main_Window extends Window{
    
    private JPanel mainPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    
    private JButton newBuyerAgent;
    private JButton newSellerAgent;
    private JButton editAgent;
    private JButton deleteAgent;

    private JList<String> buyerAgentsList;
    private JList sellerAgentsList;
    
    public Main_Window(String tit, int width, int height)
	{
		super(tit, width, height);
		ContainersManager.getInstance().startRMA();
                initiWidgets();
	}
    private void initiWidgets(){
        // Create panel for widgets and layout
		getWindow().setResizable(false);
		getWindow().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                
		westPanel = new JPanel(new FlowLayout());
                buyerAgentsList = new JList<String>();
                westPanel.add(buyerAgentsList);
                
                
                eastPanel = new JPanel(new FlowLayout());
                sellerAgentsList = new JList<String>();
                eastPanel.add(sellerAgentsList);
                
                southPanel = new JPanel(new FlowLayout());
                newBuyerAgent = new JButton("Create Buyer Agent");
                southPanel.add(newBuyerAgent);
                newSellerAgent = new JButton("Create Seller Agent");
                southPanel.add(newSellerAgent);
                editAgent = new JButton("Edit Agent");
                southPanel.add(editAgent);
                deleteAgent = new JButton("Delete Agent");
                southPanel.add(deleteAgent);
                
                
                
                mainPanel = new JPanel(new BorderLayout());
                mainPanel.add(westPanel, BorderLayout.WEST);
                mainPanel.add(eastPanel, BorderLayout.EAST);
                mainPanel.add(southPanel, BorderLayout.SOUTH);
                getWindow().add(mainPanel);
                
		newBuyerAgent.addActionListener(this);
                newSellerAgent.addActionListener(this);
                setFrameOptions();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(newBuyerAgent)){
            new CreateBuyerAgentWindow("Create New Buyer Agent", 500, 100);
        }
        if(e.getSource().equals(newSellerAgent)){
            new CreateSellerAgentWindow("Create New Seller Agent", 500, 100);
        }
    }
    
    public static void main(String args[])
	{
		new Main_Window("Agent Negotiations Simulator", 800, 800);
                
	}

    
}
