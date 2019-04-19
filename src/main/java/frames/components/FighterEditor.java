package main.java.frames.components;

import main.java.Fighter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

public class FighterEditor extends BasicComboBoxEditor {
    
    /** Components of the editor. */
    private JPanel panel;
    private JLabel label;
    private String title;
    
    
    
    public FighterEditor() {
        
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        label = new JLabel();
        label.setOpaque(false);
        label.setHorizontalAlignment(JLabel.LEFT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(2, 2, 2, 2);
        
        panel.add(label, c);
    }
    
    
    
    /**
     * Sets the contents of the FighterEditor to be contents of a Fighter object.
     * @param fighter The character variant to be displayed
     */
    public void setContents(Fighter fighter) {
        
        // Cover null case
        if (fighter == null) {
            System.out.println("[ERROR] null passed to FighterEditor.setContents()");
            return;
        }
        
        // Set the name of the Element
        title = fighter.getName();
        label.setText(title);
        
        // Set the Icon of the Element
        try {
            label.setIcon(fighter.getIcon(0));
        } catch (Exception e) {
            System.out.println("[ERROR] no default Icon found for this character in FighterEditor.setContents()");
        }
    }
}
