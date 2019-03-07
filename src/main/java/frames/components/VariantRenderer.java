package main.java.frames.components;

import javax.swing.*;
import java.awt.*;

public class VariantRenderer extends JLabel implements ListCellRenderer
{
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
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        String[] countryItem = (String[]) value;
    
        // set country name
        variantLabel.setText(countryItem[0]);
    
        // set country flag
        variantLabel.setIcon(new ImageIcon(countryItem[1]));
    
        if (isSelected) {
            variantLabel.setBackground(Color.BLUE);
            variantLabel.setForeground(Color.YELLOW);
        } else {
            variantLabel.setForeground(Color.BLACK);
            variantLabel.setBackground(Color.LIGHT_GRAY);
        }
    
        return this;
//        Graphics g= this.getGraphics();
//        g.drawImage();
    }
}
