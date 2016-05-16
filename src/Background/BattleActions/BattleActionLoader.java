/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.DeBuffs.Buff;
import Background.DeBuffs.Effect;
import Background.ElementHandler;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class BattleActionLoader {
    public static final int 
            FIREBALL=0, ICEBLAST=1, QUAKE=2,GUST=3,
            CURE=100,RAISE=101,
            SLICE=200,
            BRAVERY=300;
    public static BattleAction loadAction(int actionID){
        switch(actionID){
            case FIREBALL   :return new DamageSpell("Fireball","deals 15-20 Fire damage",15,5,ElementHandler.FIRE,12);
            case ICEBLAST   :return new DamageSpell("Ice Blast","deals 15-20 Water damage",15,5,ElementHandler.WATER,12);
            case QUAKE      :return new DamageSpell("Quake","deals 15-20 Earth damage",15,5,ElementHandler.EARTH,12);
            case GUST       :return new DamageSpell("Gust","deals 15-20 Air damage",15,5,ElementHandler.AIR,12);
            case CURE       :return new HealingSpell("Cure","Heals an ally for 10-20hp",10,10,false,5);
            case RAISE      :return new HealingSpell("Raise","Revives an ally with 5hp",5,0,true,20);
                
            case SLICE      :return new PhysicalAction("Slice","A quick slash dealing 10-15dmg",10,5,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
            case BRAVERY    :return new EffectSpell("Bravery","25% Str buff",10,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP,"Bravery"));
        }
        return null;
    }
}