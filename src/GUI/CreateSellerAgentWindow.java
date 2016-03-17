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
/**
 *
 * @author Matthew
 */
public class CreateSellerAgentWindow extends Window{
   
    ContainerController myContainer;
    public  String SECONDARY_PROPERTIES_FILE = "cfg/containerServer.cfg"; 
    
    private String agentName;
    private String loweringValue;
    
    private JPanel panel;
    private JPanel panelName;
    private JPanel panelButtons;
    private JPanel panelSettings;
    private JPanel panelListOfItems;
    private JPanel panelListAndColumnNames;
    private JPanel panelColumnNames;
    private JPanel panelLists;
    private JPanel panelListButtons;
    private JPanel panelNegotiationStrategy;
    
    private JLabel nameLabel;
    private JLabel listOfItemsLabel;
    private JLabel nameItemLabel;
    private JLabel utilityItemLabel;
    private JLabel valueItemLabel;
    private JLabel negotiationStrategyLabel;
    
    private JTextField nameText;
    private JTextField loweringValueText;
    
    private JButton addItemButton;
    private JButton editItemButton;
    private JButton deleteItemButton;
    private JButton acceptButton;
    private JButton cancelButton;
    
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
                
                panelSettings = new JPanel(new BorderLayout());
                panelListOfItems = new JPanel(new BorderLayout());
                panelSettings.add(panelListOfItems, BorderLayout.CENTER);
                panelNegotiationStrategy = new JPanel(new BorderLayout());
                panelSettings.add(panelNegotiationStrategy, BorderLayout.SOUTH);
                
                listOfItemsLabel = new JLabel("List of items");
                panelListOfItems.add(listOfItemsLabel, BorderLayout.NORTH);
                panelListAndColumnNames = new JPanel(new BorderLayout());
                panelListOfItems.add(panelListAndColumnNames, BorderLayout.CENTER);
                panelListButtons = new JPanel(new FlowLayout());
                panelListOfItems.add(panelListButtons, BorderLayout.SOUTH);
                
                panelColumnNames = new JPanel(new FlowLayout());
                panelListAndColumnNames.add(panelColumnNames, BorderLayout.NORTH);
                panelLists = new JPanel(new FlowLayout());
                panelListAndColumnNames.add(panelLists, BorderLayout.CENTER);
                
                nameItemLabel = new JLabel("Name");
                panelColumnNames.add(nameItemLabel);
                utilityItemLabel = new JLabel("Utility");
                panelColumnNames.add(utilityItemLabel);
                valueItemLabel = new JLabel("value");
                panelColumnNames.add(valueItemLabel);
                
                addItemButton = new JButton("Add Item");
                panelListButtons.add(addItemButton);
                editItemButton = new JButton("Edit Item");
                panelListButtons.add(editItemButton);
                deleteItemButton = new JButton("Delete Item");
                panelListButtons.add(deleteItemButton);
                
                negotiationStrategyLabel = new JLabel("Negotiation Strategy");
                panelNegotiationStrategy.add(negotiationStrategyLabel, BorderLayout.NORTH);
                loweringValueText = new JTextField("100");
                panelNegotiationStrategy.add(loweringValueText, BorderLayout.SOUTH);
                
                panelButtons = new JPanel(new FlowLayout());
                acceptButton = new JButton("Accept");
                panelButtons.add(acceptButton);
                cancelButton = new JButton ("Cancel");
                panelButtons.add(cancelButton);
                
                panel = new JPanel(new BorderLayout());
                panel.add(panelName, BorderLayout.NORTH);
                panel.add(panelSettings, BorderLayout.CENTER);
                panel.add(panelButtons, BorderLayout.SOUTH);
                getWindow().add(panel);
                
                acceptButton.addActionListener(this);
                setFrameOptions();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(acceptButton)){        
            agentName = nameText.getText();
            loweringValue = loweringValueText.getText();
            args = new Object[1];
            args[0] = loweringValue;
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
