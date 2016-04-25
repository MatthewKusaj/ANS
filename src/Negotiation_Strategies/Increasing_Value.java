package Negotiation_Strategies;

import GUI.BuyerItemsGui;

/**
 *
 * @author Mateusz Kusaj
 */
public class Increasing_Value {
    public static String increasingValue;
    public void IncreasingValue(){
        
    }
    public static String addIncreasingValue(){
        increasingValue = BuyerItemsGui.increasingValueText.getText();
        return increasingValue;
    }
}
