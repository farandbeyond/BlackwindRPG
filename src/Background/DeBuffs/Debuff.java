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
public class Debuff extends Effect{
    public static final int
            TENP=1,
            TWENTYFIVEP=2,
            FIFTYP=3;
    private int stat;
    private double decrease;
    private int savedDecrease;
    public Debuff(String name, String source, int duration, int stat, double decrease){
        super(name,source,duration);
        this.stat=stat;
        this.decrease=decrease;
    }
    @Override
    public void assign(BattleEntity e) {
        savedDecrease = (int)(e.getBaseStat(stat)*decrease);
        //System.out.println(savedDecrease);
        e.reduceStat(stat,savedDecrease);
    }
    @Override
    public void remove(BattleEntity e) {
        e.increaseStat(stat, savedDecrease);
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
    //gets
    public int getDecreaseLevel(){
        if(decrease==.1)
            return TENP;
        if(decrease==.25)
            return TWENTYFIVEP;
        if(decrease==.5)
            return FIFTYP;
        return 0;
    }
    public int getType(){return DEBUFF;}
    public int getStat(){return stat;}
    public double getDecrease(){return decrease;}
    public int getSavedDecrease(){
        if(savedDecrease!=0)
            return savedDecrease;
        return 0;
    }
    public String toString(){
        return String.format("%s, from %s. Currently decreasing %s by %d\n", getName(),getSource(),StatID.getStatName(stat),getSavedDecrease());
    }
    
    
}
