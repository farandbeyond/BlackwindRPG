/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.DeBuffs;

import Background.BattleEntity;

/**
 *
 * @author Connor
 */
public class Buff extends Effect{
    public static final int
            TENP=1,
            TWENTYFIVEP=2,
            FIFTYP=3;
            
            
    int stat;
    double increase;
    int savedIncrease;
    
    public Buff(String name, String source, int duration, int stat, double increase){
        super(name,source,duration);
        this.stat=stat;
        this.increase=increase;
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
    }

    @Override
    public void onTick(BattleEntity e) {
       duration--;
    }
    
    
}
