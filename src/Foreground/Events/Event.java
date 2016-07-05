/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Event {
    ArrayList<EventSegment> segments;
    int currentSegment;
    boolean reTrigger, triggered;
    public Event(boolean repeatable){
        segments = new ArrayList<>();
        currentSegment = -1;
        reTrigger = repeatable;
        triggered = false;
    }
    public void addSegment(EventSegment e){
        segments.add(e);
    }
    public String nextSegment(Inventory i, Party p){
        
        currentSegment++;
        //System.out.println(currentSegment);
        return segments.get(currentSegment).activate(i,p);
        
    }
    
    public void reset(){
        currentSegment = -1;
        triggered = true;
    }
    public boolean reTriggerable(){return reTrigger;}
    public boolean triggered(){return triggered;}
}
