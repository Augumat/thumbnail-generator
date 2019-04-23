package main.java.frames.components.fighter;

import main.java.Fighter;

import javax.swing.*;

public class FighterSelectBox extends JComboBox<Fighter> {

    public FighterSelectBox(Fighter[] allFighters) {

        DefaultComboBoxModel<Fighter> model = new DefaultComboBoxModel<>();

        // Add every fighter to the list
        for (Fighter current : allFighters) {
            model.addElement(current);
        }

        setModel(model);
        setRenderer(new FighterRenderer());
        setEditor(new FighterEditor());
    }
}
