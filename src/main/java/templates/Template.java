package main.java.templates;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Template {
    
    // Info about the template folder itself
    private String templateDisplayName;
    private float templateVersionNumber;
    private String directoryName;
    
    // Info for use by the generator
    private String eventName;
    private BufferedImage background;
    private BufferedImage foreground;
    
    //todo add font specifications and stuff maybe
    
    
    
    public String getEventName() {
        return eventName;
    }
    
    public BufferedImage getBackground() {
        
        if (background == null) {
            try {
                background = ImageIO.read(getClass().getResource("/templates/" + directoryName + "/bg.png"));
            } catch (IOException e) {
                System.out.println(
                        "[ERROR] No file found as this Template's background;\n        "
                      + this.toString() + " is missing resource \"bg.png\""
                );
            }
        }
        return background;
    }
    
    public BufferedImage getForeground() {
        
        if (foreground == null) {
            try {
                foreground = ImageIO.read(getClass().getResource("/templates/" + directoryName + "/fg.png"));
            } catch (IOException e) {
                System.out.println(
                        "[ERROR] No file found as this Template's foreground;\n        "
                      + this.toString() + " is missing resource \"fg.png\""
                );
            }
        }
        return foreground;
    }
    
    @Override
    public String toString() {
        return templateDisplayName + " v" + templateVersionNumber;
    }
}
