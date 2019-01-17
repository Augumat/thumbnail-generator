package main.java;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App implements Runnable
{
    /** The main window of the program. */
    JFrame main;
    /** The preview frame for generated thumbnails. */
    JFrame preview;
    
    @Override
    public void run()
    {
        //todo implement
        main = new JFrame();
    }
    
    private String loadFile(String fileName)
    {
        StringBuilder output = new StringBuilder();
        
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File toLoad = new File(classLoader.getResource(fileName).getFile());
        try
        {
            Scanner scanner = new Scanner(toLoad);
            while (scanner.hasNextLine())
            {
                //todo load costume data and character data this way
//                String line = scanner.nextLine();
//                output.append(line).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return output.toString();
    }
}
