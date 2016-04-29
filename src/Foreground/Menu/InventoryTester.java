/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.Inventory;
import Background.Party.Party;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Connor
 */
public class InventoryTester {
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Party party = new Party(4);
        Inventory inv = new Inventory(15);
        frame.setLayout(null);
        frame.setSize(618, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        PauseMenu menu = new PauseMenu(frame,party,inv);
        menu.run(party, inv);
    }
}
