package main.java.frames.components;

import main.java.Fighter;

import javax.swing.*;
import java.awt.*;

public class VariantRenderer extends JLabel implements ListCellRenderer {
    
    /** The JLabel containing the icon of this variant. */
    private JLabel variantLabel;
    
    
    
    public VariantRenderer() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(2, 2, 2, 2);
        
        variantLabel.setOpaque(true);
        variantLabel.setHorizontalAlignment(JLabel.LEFT);
        
        add(variantLabel, constraints);
        setBackground(Color.WHITE);
    }
    
    
    
    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        // Grab the fighter this list element refers to
        Fighter fighter = (Fighter) value;
    
        // Set the text of the list element to the Fighter's name
        variantLabel.setText(fighter.getName());
    
        // Set the Fighter's Icon to the stock icon of that variant's color
        Icon stockIcon = null;
        try {
            stockIcon = fighter.getIcon(index);
        } catch (Exception e) {
            System.out.println();
        }
        variantLabel.setIcon(stockIcon);
    
        // Make the box highlight light gray when selected and nothing otherwise
        if (isSelected) {
            variantLabel.setBackground(Color.LIGHT_GRAY);
        } else {
            variantLabel.setBackground(null);
        }
    
        return this;
    }
}
