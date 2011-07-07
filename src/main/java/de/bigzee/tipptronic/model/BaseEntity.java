/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bigzee.tipptronic.model;

import java.io.Serializable;

/**
 *
 * @author lutz
 */
public abstract class BaseEntity implements Serializable {

    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    public abstract Long getId();

    @Override
    public String toString() {
        return "Entity-Class: " + getClass().
                getName() + " Id: " + getId();
    }
}
