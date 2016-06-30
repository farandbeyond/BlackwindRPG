/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Items.ItemLoader;
import java.awt.Font;

/**
 *
 * @author Connor
 */
public class TempTextBox {
    public Inventory i;
    public TempTextBox(){
        i = new Inventory(5);
    }
    public Inventory getInv(){return i;}
    public static String display;
    public static void display(String displayText){
        display = displayText;
    }
    public static void printDisplay(){
        System.out.print(display);
    }
    public static String checkSpecialCharacters(String text){
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='$'){
                
            }
        }
        return text;
    }
    public static void main(String[] args){
        TempTextBox t = new TempTextBox();
        Event e = new Event();
        e.addSegment(new TextSegment("My name is wilson rose","","",""));
        e.addSegment(new ItemSegment(ItemLoader.POTION,5));
        e.addSegment(new ItemSegment(ItemLoader.BRONZESWORD,1));
        e.addSegment(new TextSegment("this is going to be a very long statment repeated four times","this is going to be a very long statment repeated four times","this is going to be a very long statment repeated four times","this is going to be a very long statment repeated four times"));
        while(true){
            String text = e.nextSegment(t.getInv(),null);
            text = checkSpecialCharacters(text);
            if(text.equals("break"))
                break;
            else
                System.out.println(text);
        }
        for(int x=0;x<t.getInv().getNumberOfItemsInInventory();x++){
            System.out.println(t.getInv().getItem(x).superToString());
        }
    }
}
