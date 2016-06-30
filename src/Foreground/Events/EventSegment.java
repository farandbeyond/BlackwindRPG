/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;

/**
 *
 * @author Connor
 */
public abstract class EventSegment {
    public abstract String activate(Inventory i, Party p);
}
