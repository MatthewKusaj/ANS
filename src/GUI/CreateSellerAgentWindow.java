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
 * @author Mateusz Kusaj
 */
public class CreateSellerAgentWindow extends Window{
   
    ContainerController myContainer;
    
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
    
    private JLabel fillerLabel;
    private JLabel nameLabel;
    private JLabel listOfItemsLabel;
    private JLabel nameItemLabel;
    private JLabel valueItemLabel;
    
    private JTextField nameText;
    
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton acceptButton;
    private JButton cancelButton;
    
    private JList<String> nameOfItemsList;
    public static DefaultListModel nameOfItemsModel;
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
        //Creates all widgets and the entire layout
		getWindow().setResizable(false);
                
                panelName = new JPanel(new FlowLayout());
                nameLabel = new JLabel("Agent Name");
                panelName.add(nameLabel);
                nameText = new JTextField(20);
                panelName.add(nameText);
                
                panelSettings = new JPanel(new BorderLayout());
                panelListOfItems = new JPanel(new BorderLayout());
                panelSettings.add(panelListOfItems, BorderLayout.CENTER);
                
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
                fillerLabel = new JLabel("          ");
                panelColumnNames.add(fillerLabel);
                valueItemLabel = new JLabel("Value");
                panelColumnNames.add(valueItemLabel);
                
                nameOfItemsList = new JList<>();
                panelLists.add(nameOfItemsList, BorderLayout.WEST);
                valueOfItemsList = new JList<>();
                panelLists.add(valueOfItemsList, BorderLayout.EAST);
                
                addItemButton = new JButton("Add Item");
                panelListButtons.add(addItemButton);
                fillerLabel = new JLabel("          ");
                panelListButtons.add(fillerLabel);
                fillerLabel = new JLabel("          ");
                panelListButtons.add(fillerLabel);
                deleteItemButton = new JButton("Delete Item");
                panelListButtons.add(deleteItemButton);
                
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
                cancelButton.addActionListener(this);
                deleteItemButton.addActionListener(this);
                
                nameOfItemsModel = new DefaultListModel();
                nameOfItemsList.setModel(nameOfItemsModel);
                valueOfItemsModel = new DefaultListModel();
                valueOfItemsList.setModel(valueOfItemsModel);
                
                setFrameOptions();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addItemButton)){
            //Opens a new window that can be used to create new item that wil be added to the catalogue of this particular agent
                agentName = nameText.getText();
                args = new Object[1];
                args[0] = null;
            
		try{
                    if(!ContainersManager.getInstance().sellerContainerExists()){
                        ContainersManager.getInstance().createSellerContainer();
                    }
                    myContainer = ContainersManager.getInstance().getSellerContainer();

                        AgentController ac = myContainer.createNewAgent(agentName, "Agent_Management.SellerAgent", args);
                        ac.start();
                    }   catch(Exception ex) {
		}
        }
        if(e.getSource().equals(acceptButton)){        
            //implements the agent and add him to the list on the Main Window
            Main_Window.sellerAgentsListModel.addElement(agentName);
            getWindow().dispose();
        }
         if(e.getSource().equals(cancelButton)){
             //Kills the agent and returns to the Main Window
            agentName = nameText.getText();
    
		try{
                    myContainer = ContainersManager.getInstance().getSellerContainer();

                        AgentController ac = myContainer.getAgent(agentName + "@192.168.0.13:1099/JADE", true);
                        ac.kill();
                        
                    }   catch(Exception ex) {
		}
                getWindow().dispose();
        }
        if(e.getSource().equals(deleteItemButton)){
            //selectedItem = nameOfItemsList.getSelectedValue();
            
        }
    }       
}
