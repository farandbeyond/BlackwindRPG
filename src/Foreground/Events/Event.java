/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Event implements Runnable{
    ArrayList<EventSegment> segments;
    String name;
    int currentSegment;
    boolean reTrigger, triggered;
    boolean sent;
    public Event(boolean repeatable,String name){
        this.name = name;
        segments = new ArrayList<>();
        currentSegment = -1;
        reTrigger = repeatable;
        triggered = false;
        sent = false;
    }
    public void addSegment(EventSegment e){
        segments.add(e);
    }
    public String nextSegment(Blackwind b,Inventory i, Party p){
        currentSegment++;
        //System.out.println(currentSegment);
        String segmentData = segments.get(currentSegment).activate(b,i,p);
        if(segmentData.equals("falseFlag!!")){
            System.out.println("False Flag Found!");
            findSegment(FalseMarkerSegment.class,((FlagCheckSegment)segments.get(currentSegment)).getMarkID());
            segmentData="adv!!";
            //currentSegment++;
        }
        return segmentData;
    }
    
    public EventSegment getActiveSegment(){
        return segments.get(currentSegment);
    }
    public int getCurrentSegment(){
        return currentSegment;
    }
    public void findSegment(Class eventType, int markID){
        if(segments.get(currentSegment).getClass()==eventType){
            System.out.println("Mark found");
            if(((FalseMarkerSegment)getActiveSegment()).getMarkID()==markID){
                System.out.println("Match found");
                //currentSegment++;
                //System.out.println(segments.get(currentSegment));
                sent = true;
                return;
            }
        }
        currentSegment++;
        //System.out.println(currentSegment);
        
        //currentSegment++;
        findSegment(eventType, markID);
    }
    
    public void reset(){
        currentSegment = -1;
        triggered = true;
    }
    public boolean getSent(){return sent;}
    public void deSend(){sent = false;}
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public boolean reTriggerable(){return reTrigger;}
    public boolean triggered(){return triggered;}

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
