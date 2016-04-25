package Negotiation_Strategies;

import GUI.SellerItemsGui;

/**
 *
 * @author Mateusz Kusaj
 */
public class Lowering_Value {
    public static String loweringValue;
    public void LoweringValue(){
        
    }
    public static String addLoweringValue(){
        loweringValue = SellerItemsGui.loweringValueText.getText();
        return loweringValue;
    }
}
