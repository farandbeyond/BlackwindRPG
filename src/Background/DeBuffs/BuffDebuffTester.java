/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.DeBuffs;

import Background.BattleEntity;
import Background.BattleEntityLoader;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class BuffDebuffTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("Creation of entities and effects");
        BattleEntity tester = BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY);
        Effect StrBuff = Effect.effectLoader(Effect.BUFF, StatID.STR, Buff.TWENTYFIVEP, "DevMagic");
        Effect IntDebuff = Effect.effectLoader(Effect.DEBUFF, StatID.INT, Buff.TENP, "DevMagic");
        text("----------Test02----------");
        text("Assigning buff/debuff to entities");
        tester.addEffect(StrBuff);
        tester.addEffect(IntDebuff);
        text("----------Test03----------");
        text("Assigning buff/debuff to entities");
        tester.printAllBaseStats();
        tester.printAllStats();
        tester.printAllEffects();
        text("----------Test03----------");
        text("Tick effects down to 0");
        //tester.printAllEffectDurations();
        tester.tickAllEffects();
        //tester.printAllEffectDurations();
        tester.tickAllEffects();
        tester.tickAllEffects();
        tester.tickAllEffects();
        tester.tickAllEffects();
        tester.printAllBaseStats();
        tester.printAllStats();
        //tester.printAllEffectDurations();
        tester.printAllEffects();
        tester.tickAllEffects();
        tester.printAllEffects();
        text("----------Test03----------");
        text("Tick effects down to 0");
    }
    public static void text(String text){
        System.out.println(text);
    }
}
