package main.java.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//todo make all package private
public class Preview extends JFrame {
    
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    
    public Preview(BufferedImage thumbnail, BufferedImage windowIcon) {
        
        // Create this frame with the proper name
        super("Thumbnail Preview");
        
        // If the window icon or the thumbnail are null, dispose of the window after printing an error message
        if (thumbnail == null) {
            System.out.println("[ERROR] Attempted to create a preview window with a null thumbnail");
            this.dispose();
            return;
        }
        if (windowIcon == null) {
            System.out.println("[ERROR] Attempted to create a preview window with a null icon");
            this.dispose();
            return;
        }
        
        // Initialize the window
        this.setIconImage(windowIcon);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        
        // Add a custom panel that just displays the passed thumbnail in the window
        JPanel previewPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(thumbnail, 0, 0, null);
            }
        };
        previewPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.add(previewPanel);
        this.pack();
        
        // Set the window visible
        this.setVisible(true);
    }
}
