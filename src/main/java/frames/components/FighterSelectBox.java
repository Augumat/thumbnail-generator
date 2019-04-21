package main.java.frames.components;

import main.java.Fighter;

import javax.swing.*;

public class FighterSelectBox extends JComboBox<Fighter> {
    
    public FighterSelectBox(Fighter defaultSkin) {

        DefaultComboBoxModel<Fighter> model = new DefaultComboBoxModel<>();

        // Add 8 copies of the chosen Fighter to the list
        for (int i = 0; i < 8; i++) {
            model.addElement(defaultSkin);
        }

        setModel(model);
        setRenderer(new FighterRenderer(FighterRenderer.RenderOption.VARIANT));
        setEditor(new FighterEditor());
    }

    public FighterSelectBox(Fighter[] allFighters) {

        DefaultComboBoxModel<Fighter> model = new DefaultComboBoxModel<>();

        // Add every fighter to the list
        for (Fighter current : allFighters) {
            model.addElement(current);
        }

        setModel(model);
        setRenderer(new FighterRenderer(FighterRenderer.RenderOption.FIGHTER));
        setEditor(new FighterEditor());
    }
}
