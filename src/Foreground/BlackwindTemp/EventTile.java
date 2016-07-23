/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.Events.Event;
import Foreground.Events.EventReader;

/**
 *
 * @author Connor
 */
public class EventTile extends Tile{
    Event event;

    public EventTile(int id, String eventName, String mapName) {
        super(id);
        this.event = EventReader.loadEvent(eventName,mapName);
    }

    @Override
    public void activate(Blackwind b, Sprite mc, Map m, Party p, Inventory i) {
        try{
            if(!event.reTriggerable()&&event.triggered())
                return;
            b.textBox.loadEvent(event,b);
            Blackwind.gameState = Blackwind.EVENT;
        }catch(NullPointerException e){
            
        }
        //System.out.println("Event in trigger");
    }

    @Override
    public String getDetails() {
        return String.format("%d/e/%s", getID(),event.getName());
    }
    
    
    
}
