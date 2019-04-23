package main.java.frames.components.fighter;

import main.java.Fighter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

class FighterEditor extends BasicComboBoxEditor {
    
    /** Components of the editor. */
    private JPanel panel;
    private JLabel label;
    private Fighter selectedFighter;



    FighterEditor() {

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

        System.out.println("setting item" + item.toString());

        if (!(item instanceof Fighter)) {
            return;
        }
        selectedFighter = (Fighter) item;
        label.setText(selectedFighter.getName());
        label.setIcon(null);
    }
}
