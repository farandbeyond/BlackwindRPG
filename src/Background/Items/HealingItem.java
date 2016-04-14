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
    public void use(BattleEntity target) {
        reduceQuantity();
        if(revives){
            target.raise(healValue);
        }else{
            switch(type){
                case HPMP:target.regainMp(healValue);
                case HP:target.heal(healValue);break;
                case MP:target.regainMp(healValue);break;
                
            }
        }
    }
    
    public int getHealValue(){return healValue;}
    public boolean getRevives(){return revives;}
}
