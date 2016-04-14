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
    private final int healValue;
    private final boolean revives;
    public HealingItem(int id,String name, String description, int quantity, int maxQuantity, int shopValue, int healValue, boolean revives){
        super(id,name,description,quantity,maxQuantity,shopValue);
        this.healValue=healValue;
        this.revives=revives;
    }
    
    @Override
    public void use(BattleEntity target) {
        reduceQuantity();
        if(revives){
            target.raise(healValue);
        }else{
            target.heal(healValue);
        }
    }
    
    public int getHealValue(){return healValue;}
    public boolean getRevives(){return revives;}
}
