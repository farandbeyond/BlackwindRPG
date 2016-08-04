/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class ShopTriggerSegment extends EventSegment{
    
    ShopTriggerSegment(){
        
    }
    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        b.prepShop(loadShopItemList(b));
        return "adv!!";
    }
    public ArrayList<Integer> loadShopItemList(Blackwind b){
        try{
            String line = "";
            ArrayList<String> contents = new ArrayList<>();
            String filePath = String.format("events/%s/shop.txt",b.getMap().getName());
            InputStream input = new FileInputStream(filePath);
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader fileReader = new BufferedReader(inputReader);
            //tile loading
            try{
                while((line=fileReader.readLine())!=null){
                        //System.out.println(line);
                        contents.add(line);
                }
            }catch(IOException e){
                
            }
            ArrayList<Integer> itemIDs = new ArrayList<>();
            for(String s:contents)
                itemIDs.add(Integer.parseInt(s));
            return itemIDs;
        }catch(FileNotFoundException e){
            return null;
        }
    }
    
}
