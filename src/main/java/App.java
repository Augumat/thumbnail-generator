package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App implements Runnable
{
    /** Selection window size constraints. */
    private static final int MAIN_WIDTH = 515;
    private static final int MAIN_HEIGHT = 200;
    /** Preview window size constraints. */
    private static final int PREVIEW_WIDTH = 1280;
    private static final int PREVIEW_HEIGHT = 720;
    
    /** Whether or not the program is running. */
    private boolean running;
    /** The main window of the program. */
    private JFrame selectionWindow;
    /** The selection pane of the application. */
    private JPanel selectionPane;
    
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
    private String eventNumber;
    
    /** True if the preview window is visible, false otherwise. */
    private boolean previewVisible;
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
    
    
    
    /** The central method of the application. */
    @Override
    public void run()
    {
        //initialize the app
        if (!init())
        {
            return;
        }
    
        //stub testing todo remove
        //testExport();
        
        //enter the loop
        while (running)
        {
//            if (export())
//            {
//                reset();
//            }
        }

        //exiting the loop ends the program.
        selectionWindow.dispose();
        previewWindow.dispose();
        return;
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
            futuraCondensed = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Futura_Condensed_Regular.ttf")).deriveFont(72F);
            lucidaSans = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Lucida_Sans_Regular.ttf")).deriveFont(48F);
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
    
        //begin icon loading
        BufferedImage windowIcon;
        try
        {
            windowIcon = ImageIO.read(new File("src/main/resources/icon.png"));
        }
        catch (java.io.IOException e)
        {
            System.out.println("[ERROR] Window icon load aborted.");
            e.printStackTrace();
            return false;
        }
        //end icon loading
        
        //begin selection window creation
        selectionWindow = new JFrame("Thumbnail Generator v1.1");
        selectionWindow.setIconImage(windowIcon);
        selectionWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        selectionWindow.setResizable(false);
    
        selectionPane = new JPanel();
        selectionWindow.add(selectionPane);
        
        selectionPane.setLayout(null);
        selectionPane.setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
        
        //begin component creation
        Dimension buttonPreferredSize = new Dimension(120, 25);
        Dimension tagPreferredSize = new Dimension(180, 25);
        Dimension labelPreferredSize = new Dimension(85, 25);
        Dimension fighterPreferredSize = new Dimension(160, 24);
        Dimension variantPreferredSize = new Dimension(50, 24);
        Dimension matchPreferredSize = new Dimension(240, 25);
        Dimension eventPreferredSize = new Dimension(70, 25);
        Insets insets = new Insets(10, 9, 10, 9);
        Dimension componentSize;
        
        //begin main control buttons
        JButton bPreview = new JButton("Show Preview");
        bPreview.setPreferredSize(buttonPreferredSize);
        componentSize = bPreview.getPreferredSize();
        bPreview.setBounds(
                MAIN_WIDTH - insets.right - componentSize.width,
                MAIN_HEIGHT - insets.bottom - componentSize.height - 60,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bPreview);
        
        JButton bGenerate = new JButton("Generate");
        bGenerate.setPreferredSize(buttonPreferredSize);
        componentSize = bGenerate.getPreferredSize();
        bGenerate.setBounds(
                MAIN_WIDTH - insets.right - componentSize.width,
                MAIN_HEIGHT - insets.bottom - componentSize.height - 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bGenerate);
        
        JButton bReset = new JButton("Reset");
        bReset.setPreferredSize(buttonPreferredSize);
        componentSize = bReset.getPreferredSize();
        bReset.setBounds(
                MAIN_WIDTH - insets.right - componentSize.width,
                MAIN_HEIGHT - insets.bottom - componentSize.height,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bReset);
        //end main control buttons
        
        //begin tag/fighter/variant fields
        JTextField tTagLeft = new JTextField(tagLeft);
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
        
        JTextField tTagRight = new JTextField(tagRight);
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
    
        JComboBox cFighterLeft = new JComboBox();
        cFighterLeft.setPreferredSize(fighterPreferredSize);
        componentSize = cFighterLeft.getPreferredSize();
        cFighterLeft.setBounds(
                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cFighterLeft);
    
        JComboBox cFighterRight = new JComboBox();
        cFighterRight.setPreferredSize(fighterPreferredSize);
        componentSize = cFighterRight.getPreferredSize();
        cFighterRight.setBounds(
                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cFighterRight);
    
        JComboBox cVariantLeft = new JComboBox();
        cVariantLeft.setPreferredSize(variantPreferredSize);
        componentSize = cVariantLeft.getPreferredSize();
        cVariantLeft.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantLeft);
    
        JComboBox cVariantRight = new JComboBox();
        cVariantRight.setPreferredSize(variantPreferredSize);
        componentSize = cVariantRight.getPreferredSize();
        cVariantRight.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantRight);
        //end tag and character / costume fields
        
        //begin match data fields
        JTextField tMatchTitle = new JTextField(tagLeft);
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
    
        JTextField tEventNumber = new JTextField(tagLeft);
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
        previewWindow = new JFrame("Thumbnail Preview");
        previewWindow.setIconImage(windowIcon);
        previewWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        previewWindow.setResizable(false);
        
        previewCanvas = new Canvas();
        previewCanvas.setSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        previewWindow.add(previewCanvas);
        
        previewWindow.pack();
        
        previewVisible = false;
    
        //end preview window creation
        
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
            selectionWindow.dispose();
            return false;
        }
        //end template loading
        
        //resets other vars
        reset();
        
        //finish and return success flag
        return true;
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset()
    {
        //todo implement
        hidePreview();
        
        fighterLeft = new Fighter(0);
        fighterRight = new Fighter(0);
    }
    
    /** Reveals the preview window. */
    private void revealPreview()
    {
        BufferedImage previewThumbnail = generate();
        if (previewThumbnail != null)
        {
            previewCanvas.getGraphics().drawImage(previewThumbnail, 0, 0, null);
            previewWindow.setLocation(0, 0);
            previewVisible = true;
            previewWindow.setVisible(true);
            previewWindow.requestFocus();
        }
        else
        {
            System.out.println("[ERROR] Preview reveal failed.");
        }
    }
    /** Hides the preview window. */
    private void hidePreview()
    {
        previewVisible = false;
        previewWindow.setVisible(false);
        previewCanvas.getGraphics().drawRect(0,0, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        selectionWindow.requestFocus();
    }
    
    
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
            FileDialog dialog = new FileDialog(selectionWindow, "Save", FileDialog.SAVE);
            dialog.setVisible(true);
            String path = dialog.getDirectory() + dialog.getFile();
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
    /**
     * Generates a thumbnail and returns it as a new BufferedImage.
     * @return The newly generated thumbnail.
     */
    private BufferedImage generate()
    {
        if (isGeneratorReady())
        {
            //create canvas using background template
            currentThumbnail = bgTemplate.createGraphics();
        
            //draw both characters to the canvas in their respective places
            currentThumbnail.drawImage(fighterLeft.getRender(),0,0,639,639,0,0,639,639,null);
            currentThumbnail.drawImage(fighterRight.getRender(),1279,0,640,639,0,0,639,639,null);
        
            //draw the foreground template over the previous
            currentThumbnail.drawImage(fgTemplate,0,0,null);
        
            //begin text field drawing
            String slambana = "SLAMBAMA #";
            int leftStart = 0;
            int rightStart = 720;
            int playerBoxLength = 560;
            int centerLine = 640;
        
            FontMetrics futuraMetrics = currentThumbnail.getFontMetrics(futuraCondensed);
            FontMetrics lucidaMetrics = currentThumbnail.getFontMetrics(lucidaSans);
        
            int leftTagIndent = leftStart + ((playerBoxLength - (futuraMetrics.stringWidth(tagLeft))) / 2);
            int rightTagIndent = rightStart + ((playerBoxLength - (futuraMetrics.stringWidth(tagRight))) / 2);
            int eventNumberIndent = centerLine - ((futuraMetrics.stringWidth(slambana + eventNumber)) / 2);
            int roundNumberIndent = centerLine - ((lucidaMetrics.stringWidth(roundTitle)) / 2);
        
            currentThumbnail.setColor(Color.WHITE);
            currentThumbnail.setFont(futuraCondensed);
            currentThumbnail.drawString(tagLeft, leftTagIndent, 75);
            currentThumbnail.drawString(tagRight, rightTagIndent, 75);
            currentThumbnail.drawString(slambana + eventNumber, eventNumberIndent,655);
        
            currentThumbnail.setColor(new Color(160,160,160));
            currentThumbnail.setFont(lucidaSans);
            currentThumbnail.drawString(roundTitle, roundNumberIndent,710);
            //end text field drawing
            
            //finalize and return
            return copyImage(bgTemplate);
        }
        System.out.println("[ERROR] Thumbnail generation failed.");
        return null;
    }
    /**
     * Checks if all of the information required to generate a thumbnail is
     * @return true if the generator is ready, false otherwise.
     */
    private boolean isGeneratorReady()
    {
        if (bgTemplate == null ||
            fgTemplate == null ||
            fighterLeft == null || fighterLeft.getFighterID() == 0 ||
            fighterRight == null || fighterRight.getFighterID() == 0 ||
            tagLeft == null || tagLeft == "" ||
            tagRight == null || tagRight == "" ||
            roundTitle == null || eventNumber == "" ||
            eventNumber == null || eventNumber == ""
        )
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
        tempGraphics.dispose();
        return newImage;
    }
    
    
    
    
    
    
    
    
    
    //temp
    private void testExport()
    {
        fighterLeft = new Fighter(5);
        fighterRight = new Fighter(3);
        tagLeft = "SW | Sheepie";
        tagRight = "Anonymous Moniker";
        roundTitle = "LOSERS QUARTER FINALS";
        eventNumber = "57";
        
        export();
    }
}
