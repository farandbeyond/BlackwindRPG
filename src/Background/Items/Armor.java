/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class Armor extends Equipment{
    int primaryStat,secondaryStat;
    int primaryValue,secondaryValue;
    public Armor(int id, String name, String description, int quantity, int maxQuantity, int primaryStat, int primaryIncrease, int secondaryStat, int secondaryIncrease, int shopValue){
        super(id,name,description,quantity,maxQuantity,shopValue);
        this.primaryStat=primaryStat;
        this.primaryValue=primaryIncrease;
        this.secondaryStat=secondaryStat;
        this.secondaryValue=secondaryIncrease;
    }
    @Override
    public void equip(BattleEntity target) {
        this.equipper=target;
        equipper.increaseStat(primaryStat, primaryValue);
        equipper.increaseStat(secondaryStat, secondaryValue);
    }

    @Override
    public void unEquip() {
        equipper.reduceStat(primaryStat, primaryValue);
        equipper.reduceStat(secondaryStat, secondaryValue);
        this.equipper=null;
    }

    @Override
    public void use(BattleEntity target) {
        System.out.println("No use");
    }
    public String toString(){
        return String.format("%s\nincreases %s by %d\nincreases %s by %d", 
                super.toString(),
                StatID.getStatName(primaryStat),
                primaryValue,
                StatID.getStatName(secondaryStat),
                secondaryValue);
    }
    
}
