package com.example;

import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    public static int FPS = 30;
    public static int BIRD_SPEED = 5;

    public static int X, Y;

    public static void main(String[] args) {
        // X = 1500; Y = 700;
        MainFrame frame = createWindow();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Bird b = new Bird(e.getX(), e.getY(), 0);
                frame.add(b);
            }
        });
        frame.setVisible(true);

        TrackPanel trackPanel = new TrackPanel();
        System.out.println(frame.getWidth()+", "+ frame.getHeight());
        trackPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        trackPanel.setBackground(Color.BLACK);
        frame.add(trackPanel);

        // You might need to revalidate/repaint after adding components to a visible frame
        frame.revalidate();
        frame.repaint();


        // Update window dimensions after frame is visible
        Bird.windowHeight = frame.getHeight();
        Bird.windowWidth = frame.getWidth();


        Timer animationTimer = new Timer(30, e -> {
            int[] center = Bird.getGlobalCenterOfMass();
            for (Bird b : Bird.birds) {
                b.steerTowards(center[0], center[1]);
                b.moveForward(Main.BIRD_SPEED);
            }
        });
        animationTimer.start();
    }

    private static MainFrame createWindow() {
        MainFrame resp = new MainFrame();
        Bird.frame = resp;

        // Initializing static fields for bird class
        Bird.windowHeight = resp.getHeight();
        Bird.windowWidth = resp.getWidth();
        return resp;
    }
}