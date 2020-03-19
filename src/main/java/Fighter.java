package main.java;

import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Defines a Fighter and stores the images, offsets, and variant costumes associated with them.
 * @author Augumat
 */
public class Fighter {
    
    /** A list of the sorting modes that can be used to compare Fighters to each other. */
    public enum SortingMode {
        Default,     // Sorts Fighters in ascending order of ID, and ascending order from regular Fighter to echo
        Alphabetical // Sorts Fighters in lexicographical order based on their display names
    }
    
    /** The number of Variants that each fighter has. */
    public static final int VARIANT_BOUND = 8;
    
    /** ID number of this Fighter, ordered according to  */
    private int id;
    
    /** Whether or not this Fighter is an echo fighter. */
    private boolean echo;
    
    /** A simplified version of this Fighter's name, and also the name of their resources folder. */
    private String simpleName;
    
    /**
     * Properly formatted full version of this Fighter's name. This is also used as the default name for the Fighter
     * and the name used for any variants that do not have unique names associated with them.
     */
    private String properName;
    
    /**
     * A list of length 8 that stores the unique names of each variant of this Fighter. If an element is null, then the
     * variant at that index will be set to the properName of the Fighter. If the array itself is null, every element of
     * the array will be set to the properName.
     */
    private String[] variantNames;
    
    /**
     * This indicates whether or not the variant corresponding to the index in the array has a valid render that can be
     * loaded from resources. The JSON for this section should be updated manually when new renders are added to the
     * project. If this field is null, all 8 alternate costumes are supported.
     */
    private boolean[] variantSupported;
    
    /** An array of the Fighter's stock icons, includes all 8 variants. */
    private Icon[] icons;
    
//    /** An array of the Fighter's full-scale renders, includes all 8 variants. */
//    private BufferedImage[] renders;
    
    
    
    /**
     * Initializes the fighter and loads all of the supported icons and renders associated with this fighter. This is
     * intended to be called on every Fighter after they are loaded from JSON.
     */
    public void init() {
        // Attempt to load each icon from /res/ if it is supported, otherwise leave it as null.  If an icon is allegedly
        // supported but cannot be loaded, throw an exception.
        icons = new Icon[VARIANT_BOUND];
        for (int variantID = 0; variantID < VARIANT_BOUND; variantID++) {
            if (variantSupported == null || variantSupported[variantID]) {
                try {
                    icons[variantID] = new ImageIcon(ImageIO.read(getClass().getResource(
                            "/fighters/" + simpleName + "/icon" + variantID + ".png"
                    )));
                } catch (java.io.IOException e) {
                    System.out.println("[ERROR] Failed to load " + simpleName + "'s " + variantID + " render, which was allegedly supported.");
                }
            }
        }
        
        // Set the variant names for each index of VariantNames
        for (int i = 0; i < variantNames.length; i++) {
            if (variantNames[i] == null) {
                variantNames[i] = properName;
            }
        }
    }
    
    /** Getter for fighters id. */
    public int getId() {
        return id;
    }
    
    /** Simple getter for the Fighter's formal name. */
    public String getName() {
        return properName;
    }
    
    /** Simple getter for the Fighter's status as an echo fighter. */
    public boolean isEcho() {
        return echo;
    }
    
    /** Gets the Fighter's formal name for when it is wearing the specified variant. */
    public String getName(int variantId) {
        if (variantId < 0 || variantId >= variantNames.length) {
            return null;
        }
        return variantNames[variantId];
    }
    
    /**
     * Attempts to retrieve the render of this Fighter with the specified variant ID.
     * @param variantId A number from 0-7 inclusive that indicates which render of the fighter to return from the
     *                  available variants.
     * @return Returns the render of this Fighter of the variant specified.
     * @throws Exception Throws an Exception with a message reporting that an illegal variant number was requested or
     *                   that the variant specified is not currently supported in this version of the application.
     */
    @NotNull
    public BufferedImage getRender(final int variantId) throws Exception {
        
        // If the specified variantId is outside the bounds of acceptable values, throw an exception
        if (variantId < 0 || variantId >= VARIANT_BOUND) {
            throw new Exception("Attempted to get a Fighter render that was not between 0-7 inclusive");
        }
        
        // If the current version does not support the fighter at the given index, throw an exception
        if (variantSupported != null && !variantSupported[variantId]) {
            throw new Exception("Attempted to get a Fighter render that is not currently supported by Thumbnail-Generator");
        }
        
        // If the inputs are acceptable, return the render of the specified variant
        try {
            return ImageIO.read(getClass().getResource(
                    "/fighters/" + simpleName + "/render" + variantId + ".png"
            ));
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Failed to load " + simpleName + "'s " + variantId + " render, which was allegedly supported.");
            System.exit(1);
        }
        
        // In any other case return a blank 640x640 image
        return new BufferedImage(640, 640, BufferedImage.TYPE_INT_ARGB);
    }
    
    /**
     * Attempts to retrieve the stock icon of this Fighter with the specified variant ID.
     * @param variantId A number from 0-7 inclusive that indicates which icon of the fighter to return from the
     *                  available variants.
     * @return Returns the icon of this Fighter of the variant specified.
     * @throws Exception Throws an Exception with a message reporting that an illegal variant number was requested or
     *                   that the variant specified is not currently supported in this version of the application.
     */
    @NotNull
    public Icon getIcon(final int variantId) throws Exception {
        
        // If the specified variantId is outside the bounds of acceptable values, throw an exception
        if (variantId < 0 || variantId >= VARIANT_BOUND) {
            throw new Exception("Attempted to get a Fighter icon that was not between 0-7 inclusive");
        }
        
        // If the current version does not support the fighter at the given index, throw an exception
        if (variantSupported != null && !variantSupported[variantId]) {
            throw new Exception("Attempted to get a Fighter icon that is not currently supported by Thumbnail-Generator");
        }
        
        // If the inputs are acceptable, return the render of the specified variant
        return icons[variantId];
    }
    
    /**
     * Compares one Fighter to another according to a specified SortingMode.
     * @param first The first Fighter to compare.
     * @param second The second Fighter to compare.
     * @param mode How the Fighters should be compared, should be a member of Fighter.SortingMode
     * @return A positive number if the first Fighter should appear before the second, or a negative number in the
     *         opposite case.
     */
    private static int compare(Fighter first, Fighter second, SortingMode mode) {
        switch (mode) {
            case Default:
                // If the two Fighters are equivalent, return 0
                if (first.equals(second)) {
                    return 0;
                }
                
                // Get the difference in IDs
                int comparisonWeight = first.getId() - second.getId();
                
                // If the IDs are the same, use the status as an echo to compare them (ECHOS COME AFTER DEFAULTS)
                if (comparisonWeight == 0 && second.isEcho()) {
                    return -1;
                } else if (comparisonWeight == 0 && first.isEcho()) {
                    return 1;
                }
                
                // If the comparisonWeight is not zero, simply return the weight, since it will be signed correctly
                return comparisonWeight;
            
            case Alphabetical:
                // Return the lexicographical difference
                String firstName = first.getName();
                String secondName = second.getName();
                return firstName.compareToIgnoreCase(secondName);
            
            default:
                // If there is no valid mode selected, return values that would leave the list as-is
                return 0;
        }
    }
    
    /**
     * Sorts a list of Fighters based on the provided SortingMode. The provided list is modified directly by reference.
     * This will always preserve every element, only rearranging the contents of the list.
     * @param list The list of Fighters to be sorted.
     * @param mode One of the members of SortingMode, they do what it says on the tin.
     */
    public static void sort(List<Fighter> list, SortingMode mode) {
        list.sort((f1, f2) -> compare(f1, f2, mode));
    }
    
    /** Returns true of the Fighter ID and its echo status are the same. */
    @Override
    public boolean equals(Object that) {
        return that instanceof Fighter
            && this.id == ((Fighter) that).id
            && this.echo == ((Fighter) that).echo;
    }
}
