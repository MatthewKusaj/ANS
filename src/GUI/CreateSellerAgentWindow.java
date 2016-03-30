/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agent_Management.ContainersManager;
import Agent_Management.SellerAgent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Matthew
 */
public class CreateSellerAgentWindow extends Window{
   
    ContainerController myContainer;
    //public  String SECONDARY_PROPERTIES_FILE = "cfg/containerServer.cfg"; 
    
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
    
    private JLabel fillerLabel;
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
    
    private JList<String> nameOfItemsList;
    public static DefaultListModel nameOfItemsModel;
    private JList<String> utilityOfItemsList;
    public static DefaultListModel utilityOfItemsModel;
    private JList<String> valueOfItemsList;
    public static DefaultListModel valueOfItemsModel;
    
    private final SellerAgent newSellerAgent;
    private Object[] args;
    
    public CreateSellerAgentWindow (String tit, int width, int height)
	{
		super(tit, width, height);

                newSellerAgent = new SellerAgent();
                initiWidgets();
        }
    private void initiWidgets(){
        // Create panel for widgets and layout
		getWindow().setResizable(false);
                
                panelName = new JPanel(new FlowLayout());
                nameLabel = new JLabel("Agent Name");
                panelName.add(nameLabel);
                nameText = new JTextField(20);
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
                fillerLabel = new JLabel("          ");
                panelColumnNames.add(fillerLabel);
                utilityItemLabel = new JLabel("Utility");
                panelColumnNames.add(utilityItemLabel);
                fillerLabel = new JLabel("          ");
                panelColumnNames.add(fillerLabel);
                valueItemLabel = new JLabel("Value");
                panelColumnNames.add(valueItemLabel);
                
                nameOfItemsList = new JList<>();
                panelLists.add(nameOfItemsList, BorderLayout.WEST);
                utilityOfItemsList = new JList<>();
                panelLists.add(utilityOfItemsList, BorderLayout.CENTER);
                valueOfItemsList = new JList<>();
                panelLists.add(valueOfItemsList, BorderLayout.EAST);
                
                addItemButton = new JButton("Add Item");
                panelListButtons.add(addItemButton);
                fillerLabel = new JLabel("          ");
                panelListButtons.add(fillerLabel);
                editItemButton = new JButton("Edit Item");
                panelListButtons.add(editItemButton);
                fillerLabel = new JLabel("          ");
                panelListButtons.add(fillerLabel);
                deleteItemButton = new JButton("Delete Item");
                panelListButtons.add(deleteItemButton);
                
                negotiationStrategyLabel = new JLabel("Negotiation Strategy");
                panelNegotiationStrategy.add(negotiationStrategyLabel, BorderLayout.NORTH);
                loweringValueText = new JTextField(20);
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
                addItemButton.addActionListener(this);
                
                nameOfItemsModel = new DefaultListModel();
                nameOfItemsList.setModel(nameOfItemsModel);
                utilityOfItemsModel = new DefaultListModel();
                utilityOfItemsList.setModel(utilityOfItemsModel);
                valueOfItemsModel = new DefaultListModel();
                valueOfItemsList.setModel(valueOfItemsModel);
                
                setFrameOptions();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addItemButton)){
                agentName = nameText.getText();
                loweringValue = loweringValueText.getText();
                args = new Object[1];
                args[0] = "50";
            
		try{
                    if(!ContainersManager.getInstance().sellerContainerExists()){
                        ContainersManager.getInstance().createSellerContainer();
                    }
                    myContainer = ContainersManager.getInstance().getSellerContainer();
                    
                    //if( newSellerAgent.getAID().getName() == agentName){

                        AgentController ac = myContainer.createNewAgent(agentName, "Agent_Management.SellerAgent", args);
                        ac.start();
                //}
                        
                    }   catch(Exception ex) {
		}
        }
        if(e.getSource().equals(acceptButton)){        
            
            Main_Window.sellerAgentsListModel.addElement(agentName);
            getWindow().dispose();
        }
    }       
}
