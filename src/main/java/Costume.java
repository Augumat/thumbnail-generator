package main.java;

import java.awt.image.BufferedImage;

/**
 * Stores the name, render, and offset data of a particular costume of a character.
 */
public class Costume
{
    /** The id of this costume. */
    private int id;
    /** The name of the character in this costume. */
    private String name;
    /** The render of the costume as loaded from a PNG. */
    private BufferedImage render;
    
    /**
     * Creates a Costume object from a JSON file
     *
     * @param data The data file for this Costume.
     * @param image The image file for this Costume.
     */
    public Costume(String data, BufferedImage image)
    {
        //todo implement
    }
    
    /**
     * Getter for name.
     * @return The name of the character in this Costume.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Getter for id.
     * @return The id of the character in this Costume.
     */
    public int getID()
    {
        return id;
    }
}
