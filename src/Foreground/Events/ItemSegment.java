/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Items.Item;
import Background.Items.ItemLoader;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;

/**
 *
 * @author Connor
 */
public class ItemSegment extends EventSegment{
    int itemID, quantity;
    
    public ItemSegment(int itemID, int quantity){
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public String activate(Blackwind b, Inventory i, Party p) {
        Item it = ItemLoader.loadItem(itemID, quantity);
        if(i.canAdd(it)){
            i.add(it);
            return String.format("Received %s x%d", it.getName(),quantity);
        }
        return "Inventory is full.";
    }
    
    
}
