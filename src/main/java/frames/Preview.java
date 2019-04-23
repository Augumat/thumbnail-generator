package main.java.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//todo make all package private
class Preview extends JFrame {
    
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    
    //todo remove this constant it's a hacky fix for a weird issue
    /** Constant for offset in display vs window size discrepancies. */
    private static final int HEIGHT_CONSTANT = 30;
    
    Preview(BufferedImage thumbnail, BufferedImage windowIcon, double displaySizeScalar) {
        
        // Create this frame with the proper name
        super("Thumbnail Preview");
        
        // Scale the size of the Preview window
        if (displaySizeScalar <= 0) {
            System.out.println("[ERROR] Negative or zero thumbnail Preview scalar attempted, resetting to default");
            displaySizeScalar = 1;
        }
        int scaledWidth = (int) (WINDOW_WIDTH * displaySizeScalar);
        int scaledHeight = (int) (WINDOW_HEIGHT * displaySizeScalar);
        
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
        this.setSize(scaledWidth, scaledHeight);
        this.setResizable(false);
        
        // Add a custom panel that just displays the passed thumbnail in the window
        JPanel previewPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                //todo remove these comments if they aren't fixing the problem
                //g.setColor(Color.DARK_GRAY);
                //g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                g.drawImage(thumbnail, 0, 0, scaledWidth, scaledHeight, null);
            }
        };
        previewPanel.setSize(new Dimension(scaledWidth, scaledHeight));
        this.setSize(new Dimension(scaledWidth, scaledHeight + (int) (HEIGHT_CONSTANT / displaySizeScalar)));
        this.add(previewPanel);
        
        //System.out.println("scaledWidth: " + scaledWidth);
        //System.out.println("scaledHeight: " + scaledHeight);
        //System.out.println("width: " + this.getWidth());
        //System.out.println("height: " + this.getHeight());
        
        // Set the window visible
        this.setVisible(true);
    }
}
