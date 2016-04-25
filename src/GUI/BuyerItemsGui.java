package GUI;

import Agent_Management.BuyerAgent;
import Agent_Management.ContainersManager;
import Negotiation_Strategies.Increasing_Value;
import Negotiation_Strategies.Maximal_Price;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mateusz Kusaj
 */
public class BuyerItemsGui extends Window{
    
    private String selectedAgent;
    
    private JPanel panel;
    private JPanel infoPanel;
    private JPanel namePanel;
    private JPanel buttonsPanel;
    private JPanel negotiationStrategyPanel;
    private JPanel negotiationStrategyTopPanel;
    private JPanel negotiationStrategyPropertiesPanel;
    private JPanel increasingValuePanel;
    private JPanel maximalPricePanel;
    private JPanel shareInformationPanel;
    
    public BuyerAgent myAgent;
    
    private JLabel fillerLabel;
    private JLabel nameLabel;
    private JLabel negotiationStrategyLabel;
    private JLabel increasingValueLabel;
    private JLabel maximalPriceLabel;
    private JLabel shareInformationLabel;
    private JLabel truthLabel;
    
    public static JTextField nameText;
    public static JTextField increasingValueText;
    public static JTextField maximalPriceText;
    
    private JComboBox shareInformationCB;
    private JComboBox truthCB;
    
    private JButton acceptButton;
    private JButton cancelButton;

    private static ArrayList<String> properties;
    public BuyerItemsGui(String tit, int width, int height, BuyerAgent b) {
        super(tit, width, height, BuyerAgent.getInstance());
        
        myAgent = b;
        initiWidgets();
    }

    private void initiWidgets() {
        //Creates all widgets and the entire layout
        getWindow().setResizable(false);
        
        namePanel = new JPanel(new FlowLayout());
        nameLabel = new JLabel("Name");
        namePanel.add(nameLabel);
        nameText = new JTextField(15);
        namePanel.add(nameText);

        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(namePanel, BorderLayout.NORTH);
        
        buttonsPanel = new JPanel(new BorderLayout());
        acceptButton = new JButton("Accept");
        buttonsPanel.add(acceptButton, BorderLayout.WEST);
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(cancelButton, BorderLayout.EAST);
        
        negotiationStrategyPanel = new JPanel(new BorderLayout());
        negotiationStrategyTopPanel = new JPanel(new FlowLayout());
        negotiationStrategyPanel.add(negotiationStrategyTopPanel, BorderLayout.NORTH);
        fillerLabel = new JLabel("                       ");
        negotiationStrategyLabel = new JLabel("Negotiation Strategy");
        negotiationStrategyTopPanel.add(fillerLabel);
        negotiationStrategyTopPanel.add(negotiationStrategyLabel);
        negotiationStrategyTopPanel.add(fillerLabel);
        negotiationStrategyPropertiesPanel = new JPanel(new GridLayout(2,4));
        negotiationStrategyPanel.add(negotiationStrategyPropertiesPanel, BorderLayout.CENTER);
        increasingValuePanel = new JPanel (new FlowLayout());
        increasingValueLabel = new JLabel("Increase value");
        increasingValueText = new JTextField(20);
        negotiationStrategyPropertiesPanel.add(increasingValuePanel);
        increasingValuePanel.add(increasingValueLabel);
        increasingValuePanel.add(increasingValueText);
        maximalPricePanel = new JPanel(new FlowLayout());
        maximalPriceLabel = new JLabel("Maximal price");
        maximalPriceText = new JTextField(20);
        negotiationStrategyPropertiesPanel.add(maximalPricePanel);
        maximalPricePanel.add(maximalPriceLabel);
        maximalPricePanel.add(maximalPriceText);
        shareInformationPanel = new JPanel(new FlowLayout());
        shareInformationLabel = new JLabel("Share Information");
        String[] shareInformationOptions = {"Never share infromation","Always share information" };
        shareInformationCB = new JComboBox(shareInformationOptions);
        negotiationStrategyPropertiesPanel.add(shareInformationPanel);
        shareInformationPanel.add(shareInformationLabel);
        shareInformationPanel.add(shareInformationCB);
        truthLabel = new JLabel("How honest should I be?");
        String[] truthValue = {"Always truth", "Sometimes truth", "Always lie"};
        truthCB = new JComboBox(truthValue);
        shareInformationPanel.add(truthLabel);
        shareInformationPanel.add(truthCB);
        
        panel = new JPanel(new BorderLayout());
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(negotiationStrategyPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        getWindow().add(panel);
        setFrameOptions();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(acceptButton)){
            selectedAgent = CreateBuyerAgentWindow.nameText.getText();
             ContainerController myContainer = ContainersManager.getInstance().getBuyerContainer();
            try {
                AgentController ac = myContainer.getAgent(selectedAgent +"@192.168.0.13:1099/JADE", true);
                ac.suspend();
            } catch (ControllerException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
            String title = nameText.getText().trim();
            System.out.println(title);

            String option = (String) shareInformationCB.getSelectedItem();
            String truth = (String) truthCB.getSelectedItem();
            properties = new ArrayList<>();
            properties.add("0");
            properties.add("0");
            
            myAgent.updateCatalogue(title, properties);
            
            
            
            CreateBuyerAgentWindow.nameOfItemsModel.addElement(title);
            
            if (increasingValueText.getText() == null){
                properties.add("0");
            }else{
                properties.add(Increasing_Value.addIncreasingValue());
            }
            if (maximalPriceText.getText() == null){
                properties.add("0");
            }else{
                properties.add(Maximal_Price.addMaximalPrice());
            }
            properties.add(option);
            properties.add(truth);
            System.out.println(properties);
              
            
            nameText.setText(null);
            increasingValueText.setText(null);
            maximalPriceText.setText(null);
            
            }catch(Exception ec){
                JOptionPane.showMessageDialog(getWindow(), "Invalid values. "+ec.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
            }
        }
        if(e.getSource().equals(cancelButton)){
            getWindow().dispose();
        }
    }
    public static ArrayList<String> getProperties(){
        return properties;
    }
    
}
