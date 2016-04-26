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
        if(inventory.size()<invSize){
                return true;
        }
        for(Item item:inventory){
            if(item.getId()==i.getId()){
                if(item.getQuantity()+i.getQuantity()<=item.getMaxQuantity()){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    public void add(Item i){
        if(inventory.size()<invSize){
              inventory.add(i);
              return;
        }
        for(Item item:inventory){
            if(item.getId()==i.getId()&&item.getQuantity()+i.getQuantity()<=item.getMaxQuantity()){
                item.restock(i.getQuantity());
                return;
            }
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
    public Item getItem(int i){
        return inventory.get(i);
    }
    public int getInvSize(){return invSize;}
}
