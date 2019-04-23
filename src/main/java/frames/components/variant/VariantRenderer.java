package main.java.frames.components.variant;

import main.java.Fighter;

import javax.swing.*;
import java.awt.*;

class VariantRenderer extends JLabel implements ListCellRenderer {
    
    /** The JLabel containing the icon of this Fighter. */
    private JLabel fighterLabel;
    
    /** Used for Variant select boxes, stores the Fighter whose variants will be shown. */
    private Fighter displayFighter;
    
    
    /** Constructor for Variant select combo boxes. */
    VariantRenderer(Fighter fighter) {
        displayFighter = fighter;
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(2, 2, 2, 2);
        
        fighterLabel = new JLabel();
        fighterLabel.setOpaque(true);
        fighterLabel.setHorizontalAlignment(JLabel.CENTER);
        
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
        Integer currentIndex = (Integer) value;
        
        // Set the Fighter's Icon to the default stock icon of that Fighter
        Icon stockIcon = null;
        if (index != -1) {
            try {
                fighterLabel.setText(null);
                stockIcon = displayFighter.getIcon(currentIndex);
            } catch (Exception e) {
                //            System.out.println(
                //                    "[ERROR] Icon not found from VariantRenderer request of "
                //                            + currentIndex + " from " + displayFighter.getName()
                //            );
            }
        } else {
            // If the selected value in the selection box, display which variant is selected (1-indexed for users)
            fighterLabel.setText("" + (currentIndex + 1));
        }
        fighterLabel.setIcon(stockIcon);
        
        // Make the box highlight light gray when selected and nothing otherwise
        if (isSelected) {
            fighterLabel.setBackground(Color.WHITE);
        } else {
            fighterLabel.setBackground(null);
        }
        
        return fighterLabel;
    }
}
