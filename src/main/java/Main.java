package main.java;

import main.java.frames.Generator;

import javax.swing.*;

public class Main {
    public static void main(String[] unused) {
        
        // Attempt to set the look and feel of the program to the system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
               | InstantiationException
               | IllegalAccessException
               | UnsupportedLookAndFeelException e) {
            System.out.println("[ERROR] Failed to find look and feel");
        }
        
        // Create a Generator instance, launching the program
        new Generator();
    }
}
