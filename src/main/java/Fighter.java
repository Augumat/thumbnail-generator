package main.java;

import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Defines a Fighter and stores the images, offsets, and variant costumes associated with them.
 * @author Augumat
 */
public class Fighter implements Comparable<Fighter> {
    
    /** The number of Variants that each fighter has. */
    private static int VARIANT_BOUND = 8;
    
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
     * project.
     */
    private boolean[] variantSupported;
    
    /** An array of the Fighter's stock icons, includes all 8 variants. */
    private Icon[] icons;
    
    /** An array of the Fighter's full-scale renders, includes all 8 variants. */
    private BufferedImage[] renders;
    
    
    
    /**
     * Initializes the fighter and loads all of the supported icons and renders associated with this fighter
     */
    public void init() {
    
        // Attempt to load each render from /res/ if it is supported, otherwise leave it as null.  If a render is
        // allegedly supported but cannot be loaded, throw an exception.
        renders = new BufferedImage[VARIANT_BOUND];
        for (int variantID = 0; variantID < VARIANT_BOUND; variantID++) {
            if (variantSupported[variantID]) {
                try {
                    renders[variantID] = ImageIO.read(getClass().getResource(
                            "/fighters/" + simpleName + "/render" + variantID + ".png"
                    ));
                } catch (java.io.IOException e) {
                    System.out.println("[ERROR] Failed to load " + simpleName + "'s " + variantID + " render, which was allegedly supported.");
                }
            }
        }
    
        // Attempt to load each icon from /res/ if it is supported, otherwise leave it as null.  If an icon is allegedly
        // supported but cannot be loaded, throw an exception.
        icons = new Icon[VARIANT_BOUND];
        for (int variantID = 0; variantID < VARIANT_BOUND; variantID++) {
            if (variantSupported[variantID]) {
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
        if (!variantSupported[variantId]) {
            throw new Exception("Attempted to get a Fighter render that is not currently supported by Thumbnail-Generator");
        }
        
        // If the inputs are acceptable, return the render of the specified variant
        return renders[variantId];
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
        if (!variantSupported[variantId]) {
            throw new Exception("Attempted to get a Fighter icon that is not currently supported by Thumbnail-Generator");
        }
        
        // If the inputs are acceptable, return the render of the specified variant
        return icons[variantId];
    }
    
    /** Returns true of the Fighter ID and its echo status are the same. */
    @Override
    public boolean equals(Object that) {
        return that instanceof Fighter
            && this.id == ((Fighter) that).id
            && this.echo == ((Fighter) that).echo;
    }
    
    /** Sorts Fighters in ascending order of ID, and ascending order from regular Fighter to echo. */
    @Override
    public int compareTo(Fighter that) {
        
        // If the two Fighters are equivalent, return 0
        if (this.equals(that)) {
            return 0;
        }
        
        // Get the difference in IDs
        int comparisonWeight = this.id - that.id;
        
        // If the IDs are the same, use the status as an echo to compare them (ECHOS COME AFTER DEFAULTS)
        if (comparisonWeight == 0 && that.echo) {
            return -1;
        } else if (comparisonWeight == 0 && this.echo) {
            return 1;
        }
        
        // If the comparisonWeight is not zero, simply return the weight, since it will be signed correctly
        return comparisonWeight;
    }
    
    
    
    // todo the goal is to remove everything from below ------------------------- THIS POINT --------------------------
    
    /** The selection of characters able to be selected . */
    @Deprecated
    public static final String[] FILE =
            {
                    "_",                /* ID:0  */
                    "mario",            /* ID:1  */
                    "donkeykong",       /* ID:2  */
                    "link",             /* ID:3  */
                    "samus",            /* ID:4  */
                    "darksamus",        /* ID:5  */
                    "yoshi",            /* ID:6  */
                    "kirby",            /* ID:7  */
                    "fox",              /* ID:8  */
                    "pikachu",          /* ID:9  */
                    "luigi",            /* ID:10 */
                    "ness",             /* ID:11 */
                    "captainfalcon",    /* ID:12 */
                    "jigglypuff",       /* ID:13 */
                    "peach",            /* ID:14 */
                    "daisy",            /* ID:15 */
                    "bowser",           /* ID:16 */
                    "iceclimbers",      /* ID:17 */
                    "sheik",            /* ID:18 */
                    "zelda",            /* ID:19 */
                    "drmario",          /* ID:20 */
                    "pichu",            /* ID:21 */
                    "falco",            /* ID:22 */
                    "marth",            /* ID:23 */
                    "lucina",           /* ID:24 */
                    "younglink",        /* ID:25 */
                    "ganondorf",        /* ID:26 */
                    "mewtwo",           /* ID:27 */
                    "roy",              /* ID:28 */
                    "chrom",            /* ID:29 */
                    "mrgame&watch",     /* ID:30 */
                    "metaknight",       /* ID:31 */
                    "pit",              /* ID:32 */
                    "darkpit",          /* ID:33 */
                    "zerosuitsamus",    /* ID:34 */
                    "wario",            /* ID:35 */
                    "snake",            /* ID:36 */
                    "ike",              /* ID:37 */
                    "pokemontrainer",   /* ID:38 */
                    "diddykong",        /* ID:39 */
                    "lucas",            /* ID:40 */
                    "sonic",            /* ID:41 */
                    "kingdedede",       /* ID:42 */
                    "olimar",           /* ID:43 */
                    "lucario",          /* ID:44 */
                    "rob",              /* ID:45 */
                    "toonlink",         /* ID:46 */
                    "wolf",             /* ID:47 */
                    "villager",         /* ID:48 */
                    "megaman",          /* ID:49 */
                    "wiifittrainer",    /* ID:50 */
                    "rosalina&luma",    /* ID:51 */
                    "littlemac",        /* ID:52 */
                    "greninja",         /* ID:53 */
                    "palutena",         /* ID:54 */
                    "pacman",           /* ID:55 */
                    "robin",            /* ID:56 */
                    "shulk",            /* ID:57 */
                    "bowserjr",         /* ID:58 */
                    "duckhunt",         /* ID:59 */
                    "ryu",              /* ID:60 */
                    "ken",              /* ID:61 */
                    "cloud",            /* ID:62 */
                    "corrin",           /* ID:63 */
                    "bayonetta",        /* ID:64 */
                    "inkling",          /* ID:65 */
                    "ridley",           /* ID:66 */
                    "simon",            /* ID:67 */
                    "richter",          /* ID:68 */
                    "kingkrool",        /* ID:69 */
                    "isabelle",         /* ID:70 */
                    "incineroar",       /* ID:71 */
                    "miibrawler",       /* ID:72 */
                    "miiswordfighter",  /* ID:73 */
                    "miigunner",        /* ID:74 */
                    "pirahnaplant",     /* ID:75 */
                    "joker"             /* ID:76 */
            };
    /** The selection of characters able to be selected . */
    @Deprecated
    public static final String[] FIGHTER_OPTIONS =
            {
                    "(Fighter)",        /* ID:0  */
                    "Mario",            /* ID:1  */
                    "Donkey Kong",      /* ID:2  */
                    "Link",             /* ID:3  */
                    "Samus",            /* ID:4  */
                    "Dark Samus",       /* ID:5  */
                    "Yoshi",            /* ID:6  */
                    "Kirby",            /* ID:7  */
                    "Fox",              /* ID:8  */
                    "Pikachu",          /* ID:9  */
                    "Luigi",            /* ID:10 */
                    "Ness",             /* ID:11 */
                    "Captain Falcon",   /* ID:12 */
                    "Jigglypuff",       /* ID:13 */
                    "Peach",            /* ID:14 */
                    "Daisy",            /* ID:15 */
                    "Bowser",           /* ID:16 */
                    "Ice Climbers",     /* ID:17 */
                    "Sheik",            /* ID:18 */
                    "Zelda",            /* ID:19 */
                    "Dr. Mario",        /* ID:20 */
                    "Pichu",            /* ID:21 */
                    "Falco",            /* ID:22 */
                    "Marth",            /* ID:23 */
                    "Lucina",           /* ID:24 */
                    "Young Link",       /* ID:25 */
                    "Ganondorf",        /* ID:26 */
                    "Mewtwo",           /* ID:27 */
                    "Roy",              /* ID:28 */
                    "Chrom",            /* ID:29 */
                    "Mr. Game & Watch", /* ID:30 */
                    "Meta Knight",      /* ID:31 */
                    "Pit",              /* ID:32 */
                    "Dark Pit",         /* ID:33 */
                    "Zero Suit Samus",  /* ID:34 */
                    "Wario",            /* ID:35 */
                    "Snake",            /* ID:36 */
                    "Ike",              /* ID:37 */
                    "Pokemon Trainer",  /* ID:38 */
                    "Diddy Kong",       /* ID:39 */
                    "Lucas",            /* ID:40 */
                    "Sonic",            /* ID:41 */
                    "King Dedede",      /* ID:42 */
                    "Olimar",           /* ID:43 */
                    "Lucario",          /* ID:44 */
                    "R.O.B.",           /* ID:45 */
                    "Toon Link",        /* ID:46 */
                    "Wolf",             /* ID:47 */
                    "Villager",         /* ID:48 */
                    "Megaman",          /* ID:49 */
                    "Wii Fit Trainer",  /* ID:50 */
                    "Rosalina & Luma",  /* ID:51 */
                    "Little Mac",       /* ID:52 */
                    "Greninja",         /* ID:53 */
                    "Palutena",         /* ID:54 */
                    "Pac Man",          /* ID:55 */
                    "Robin",            /* ID:56 */
                    "Shulk",            /* ID:57 */
                    "Bowser Jr.",       /* ID:58 */
                    "Duck Hunt",        /* ID:59 */
                    "Ryu",              /* ID:60 */
                    "Ken",              /* ID:61 */
                    "Cloud",            /* ID:62 */
                    "Corrin",           /* ID:63 */
                    "Bayonetta",        /* ID:64 */
                    "Inkling",          /* ID:65 */
                    "Ridley",           /* ID:66 */
                    "Simon",            /* ID:67 */
                    "Richter",          /* ID:68 */
                    "King K. Rool",     /* ID:69 */
                    "Isabelle",         /* ID:70 */
                    "Incineroar",       /* ID:71 */
                    "Mii Brawler",      /* ID:72 */
                    "Mii Swordfighter", /* ID:73 */
                    "Mii Gunner",       /* ID:74 */
                    "Pirahna Plant",    /* ID:75 */
                    "Joker"             /* ID:76 */
            };

    /** The total number of Fighters in the game. */
    @Deprecated
    public static int FIGHTER_BOUND = 77;

    /** The default variant for every Fighter. */
    @Deprecated
    public static int DEFAULT_VARIANT = 0;

    /** The id of this Fighter. */
    @Deprecated
    private int fighterID;
    /** The id of the current variant of this Fighter. */
    @Deprecated
    private int variantID;

    /** This Fighter's versus render. */
    @Deprecated
    private BufferedImage render;
    /** This Fighter's stock icon. */
    @Deprecated
    private ImageIcon icon;



    /**
     * Basic constructor.
     * Creates a Fighter object based on an id number referring to a specific image in resources.
     * @param initFighter The id of the Fighter being loaded from file.
     */
    @Deprecated
    public Fighter(int initFighter) {
        // Set IDs
        if (initFighter >= 0 && initFighter < FIGHTER_BOUND) {
            fighterID = initFighter;
        } else {
            System.out.println("[ERROR] Invalid fighters id passed.");
            fighterID = 0;
        }
        variantID = DEFAULT_VARIANT;

        // Set icon and render
        try {
            //todo remove this trash and just copy paste the names like a normal person
            try {
                render = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("[ERROR] Failed alternate name request");
                render = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            }
        }
        catch (java.io.IOException e) {
            System.out.println("[ERROR] Render load aborted.");
            e.printStackTrace();
            render = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        //todo set icon
    }

    /**
     * Variant constructor.
     * Creates a Fighter object based on id numbers referring to a specific image in resources.
     * @param initFighter The id of the Fighter being created.
     * @param initVariant The id of the Variant of the Fighter being created.
     */
    @Deprecated
    public Fighter(int initFighter, int initVariant) {
        // Set IDs
        if (initFighter >= 0 && initFighter < FIGHTER_BOUND) {
            fighterID = initFighter;
            if (initVariant >= 0 && initVariant < VARIANT_BOUND) {
                variantID = initVariant;
            } else {
                System.out.println("[ERROR] Invalid variant id passed.");
                variantID = DEFAULT_VARIANT;
            }
        } else {
            System.out.println("[ERROR] Invalid fighters id passed.");
            fighterID = 0;
            variantID = 0;
        }

        // Set icon and render
        try {
            //todo remove this trash and just copy paste the names like a normal person
            try {
                render = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("[ERROR] Failed alternate name request");
                render = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            }
        }
        catch (java.io.IOException e) {
            System.out.println("[ERROR] Render load aborted.");
            e.printStackTrace();
            render = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        //todo set icon
    }
}
