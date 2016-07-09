/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;

/**
 *
 * @author Connor
 */
public class TextSegment extends EventSegment{
    String[] text;
    public TextSegment(String line1, String line2, String line3, String line4){
        text = new String[4];
        text[0]=line1;
        text[1]=line2;
        text[2]=line3;
        text[3]=line4;
    }
    public String activate(Blackwind b,Inventory i, Party p) {
        return String.format("%s\n%s\n%s\n%s", text[0],text[1],text[2],text[3]);
    }
}
