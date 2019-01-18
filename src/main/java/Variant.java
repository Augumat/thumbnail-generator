package main.java;

import java.awt.image.BufferedImage;

/**
 * Stores the name, render, and offset data of a particular Variant of a Fighter.
 */
public class Variant
{
    /** The id of this Variant. */
    private int id;
    /** The name of the Fighter of this Variant. */
    private String name;
    /** The render of the Variant as loaded from a PNG. */
    private BufferedImage render;
    
    /**
     * Creates a Variant object from a JSON file
     *
     * @param data The data file for this Variant.
     * @param image The image file for this Variant.
     */
    public Variant(String data, BufferedImage image)
    {
        //todo implement
    }
    
    /**
     * Getter for name.
     * @return The name of the Fighter equipped with this Variant.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Getter for id.
     * @return The id of the Fighter equipped with this Variant.
     */
    public int getID()
    {
        return id;
    }
}
