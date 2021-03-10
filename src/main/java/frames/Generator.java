package main.java.frames;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.Fighter;
import main.java.frames.components.fighter.FighterSelectBox;
import main.java.frames.components.variant.VariantSelectBox;
import main.java.templates.Template;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The main window of the program, responsible for receiving input and interfacing with the user.
 */
public class Generator extends JFrame {
    
    private static final String version = "v2.7.1";
    
    private static final int WINDOW_WIDTH = 515;
    private static final int WINDOW_HEIGHT = 205;
    
    /** A list of every Fighter discovered by the */
    private ArrayList<Fighter> allFighters;
    
    /** The selection pane of the application. */
    private JPanel selectionPane;
    /** The menu containing all of the relevant options regarding templates and Fighter ordering. */
    private JMenuBar menuBar;
    
    /** The menu for selecting which template to use for the generated thumbnail. */
    private JMenu templateMenu;
    /** List of radio buttons in the Template select menu. */
    private JMenuItem[] templateSelectButtons;
    /** The list of Templates loaded by this version of the generator. */
    private Template[] templates;
    /** The index of the currently selected Template. */
    private int selectedTemplateIndex;
    
    /** A menu for selecting how Fighters should be ordered in the Fighter select combo boxes. */
    private JMenu fighterSortMenu;
    /** List of radio buttons in the Template select menu. */
    private JMenuItem[] fighterSortButtons;
    /** The index of the currently selected Fighter sorting mode. */
    private Fighter.SortingMode selectedSort;
    
    /** A menu for selecting option on how the preview window should be displayed (size, etc). */
    private JMenu previewMenu;
    /** List of radio buttons in the Preview options menu. */
    private JMenuItem[] previewSelectButtons;
    /** A number strictly greater than zero that determines how the preview window is scaled when generated. */
    private double selectedPreviewScalar;
    
    /** Records the tag of the player on the left. */
    private JTextField tTagLeft;
    /** Records the tag of the player on the right. */
    private JTextField tTagRight;
    
    /** Records the fighter that the player on the left chose. */
    private FighterSelectBox cFighterLeft;
    /** Records the fighter that the player on the right chose. */
    private FighterSelectBox cFighterRight;
    
    /** Records the variant that the player on the left chose. */
    private VariantSelectBox cVariantLeft;
    /** Records the variant that the player on the right chose. */
    private VariantSelectBox cVariantRight;
    
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
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        //begin font loading ...
        try {
            futuraCondensed = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Futura_Condensed_Regular.ttf")).deriveFont(72F);
            lucidaSans = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Lucida_Sans_Regular.ttf")).deriveFont(48F);
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
            System.out.println("[ERROR] Window icon load aborted");
            e.printStackTrace();
            System.exit(1);
        }
        this.setIconImage(windowIcon);
        //end icon loading
        
        //begin template loading ...
        // Grab the relative URL of the application jar
        URL jarLocation = getClass().getProtectionDomain().getCodeSource().getLocation();
        ArrayList<String> jsonFiles = new ArrayList<>();
        try {
            ZipInputStream in = new ZipInputStream(jarLocation.openStream());
            while (true) {
                // Grabs the next entry in the input stream, exits the loop when there are no more entries
                ZipEntry currentEntry = in.getNextEntry();
                if (currentEntry == null) {
                    break;
                }
                
                // Grab the current entry and see if it is a json file in the correct folder
                String name = currentEntry.getName();
                if (name.startsWith("templates/") && name.endsWith(".json")) {
                    jsonFiles.add(name);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Could not load Templates");
            System.exit(-1);
        }
    
        // Creates a Template object for every File in jsonFiles and stores them in templates
        templates = new Template[jsonFiles.size()];
        for (int i = 0; i < templates.length; i++) {
        
            // Grab the raw String data of the current File
            String rawString = "";
            try {
                InputStream in = getClass().getResourceAsStream("/" + jsonFiles.get(i));
                byte[] byteString = new byte[in.available()];
                in.read(byteString);
                rawString = new String(byteString);
                
            } catch (IOException e) {
                System.out.println("[ERROR] Couldn't find listed Template file");
                e.printStackTrace();
                System.exit(-1);
            }
        
            // Parse that raw String data into a Show object and add it to the output array
            templates[i] = new Gson().fromJson(rawString, Template.class);
        }
        //end template loading
    
        //begin Fighter loading ...
        // Attempts to load the fighter JSON to fighterData
        String fighterData = "";
        try {
            InputStream in = getClass().getResourceAsStream("/fighterData.json");
            byte[] byteString = new byte[in.available()];
            in.read(byteString);
            fighterData = new String(byteString);
            in.close();
            
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to load Fighter data");
            e.printStackTrace();
            System.exit(-1);
        }
        
        // Load all the discovered Fighters into a list
        java.lang.reflect.Type FighterList = new TypeToken<ArrayList<Fighter>>() {}.getType();
        allFighters = new Gson().fromJson(fighterData, FighterList);
        for (Fighter current: allFighters) {
            current.init();
        }
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
                //g.drawImage(getWindowBackground(), 0, 0, null);
                //todo maybe add this if I do a button overhaul
            }
        };
        this.add(selectionPane);
        selectionPane.setLayout(null);
        selectionPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        //enter menu system ...
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
                if (!fighterSortButtons[selectedSort.ordinal()].isSelected()) {
                    // Handle case where the index that was already selected is deselected
                    fighterSortButtons[selectedSort.ordinal()].setSelected(true);
                    
                } else {
                    
                    // Deselect everything except the newly selected sort
                    fighterSortButtons[selectedSort.ordinal()].setSelected(false);
                    for (int j = 0; j < fighterSortButtons.length; j++) {
                        if (fighterSortButtons[j].isSelected()) {
                            selectedSort = Fighter.SortingMode.values()[j];
                        }
                    }
                    
                    // Reset the Fighter select boxes and Variant boxes
                    Fighter.sort(allFighters, selectedSort);
                    for (Fighter current: allFighters) {
                        cFighterLeft.addItem(current);
                        cFighterRight.addItem(current);
                    }
                    for (int j = 0; j < allFighters.size(); j++) {
                        cFighterLeft.removeItemAt(0);
                        cFighterRight.removeItemAt(0);
                    }
                    cVariantLeft.setEnabled(false);
                    cVariantRight.setEnabled(false);
                }
            });
            fighterSortMenu.add(currentOption);
        }
        selectedSort = Fighter.SortingMode.Default;
        fighterSortMenu.getItem(0).setSelected(true);
        menuBar.add(fighterSortMenu);
        
        // Create the template menu
        templateMenu = new JMenu("Templates");
        templateSelectButtons = new JRadioButtonMenuItem[templates.length];
        for (int i = 0; i < templates.length; i++) {
            JRadioButtonMenuItem currentOption = new JRadioButtonMenuItem(templates[i].toString());
            templateSelectButtons[i] = currentOption;
            currentOption.addActionListener(e -> {
                if (!templateSelectButtons[selectedTemplateIndex].isSelected()) {
                    templateSelectButtons[selectedTemplateIndex].setSelected(true);
                } else {
                    templateSelectButtons[selectedTemplateIndex].setSelected(false);
                    for (int j = 0; j < templateSelectButtons.length; j++) {
                        if (templateSelectButtons[j].isSelected()) {
                            selectedTemplateIndex = j;
                        }
                    }
                }
            });
            templateMenu.add(currentOption);
        }
        templateMenu.getItem(0).setSelected(true);
        menuBar.add(templateMenu);
        
        // Create the Preview scalar option menu
        previewMenu = new JMenu("Preview");
        previewSelectButtons = new JRadioButtonMenuItem[1];
        //previewSelectButtons[0] = new JRadioButtonMenuItem("[1/1] Full size ");
        previewSelectButtons[0] = new JRadioButtonMenuItem("Thumbnail size [1/6]");
        previewSelectButtons[0].addActionListener(e -> {
            if (previewSelectButtons[0].isSelected()) {
                selectedPreviewScalar = 1.0 / 6.0;
            } else {
                selectedPreviewScalar = 1.00;
            }
        });
        previewMenu.add(previewSelectButtons[0]);
        menuBar.add(previewMenu);
        //end menu system
        
        //begin main control buttons ...
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
    
        Dimension tagPreferredSize = new Dimension(170, 25);
        Dimension labelPreferredSize = new Dimension(85, 25);
    
        //begin tag fields ...
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
        
        //begin match data fields ...
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
        
        Dimension fighterPreferredSize = new Dimension(170, 24);
        
        //begin fighter fields ...
        cFighterLeft = new FighterSelectBox(allFighters.toArray(new Fighter[0]));
        cFighterLeft.addActionListener(e -> {
            cVariantLeft.setFighter((Fighter) cFighterLeft.getSelectedItem());
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

        cFighterRight = new FighterSelectBox(allFighters.toArray(new Fighter[0]));
        cFighterRight.addActionListener(e -> {
            cVariantRight.setFighter((Fighter) cFighterRight.getSelectedItem());
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
        //end fighter fields
        
        Dimension variantPreferredSize = new Dimension(50, 24);
        
        //begin variant fields ...
        cVariantLeft = new VariantSelectBox();
        cVariantLeft.setPreferredSize(variantPreferredSize);
        componentSize = cVariantLeft.getPreferredSize();
        cVariantLeft.setMaximumRowCount(Fighter.VARIANT_BOUND); // 8 is the number of variants per Fighter
        cVariantLeft.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantLeft);

        cVariantRight = new VariantSelectBox();
        cVariantRight.setPreferredSize(variantPreferredSize);
        componentSize = cVariantRight.getPreferredSize();
        cVariantRight.setMaximumRowCount(Fighter.VARIANT_BOUND); // 8 is the number of variants per Fighter
        cVariantRight.setBounds(
                insets.left + tagPreferredSize.width + fighterPreferredSize.width + labelPreferredSize.width + 20,
                insets.top + 30,
                componentSize.width,
                componentSize.height
        );
        selectionPane.add(cVariantRight);
        //end variant fields
        
        //end component creation
    }
    
    /** Resets all temporary variables and provides a clean slate for creating a new thumbnail. */
    private void reset() {
        
        tTagLeft.setText("");
        tTagRight.setText("");
    
        tMatchTitle.setText("");
        tEventNumber.setText("");
        
        cFighterLeft.setSelectedIndex(0);
        cFighterRight.setSelectedIndex(0);
        
        cVariantLeft.setFighter(null);
        cVariantLeft.setEnabled(false);
        cVariantLeft.setSelectedIndex(0);
        cVariantRight.setFighter(null);
        cVariantRight.setEnabled(false);
        cVariantRight.setSelectedIndex(0);
    }
    
    /**
     * Generates a thumbnail from the accumulated state of the App class if everything is defined and prompts to save
     * the file to a user-defined location.
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
            dialog.setFile("*.png");
            dialog.setFilenameFilter((dir, name) -> name.length() > 4 && name.substring(name.length() - 4).equals(".png"));
            dialog.setVisible(true);
            String path = dialog.getDirectory() + dialog.getFile(); //todo this line and dialog.getFile specifically are interesting...
            ImageIO.write(generatedThumbnail, "PNG", new File(path));
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write thumbnail to file.");
            e.printStackTrace();
        }
        //end save prompt
    }
    
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
                    (allFighters.get(cFighterLeft.getSelectedIndex())).getRender(cVariantLeft.getSelectedIndex()),
                    0, 0, 639, 639, 0, 0, 639, 639, null
            );
            // Draw the Right fighter
            output.drawImage(
                    (allFighters.get(cFighterRight.getSelectedIndex())).getRender(cVariantRight.getSelectedIndex()),
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
