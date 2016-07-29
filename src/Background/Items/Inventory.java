/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Inventory {
    private ArrayList<Item> inventory;
    private int invSize;
    
    public Inventory(int size){
        this.invSize=size;
        inventory = new ArrayList<>();
    }
    //inventory managers
    public boolean canAdd(Item i){
        //if item exists in inventory
        if(inventory.isEmpty())
            return true;
        for(Item item:inventory){
            if(item.getId()==i.getId()){
                if(item.getQuantity()+i.getQuantity()<=item.getMaxQuantity()){
                    //and is not at max quantity, can add
                    return true;
                }else{
                    //is at max quantity, can not add
                    return false;
                }
            }
        }
        if(inventory.size()<invSize){
            //if item is not in inventory, and inventory has room, can add
            return true;
        }
        //if none of the above, cannot add
        return false;
    }
    public void add(Item i){
        for(Item item:inventory){
            if(item.getId()==i.getId()&&item.getQuantity()+i.getQuantity()<=item.getMaxQuantity()){
                item.restock(i.getQuantity());
                return;
            }
        }
        if(inventory.size()<invSize){
              inventory.add(i);
              updateInventory();
              return;
        }
        throw new Error("Couldnt add item");
    }
    public void remove(Item i){
        if(inventory.contains(i)){
            inventory.remove(i);
        }
    }
    public void remove(int slot){
        inventory.remove(slot);
    }
    public void swap(int swap, int swapTo){
        Item temp = inventory.get(swap);
        inventory.set(swap,inventory.get(swapTo));
        inventory.set(swapTo,temp);
    }
    public void useItem(int i, BattleEntity target){
        inventory.get(i).use(target);
    }
    public void dropItem(int i){
        inventory.remove(i);
    }
    public void updateInventory(){
        for(int i=inventory.size()-1;i>=0;i--){
            if(inventory.get(i).getQuantity()==0){
                inventory.remove(i);
            }
        }
    }
    //prints
    public void printAllItems(){
        for(Item i:inventory){
            System.out.print("-");
            System.out.println(i.superToString());
        }
    }   
    //gets
    public Item getItem(int i){return inventory.get(i);}
    public ArrayList<Item> getItemList(){return inventory;}
    public int getInvSize(){return invSize;}
    public int getNumberOfItemsInInventory(){
        int items=0;
        for(Item item:inventory){
            items++;
        }
        return items;
    }
    //item subclass gets
    public HealingItem getHealingItem(int i){return (HealingItem)inventory.get(i);}
    public DamageItem getDamageItem(int i){return (DamageItem)inventory.get(i);}
    public Equipment getEquipmentItem(int i){return (Equipment)inventory.get(i);}
    public Weapon getWeaponItem(int i){return (Weapon)inventory.get(i);}
    public Armor getArmorItem(int i){return (Armor)inventory.get(i);}
    
    
}
