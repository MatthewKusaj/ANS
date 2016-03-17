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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matthew
 */
public class SellerItemsGui extends Window{
    
    private JPanel panel;
    private JPanel namePanel;
    private JPanel utilityPanel;
    private JPanel valuePanel;
    
    public SellerAgent myAgent;
    
    private JLabel nameLabel;
    private JLabel utilityLabel;
    private JLabel valueLabel;
    
    private JTextField nameText;
    private JTextField utilityText;
    private JTextField valueText;

    public SellerItemsGui(String tit, int width, int height, SellerAgent a) {
        super(tit, width, height, SellerAgent.getInstance());
        
        myAgent = a;
        initiWidgets();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initiWidgets() {
        getWindow().setResizable(false);
        
        panel = new JPanel(new BorderLayout());
        namePanel = new JPanel(new FlowLayout());
        nameLabel = new JLabel("Name");
        namePanel.add(nameLabel);
        nameText = new JTextField(15);
        namePanel.add(nameText);
        utilityPanel = new JPanel(new FlowLayout());
        utilityLabel = new JLabel("Utility");
        utilityPanel.add(utilityLabel);
        utilityText = new JTextField(15);
        valuePanel = new JPanel(new FlowLayout());
        valueLabel = new JLabel("Value");
        valuePanel.add(valueLabel);
        valueText = new JTextField(15);
        
    }
    
    
    
}
