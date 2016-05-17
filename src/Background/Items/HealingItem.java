/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;

/**
 *
 * @author Connor
 */
public class HealingItem extends Item{
    public static final int
            HP=0,
            MP=1,
            HPMP=2;
    private final int healValue,type;
    private final boolean revives;
    public HealingItem(int id,String name, String description, int quantity, int maxQuantity, int healValue, int type, boolean revives, int shopValue){
        super(id,name,description,quantity,maxQuantity,shopValue);
        this.healValue=healValue;
        this.revives=revives;
        this.type=type;
    }
    
    @Override
    public String use(BattleEntity target) {
        reduceQuantity();
        if(revives){
            target.raise(healValue);
            return String.format("%s raised %s for %d",getName(), target.getName(),healValue);
        }else{
            switch(type){
                case HPMP:
                    target.regainMp(healValue);
                    target.heal(healValue);
                    return String.format("%s regained %d Hp and Mp from %s", target.getName(),healValue,getName());
                case HP:
                    target.heal(healValue);
                    return String.format("%s regained %d Hp from %s", target.getName(),healValue,getName());
                case MP:
                    target.regainMp(healValue);
                    return String.format("%s regained %d Mp from %s", target.getName(),healValue,getName());
            }
        }
        return "Unknown error";
    }
    //gets
    public int getHealValue(){return healValue;}
    public int getType(){return type;}
    public boolean getRevives(){return revives;}
}
