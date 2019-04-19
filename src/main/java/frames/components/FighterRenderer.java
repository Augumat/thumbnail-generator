package main.java.frames.components;

import main.java.Fighter;

import javax.swing.*;
import java.awt.*;

class FighterRenderer extends JLabel implements ListCellRenderer {
    
    /** Options for what kind of renderer this should be. */
    enum RenderOption {
        FIGHTER,
        VARIANT
    }
    
    /** The JLabel containing the icon of this Fighter. */
    private JLabel fighterLabel;
    
    /** Which rendering type this FighterRenderer should act as. */
    private RenderOption renderType;
    
    
    
    /** Constructor for Fighter select combo boxes. */
    FighterRenderer(RenderOption type) {
        renderType = type;
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(2, 2, 2, 2);
        
        fighterLabel.setOpaque(true);
        fighterLabel.setHorizontalAlignment(JLabel.LEFT);
        
        add(fighterLabel, c);
        setBackground(Color.LIGHT_GRAY);
    }
    
    
    
    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        // Grab the fighter this list element refers to
        Fighter fighter = (Fighter) value;
    
        // Set the text of the list element to the Fighter's name (when in fighter mode, the ordinal will be zero)
        if (renderType == RenderOption.FIGHTER) {
            fighterLabel.setText(fighter.getName(0));
        }
    
        // Set the Fighter's Icon to the default stock icon of that Fighter
        Icon stockIcon = null;
        try {
            stockIcon = fighter.getIcon(index * renderType.ordinal());
        } catch (Exception e) {
            System.out.println("[ERROR] Icon not found in FighterRenderer request");
        }
        fighterLabel.setIcon(stockIcon);
    
        // Make the box highlight light gray when selected and nothing otherwise
        if (isSelected) {
            fighterLabel.setBackground(Color.WHITE);
        } else {
            fighterLabel.setBackground(null);
        }
    
        return this;
    }
}
