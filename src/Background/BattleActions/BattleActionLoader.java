/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.DeBuffs.Buff;
import Background.DeBuffs.Effect;
import Background.ElementHandler;
import Background.Items.Item;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class BattleActionLoader {
    public static final int 
            FIREBALL=0, ICEBLAST=1, QUAKE=2,GUST=3,
            CURE=100,RAISE=101,
            ATTACK=200, SLICE=201,
            BRAVERY=300,SHELTER=301,
            RAISEGUARD = 400, ENRAGE=401;
    public static BattleAction loadAttack(BattleEntity caster){
        return new PhysicalAction(caster,"Attack","The basic attack action everyone has",1,1,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
    }
    public static BattleAction noAction(BattleEntity caster){
        return new DeathAction(caster,"Dead","Caster was dead at round start");
    }
    public static BattleAction loadAttack(BattleEntity caster, int newBase, int newRoll){
        return new PhysicalAction(caster,"Attack","The basic attack action everyone has",newBase,newRoll,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
    }
    public static BattleAction loadItemAction(BattleEntity caster, Item i){
        return new UseItem(caster,i);
    }
    public static BattleAction loadAction(int actionID){
        switch(actionID){
            case FIREBALL   :return new DamageSpell("Fireball","deals 15-20 Fire damage",15,5,ElementHandler.FIRE,5);
            case ICEBLAST   :return new DamageSpell("Ice Blast","deals 15-20 Water damage",15,5,ElementHandler.WATER,5);
            case QUAKE      :return new DamageSpell("Quake","deals 15-20 Earth damage",15,5,ElementHandler.EARTH,5);
            case GUST       :return new DamageSpell("Gust","deals 15-20 Air damage",15,5,ElementHandler.AIR,5);
            case CURE       :return new HealingSpell("Cure","Heals an ally for 10-20hp",10,10,false,5);
            case RAISE      :return new HealingSpell("Raise","Revives an ally with 5hp",5,0,true,20);
            case ATTACK     :return new PhysicalAction("Attack","The basic attack action everyone has",1,1,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);    
            case SLICE      :return new PhysicalAction("Slice","A quick slash dealing 10-15dmg",10,5,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
            case BRAVERY    :return new EffectSpell("Bravery","25% Str buff",10,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP,"Bravery"));
            case SHELTER    :return new EffectSpell("Shelter","25% Vit buff",10,Effect.effectLoader(Effect.BUFF, StatID.VIT, Buff.TWENTYFIVEP, "Shelter"));
        //enemyExclusive
            case RAISEGUARD :return new SelfBuff("Raise Guard","Defense increases",0,Effect.effectLoader(Effect.BUFF, StatID.VIT, Buff.FIFTYP, "Raise Guard",1));
            case ENRAGE     :return new SelfBuff("Enrage"     ,"Strength increases",0,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP, "Pure Goblinoit Rage",3));
        }
        return null;
    }
}
