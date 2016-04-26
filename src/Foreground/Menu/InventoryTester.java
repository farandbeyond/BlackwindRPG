/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Connor
 */
public class InventoryTester {
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(618, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        PauseMenu menu = new PauseMenu(frame,null,null);
        menu.run(null, null);
    }
}
