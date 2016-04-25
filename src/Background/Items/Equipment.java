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
public abstract class Equipment extends Item{
    private BattleEntity equipper;
    public Equipment(int id, String name, String description, int quantity, int maxQuantity, int shopValue){
        super(id,name,description,quantity,maxQuantity,shopValue);
    }
    public abstract void equip(BattleEntity target);
    public abstract void unEquip();
    public void setEquipper(BattleEntity equipper){this.equipper=equipper;}
    public BattleEntity getEquipper(){return equipper;}
    public String toString(){
        return super.toString();
    }
}
