package main.java;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Defines a Fighter and stores the images, offsets, and costumes (WIP) associated with them.
 */
public class Fighter
{
    /** The selection of characters able to be selected . */
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
    /** The static list of the name of each Fighter organized by Fighter IDs and Variant IDs. */
    public static String[][] VARIANT_OPTIONS =
            {
                        {""},                 /* ID:0  */
                        {"Mario"},            /* ID:1  */
                        {"Donkey Kong"},      /* ID:2  */
                        {"Link"},             /* ID:3  */
                        {"Samus"},            /* ID:4  */
                        {"Dark Samus"},       /* ID:5  */
                        {"Yoshi"},            /* ID:6  */
                        {"Kirby"},            /* ID:7  */
                        {"Fox"},              /* ID:8  */
                        {"Pikachu"},          /* ID:9  */
                        {"Luigi"},            /* ID:10 */
                        {"Ness"},             /* ID:11 */
                        {"Captain Falcon"},   /* ID:12 */
                        {"Jigglypuff"},       /* ID:13 */
                        {"Peach"},            /* ID:14 */
                        {"Daisy"},            /* ID:15 */
                        {"Bowser"},           /* ID:16 */
                        {"Ice Climbers"},     /* ID:17 */
                        {"Sheik"},            /* ID:18 */
                        {"Zelda"},            /* ID:19 */
                        {"Dr. Mario"},        /* ID:20 */
                        {"Pichu"},            /* ID:21 */
                        {"Falco"},            /* ID:22 */
                        {"Marth"},            /* ID:23 */
                        {"Lucina"},           /* ID:24 */
                        {"Young Link"},       /* ID:25 */
                        {"Ganondorf"},        /* ID:26 */
                        {"Mewtwo"},           /* ID:27 */
                        {"Roy"},              /* ID:28 */
                        {"Chrom"},            /* ID:29 */
                        {"Mr. Game & Watch"}, /* ID:30 */
                        {"Meta Knight"},      /* ID:31 */
                        {"Pit"},              /* ID:32 */
                        {"Dark Pit"},         /* ID:33 */
                        {"Zero Suit Samus"},  /* ID:34 */
                        {"Wario"},            /* ID:35 */
                        {"Snake"},            /* ID:36 */
                        {"Ike"},              /* ID:37 */
                        {"Pokemon Trainer"},  /* ID:38 */
                        {"Diddy Kong"},       /* ID:39 */
                        {"Lucas"},            /* ID:40 */
                        {"Sonic"},            /* ID:41 */
                        {"King Dedede"},      /* ID:42 */
                        {"Olimar", "Olimar", "Olimar", "Olimar", "Alph", "Alph", "Alph", "Alph"}, /* ID:43 */
                        {"Lucario"},          /* ID:44 */
                        {"R.O.B."},           /* ID:45 */
                        {"Toon Link"},        /* ID:46 */
                        {"Wolf"},             /* ID:47 */
                        {"Villager"},         /* ID:48 */
                        {"Megaman"},          /* ID:49 */
                        {"Wii Fit Trainer"},  /* ID:50 */
                        {"Rosalina & Luma"},  /* ID:51 */
                        {"Little Mac"},       /* ID:52 */
                        {"Greninja"},         /* ID:53 */
                        {"Palutena"},         /* ID:54 */
                        {"Pac Man"},          /* ID:55 */
                        {"Robin"},            /* ID:56 */
                        {"Shulk"},            /* ID:57 */
                        {"Bowser Jr.", "Larry", "Roy", "Wendy", "Iggy", "Morton", "Lemmy", "Ludwig"}, /* ID:58 */
                        {"Duck Hunt"},        /* ID:59 */
                        {"Ryu"},              /* ID:60 */
                        {"Ken"},              /* ID:61 */
                        {"Cloud"},            /* ID:62 */
                        {"Corrin"},           /* ID:63 */
                        {"Bayonetta"},        /* ID:64 */
                        {"Inkling"},          /* ID:65 */
                        {"Ridley", "Meta Ridley", "Ridley", "Ridley", "Ridley", "Ridley", "Ridley", "Mecha Ridley"}, /* ID:66 */
                        {"Simon"},            /* ID:67 */
                        {"Richter"},          /* ID:68 */
                        {"King K. Rool"},     /* ID:69 */
                        {"Isabelle"},         /* ID:70 */
                        {"Incineroar"},       /* ID:71 */
                        {"Mii Brawler"},      /* ID:72 */
                        {"Mii Swordfighter"}, /* ID:73 */
                        {"Mii Gunner"},       /* ID:74 */
                        {"Pirahna Plant"},    /* ID:75 */
                        {"Joker"}             /* ID:76 */
            };
    /** The static list of whether each Fighter has different Variant names organized by IDs. */
    public static boolean[] VARIANT_NAMED =
            {
                    false, /* ID:0  N/A */
                    false, /* ID:1  Mario */
                    false, /* ID:2  Donkey Kong */
                    false, /* ID:3  Link */
                    false, /* ID:4  Samus */
                    false, /* ID:5  Dark Samus */
                    false, /* ID:6  Yoshi */
                    false, /* ID:7  Kirby */
                    false, /* ID:8  Fox */
                    false, /* ID:9  Pikachu */
                    false, /* ID:10 Luigi */
                    false, /* ID:11 Ness */
                    false, /* ID:12 Captain Falcon */
                    false, /* ID:13 Jigglypuff */
                    false, /* ID:14 Peach */
                    false, /* ID:15 Daisy */
                    false, /* ID:16 Bowser */
                    false, /* ID:17 Ice Climbers */
                    false, /* ID:18 Sheik */
                    false, /* ID:19 Zelda */
                    false, /* ID:20 Dr. Mario */
                    false, /* ID:21 Pichu */
                    false, /* ID:22 Falco */
                    false, /* ID:23 Marth */
                    false, /* ID:24 Lucina */
                    false, /* ID:25 Young Link */
                    false, /* ID:26 Ganondorf */
                    false, /* ID:27 Mewtwo */
                    false, /* ID:28 Roy */
                    false, /* ID:29 Chrom */
                    false, /* ID:30 Mr. Game & Watch */
                    false, /* ID:31 Meta Knight */
                    false, /* ID:32 Pit */
                    false, /* ID:33 Dark Pit */
                    false, /* ID:34 Zero Suit Samus */
                    false, /* ID:35 Wario */
                    false, /* ID:36 Snake */
                    false, /* ID:37 Ike */
                    false, /* ID:38 Pokemon Trainer */
                    false, /* ID:39 Diddy Kong */
                    false, /* ID:40 Lucas */
                    false, /* ID:41 Sonic */
                    false, /* ID:42 King Dedede */
                    true , /* ID:43 Olimar */
                    false, /* ID:44 Lucario */
                    false, /* ID:45 R.O.B. */
                    false, /* ID:46 Toon Link */
                    false, /* ID:47 Wolf */
                    false, /* ID:48 Villager */
                    false, /* ID:49 Mega Man */
                    false, /* ID:50 Wii Fit Trainer */
                    false, /* ID:51 Rosalina & Luma */
                    false, /* ID:52 Little Mac */
                    false, /* ID:53 Greninja */
                    false, /* ID:54 Palutena */
                    false, /* ID:55 Pac Man */
                    false, /* ID:56 Robin */
                    false, /* ID:57 Shulk */
                    true , /* ID:58 Bowser Jr. */
                    false, /* ID:59 Duck Hunt */
                    false, /* ID:60 Ryu */
                    false, /* ID:61 Ken */
                    false, /* ID:62 Cloud */
                    false, /* ID:63 Corrin */
                    false, /* ID:64 Bayonetta */
                    false, /* ID:65 Inkling */
                    true , /* ID:66 Ridley */
                    false, /* ID:67 Simon */
                    false, /* ID:68 Richter */
                    false, /* ID:69 King K. Rool */
                    false, /* ID:70 Isabelle */
                    false, /* ID:71 Incineroar */
                    false, /* ID:72 Mii Brawler */
                    false, /* ID:73 Mii Swordfighter */
                    false, /* ID:74 Mii Gunner */
                    false, /* ID:75 Pirahna Plant */
                    false  /* ID:76 Joker */
            };
    
    /** The total number of Fighters in the game. */
    public static int FIGHTER_BOUND = 77;
    /** The total number of Fighters in the game. */
    public static int VARIANT_BOUND = 8;
    /** The default variant for every Fighter. */
    public static int DEFAULT_VARIANT = 0;
    
    /** The id of this Fighter. */
    private int fighterID;
    /** The id of the current variant of this Fighter. */
    private int variantID;
    
    /**
     * Basic constructor.
     * Creates a Fighter object based on an id number referring to a specific image in resources.
     * @param initFighter The id of the Fighter being loaded from file.
     */
    public Fighter(int initFighter)
    {
        if (initFighter >= 0 && initFighter < FIGHTER_BOUND)
        {
            fighterID = initFighter;
            variantID = DEFAULT_VARIANT;
        }
        else
        {
            System.out.println("[ERROR] Invalid fighters id passed.");
            fighterID = 0;
            variantID = 0;
        }
    }
    /**
     * Variant constructor.
     * Creates a Fighter object based on id numbers referring to a specific image in resources.
     * @param initFighter The id of the Fighter being created.
     * @param initVariant The id of the Variant of the Fighter being created.
     */
    public Fighter(int initFighter, int initVariant)
    {
        if (initFighter >= 0 && initFighter < FIGHTER_BOUND)
        {
            fighterID = initFighter;
            if (initVariant >= 0 && initVariant < VARIANT_BOUND)
            {
                variantID = initVariant;
            }
            else
            {
                System.out.println("[ERROR] Invalid variant id passed.");
                variantID = DEFAULT_VARIANT;
            }
        }
        else
        {
            System.out.println("[ERROR] Invalid fighters id passed.");
            fighterID = 0;
            variantID = 0;
        }
    }
    
    /** Getter for fighters id. */
    public int getFighterID()
    {
        return fighterID;
    }
    
    /**
     * Returns a render of this Fighter in the form of a BufferedImage.
     * @return the render of the fighters described by this instance of Fighter.
     */
    public BufferedImage getRender()
    {
        BufferedImage output;
        try
        {
            URL url;
            File file;
            //todo remove this trash and just copy paste the names like a normal person
            try
            {
                output = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("[ERROR] Failed alternate name request");
                output = ImageIO.read(getClass().getResource("/fighters/" + FILE[fighterID] + "/render" + variantID + ".png"));
            }
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Render load aborted.");
            e.printStackTrace();
            output = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        return output;
    }
    
    /** Evaluates to the name of the Fighter's default Variant. */
    @Override
    public String toString()
    {
        if (VARIANT_NAMED[fighterID])
        {
            return VARIANT_OPTIONS[fighterID][variantID];
        }
        return VARIANT_OPTIONS[fighterID][0];
    }
}
