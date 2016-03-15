/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 * Abstract class to create a generic window
 *
 * @author Matthew
 */
public abstract class Window implements ActionListener{
	
	private String title;
	private int width;
	private int height;
	private JFrame window;
	private Border border;
	/**
	 * Constructor for small window
	 * @param tit title
	 * @param width
	 * @param height
	 */
	public Window(String tit, int width, int height){
		title = tit;
		this.width = width;
		this.height = height;
		window = new JFrame(title);
	}
	/**
	 * Constructor for full screen window
	 * @param tit
	 * @param fullScreen true if page is full screen
	 */
	public Window(String tit, boolean fullScreen){
		title = tit;
		window = new JFrame(title);
		if(fullScreen == true){
			window.setExtendedState(Frame.MAXIMIZED_BOTH); 
			window.setMinimumSize(new Dimension(400,400));
			window.setDefaultCloseOperation(0);
		}
		window.setVisible(true);

	}
	/**
	 * Set small frame options to make layout fit correctly
	 */
	public void setFrameOptions(){
		window.setMinimumSize(new Dimension(width, height));
		window.pack();
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	/**
	 * 
	 * @return frame object of this window
	 */
	public JFrame getWindow(){
		return window;
	}
	/**
	 * 
	 * @return generic border for all widgets in application
	 */
	public Border getBorder(){
		border = BorderFactory.createLineBorder(Color.GRAY, 2);
		return border;
	}
}
