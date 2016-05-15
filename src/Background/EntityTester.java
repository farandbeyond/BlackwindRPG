/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;

import Background.DeBuffs.Debuff;
import Background.DeBuffs.Effect;

/**
 *
 * @author Connor
 */
public class EntityTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("Loading the first entity");
        BattleEntity wilson = BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY);
        text("----------Test02----------");
        text("XP checker");
        wilson.xpToLevel();
        System.out.printf("Wilson has %d exp until his next level\n",wilson.getTotalExpRequiredToLevel());
        text("----------Test03----------");
        text("Level up Wilson");
        wilson.printAllStats();
        wilson.giveExp(100);
        wilson.printAllStats();
        text("----------Test04----------");
        text("Reset wilson and give him enough xp to level twice");
        wilson = BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY);
        wilson.giveExp(500);
        wilson.printAllStats();
        text("----------Test05----------");
        text("Cause wilson to take damage, then heal");
        wilson.printHpAndMp();
        text("10 damage");
        wilson.damage(10);
        wilson.printHpAndMp();
        text("8 heal");
        wilson.heal(8);
        wilson.printHpAndMp();
        text("----------Test06----------");
        text("Cause wilson to take fatal damage. try to heal him, then raise him after");
        wilson.damage(50);
        wilson.printHpAndMp();
        wilson.heal(50);
        wilson.printHpAndMp();
        wilson.raise(40);
        wilson.printHpAndMp();
        text("----------Test07----------");
        text("Heal to full");
        wilson.damage(50);
        wilson.printHpAndMp();
        wilson.healToFull();
        wilson.printHpAndMp();
        text("----------Test07----------");
        text("Load an enemy");
        BattleEntity enemy = BattleEntityLoader.loadEntity(BattleEntityLoader.ENEMYONE);
        enemy.printAllStats();
        text("----------Test07----------");
        text("Do some battle stuff");
        wilson.damage(4);
        enemy.addEffect(Effect.effectLoader(Effect.DEBUFF, StatID.VIT, Debuff.TWENTYFIVEP, wilson.getName()));
        enemy.damage(10);
        wilson.printHpAndMp();
        wilson.printAllEffects();
        wilson.printAllEffectDurations();
        enemy.printHpAndMp();
        enemy.printAllEffects();
        enemy.printAllEffectDurations();
        
    }
    public static void text(String text){
        System.out.println(text);
    }
}
