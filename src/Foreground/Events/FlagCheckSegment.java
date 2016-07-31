/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;
import Foreground.BlackwindTemp.EventFlags;

/**
 *
 * @author Connor
 */
public class FlagCheckSegment extends EventSegment{
    int flag, markID;
    FlagCheckSegment(int markID,int flagToCheck){
        this.markID = markID;
        flag = flagToCheck;
    }

    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        if(EventFlags.checkFlag(flag))
            return "adv!!";
        else
            return "falseFlag!!";
    }
    public int getMarkID(){return markID;}
}
