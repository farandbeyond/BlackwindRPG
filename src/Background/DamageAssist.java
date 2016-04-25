/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;

/**
 *
 * @author Connor
 */
public class DamageAssist {
    //loops until i is the number below double damage (eg 6.5 becomes 6)
    public static int round(double damage){
        int roundedDamage=0;           
        for(int i=0;i<damage;i++){
            roundedDamage=i;
        }
        return roundedDamage;
    }
}
