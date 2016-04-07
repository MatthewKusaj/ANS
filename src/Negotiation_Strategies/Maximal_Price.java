/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negotiation_Strategies;

import GUI.BuyerItemsGui;

/**
 *
 * @author Admin
 */
public class Maximal_Price {
    public static String maximalPrice; 
    public void Maximal_Price(){
        
    }
    public static String addMaximalPrice(){
        maximalPrice = BuyerItemsGui.maximalPriceText.getText();
        return maximalPrice;
    }
    
}
