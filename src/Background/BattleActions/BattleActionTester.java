/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.BattleEntityLoader;
import Background.DeBuffs.Buff;
import Background.DeBuffs.Effect;
import Background.ElementHandler;
import Background.StatID;


/**
 *
 * @author Connor
 */
public class BattleActionTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("Creation of battle actions");
        BattleEntity tester = BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY);
        BattleEntity target = BattleEntityLoader.loadEntity(BattleEntityLoader.ENEMYONE);
        BattleAction[] actions = new BattleAction[10];
        actions[0]=new DamageSpell(tester,"Fireball","deals 15-20 firedamage",15,5,ElementHandler.FIRE,12);
        actions[1]=new HealingSpell(tester,"Cure","heals 10-15 hp",10,5,10);
        actions[2]=new PhysicalAction(tester,"Slice","A quick slash dealing 10-15dmg",10,5,StatID.STR,StatID.VIT,ElementHandler.NEUTRAL);
        actions[3]=new EffectSpell(tester,"Bravery","25% Str buff",0,Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP, tester.getName()));
        text("----------Test02----------");
        text("Using battleActions on the target");
        tester.printAllStats();
        tester.printHpAndMp();
        System.out.println("-Fireball: Not Enough MP");
        if(tester.canCast((Spell)actions[0]))
            actions[0].execute(target);
        target.printHpAndMp();
        System.out.println("-Cure: Enough MP");
        if(tester.canCast((Spell)actions[1]))
            actions[1].execute(target);
        target.printHpAndMp();
        System.out.println("-Slice: No cost");
        //if(tester.canCast((Spell)actions[2]))
        //this throws "ClassCastException" for future reference
            actions[2].execute(target);
        target.printHpAndMp();
        System.out.println("-Bravery: Enough MP");
        if(tester.canCast((Spell)actions[3]))
            actions[3].execute(target);
        target.printHpAndMp();
        System.out.println("-");
        tester.printAllStats();
        tester.printHpAndMp();
        text("------");
        target.printAllStats();
        target.printHpAndMp();
        
    }
    public static void text(String text){
        System.out.println(text);
    }
}
