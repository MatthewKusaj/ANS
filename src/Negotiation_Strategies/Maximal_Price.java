package Negotiation_Strategies;

import GUI.BuyerItemsGui;

/**
 *
 * @author Mateusz Kusaj
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
