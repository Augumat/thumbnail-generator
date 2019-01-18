package main.java;

import java.util.ArrayList;

/**
 * Defines a Fighter and stores the images, offsets, and costumes (WIP) associated with them.
 */
public class Fighter
{
    /** The id of this Fighter. */
    private int id;
    /** The default name of this Fighter. */
    private String name;
    /** All of the Variants that this fighter can wear. */
    private ArrayList<Variant> variants;
    
    /**
     * Creates a Fighter object based on an id number referring to a specific folder in resources.
     * @param initID The id of the Fighter being loaded from file.
     */
    public Fighter(int initID)
    {
        //todo implement
        id = initID;
    
        
    }
    
    /**
     * Getter for name.
     * @return The default name of this Fighter.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Getter for id.
     * @return The id of this Fighter.
     */
    public int getID()
    {
        return id;
    }
}
