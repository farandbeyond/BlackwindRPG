/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.DeBuffs;

import Background.BattleEntity;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class Buff extends Effect{
    public static final int
            TENP=1,
            TWENTYFIVEP=2,
            FIFTYP=3;
            
            
    private int stat;
    private double increase;
    private int savedIncrease;
    
    public Buff(String name, String source, int duration, int stat, double increase){
        super(name,source,duration);
        this.stat=stat;
        this.increase=increase;
        savedIncrease=0;
    }
    
    public int getStat(){return stat;}
    public double getIncrease(){return increase;}
    public int getSavedIncrease(){
        if(savedIncrease!=0)
            return savedIncrease;
        return 0;
    }
    @Override
    public void assign(BattleEntity e) {
        savedIncrease = (int)(e.getStat(stat)*increase);
        System.out.println(savedIncrease);
        e.increaseStat(stat,savedIncrease);
    }

    @Override
    public void remove(BattleEntity e) {
        e.reduceStat(stat, savedIncrease);
        //e.removeEffect(this);
    }

    @Override
    public void onTick(BattleEntity e) {
       setDuration(getDuration()-1);
       if(getDuration()<0)
           setDuration(0);
       //if(duration==0){
       //    remove(e);
       //}
    }
    public String toString(){
        return String.format("%s, from %s. Currently increasing %s by %d\n", getName(),getSource(),StatID.getStatName(stat),getSavedIncrease());
    }
    
}
