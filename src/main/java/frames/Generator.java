package main.java.frames;

import com.google.gson.Gson;
import main.java.Fighter;
import main.java.templates.Template;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The main window of the program, responsible for receiving input and interfacing with the user.
 */
public class Generator extends JFrame implements ItemListener {
    
    private static final String version = "v2.0";
    
    private static final int WINDOW_WIDTH = 515;
    private static final int WINDOW_HEIGHT = 175;
    
    /** The selection pane of the application. */
    private JPanel selectionPane;
    
    /** Records the tag of the player on the left. */
    private JTextField tTagLeft;
    /** Records the tag of the player on the right. */
    private JTextField tTagRight;
    
    /** Records the fighter that the player on the left chose. */
    private JComboBox<Fighter> cFighterLeft;
    /** Records the fighter that the player on the right chose. */
    private JComboBox<Fighter> cFighterRight;
    
    /** Records the variant that the player on the left chose. */
    private JComboBox<Fighter> cVariantLeft;
    /** Records the variant that the player on the right chose. */
    private JComboBox<Fighter> cVariantRight;
    
    /** Records the match title (grand finals, losers semifinals, etc.). */
    private JTextField tMatchTitle;
    
    /** Records the event number (34 would produce Slambana #34). */
    private JTextField tEventNumber;
    
    /** The button that creates a preview window of what the thumbnail would look like in its current state. */
    private JButton bPreview;
    /** The button that generates the thumbnail and cues a save prompt with the currently entered information. */
    private JButton bGenerate;
    /** The button that resets all of the fields of the Generator. */
    private JButton bReset;
    
    /** The font "Futura Condensed", as used in the player names. */
    private Font futuraCondensed;
    /** The font "Lucida Sans", as used in the description. */
    private Font lucidaSans;
    
    /** The icon that all of the frames of this program use. */
    private BufferedImage windowIcon;
    
    
    
    public Generator() {
        
        // Initialize the Generator with its proper name and version
        super("Thumbnail Generator " + version);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        //begin font loading ...
        try {
            futuraCondensed = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/futura_condensed_regular.ttf")).deriveFont(72F);
            lucidaSans = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/lucida_sans_regular.ttf")).deriveFont(48F);
        } catch (FontFormatException | IOException e) {
            if (e instanceof IOException) {
                System.out.println("[ERROR] Font loading aborted.");
            } else {
                System.out.println("[ERROR] Font creation aborted.");
            }
            e.printStackTrace();
            System.exit(1);
        }
        //end font loading
    
        //begin icon loading ...
        windowIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            windowIcon = ImageIO.read(getClass().getResource("/icon.png"));
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Window icon load aborted.");
            e.printStackTrace();
            System.exit(1);
        }
        this.setIconImage(windowIcon);
        //end icon loading
    
        buildGui();
        
        this.setVisible(true);
    }
    
    
    
    /** Creates and arranges all of the GUI elements of the generator. */
    private void buildGui() {
        
        // Create the selection pane with the line in it and add it to this frame
        selectionPane = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawLine(5, 106, WINDOW_WIDTH - 5, 106);
                //g.drawImage(Temp.getWindowBackground(), 0, 0, null);
                //todo maybe add this if I do a button overhaul
            }
        };
        this.add(selectionPane);
        selectionPane.setLayout(null);
        selectionPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    
        // Create components
    
        //begin main control buttons
        Dimension buttonPreferredSize = new Dimension(120, 25);
        Dimension bigButtonPreferredSize = new Dimension(160, 45);
        Insets insets = new Insets(10, 9, 10, 9);
        Dimension componentSize;
    
        // Create the preview button
        bPreview = new JButton("Show Preview");
        bPreview.addActionListener(e -> new Preview(generate(), windowIcon));
        bPreview.setPreferredSize(buttonPreferredSize);
        componentSize = bPreview.getPreferredSize();
        bPreview.setBounds(
                insets.left + 38,
                WINDOW_HEIGHT - insets.bottom - buttonPreferredSize.height - 10,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bPreview);
    
        // Create the generate button
        bGenerate = new JButton("Generate");
        bGenerate.addActionListener(e -> {
            export();
            reset();
        });
        bGenerate.setPreferredSize(bigButtonPreferredSize);
        componentSize = bGenerate.getPreferredSize();
        bGenerate.setBounds(
                insets.left + buttonPreferredSize.width + 48,
                WINDOW_HEIGHT - insets.bottom - bigButtonPreferredSize.height,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bGenerate);
    
        // Create the reset button
        bReset = new JButton("Reset");
        bReset.addActionListener(e -> reset());
        bReset.setPreferredSize(buttonPreferredSize);
        componentSize = bReset.getPreferredSize();
        bReset.setBounds(
                insets.left + buttonPreferredSize.width + bigButtonPreferredSize.width + 58,
                WINDOW_HEIGHT - insets.bottom - buttonPreferredSize.height - 10,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(bReset);
        //end main control buttons
    
        Dimension tagPreferredSize = new Dimension(180, 25);
        Dimension labelPreferredSize = new Dimension(85, 25);
        Dimension fighterPreferredSize = new Dimension(160, 24);
        Dimension variantPreferredSize = new Dimension(50, 24);
        Dimension matchPreferredSize = new Dimension(240, 25);
        Dimension eventPreferredSize = new Dimension(70, 25);
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset() {
        
        //todo remove the editable options
        tTagLeft.setEditable(true);
        tTagLeft.setText("");
        tTagRight.setEditable(true);
        tTagRight.setText("");
        
        cFighterLeft.setSelectedIndex(0);
        cFighterLeft.setEnabled(true);
        cVariantLeft.removeAllItems();
        cVariantLeft.setEnabled(false);
        cFighterRight.setSelectedIndex(0);
        cFighterRight.setEnabled(true);
        cVariantRight.removeAllItems();
        cVariantRight.setEnabled(false);
        
        tMatchTitle.setEditable(true);
        tMatchTitle.setText("");
        
        tEventNumber.setEditable(true);
        tEventNumber.setText("");
    }
    
    /**
     * Generates a thumbnail from the accumulated state of the App class if everything is defined and prompts to save
     * the file to a user-defined location.
     * @return true if a thumbnail is successfully generated, false otherwise.
     */
    private void export() {
        //generates the thumbnail and saves it to a variable
        BufferedImage generatedThumbnail = generate();
        
        //begin save prompt
        try {
            //todo use JFileChooser instead of FileDialog
            //            JFileChooser chooser = new JFileChooser();
            //            chooser.setDialogTitle("AyyLmao");
            //            String path = dialog.getDirectory() + dialog.getFile();
            //            ImageIO.write(generatedThumbnail, "PNG", new File(path));
            //            chooser.showDialog(selectionWindow, "Save Thumbnail");
            
            FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
            dialog.setVisible(true);
            String path = dialog.getDirectory() + dialog.getFile(); //todo this line and dialog.getFile specifically are interesting...
            ImageIO.write(generatedThumbnail, "PNG", new File(path));
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write thumbnail to file.");
            e.printStackTrace();
        }
        //end save prompt
    }
    
    //todo generate goes to Generator, export stays
    /**
     * Generates a thumbnail and returns it as a new BufferedImage.
     * @return The newly generated thumbnail.
     */
    private BufferedImage generate() {
        
        // Grab the resources needed from the currently selected template
        //todo remove temp and load actual current template
        final String tempSlambana =
                "{\n" +
                "  \"templateDisplayName\": \"Slambana\",\n" +
                "  \"templateVersionNumber\": 1.0,\n" +
                "  \"directoryName\": \"slambana\",\n" +
                "  \"eventName\": \"Slambana #\"\n" +
                "}";
        Template template = (new Gson()).fromJson(tempSlambana, Template.class);
        
        //create canvas using background template
        BufferedImage thumbnail = copyImage(template.getBackground());
        Graphics currentThumbnail = thumbnail.createGraphics();
    
        //draw both characters to the canvas in their respective places
        try {
            // Draw the left Fighter
            currentThumbnail.drawImage(
                    ((Fighter) cFighterLeft.getSelectedItem()).getRender(cVariantLeft.getSelectedIndex()),
                    0, 0, 639, 639, 0, 0, 639, 639, null
            );
            // Draw the Right fighter
            currentThumbnail.drawImage(
                    ((Fighter) cFighterRight.getSelectedItem()).getRender(cVariantRight.getSelectedIndex()),
                    1279, 0, 640, 639, 0, 0, 639, 639, null
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    
        //draw the foreground template over the previous
        currentThumbnail.drawImage(template.getForeground(),0,0,null);
    
        //begin text field drawing
        int leftStart = 0;
        int rightStart = 720;
        int playerBoxLength = 560;
        int centerLine = 640;
    
        FontMetrics futuraMetrics = currentThumbnail.getFontMetrics(futuraCondensed);
        FontMetrics lucidaMetrics = currentThumbnail.getFontMetrics(lucidaSans);
        
        int leftTagIndent = leftStart + ((playerBoxLength - (futuraMetrics.stringWidth(tTagLeft.getText()))) / 2);
        int rightTagIndent = rightStart + ((playerBoxLength - (futuraMetrics.stringWidth(tTagRight.getText()))) / 2);
        int eventNumberIndent = centerLine - ((futuraMetrics.stringWidth(template.getEventName() + tEventNumber.getText())) / 2);
        int roundNumberIndent = centerLine - ((lucidaMetrics.stringWidth(tMatchTitle.getText())) / 2);
    
        currentThumbnail.setColor(Color.WHITE);
        currentThumbnail.setFont(futuraCondensed);
        currentThumbnail.drawString(tTagLeft.getText(), leftTagIndent, 75);
        currentThumbnail.drawString(tTagRight.getText(), rightTagIndent, 75);
        currentThumbnail.drawString(template.getEventName() + tEventNumber.getText(), eventNumberIndent,655);
    
        currentThumbnail.setColor(new Color(160,160,160));
        currentThumbnail.setFont(lucidaSans);
        currentThumbnail.drawString(tMatchTitle.getText(), roundNumberIndent,710);
        //end text field drawing
    
        //finalize and return
        return thumbnail;
    }
    
    /**
     * Copies a passed BufferedImage and returns an identical copy.
     * @param oldImage The image to be copied.
     * @return A copy of the original image.
     */
    private static BufferedImage copyImage(BufferedImage oldImage) {
        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());
        Graphics2D tempGraphics = newImage.createGraphics();
        tempGraphics.drawImage(oldImage, 0, 0, null);
        return newImage;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
    
    }
    
    //todo add radio button menu items for different fighter sorting modes
}
