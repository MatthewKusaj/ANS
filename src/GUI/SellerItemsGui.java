/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agent_Management.SellerAgent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matthew
 */
public class SellerItemsGui extends Window{
    
    private JPanel panel;
    private JPanel infoPanel;
    private JPanel namePanel;
    private JPanel utilityPanel;
    private JPanel valuePanel;
    private JPanel buttonsPanel;
    
    public SellerAgent myAgent;
    
    private JLabel nameLabel;
    private JLabel utilityLabel;
    private JLabel valueLabel;
    
    private JTextField nameText;
    private JTextField utilityText;
    private JTextField valueText;
    
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
        utilityPanel = new JPanel(new FlowLayout());
        utilityLabel = new JLabel("Utility");
        utilityPanel.add(utilityLabel);
        utilityText = new JTextField(15);
        utilityPanel.add(utilityText);
        valuePanel = new JPanel(new FlowLayout());
        valueLabel = new JLabel("Value");
        valuePanel.add(valueLabel);
        valueText = new JTextField(15);
        valuePanel.add(valueText);
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(namePanel, BorderLayout.NORTH);
        infoPanel.add(utilityPanel, BorderLayout.CENTER);
        infoPanel.add(valuePanel, BorderLayout.SOUTH);
        
        buttonsPanel = new JPanel(new BorderLayout());
        acceptButton = new JButton("Accept");
        buttonsPanel.add(acceptButton, BorderLayout.WEST);
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(cancelButton, BorderLayout.EAST);
        
        panel = new JPanel(new BorderLayout());
        panel.add(infoPanel, BorderLayout.CENTER);
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
            String utility = utilityText.getText().trim();
            String value = valueText.getText().trim();
            
            properties = new ArrayList<>();
            properties.add("$u" + utility + "u$");
            properties.add("$v" + value + "v$");
            
            myAgent.updateCatalogue(title, properties);
            
            nameText.setText(null);
            utilityText.setText(null);
            valueText.setText(null);
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
