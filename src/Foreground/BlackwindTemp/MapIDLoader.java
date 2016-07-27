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
        //-1 means "non enounter area" in this context
        if(mapName.equals("Town")){
            return -1;
        }else if(mapName.equals("Forest")){
            return 0;
        }
        return -1;
    }
}
