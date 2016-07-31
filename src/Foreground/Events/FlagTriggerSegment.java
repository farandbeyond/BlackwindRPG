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
public class FlagTriggerSegment extends EventSegment{
    int flag;
    FlagTriggerSegment(int flagToTrigger){
        this.flag = flagToTrigger;
    }
    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        EventFlags.triggerFlag(flag);
        return "adv!!";
    }
    
}
