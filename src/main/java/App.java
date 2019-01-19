package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App implements Runnable
{
    /** Window size constants. */
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;
    
    /** Whether or not the program is running. */
    private boolean running;
    /** The main window of the program. */
    private JFrame window;
    /** The input pane of the application. */
    private JPanel inputPane;
    /** The layout of the program. */
    private LayoutManager layout;
    
    /** The font "Futura Condensed", as used in the player names. */
    private Font futuraCondensed;
    /** The font "Lucida Sans", as used in the description. */
    private Font lucidaSans;
    
    /** The canvas that previews of the thumbnails will be drawn to. */
    private Canvas canvas;
    /** A selection box for a Fighter. */
    private JComboBox<String> fighterBox;
    /** An input field for inputting a name for a player. */
    private JTextField nameBox;
    /** A button. */
    private JButton button;
    
    /** The background template. */
    private BufferedImage bgTemplate;
    /** The foreground template. */
    private BufferedImage fgTemplate;
    
    /** The left Fighter. */
    private Fighter fighterLeft;
    /** The right Fighter. */
    private Fighter fighterRight;
    /** The tag of the player on the left. */
    private String tagLeft;
    /** The tag of the player on the right. */
    private String tagRight;
    /** Which round of the tournament is being played. */
    private String roundTitle;
    /** The event number of the tournament. */
    private int eventNumber;
    
    /** The Graphics object used to paint the canvas. */
    private Graphics2D currentThumbnail;
    /** The Constraints object for formatting. */
    private GridBagConstraints c;
    
    
    
    /** The central method of the application. */
    @Override
    public void run()
    {
        //initialize the app
        if (!init())
        {
            return;
        }
    
        //stub testing
        testExport();
        return;
        
//        //enter the loop
//        while (running)
//        {
//            if (export())
//            {
//                reset();
//            }
//        }
//
//        //exiting the loop ends the program.
//        window.dispose();
//        return;
        
        //todo implement

//        inputPane.setLayout(new GridBagLayout());
//        c.fill = GridBagConstraints.HORIZONTAL;
//
//        //stub test
//        String[] names = {"Mario", "Fox", "Kirby", "Samus"};
//        nameBox1 = new JTextField("Player 1", 1);
//        c.weightx = 0.5;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        inputPane.add(fighterBox1, c);
//
//        button = new JButton("Button 2");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 0;
//        inputPane.add(button, c);
//
//        button = new JButton("Button 3");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 2;
//        c.gridy = 0;
//        inputPane.add(button, c);
//
//        button = new JButton("Long-Named Button 4");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 40;      //make this component tall
//        c.weightx = 0.0;
//        c.gridwidth = 3;
//        c.gridx = 0;
//        c.gridy = 1;
//        inputPane.add(button, c);
//
//        button = new JButton("5");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 0;       //reset to default
//        c.weighty = 1.0;   //request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//        c.insets = new Insets(10,0,0,0);  //top padding
//        c.gridx = 1;       //aligned with button 2
//        c.gridwidth = 2;   //2 columns wide
//        c.gridy = 2;       //third row
//        inputPane.add(button, c);
        
        
//        fighterBox1 = new JComboBox<Fighter>(fighters);
//        fighterBox2 = new JComboBox<Fighter>(fighters);
//        variantBox1 = new JComboBox<Variant>();
//        variantBox2 = new JComboBox<Variant>();
//        nameBox1 = new JTextField("Player 1", 1);
//        nameBox2 = new JTextField("Player 2", 1);
//        window.add(nameBox1);
//        window.add(nameBox2);
    
//        window.setLayout(layout);
//        window.add(inputPane, c);
//        window.setVisible(true);
    }
    
    /**
     * The first things called when the app runs.
     * @return true if initialization was successful, false otherwise.
     */
    private boolean init()
    {
        //designates the App as running
        running = true;
        
        //begin font loading
        try
        {
            futuraCondensed = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Futura_Condensed_Regular.ttf"));
            lucidaSans = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Lucida_Sans_Regular.ttf"));
        }
        catch (FontFormatException | IOException e)
        {
            if (e instanceof IOException)
            {
                System.out.println("[ERROR] Font loading aborted.");
            }
            else
            {
                System.out.println("[ERROR] Font creation aborted.");
            }
            e.printStackTrace();
            return false;
        }
        
        //end font loading
        
        //begin window creation
        window = new JFrame("Thumbnail Generator v1.0");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        //end window loading
        
        //begin window icon
        BufferedImage windowIcon;
        try
        {
            windowIcon = ImageIO.read(new File("src/main/resources/icon.png"));
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Window icon load aborted.");
            e.printStackTrace();
            windowIcon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        }
        window.setIconImage(windowIcon);
        //end window icon
        
        //defines layout
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        
        //initializes and adds components
        inputPane = new JPanel(layout);
        c.fill = GridBagConstraints.HORIZONTAL;
    
        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        inputPane.add(button, c);
        
        //begin template loading
        try
        {
            bgTemplate = ImageIO.read(new File("src/main/resources/bg.png"));
            fgTemplate = ImageIO.read(new File("src/main/resources/fg.png"));
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Template load aborted.");
            e.printStackTrace();
            window.dispose();
            return false;
        }
        //end template loading
        
        
        //resets other vars
        reset();
        
        //reveals the window
        window.setVisible(true);
        window.requestFocus();
        
        //finish and return success flag
        return true;
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset()
    {
        //todo implement
    }
    
    /**
     * Generates a thumbnail from the accumulated state of the App class if everything is defined and prompts to save
     * the file to a user-defined location.
     * @return true if a thumbnail is successfully generated, false otherwise.
     */
    private boolean export()
    {
        if (isGeneratorReady())
        {
            //create canvas using background template
            currentThumbnail = bgTemplate.createGraphics();
            
            //draw both characters to the canvas in their respective places
            currentThumbnail.drawImage(fighterLeft.getRender(),0,0,639,639,0,0,639,639,null);
            currentThumbnail.drawImage(fighterRight.getRender(),640,0,1279,639,0,0,639,639,null);
            
            //draw the foreground template over the previous
            currentThumbnail.drawImage(fgTemplate,0,0,null);
            
            //draw text over the overlay
            currentThumbnail.setFont(futuraCondensed.deriveFont(72F));
            currentThumbnail.drawString(tagLeft,120,80);
            currentThumbnail.drawString(tagRight,735,80);
    
            currentThumbnail.setFont(futuraCondensed.deriveFont(60F));
            currentThumbnail.drawString("SLAMBANA #" + eventNumber,400,650);
            
            currentThumbnail.setFont(lucidaSans.deriveFont(48F));
            currentThumbnail.drawString(roundTitle,420,710);
            
            //begin save prompt
            try
            {
                FileDialog dialog = new FileDialog(window, "Save", FileDialog.SAVE);
                dialog.setVisible(true);
                String path = dialog.getDirectory() + dialog.getFile();
                ImageIO.write(bgTemplate, "PNG", new File(path));
            }
            catch (IOException e)
            {
                System.out.println("[ERROR] Failed to write thumbnail to file.");
                e.printStackTrace();
                return false;
            }
            //end save prompt
    
            return true;
        }
        return false;
    }
    /**
     * Checks if all of the information required to generate a thumbnail is
     * @return true if the generator is ready, false otherwise.
     */
    private boolean isGeneratorReady()
    {
        if (bgTemplate == null ||
            fgTemplate == null ||
            fighterLeft == null ||
            fighterRight == null ||
            tagLeft == null ||
            tagRight == null ||
            roundTitle == null ||
            eventNumber == 0)
        {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private void testExport()
    {
        fighterLeft = new Fighter(5);
        fighterRight = new Fighter(3);
        tagLeft = "Juneau";
        tagRight = "Aug";
        roundTitle = "WINNERS ROUND 1";
        eventNumber = 69;
        
        export();
    }
}
