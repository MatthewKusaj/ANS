/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negotiation_Strategies;

import GUI.SellerItemsGui;

/**
 *
 * @author Admin
 */
public class Minimal_Price {
    public static String minimalPrice; 
    public void Minimal_Price(){
        
    }
    public static String addMinimalPrice(){
        minimalPrice = SellerItemsGui.minimalPriceText.getText();
        return minimalPrice;
    }
    
}
