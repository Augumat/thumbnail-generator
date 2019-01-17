package main.java;

import java.util.ArrayList;

/**
 * Defines a character and stores the images, offsets, and costumes (WIP) associated with them.
 */
public class Character
{
    /** The id of this Character. */
    private int id;
    /** The default name of this Character. */
    private String name;
    /** All of the Costumes that this character can wear. */
    private ArrayList<Costume> costumes;
    
    /**
     * Creates a character object based on an id number referring to a specific folder in resources.
     * @param initID The id of the Character being loaded from file.
     */
    public Character(int initID)
    {
        //todo implement
        id = initID;
    
        
    }
    
    /**
     * Getter for name.
     * @return The default name of this Character.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Getter for id.
     * @return The id of this Character.
     */
    public int getID()
    {
        return id;
    }
}
