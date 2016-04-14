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
public abstract class Item {
    private final String name, description;
    private int quantity;
    private final int id, maxQuantity, shopValue;
    public abstract void use(BattleEntity target);
    
    public Item(int id,String name, String description, int quantity, int maxQuantity, int shopValue){
        this.id=id;
        this.name=name;
        this.description=description;
        this.quantity=quantity;
        this.maxQuantity=maxQuantity;
        this.shopValue=shopValue;
    }
    public void reduceQuantity(){
        quantity--;
    }
    public boolean restock(int numberToRestock){
        quantity+=numberToRestock;
        if(quantity>maxQuantity){
            System.out.println("Too many items");
            quantity=maxQuantity;
            return false;
        }
        return true;
    }
    
    //gets
    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getQuantity(){return quantity;}
    public int getId(){return id;}
    public int getMaxQuantity(){return maxQuantity;}
    public int getShopValue(){return shopValue;}
    
    
}
