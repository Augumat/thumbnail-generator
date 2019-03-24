package main.java;

import main.java.frames.Preview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Temp implements Runnable
{
    /** todo remove after fixing variants */
    private static final String[] TEMP_VARIANCE = {"0","1","2","3","4","5","6","7"};
    
    //begin vars
    /** Selection window size constraints. */
    private static final int MAIN_WIDTH = 515;
    private static final int MAIN_HEIGHT = 175;
    /** Preview window size constraints. */
    private static final int PREVIEW_WIDTH = 1280;
    private static final int PREVIEW_HEIGHT = 720;
    /** Version Number. */
    private static final String version = "v2.0";
    
    /** The main window of the program. */
    private JFrame selectionWindow;
    /** The selection pane of the application. */
    private JPanel selectionPane;
    /** Components of the selection panel. */
    private JButton bPreview;
    private JButton bGenerate;
    private JButton bReset;
    private JTextField tTagLeft;
    private JTextField tTagRight;
    private JComboBox cFighterLeft;
    private JComboBox cFighterRight;
    private JComboBox cVariantLeft;
    private JComboBox cVariantRight;
    private JTextField tMatchTitle;
    private JTextField tEventNumber;
    
    //temp
    private BufferedImage windowIcon;
    
    /** Flag variables for data entered. */
    
    /** The left Fighter. */
    private Fighter fighterLeft;
    /** The right Fighter. */
    private Fighter fighterRight;
    /** The tag of the player on the left. */
    private String tagLeft;
    /** The tag of the player on the right. */
    private String tagRight;
    /** Which round of the tournament is being played. */
    private String matchTitle;
    /** The event number of the tournament. */
    private String eventNumber;
    
    /** The JFrame containing the current preview thumbnail. */
    private JFrame previewWindow;
    /** The canvas that previews of the thumbnails will be drawn to. */
    private Canvas previewCanvas;
    /** The Graphics object used to paint the canvas. */
    private Graphics2D currentThumbnail;
    
    /** The font "Futura Condensed", as used in the player names. */
    private Font futuraCondensed;
    /** The font "Lucida Sans", as used in the description. */
    private Font lucidaSans;
    
    /** The background template. */
    private BufferedImage bgTemplate;
    /** The foreground template. */
    private BufferedImage fgTemplate;
    //end vars
    
    
    
    /** The central method of the application. */
    @Override
    public void run()
    {
        //initialize the app
        init();
        
        //???
    }
    
    //todo was temp for testing, might be added as util of some sort
//    public static BufferedImage getWindowBackground()
//    {
//        try
//        {
//            return ImageIO.read(Temp.class.getResource("/generator.png"));
//        }
//        catch (java.io.IOException e)
//        {
//            System.out.println("[ERROR] Window background load aborted.");
//            e.printStackTrace();
//            System.exit(1);
//            return null;
//        }
//    }
    
    /**
     * The first things called when the app runs.
     * @return true if initialization was successful, false otherwise.
     */
    private void init()
    {
        //begin font loading
        try
        {
            futuraCondensed = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/futura_condensed_regular.ttf")).deriveFont(72F);
            lucidaSans = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/lucida_sans_regular.ttf")).deriveFont(48F);
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
            System.exit(1);
        }
        //end font loading
    
        //begin icon loading
        windowIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try
        {
            windowIcon = ImageIO.read(getClass().getResource("/icon.png"));
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Window icon load aborted.");
            e.printStackTrace();
            System.exit(1);
        }
        //end icon loading
        
        //begin selection window creation
        //todo use this instead
        //Generator mainWindow = new Generator();
        //Preview previewWindow = new Preview(null); //(takes a BufferedImage input, null is blank)
        //todo actually maybe don't do that, create a new preview window whenever it's needed
        selectionWindow = new JFrame("Thumbnail Generator " + version);
        selectionWindow.setIconImage(windowIcon);
        selectionWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        selectionWindow.setResizable(false);
    
        selectionPane = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawLine(5, 106, MAIN_WIDTH - 5, 106);
                //g.drawImage(Temp.getWindowBackground(), 0, 0, null);
                //todo maybe add this if I do a button overhaul
            }
        };
        selectionWindow.add(selectionPane);
        
        selectionPane.setLayout(null);
        selectionPane.setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
        
        //begin component creation
        Dimension buttonPreferredSize = new Dimension(120, 25);
        Dimension bigButtonPreferredSize = new Dimension(160, 45);
        Dimension tagPreferredSize = new Dimension(180, 25);
        Dimension labelPreferredSize = new Dimension(85, 25);
        Dimension fighterPreferredSize = new Dimension(160, 24);
        Dimension variantPreferredSize = new Dimension(50, 24);
        Dimension matchPreferredSize = new Dimension(240, 25);
        Dimension eventPreferredSize = new Dimension(70, 25);
        Insets insets = new Insets(10, 9, 10, 9);
        Dimension componentSize;
        
        //begin main control buttons
        bPreview = new JButton("Show Preview");
        bPreview.addActionListener(e -> revealPreview());
        bPreview.setPreferredSize(buttonPreferredSize);
        componentSize = bPreview.getPreferredSize();
        bPreview.setBounds(
                insets.left + 38,
                MAIN_HEIGHT - insets.bottom - buttonPreferredSize.height - 10,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bPreview);
        
        bGenerate = new JButton("Generate");
        bGenerate.addActionListener(e ->
        {
            export();
            reset();
        });
        bGenerate.setPreferredSize(bigButtonPreferredSize);
        componentSize = bGenerate.getPreferredSize();
        bGenerate.setBounds(
                insets.left + buttonPreferredSize.width + 48,
                MAIN_HEIGHT - insets.bottom - bigButtonPreferredSize.height,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bGenerate);
        
        bReset = new JButton("Reset");
        bReset.addActionListener(e -> reset());
        bReset.setPreferredSize(buttonPreferredSize);
        componentSize = bReset.getPreferredSize();
        bReset.setBounds(
                insets.left + buttonPreferredSize.width + bigButtonPreferredSize.width + 58,
                MAIN_HEIGHT - insets.bottom - buttonPreferredSize.height - 10,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bReset);
        //end main control buttons
        
        //begin tag/fighters/variant fields
        tTagLeft = new JTextField(tagLeft);
        tTagLeft.addActionListener(e ->
        {
            tagLeft = tTagLeft.getText();
            tTagLeft.setEditable(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        tTagLeft.setPreferredSize(tagPreferredSize);
        componentSize = tTagLeft.getPreferredSize();
        tTagLeft.setBounds(
                insets.left + labelPreferredSize.width,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        JLabel lTagLeft = new JLabel("Player Tag (L)");
        lTagLeft.setPreferredSize(labelPreferredSize);
        componentSize = lTagLeft.getPreferredSize();
        lTagLeft.setBounds(
                insets.left,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(lTagLeft);
        selectionPane.add(tTagLeft);
        
        tTagRight = new JTextField(tagRight);
        tTagRight.addActionListener(e ->
        {
            tagRight = tTagRight.getText();
            tTagRight.setEditable(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        tTagRight.setPreferredSize(tagPreferredSize);
        componentSize = tTagRight.getPreferredSize();
        tTagRight.setBounds(
                insets.left + labelPreferredSize.width,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        JLabel lTagRight = new JLabel("Player Tag (R)");
        lTagRight.setPreferredSize(labelPreferredSize);
        componentSize = lTagRight.getPreferredSize();
        lTagRight.setBounds(
                insets.left,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(lTagRight);
        selectionPane.add(tTagRight);
    
        cFighterLeft = new JComboBox(Fighter.FIGHTER_OPTIONS);
        cFighterLeft.addActionListener(e ->
        {
            cFighterLeft.setEnabled(false);
            for (String current: TEMP_VARIANCE/*todo Variant select remove --- Fighter.VARIANT_OPTIONS[fighterLeft.getId()]*/)
            {
                cVariantLeft.addItem(current);
            }
            cVariantLeft.setEnabled(true);
        });
        cFighterLeft.setPreferredSize(fighterPreferredSize);
        componentSize = cFighterLeft.getPreferredSize();
        cFighterLeft.setBounds(
                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cFighterLeft);
    
        cFighterRight = new JComboBox(Fighter.FIGHTER_OPTIONS);
        cFighterRight.addActionListener(e ->
        {
            cFighterRight.setEnabled(false);
            for (String current: TEMP_VARIANCE/*todo Variant select remove --- Fighter.VARIANT_OPTIONS[fighterRight.getId()]*/)
            {
                cVariantRight.addItem(current);
            }
            cVariantRight.setEnabled(true);
        });
        cFighterRight.setPreferredSize(fighterPreferredSize);
        componentSize = cFighterRight.getPreferredSize();
        cFighterRight.setBounds(
                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cFighterRight);
    
        cVariantLeft = new JComboBox();
        cVariantLeft.addActionListener(e ->
        {
            fighterLeft = new Fighter(cFighterLeft.getSelectedIndex(), cVariantLeft.getSelectedIndex());
            cVariantLeft.setEnabled(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        cVariantLeft.setPreferredSize(variantPreferredSize);
        componentSize = cVariantLeft.getPreferredSize();
        cVariantLeft.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantLeft);
    
        cVariantRight = new JComboBox();
        cVariantRight.addActionListener(e ->
        {
            fighterRight = new Fighter(cFighterRight.getSelectedIndex(), cVariantRight.getSelectedIndex());
            cVariantRight.setEnabled(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        cVariantRight.setPreferredSize(variantPreferredSize);
        componentSize = cVariantRight.getPreferredSize();
        cVariantRight.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantRight);
        //end tag/fighters/variant fields
        
        //begin match data fields
        tMatchTitle = new JTextField(tagLeft);
        tMatchTitle.addActionListener(e ->
        {
            matchTitle = tMatchTitle.getText();
            tMatchTitle.setEditable(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        tMatchTitle.setPreferredSize(matchPreferredSize);
        componentSize = tMatchTitle.getPreferredSize();
        tMatchTitle.setBounds(
                insets.left + labelPreferredSize.width,
                insets.top + 60,
                componentSize.width,
                componentSize.height
        );
        JLabel lMatchTitle = new JLabel("      Match Title");
        lMatchTitle.setPreferredSize(labelPreferredSize);
        componentSize = lMatchTitle.getPreferredSize();
        lMatchTitle.setBounds(
                insets.left,
                insets.top + 60,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(lMatchTitle);
        selectionPane.add(tMatchTitle);
    
        tEventNumber = new JTextField(tagLeft);
        tEventNumber.addActionListener(e ->
        {
            eventNumber = tEventNumber.getText();
            tEventNumber.setEditable(false);
            if (isGeneratorReady())
            {
                fullTrigger();
            }
        });
        tEventNumber.setPreferredSize(eventPreferredSize);
        componentSize = tEventNumber.getPreferredSize();
        tEventNumber.setBounds(
                insets.left + labelPreferredSize.width + matchPreferredSize.width + eventPreferredSize.width + 31,
                insets.top + 60,
                componentSize.width,
                componentSize.height
        );
        JLabel lEventNumber = new JLabel("  Event Number");
        lEventNumber.setPreferredSize(labelPreferredSize);
        componentSize = lEventNumber.getPreferredSize();
        lEventNumber.setBounds(
                insets.left + labelPreferredSize.width + matchPreferredSize.width + 10,
                insets.top + 60,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(lEventNumber);
        selectionPane.add(tEventNumber);
        
        //end match data fields
        
        //end component creation
        
        selectionWindow.pack();
        
        selectionWindow.setLocationRelativeTo(null);
        selectionWindow.setVisible(true);
        selectionWindow.requestFocus();
        
        //end selection window creation
    
        //begin preview window creation
//        previewWindow = new JFrame("Thumbnail Preview");
//        previewWindow.setIconImage(windowIcon);
//        previewWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
//        previewWindow.setResizable(false);
    
//        previewCanvas = new Canvas();
//        previewCanvas.setSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
//        previewWindow.add(previewCanvas);
//
//        previewWindow.pack();
        
        //end preview window creation
        
        //begin template loading
        try
        {
            //todo add multiple template support
            bgTemplate = ImageIO.read(getClass().getResource("/templates/slambana/bg.png"));
            fgTemplate = ImageIO.read(getClass().getResource("/templates/slambana/fg.png"));
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Template load aborted.");
            e.printStackTrace();
            selectionWindow.dispose();
            System.exit(1);
        }
        //end template loading
        
        //resets other vars
        reset();
    }
    
    /** The method that is called when every field is filled. */
    private void fullTrigger()
    {
        bGenerate.setEnabled(true);
        bPreview.setEnabled(true);
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset()
    {
        hidePreview();
        
        bPreview.setEnabled(true);
        bGenerate.setEnabled(false);
        
        tagLeft = "";
        tTagLeft.setEditable(true);
        tTagLeft.setText("");
        tagRight = "";
        tTagRight.setEditable(true);
        tTagRight.setText("");
        
        fighterLeft = new Fighter(0);
        cFighterLeft.setSelectedIndex(0);
        cFighterLeft.setEnabled(true);
        cVariantLeft.removeAllItems();
        cVariantLeft.setEnabled(false);
        fighterRight = new Fighter(0);
        cFighterRight.setSelectedIndex(0);
        cFighterRight.setEnabled(true);
        cVariantRight.removeAllItems();
        cVariantRight.setEnabled(false);
        
        matchTitle = "";
        tMatchTitle.setEditable(true);
        tMatchTitle.setText("");
        eventNumber = "";
        tEventNumber.setEditable(true);
        tEventNumber.setText("");
    }
    
    
    
    /** Reveals the preview window. */
    private void revealPreview()
    {
        new Preview(generate(), windowIcon);
//        if (previewThumbnail != null)
//        {
//            System.out.println("Entered preview");
//
//            previewWindow = new Preview(previewThumbnail, icon);
//
//            previewWindow.setVisible(true);
//            Graphics tempGraphics = previewWindow.getGraphics();
//            tempGraphics.drawRect(1,1,30,30);
//            tempGraphics.drawImage(previewThumbnail, 0, 0, previewThumbnail.getWidth(), previewThumbnail.getHeight(), null);
//            previewWindow.requestFocus();
//        }
//        else
//        {
//            System.out.println("[ERROR] Preview reveal failed.");
//        }
    }
    /** Hides the preview window. */
    private void hidePreview()
    {
//        previewWindow.setVisible(false);
//        selectionWindow.requestFocus();
    }
    
    //todo generate goes to Generator, export stays
    /**
     * Generates a thumbnail from the accumulated state of the App class if everything is defined and prompts to save
     * the file to a user-defined location.
     * @return true if a thumbnail is successfully generated, false otherwise.
     */
    private boolean export()
    {
        //generates the thumbnail and saves it to a variable
        BufferedImage generatedThumbnail = generate();
        
        //begin save prompt
        try
        {
            //todo use JFileChooser instead of FileDialog
//            JFileChooser chooser = new JFileChooser();
//            chooser.setDialogTitle("AyyLmao");
//            String path = dialog.getDirectory() + dialog.getFile();
//            ImageIO.write(generatedThumbnail, "PNG", new File(path));
//            chooser.showDialog(selectionWindow, "Save Thumbnail");
            
            FileDialog dialog = new FileDialog(selectionWindow, "Save", FileDialog.SAVE);
            dialog.setVisible(true);
            String path = dialog.getDirectory() + dialog.getFile(); //todo this line and dialog.getFile specifically are interesting...
            ImageIO.write(generatedThumbnail, "PNG", new File(path));
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
    //todo generate goes to Generator, export stays
    /**
     * Generates a thumbnail and returns it as a new BufferedImage.
     * @return The newly generated thumbnail.
     */
    private BufferedImage generate()
    {
        if (isGeneratorReady())
        {
            //create canvas using background template
            BufferedImage newThumbnail = copyImage(bgTemplate);
            currentThumbnail = newThumbnail.createGraphics();
        
            //draw both characters to the canvas in their respective places
            try {
                currentThumbnail.drawImage(fighterLeft.getRender(0), 0, 0, 639, 639, 0, 0, 639, 639, null);
                currentThumbnail.drawImage(fighterRight.getRender(0), 1279, 0, 640, 639, 0, 0, 639, 639, null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        
            //draw the foreground template over the previous
            currentThumbnail.drawImage(fgTemplate,0,0,null);
        
            //begin text field drawing
            String slambana = "SLAMBANA #";
            int leftStart = 0;
            int rightStart = 720;
            int playerBoxLength = 560;
            int centerLine = 640;
        
            FontMetrics futuraMetrics = currentThumbnail.getFontMetrics(futuraCondensed);
            FontMetrics lucidaMetrics = currentThumbnail.getFontMetrics(lucidaSans);
        
            int leftTagIndent = leftStart + ((playerBoxLength - (futuraMetrics.stringWidth(tagLeft))) / 2);
            int rightTagIndent = rightStart + ((playerBoxLength - (futuraMetrics.stringWidth(tagRight))) / 2);
            int eventNumberIndent = centerLine - ((futuraMetrics.stringWidth(slambana + eventNumber)) / 2);
            int roundNumberIndent = centerLine - ((lucidaMetrics.stringWidth(matchTitle)) / 2);
        
            currentThumbnail.setColor(Color.WHITE);
            currentThumbnail.setFont(futuraCondensed);
            currentThumbnail.drawString(tagLeft, leftTagIndent, 75);
            currentThumbnail.drawString(tagRight, rightTagIndent, 75);
            currentThumbnail.drawString(slambana + eventNumber, eventNumberIndent,655);
        
            currentThumbnail.setColor(new Color(160,160,160));
            currentThumbnail.setFont(lucidaSans);
            currentThumbnail.drawString(matchTitle, roundNumberIndent,710);
            //end text field drawing
            
            //finalize and return
            return newThumbnail;
        }
        System.out.println("[ERROR] Thumbnail generation failed.");
        return null;
    }
    //todo make this check flags after moving it to Generator, or just remove entirely and initialize all the values at the beginning
    //todo keep flags for whether or not a Fighter is selected
    /**
     * Checks if all of the information required to generate a thumbnail is
     * @return true if the generator is ready, false otherwise.
     */
    private boolean isGeneratorReady()
    {
        if (bgTemplate == null||
            fgTemplate == null ||
            fighterLeft == null || fighterLeft.getId() == 0 ||
            fighterRight == null || fighterRight.getId() == 0 ||
            tagLeft == null || tagLeft.equals("") ||
            tagRight == null || tagRight.equals("") ||
            matchTitle == null || matchTitle.equals("") ||
            eventNumber == null || eventNumber.equals(""))
        {
            return false;
        }
        return true;
    }
    
    /**
     * Copies a passed BufferedImage and returns an identical copy.
     * @param oldImage The image to be copied.
     * @return A copy of the original image.
     */
    private static BufferedImage copyImage(BufferedImage oldImage)
    {
        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());
        Graphics2D tempGraphics = newImage.createGraphics();
        tempGraphics.drawImage(oldImage, 0, 0, null);
        return newImage;
    }
}
