package main.java.frames.components.variant;

import main.java.Fighter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

class VariantEditor extends BasicComboBoxEditor {
    
    /** Components of the editor. */
    private JPanel panel;
    private JLabel label;
    private Integer selectedFighter;
    
    
    
    VariantEditor() {
        
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        label = new JLabel();
        label.setOpaque(false);
        label.setHorizontalAlignment(JLabel.LEFT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(2, 4, 2, 2);
        
        panel.add(label, c);
    }
    
    
    
    public Component getEditorComponent() {
        return this.panel;
    }
    
    public Object getItem() {
        return this.selectedFighter;
    }
    
    public void setItem(Object item) {
        if (!(item instanceof Integer)) {
            return;
        }
        selectedFighter = (Integer) item;
        label.setText("" + selectedFighter);
        label.setIcon(null);
    }
}

