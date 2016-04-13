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
public class Debuff extends Effect{
    public static final int
            TENP=1,
            TWENTYFIVEP=2,
            FIFTYP=3;
            
            
    int stat;
    double decrease;
    int savedIncrease;
    
    public Debuff(String name, String source, int duration, int stat, double decrease){
        super(name,source,duration);
        this.stat=stat;
        this.decrease=decrease;
    }
    
    @Override
    public void assign(BattleEntity e) {
        savedIncrease = (int)(e.getStat(stat)*decrease);
        System.out.println(savedIncrease);
        e.reduceStat(stat,savedIncrease);
    }

    @Override
    public void remove(BattleEntity e) {
        e.increaseStat(stat, savedIncrease);
    }

    @Override
    public void onTick(BattleEntity e) {
       duration--;
    }
    
    
}
