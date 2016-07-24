/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

/**
 *
 * @author Connor
 */
public class MapIDLoader {
    public static int getMapID(String mapName){
        if(mapName.equals("Town")){
            return 0;
        }
        return -1;
    }
}
