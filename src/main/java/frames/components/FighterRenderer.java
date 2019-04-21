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

        fighterLabel = new JLabel();
        fighterLabel.setOpaque(true);
        if (renderType == RenderOption.FIGHTER) {
            fighterLabel.setHorizontalAlignment(JLabel.LEFT);
        } else {
            fighterLabel.setHorizontalAlignment(JLabel.CENTER);
        }

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
        } else if (renderType == RenderOption.VARIANT && index != -1) {
            fighterLabel.setText("" + index);
        }

        // Set the Fighter's Icon to the default stock icon of that Fighter
        Icon stockIcon = null;
        if (index != -1) {
            try {
                stockIcon = fighter.getIcon(index * renderType.ordinal());
            } catch (Exception e) {
                System.out.println(
                        "[ERROR] Icon not found from FighterRenderer request of "
                                + index * renderType.ordinal() + " from " + fighter.getName()
                );
            }
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
