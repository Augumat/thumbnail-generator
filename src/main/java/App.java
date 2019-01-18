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
    
    /** The main window of the program. */
    private JFrame window;
    /** The input pane of the application. */
    private JPanel inputPane;
    /** The layout of the program. */
    private LayoutManager layout;
    
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
    private String round;
    /** The edition of the tournament. */
    private int edition;
    /** Whether or not the program is running. */
    private boolean running;
    
    /** The Graphics object used to paint the canvas. */
    private Graphics2D currentThumbnail;
    /** The Constraints object for formatting. */
    private GridBagConstraints c;
    
    
    
    /** The central method of the application. */
    @Override
    public void run()
    {
        init();
        
        while (running)
        {
            if (export())
            {
                reset();
            }
        }
        
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
     */
    private void init()
    {
        //designates the App as running
        running = true;
        
        //create window and set basic settings
        window = new JFrame("Thumbnail Generator v1.0");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        
        //set window icon
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
        
        //loads the basic template and initializes the Graphics object
        //open
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
            return;
        }
        //close
        
        //reveals the window
        window.setVisible(true);
        window.requestFocus();
    }
    
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
            currentThumbnail = bgTemplate.createGraphics();
            currentThumbnail.drawImage(fighterLeft.getRender(),0,0,639,639,0,0,639,639,null);
            currentThumbnail.drawImage(fighterLeft.getRender(),0,0,639,639,1279,0,640,639,null);
            currentThumbnail.drawImage(fgTemplate,0,0,null);
            try
            {
                FileDialog fDialog = new FileDialog(window, "Save", FileDialog.SAVE);
                fDialog.setVisible(true);
                String path = fDialog.getDirectory() + fDialog.getFile();
                ImageIO.write(bgTemplate, "PNG", new File(path));
            }
            catch (IOException e)
            {
                System.out.println("[ERROR] Failed to write thumbnail to file.");
                e.printStackTrace();
                return false;
            }
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
            round == null ||
            edition == 0)
        {
            return false;
        }
        return true;
    }
}
