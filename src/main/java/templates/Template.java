package main.java.templates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Template {

    // Referring to different text elements that can be uniquely colored based on the template specification
    public enum ColorElement {
        TAG,
        MATCH,
        EVENT
    };

    // Info about the template folder itself
    private String templateDisplayName;
    private float templateVersionNumber;
    private String directoryName;
    
    // Info for use by the generator
    private String eventName;
    private BufferedImage background;
    private BufferedImage foreground;
    private int tagColor;
    private int matchColor;
    private int eventNumberColor;
    
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

    public Color getColor(ColorElement element) {
        switch (element) {
            case TAG:
                return new Color(tagColor);
            case MATCH:
                return new Color(matchColor);
            case EVENT:
                return new Color(eventNumberColor);
            default:
                System.out.println(
                        "[ERROR] Invalid Template Color Get."
                );
                // Failure returns black for now
                return new Color(0);
        }
    }
    
    @Override
    public String toString() {
        return templateDisplayName + " v" + templateVersionNumber;
    }
}
