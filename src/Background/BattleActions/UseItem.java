/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.Items.Item;

/**
 *
 * @author Connor
 */
public class UseItem extends BattleAction{

    Item item;
    public UseItem(BattleEntity caster, Item i){
        super(caster,i.getName(),"Uses the item");
        item = i;
    }
    @Override
    public String execute(BattleEntity target) {return item.use(target);}
    @Override
    public int getCost() {return 0;}
    @Override
    public int getElement() {return 0;}
    public boolean targetsAllies(){
        if(item.getId()>=0&&item.getId()<=99){
            return true;
        }else{
            return false;
        }
    }
}
