package main.java.frames.components.variant;

import main.java.Fighter;

import javax.swing.*;

public class VariantSelectBox extends JComboBox<Integer> {
    
    public VariantSelectBox() {
        
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        
        // Add 8 copies of the chosen Fighter to the list
        for (int i = 0; i < 8; i++) {
            model.addElement(i);
        }
        
        setModel(model);
        setRenderer(new VariantRenderer(null));
        setEditor(new VariantEditor());
    }
    
    public void setFighter(Fighter newFighter) {
        if (newFighter == null) {
            setRenderer(new DefaultListCellRenderer());
        } else {
            setRenderer(new VariantRenderer(newFighter));
        }
    }
}
