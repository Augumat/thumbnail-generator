package main.java.frames;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.parser.JSONParser;
import main.java.Fighter;
import main.java.templates.Template;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main window of the program, responsible for receiving input and interfacing with the user.
 */
public class Generator extends JFrame {
    
    private static final String version = "v2.0";
    
    private static final int WINDOW_WIDTH = 515;
    private static final int WINDOW_HEIGHT = 205;
    
    private static final String JSON_SUFFIX = ".json";
    
    /** A list of every Fighter discovered by the */
    private ArrayList<Fighter> allFighters;
    
    /** The selection pane of the application. */
    private JPanel selectionPane;
    /** The menu containing all of the relevant options regarding templates and Fighter ordering. */
    private JMenuBar menuBar;
    
    /** The menu for selecting which template to use for the generated thumbnail. */
    private JMenu templateMenu;
    /** List of radio buttons in the Template select menu. */
    private JRadioButtonMenuItem[] templateSelectButtons;
    /** The list of Templates loaded by this version of the generator. */
    private Template[] templates;
    /** The index of the currently selected Template. */
    private int selectedTemplateIndex;
    
    /** A menu for selecting how Fighters should be ordered in the Fighter select combo boxes. */
    private JMenu fighterSortMenu;
    /** List of radio buttons in the Template select menu. */
    private JRadioButtonMenuItem[] fighterSortButtons;
    /** The index of the currently selected Fighter sorting mode. */
    private Fighter.SortingMode selectedSort;
    
    /** A number strictly greater than zero that determines how the preview window is scaled when generated. */
    private double selectedPreviewScalar = 0.5;
    
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
        
        //begin template loading ...
        // Creates a temporary dummy file in the data directory that can be easily referenced for its path
        File anchorFile = new File(FileSystems.getDefault().getPath("res/templates/").toUri());
    
        // Creates a FileFilter for directories and one for JSON
        FileFilter jsonFilter = toTest -> {
        
            // Grabs the file name of toTest
            String fileName = toTest.getName();
        
            // If the filename is shorter than or equal in size to the length of a .json suffix literal, then it could
            // not possibly be a json file, and the filter returns false.
            if (fileName.length() <= JSON_SUFFIX.length()) {
                return false;
            }
        
            // Otherwise, the lambda returns true if the file extension is equivalent to the .json suffix literal and
            // false otherwise.
            return fileName.substring(fileName.length() - JSON_SUFFIX.length()).equals(JSON_SUFFIX);
        };
        File[] jsonFiles = anchorFile.listFiles(jsonFilter);
        
        // Checks if any Templates were found and creates a list of them if they weren't
        templates = new Template[0];
        if (jsonFiles != null) {
            templates = new Template[jsonFiles.length];
        }
    
        // Creates a Template object for every File in jsonFiles and stores them in shows
        for (int i = 0; i < templates.length; i++) {
        
            // Grab the raw String data of the current File
            final Path path = FileSystems.getDefault().getPath("res/templates/", jsonFiles[i].getName());
    
            String rawString = "";
            try {
                rawString = new String(Files.readAllBytes(path));
            } catch (IOException e) {
                System.out.println("Couldn't find file: " + jsonFiles[i].getName());
            }
        
            // Parse that raw String data into a Show object and add it to the output array
            templates[i] = new Gson().fromJson(rawString, Template.class);
        }
        //end template loading
    
        //begin Fighter loading ...
        // Get the path to the fighter data
        final Path path = FileSystems.getDefault().getPath("res/", "fighterData.json");
        
        // Attempts to load the fighter JSON to fighterData
        String fighterData = "";
        try {
            fighterData = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to find any Fighter data");
            System.exit(-1);
        }
    
        // Load all the discovered Fighters into a list
        allFighters = new ArrayList<>(new Gson().fromJson(fighterData, ArrayList.class));
        //end Fighter loading
        
        //begin fighter sort loading ...
        //todo in future, possibly make these sorts configurable through a config file
        //end fighter sort loading
        
        //begin saved settings loading ...
        //todo load saved settings from last session
        //end saved settings loading
        
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
        selectionPane.add(menuBar);
    
        // Create the fighter sort options menu
        fighterSortMenu = new JMenu("Sorting");
        fighterSortButtons = new JRadioButtonMenuItem[Fighter.SortingMode.values().length];
        for (int i = 0; i < Fighter.SortingMode.values().length; i++) {
            JRadioButtonMenuItem currentOption = new JRadioButtonMenuItem(Fighter.SortingMode.values()[i].toString());
            fighterSortButtons[i] = currentOption;
            currentOption.addActionListener(e -> {
                fighterSortButtons[selectedSort.ordinal()].setSelected(false);
                for (int j = 0; j < fighterSortButtons.length; j++) {
                    if (fighterSortButtons[j].isSelected()) {
                        selectedSort = Fighter.SortingMode.values()[j];
                    }
                }
            });
            fighterSortMenu.add(currentOption);
        }
        fighterSortMenu.getItem(0).setSelected(true);
        menuBar.add(fighterSortMenu);
        
        // Create the template menu
        templateMenu = new JMenu("Templates");
        templateSelectButtons = new JRadioButtonMenuItem[templates.length];
        for (int i = 0; i < templates.length; i++) {
            JRadioButtonMenuItem currentOption = new JRadioButtonMenuItem(templates[i].toString());
            templateSelectButtons[i] = currentOption;
            currentOption.addActionListener(e -> {
                templateSelectButtons[selectedTemplateIndex].setSelected(false);
                for (int j = 0; j < templateSelectButtons.length; j++) {
                    if (templateSelectButtons[j].isSelected()) {
                        selectedTemplateIndex = j;
                    }
                }
            });
            templateMenu.add(currentOption);
        }
        templateMenu.getItem(0).setSelected(true);
        menuBar.add(templateMenu);
        //end menu system
        
        //begin main control buttons
        Dimension buttonPreferredSize = new Dimension(120, 25);
        Dimension bigButtonPreferredSize = new Dimension(160, 45);
        Insets insets = new Insets(26, 9, 36, 9);
        Dimension componentSize;
    
        // Create the preview button
        bPreview = new JButton("Show Preview");
        bPreview.addActionListener(e -> new Preview(generate(), windowIcon, selectedPreviewScalar));
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
        //cVariantLeft.removeAllItems();
        //cVariantLeft.setEnabled(false);
        //cFighterRight.setSelectedIndex(0);
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
        Template template = templates[selectedTemplateIndex];
        
        //create canvas using background template
        BufferedImage thumbnail = copyImage(template.getBackground());
        Graphics output = thumbnail.createGraphics();
    
        //draw both characters to the canvas in their respective places
        try {
            // Draw the left Fighter
            output.drawImage(
                    ((Fighter) cFighterLeft.getSelectedItem()).getRender(cVariantLeft.getSelectedIndex()),
                    0, 0, 639, 639, 0, 0, 639, 639, null
            );
            // Draw the Right fighter
            output.drawImage(
                    ((Fighter) cFighterRight.getSelectedItem()).getRender(cVariantRight.getSelectedIndex()),
                    1279, 0, 640, 639, 0, 0, 639, 639, null
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    
        //draw the foreground template over the previous
        output.drawImage(template.getForeground(),0,0,null);
    
        //begin text field drawing
        int leftStart = 0;
        int rightStart = 720;
        int playerBoxLength = 560;
        int centerLine = 640;
    
        FontMetrics futuraMetrics = output.getFontMetrics(futuraCondensed);
        FontMetrics lucidaMetrics = output.getFontMetrics(lucidaSans);
        
        int leftTagIndent = leftStart + ((playerBoxLength - (futuraMetrics.stringWidth(tTagLeft.getText()))) / 2);
        int rightTagIndent = rightStart + ((playerBoxLength - (futuraMetrics.stringWidth(tTagRight.getText()))) / 2);
        int eventNumberIndent = centerLine - ((futuraMetrics.stringWidth(template.getEventName() + tEventNumber.getText())) / 2);
        int roundNumberIndent = centerLine - ((lucidaMetrics.stringWidth(tMatchTitle.getText())) / 2);
    
        output.setColor(Color.WHITE);
        output.setFont(futuraCondensed);
        output.drawString(tTagLeft.getText(), leftTagIndent, 75);
        output.drawString(tTagRight.getText(), rightTagIndent, 75);
        output.drawString(template.getEventName() + tEventNumber.getText(), eventNumberIndent,655);
    
        output.setColor(new Color(160,160,160));
        output.setFont(lucidaSans);
        output.drawString(tMatchTitle.getText(), roundNumberIndent,710);
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
}
