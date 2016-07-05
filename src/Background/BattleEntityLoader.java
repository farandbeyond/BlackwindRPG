/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;

import Background.BattleActions.BattleActionLoader;



/**
 *
 * @author Connor
 */
public class BattleEntityLoader {
    public static final int
            TESTENTITY=0,
            WILSON=1,
            HUNTRESS=2,
            ROLAND=3,
            ENEMYONE=100,
            HEALBOT=101,
            GOBLIN=102,
            BAT=103;
            
    public static BattleEntity loadEntity(int entityID){
        switch(entityID){                          //hp,    mp,   str,   dex,   vit,   int,   res,     name,    element            level/xp
            case TESTENTITY: return new BattleEntity(20,4.8,10,3.6,10,1.0,10,1.1,10,2.0,10,0.5,10,1.0,"Player",ElementHandler.NEUTRAL,1,0);
            case WILSON:     return new BattleEntity(15,2.3,20,5.3,5 ,0.3, 8,2.5,7 ,0.9,14,2.7,10,1.8,"Wilson",ElementHandler.NEUTRAL,1,0);
            case HUNTRESS:   return new BattleEntity(15,1.4,16,2.4,6 ,0.6,15,3.4,5 ,1.1, 5,0.6, 6,0.7,"Huntress",ElementHandler.NEUTRAL,1,0); 
            case ROLAND:     return new BattleEntity(30,3.3,05,1.0,10,1.3, 5,1.4,13,2.4, 3,0.3, 9,0.4,"Roland",ElementHandler.NEUTRAL,1,0); 
                                                   //hp,mp,str,dex,vit,int,res   name             element          intendedLevel, expDropped
            case ENEMYONE:   return new BattleEntity(20,10, 6,  6,  6,  6,  6,"Training Dummy",ElementHandler.EARTH,1,50);
            case HEALBOT:    return new BattleEntity(10,50, 5,  5,  3, 15, 10,"Soraka",        ElementHandler.LIGHT,1,20);
            case GOBLIN:     return new BattleEntity(25,10, 7,  3,  8,  2,  2,"Goblin",        ElementHandler.FIRE, 1,25);
            case BAT:        return new BattleEntity(10,10, 4,  14, 6,  5,  8,"Bat",           ElementHandler.AIR,  1,10);
        }
        return null;
    }
    public static BattleEntity loadEntityWithSkills(int entityID){
        BattleEntity enemyToLoad = loadEntity(entityID);
        switch(entityID){
            case ENEMYONE:
                for(int i=0;i<8;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
                for(int i=0;i<2;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAttack(enemyToLoad,enemyToLoad.getStat(StatID.STR),enemyToLoad.getStat(StatID.DEX)));
                break;
            case HEALBOT:
                for(int i=0;i<7;i++){
                    enemyToLoad.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
                }
                for(int i=0;i<3;i++){
                    enemyToLoad.addSkill(BattleActionLoader.loadAttack(enemyToLoad, enemyToLoad.getStat(StatID.STR), 4));
                }
            break;
            case GOBLIN:
                for(int i=0;i<2;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAction(BattleActionLoader.ENRAGE));
                for(int i=0;i<8;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAttack(enemyToLoad, enemyToLoad.getStat(StatID.STR), 5));
                break;
            case BAT:
                for(int i=0;i<5;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAction(BattleActionLoader.GUST));
                for(int i=0;i<5;i++)
                    enemyToLoad.addSkill(BattleActionLoader.loadAttack(enemyToLoad,enemyToLoad.getStat(StatID.STR),enemyToLoad.getStat(StatID.DEX)));
                
        }
        return enemyToLoad;
    }
}
