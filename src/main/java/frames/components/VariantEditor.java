package main.java.frames.components;

import main.java.Fighter;

import javax.swing.*;
import java.awt.*;

public class VariantEditor
{
    /** Components of the editor. */
    private JPanel panel;
    private JLabel label;
    private String title;
    
    /**
     * //todo finish
     * Default constructor
     */
    public VariantEditor()
    {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        label = new JLabel();
        label.setOpaque(false);
        label.setHorizontalAlignment(JLabel.LEFT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(2, 2, 2, 2);
        
        panel.add(label, c);
    }
    
    /**
     * Sets the contents of the VariantEditor to be contents of a Variant object.
     * @param variant The character variant to be displayed
     */
    public void setContents(Fighter variant)
    {
        if (variant == null) {
            System.out.println("[ERROR] null passed to VariantEditor.setContents().");
            return;
        }
        
        title = variant.getName();
        label.setText(title);
        try {
            label.setIcon(variant.getIcon(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
