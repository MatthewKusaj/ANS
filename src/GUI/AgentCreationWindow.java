/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matthew
 */
public class AgentCreationWindow extends Window{
    
    private JPanel panel;
    
    private JTextField name;
    private JButton accept;
    
    public AgentCreationWindow (String tit, int width, int height)
	{
		super(tit, width, height);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
