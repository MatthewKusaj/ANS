package GUI;

import Agent_Management.SellerAgent;
import Negotiation_Strategies.Lowering_Value;
import Negotiation_Strategies.Minimal_Price;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
public class SellerItemsGui extends Window{
    
    private JPanel panel;
    private JPanel infoPanel;
    private JPanel namePanel;
    private JPanel valuePanel;
    private JPanel buttonsPanel;
    private JPanel negotiationStrategyPanel;
    private JPanel negotiationStrategyTopPanel;
    private JPanel negotiationStrategyPropertiesPanel;
    private JPanel loweringValuePanel;
    private JPanel minimalPricePanel;
    private JPanel askInformationPanel;
    
    public SellerAgent myAgent;
    
    private JLabel fillerLabel;
    private JLabel nameLabel;
    private JLabel valueLabel;
    private JLabel negotiationStrategyLabel;
    private JLabel loweringValueLabel;
    private JLabel minimalPriceLabel;
    private JLabel askInformationLabel;
    private JLabel trustLabel;
    
    public static JTextField nameText;
    private JTextField valueText;
    public static JTextField loweringValueText;
    public static JTextField minimalPriceText;
    
    private JComboBox askInformationCB;
    private JComboBox trustCB;
    
    private JButton acceptButton;
    private JButton cancelButton;

    private static ArrayList<String> properties;
    public SellerItemsGui(String tit, int width, int height, SellerAgent a) {
        super(tit, width, height, SellerAgent.getInstance());
        
        myAgent = a;
        initiWidgets();
    }

    private void initiWidgets() {
        getWindow().setResizable(false);
        
        namePanel = new JPanel(new FlowLayout());
        nameLabel = new JLabel("Name");
        namePanel.add(nameLabel);
        nameText = new JTextField(15);
        namePanel.add(nameText);
        valuePanel = new JPanel(new FlowLayout());
        valueLabel = new JLabel("Value");
        valuePanel.add(valueLabel);
        valueText = new JTextField(15);
        valuePanel.add(valueText);
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(namePanel, BorderLayout.NORTH);
        infoPanel.add(valuePanel, BorderLayout.SOUTH);
        
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
        loweringValuePanel = new JPanel (new FlowLayout());
        loweringValueLabel = new JLabel("Decreasing value");
        loweringValueText = new JTextField(20);
        negotiationStrategyPropertiesPanel.add(loweringValuePanel);
        loweringValuePanel.add(loweringValueLabel);
        loweringValuePanel.add(loweringValueText);
        minimalPricePanel = new JPanel(new FlowLayout());
        minimalPriceLabel = new JLabel("Minimal price");
        minimalPriceText = new JTextField(20);
        negotiationStrategyPropertiesPanel.add(minimalPricePanel);
        minimalPricePanel.add(minimalPriceLabel);
        minimalPricePanel.add(minimalPriceText);
        
        askInformationPanel = new JPanel(new FlowLayout());
        askInformationLabel = new JLabel("Ask for max price");
        String[] shareInformationOptions = {"Never ask","Always ask"};
        askInformationCB = new JComboBox(shareInformationOptions);
        negotiationStrategyPropertiesPanel.add(askInformationPanel);
        askInformationPanel.add(askInformationLabel);
        askInformationPanel.add(askInformationCB);
        trustLabel = new JLabel("Level of trust");
        String[] trustLevel = {"Always trust", "Sometimes trust", "Never trust"};
        trustCB = new JComboBox(trustLevel);
        askInformationPanel.add(trustLabel);
        askInformationPanel.add(trustCB);
        
        
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
            try{
            String title = nameText.getText().trim();
            String value = valueText.getText().trim();
            String option = (String) askInformationCB.getSelectedItem();
            String trust = (String) trustCB.getSelectedItem();
            properties = new ArrayList<>();
            properties.add("0");
            properties.add(value);
            
            
            myAgent.updateCatalogue(title, properties);
            
            
            
            CreateSellerAgentWindow.nameOfItemsModel.addElement(title);
            CreateSellerAgentWindow.valueOfItemsModel.addElement(value);
            
            if (loweringValueText.getText() == null){
                properties.add("0");
            }else{
                properties.add(Lowering_Value.addLoweringValue());
            }
            if (minimalPriceText.getText() == null){
                properties.add("0");
            }else{
                properties.add(Minimal_Price.addMinimalPrice());
            }
            properties.add(option);
            properties.add(trust);
            
            nameText.setText(null);
            valueText.setText(null);
            loweringValueText.setText(null);
            minimalPriceText.setText(null);
            
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
