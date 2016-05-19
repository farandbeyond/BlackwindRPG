/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;
import Background.ElementHandler;

/**
 *
 * @author Connor
 */
public class DamageItem extends Item{
    private int damage, element;
    
    public DamageItem(int id, String name, String description, int quantity, int maxQuantity, int damage, int element, int shopValue){
        super(id, name, description, quantity, maxQuantity, shopValue);
        this.damage = damage;
        this.element = element;
    }
    
    @Override
    public String use(BattleEntity target) {
        int damage = 0;
        damage+=this.damage;
        if(ElementHandler.handler(element, target.getElement())<0)
            damage*=ElementHandler.handler(element, target.getElement());
        else{
            damage*=ElementHandler.handler(element, target.getElement());
            if(damage<=0)
                damage = 1;
        }
        target.damage(damage);
        reduceQuantity();
        return String.format("%s dealt %d damage to %s", getName(), damage,target.getName());
    }
    //gets
    public int getDamage(){return damage;}
    public int getElement(){return element;}
}
