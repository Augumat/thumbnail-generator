package main.java.frames;

import com.google.gson.Gson;
import main.java.Fighter;
import main.java.frames.components.FighterSelectBox;
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
    private static final int WINDOW_HEIGHT = 205;
    
    /** The selection pane of the application. */
    private JPanel selectionPane;
    
    /** The menu containing all of the relevant options regarding templates and Fighter ordering. */
    private JMenuBar menuBar;
    /** The menu for selecting which template to use for the generated thumbnail. */
    private JMenu templateMenu;
    /** The list of Templates loaded by this version of the generator. */
    private Template[] templates;
    /** */
    private JMenu fighterSortMenu;
    
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
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
    
        //begin template loading
        //todo remove temp and load actual templates from the folder
        final String tempSlambana = "{\n" +
                        "  \"templateDisplayName\": \"Slambana\",\n" +
                        "  \"templateVersionNumber\": 1.0,\n" +
                        "  \"directoryName\": \"slambana\",\n" +
                        "  \"eventName\": \"Slambana #\"\n" +
                        "}";
        templates = new Template[1];
        templates[0] = (new Gson()).fromJson(tempSlambana, Template.class);
//        try
//        {
//            //todo create a file in the directory and then try to discover all templates in the folder
//            templates = new Template[tempFileName.discover.size];
//            for (int i = 0; i < templates.length; i++) {
//                templates[i] = new Gson().fromJson(tempFileName.get, Template.class);
//            }
//            //bgTemplate = ImageIO.read(getClass().getResource("/templates/slambana/bg.png"));
//            //fgTemplate = ImageIO.read(getClass().getResource("/templates/slambana/fg.png"));
//        }
//        catch (java.io.IOException e)
//        {
//            System.out.println("[ERROR] Template load aborted.");
//            e.printStackTrace();
//            System.exit(-1);
//        }
        //end template loading
    
        //begin fighter sort loading
        //todo implement
        //end fighter sort loading
        
        //todo load saved settings from last session
        
        // Build the GUI and all related components
        buildGui();
        
        // Reset everything to the defaults and reveal the window
        reset();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.requestFocus();
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
                g.drawLine(5, 116, WINDOW_WIDTH - 13, 116);
                //g.drawImage(Temp.getWindowBackground(), 0, 0, null);
                //todo maybe add this if I do a button overhaul
            }
        };
        this.add(selectionPane);
        selectionPane.setLayout(null);
        selectionPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        //enter menu system
        // Create the menu bar
        menuBar = new JMenuBar();
        menuBar.setBounds(
                0,
                0,
                WINDOW_WIDTH,
                20
        );
        
        // Create the template menu
        templateMenu = new JMenu("Templates");
        for (Template currentTemplate: templates) {
            JRadioButtonMenuItem current = new JRadioButtonMenuItem(currentTemplate.toString());
            current.addActionListener(e -> System.out.println("get rid of this")/*todo*/);
            templateMenu.add(current);
        }
        templateMenu.getItem(0).setSelected(true);
        menuBar.add(templateMenu);
        
        // Create the fighter sort menu
        //todo implement
//        fighterSortMenu = new JMenu("Fighter Sort Mode");
//        for (FighterSort currentSort: fighterSorts) {
//            JRadioButtonMenuItem current = new JRadioButtonMenuItem(currentSort.toString());
//            templateMenu.add();
//        }
//        fighterSortMenu.getItem(0).setSelected(true);
//        menuBar.add(fighterSortMenu);
        
        selectionPane.add(menuBar);
        //end menu system
        
        //begin main control buttons
        Dimension buttonPreferredSize = new Dimension(120, 25);
        Dimension bigButtonPreferredSize = new Dimension(160, 45);
        Insets insets = new Insets(26, 9, 36, 9);
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
    
        //begin tag fields
        tTagLeft = new JTextField();
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
    
        tTagRight = new JTextField();
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
        //end tag fields
        
        Dimension matchPreferredSize = new Dimension(240, 25);
        Dimension eventPreferredSize = new Dimension(70, 25);
        
        //begin match data fields
        tMatchTitle = new JTextField();
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
    
        tEventNumber = new JTextField();
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
    
        //todo -----------------------------------------------------------------------------------------------Below here
        
//        Dimension fighterPreferredSize = new Dimension(160, 24);
//        Dimension variantPreferredSize = new Dimension(50, 24);
//
//        cFighterLeft = new FighterSelectBox(sortedFighters);
//        cFighterLeft.addActionListener(e ->
//        {
//            cVariantLeft.setup(cFighterLeft.getSelectedItem());
//            cVariantLeft.setEnabled(true);
//        });
//        cFighterLeft.setPreferredSize(fighterPreferredSize);
//        componentSize = cFighterLeft.getPreferredSize();
//        cFighterLeft.setBounds(
//                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
//                insets.top,
//                componentSize.width,
//                componentSize.height
//        );
//        selectionPane.add(cFighterLeft);
//
//        cFighterRight = new JComboBox(Fighter.FIGHTER_OPTIONS);
//        cFighterRight.addActionListener(e ->
//        {
//            cFighterRight.setEnabled(false);
//            for (String current: TEMP_VARIANCE/*todo Variant select remove --- Fighter.VARIANT_OPTIONS[fighterRight.getId()]*/)
//            {
//                cVariantRight.addItem(current);
//            }
//            cVariantRight.setEnabled(true);
//        });
//        cFighterRight.setPreferredSize(fighterPreferredSize);
//        componentSize = cFighterRight.getPreferredSize();
//        cFighterRight.setBounds(
//                insets.left + tagPreferredSize.width + labelPreferredSize.width + 10,
//                insets.top + 30,
//                componentSize.width,
//                componentSize.height
//        );
//        selectionPane.add(cFighterRight);
//
//        cVariantLeft = new JComboBox();
//        cVariantLeft.addActionListener(e ->
//        {
//            fighterLeft = new Fighter(cFighterLeft.getSelectedIndex(), cVariantLeft.getSelectedIndex());
//            cVariantLeft.setEnabled(false);
//            if (isGeneratorReady())
//            {
//                fullTrigger();
//            }
//        });
//        cVariantLeft.setPreferredSize(variantPreferredSize);
//        componentSize = cVariantLeft.getPreferredSize();
//        cVariantLeft.setBounds(
//                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
//                insets.top,
//                componentSize.width,
//                componentSize.height
//        );
//        selectionPane.add(cVariantLeft);
//
//        cVariantRight = new JComboBox();
//        cVariantRight.addActionListener(e ->
//        {
//            fighterRight = new Fighter(cFighterRight.getSelectedIndex(), cVariantRight.getSelectedIndex());
//            cVariantRight.setEnabled(false);
//            if (isGeneratorReady())
//            {
//                fullTrigger();
//            }
//        });
//        cVariantRight.setPreferredSize(variantPreferredSize);
//        componentSize = cVariantRight.getPreferredSize();
//        cVariantRight.setBounds(
//                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
//                insets.top + 30,
//                componentSize.width,
//                componentSize.height
//        );
//        selectionPane.add(cVariantRight);
        //end tag/fighters/variant fields
        //end component creation
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset() {
        
        tTagLeft.setText("");
        tTagRight.setText("");
    
        tMatchTitle.setText("");
        tEventNumber.setText("");
        
        //cFighterLeft.setSelectedIndex(0);
        //cFighterLeft.setEnabled(true);
        //cVariantLeft.removeAllItems();
        //cVariantLeft.setEnabled(false);
        //cFighterRight.setSelectedIndex(0);
        //cFighterRight.setEnabled(true);
        //cVariantRight.removeAllItems();
        //cVariantRight.setEnabled(false);
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
