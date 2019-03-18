package main.java.frames;

import org.junit.Test;

import java.awt.image.BufferedImage;

public class PreviewTest {
    
    private BufferedImage thumbnail = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
    
    @Test
    public void testNullArgs() {
    
        System.out.println("Attempting two null arguments.");
        new Preview(null, null);
        System.out.println();
    
        System.out.println("Attempting null windowIcon.");
        new Preview(thumbnail, null);
        System.out.println();
    
        System.out.println("Attempting null thumbnail.");
        new Preview(null, icon);
        System.out.println();
    }
    
    @Test
    public void testValidPreview() {
    
        System.out.println("Attempting valid preview.");
        new Preview(thumbnail, icon);
        System.out.println();
    }
}
