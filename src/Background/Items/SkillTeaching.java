/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleActions.BattleAction;
import Background.BattleActions.BattleActionLoader;
import Background.BattleEntity;

/**
 *
 * @author Connor
 */
public class SkillTeaching extends Item{
    BattleAction b;
    public SkillTeaching(int id, String name, String description, int quantity, int maxQuantity, int battleActionID, int shopValue) {
        super(id, name, description, quantity, maxQuantity, shopValue);
        b = BattleActionLoader.loadAction(battleActionID);
    }

    @Override
    public String use(BattleEntity target) {
        reduceQuantity();
        target.addSkill(b);
        return String.format("Taught %s %s", target.getName(),b.getName());
    }

    public String toString(){
        return String.format("%s. Teaches %s as a skill", super.toString(),b.getName());
    }

    
}
