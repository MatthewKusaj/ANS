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
public class Increasing_Value {
    public static String increasingValue;
    public void IncreasingValue(){
        
    }
    public static String addIncreasingValue(){
        increasingValue = BuyerItemsGui.increasingValueText.getText();
        return increasingValue;
    }
}
