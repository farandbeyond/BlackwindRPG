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
public class RestSegment extends EventSegment{

    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        p.restAllMembers();
        return "adv!!";
    }
    
}
