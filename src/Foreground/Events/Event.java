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
    public Event(){
        segments = new ArrayList<>();
        currentSegment = -1;
    }
    public void addSegment(EventSegment e){
        segments.add(e);
    }
    public String nextSegment(Inventory i, Party p){
        try{
            currentSegment++;
            //System.out.println(currentSegment);
            return segments.get(currentSegment).activate(i,p);
        }catch(IndexOutOfBoundsException e){
            //System.out.println("Error");
            //System.out.println(e);
            return "break";
        }
    }
}
