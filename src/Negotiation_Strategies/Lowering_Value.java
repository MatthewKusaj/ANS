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
public class Lowering_Value {
    public static String loweringValue;
    public void LoweringValue(){
        
    }
    public static String addLoweringValue(){
        loweringValue = SellerItemsGui.loweringValueText.getText();
        return loweringValue;
    }
}
