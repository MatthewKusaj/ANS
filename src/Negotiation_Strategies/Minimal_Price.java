package Negotiation_Strategies;

import GUI.SellerItemsGui;

/**
 *
 * @author Mateusz Kusaj
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
