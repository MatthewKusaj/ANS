/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agent_Management.ContainersManager;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Matthew
 */
public class CreateSellerAgentWindow extends Window{
   
    ContainerController myContainer;
    public  String SECONDARY_PROPERTIES_FILE = "cfg/containerServer.cfg"; 
    
    private String agentName;
    
    private JPanel panel;
    private JPanel panelName;
    private JPanel panelButtons;
    
    private JLabel nameLabel;
    private JTextField nameText;
    private JButton accept;
    
    private Object[] args;
    
    public CreateSellerAgentWindow (String tit, int width, int height)
	{
		super(tit, width, height);
                initiWidgets();
        }
    private void initiWidgets(){
        // Create panel for widgets and layout
		getWindow().setResizable(false);
                
                panelName = new JPanel(new FlowLayout());
                nameLabel = new JLabel("Agent Name");
                panelName.add(nameLabel);
                nameText = new JTextField("Seller Agent");
                panelName.add(nameText);
                
                panelButtons = new JPanel(new FlowLayout());
                accept = new JButton("Accept");
                panelButtons.add(accept);
                
                panel = new JPanel(new BorderLayout());
                panel.add(panelName, BorderLayout.NORTH);
                panel.add(panelButtons, BorderLayout.SOUTH);
                getWindow().add(panel);
                
                accept.addActionListener(this);
                setFrameOptions();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(accept)){        
            agentName = nameText.getText();
            args = new Object[1];
            args[0] = "100";
		try{
                    if(!ContainersManager.getInstance().sellerContainerExists()){
                        ContainersManager.getInstance().createSellerContainer();
                    }
                    myContainer = ContainersManager.getInstance().getSellerContainer();
                    AgentController ac = myContainer.createNewAgent(agentName, "Agent_Management.SellerAgent", args);
                    ac.start();
                    }   catch(Exception ex) {
		}
            Main_Window.sellerAgentsListModel.addElement(agentName);
        }
    }       
}
