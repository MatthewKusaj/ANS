/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agent_Management.JADE_Backbone;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
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
public class AgentCreationWindow extends Window{
    
    private String agentName;
    
    private JPanel panel;
    private JPanel panelName;
    private JPanel panelButtons;
    
    private JLabel nameLabel;
    private JTextField nameText;
    private JButton accept;
    
    public AgentCreationWindow (String tit, int width, int height)
	{
		super(tit, width, height);
                initiWidgets();
                setFrameOptions();
        }
    private void initiWidgets(){
        // Create panel for widgets and layout
		getWindow().setResizable(false);
		getWindow().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                
                panelName = new JPanel(new FlowLayout());
                nameLabel = new JLabel("Agent Name");
                panelName.add(nameLabel);
                nameText = new JTextField("                           ");
                panelName.add(nameText);
                
                panelButtons = new JPanel(new FlowLayout());
                accept = new JButton("Accept");
                panelButtons.add(accept);
                
                panel = new JPanel(new BorderLayout());
                panel.add(panelName, BorderLayout.NORTH);
                panel.add(panelButtons, BorderLayout.SOUTH);
                getWindow().add(panel);
                
                accept.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(accept)){
            
            agentName = new String(nameText.getText());
            try {
            // get a JADE runtime
            Runtime rt = Runtime.instance();
            // create a default profile
            Profile p = new ProfileImpl();
            // create the Main-container
            ContainerController mainContainer = rt.createMainContainer(p);
            //AgentContainer ac =(AgentContainer) mainContainer.getClient();
            // create agent
            AgentController ac = mainContainer.createNewAgent(agentName, "jade.core.Agent", null); 
            // start the agent
            System.out.println("Starting Agent with a name " + agentName );
            ac.start();
            } catch (jade.wrapper.StaleProxyException e1) {
            System.err.println("Error launching agent...");
                }
            AID id = new AID(agentName, AID.ISLOCALNAME);
            
            }
            
        }
    
    
}
