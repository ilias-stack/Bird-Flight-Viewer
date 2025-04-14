package com.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

public class TrackPanel extends JPanel{

    public TrackPanel() {
        setLayout(null); // Use absolute positioning for birds
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw all bird traces
        for (Bird bird : Bird.birds) {
            List<Point> path = bird.getPathHistory();
            if (path.size() < 2) continue;

            // Configure trace appearance
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2));

            // Draw lines between consecutive points
            Point prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                Point current = path.get(i);
                g2d.drawLine(prev.x, prev.y, current.x, current.y);
                prev = current;
            }
        }
    }

}
