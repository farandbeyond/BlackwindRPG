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
            FIRE=1, WATER=2, EARTH=3,WIND=4, LIGHT=5, DARK =6,
            CURE=100,RAISE=101,
            ATTACK=200, SLICE=201,
            BRAVERY=300,SHELTER=301,
            RAISEGUARD = 400, ENRAGE=401;

    public static BattleAction loadAction(int actionID){
        switch(actionID){
            case FIRE       :return new DamageSpell(actionID,"Kasai","deals 5-10 Fire damage",5,5,ElementHandler.FIRE,5);
            case WATER      :return new DamageSpell(actionID,"Mizu","deals 5-10 Water damage",5,5,ElementHandler.WATER,5);
            case EARTH      :return new DamageSpell(actionID,"Setchi","deals 5-10 Earth damage",5,5,ElementHandler.EARTH,5);
            case WIND       :return new DamageSpell(actionID,"Kaze","deals 5-10 Air damage",5,5,ElementHandler.AIR,5);
            case LIGHT      :return new DamageSpell(actionID,"Hikari","deals 5-10 Light damage",5,5,ElementHandler.LIGHT,8);
            case DARK       :return new DamageSpell(actionID,"Yami","deals 5-10 Dark damage",5,5,ElementHandler.DARK,8);
            case CURE       :return new HealingSpell(actionID,"Cure","Heals an ally for 10-20hp",10,10,false,5);
            case RAISE      :return new HealingSpell(actionID,"Raise","Revives an ally with 5hp",5,0,true,20);
            case ATTACK     :return new PhysicalAction(actionID,"Attack","The basic attack action everyone has",1,3,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);    
            case SLICE      :return new PhysicalAction(actionID,"Slice","A quick slash dealing 10-15dmg",10,5,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
            case BRAVERY    :return new EffectSpell(actionID,"Bravery","25% Str buff",10,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP,"Bravery"));
            case SHELTER    :return new EffectSpell(actionID,"Shelter","25% Vit buff",10,Effect.effectLoader(Effect.BUFF, StatID.VIT, Buff.TWENTYFIVEP, "Shelter"));
        //enemyExclusive
            case RAISEGUARD :return new SelfBuff(actionID,"Raise Guard","Defense increases",0,Effect.effectLoader(Effect.BUFF, StatID.VIT, Buff.FIFTYP, "Raise Guard",1));
            case ENRAGE     :return new SelfBuff(actionID,"Enrage"     ,"Strength increases",0,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP, "Pure Goblinoit Rage",3));
        }
        return null;
    }
    
    public static BattleAction loadAttack(BattleEntity caster){
        return new PhysicalAction(0,caster,"Attack","The basic attack action everyone has",1,3,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
    }
    public static BattleAction noAction(BattleEntity caster){
        return new DeathAction(0,caster,"Dead","Caster was dead at round start");
    }
    public static BattleAction loadAttack(BattleEntity caster, int newBase, int newRoll){
        return new PhysicalAction(ATTACK,caster,"Attack","The basic attack action everyone has",newBase,newRoll,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
    }
    public static BattleAction loadItemAction(BattleEntity caster, Item i){
        return new UseItem(0,caster,i);
    }
}
