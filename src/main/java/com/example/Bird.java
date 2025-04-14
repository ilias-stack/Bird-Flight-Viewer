package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Bird extends JComponent {
    // Unique identifier for each bird
    private int birdId;

    // To determine the position and rotation of the bird(angles)
    private int x, y, r;
    @ToString.Exclude
    private final List<Point> pathHistory = new ArrayList<>();
    private Random random = new Random();

    public static final List<Bird> birds;
    private static int birdsCount;
    public static int windowHeight, windowWidth;
    public static final int BIRD_SIZE = 35;
    private static final int HISTORY_LENGTH = 100;
    public static MainFrame frame;

    static {
        birds = new ArrayList<>(100);
        birdsCount = 0;
    }

    private void addBird() {
        this.birdId = birdsCount;
        birdsCount++;
        birds.add(this);

        var currentBird = this;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(currentBird);
                deleteBirdByCountId(currentBird.birdId);
            }
        });

        addToHistory();
    }

    public Bird(int... props) {
        if (props.length != 3)
            throw new IllegalArgumentException("Exactly 3 parameters required but " + props.length + " were given.");
        this.x = props[0];
        this.y = props[1];
        this.r = props[2];

        this.setBounds(this.x, this.y, BIRD_SIZE, BIRD_SIZE);
        addBird();
    }

    public Bird(boolean randomInitialization) {
        if (randomInitialization) {
            this.x = random.nextInt(windowWidth);
            this.y = random.nextInt(windowHeight);
            this.r = random.nextInt(360);
        }
        this.setBounds(this.x, this.y, BIRD_SIZE, BIRD_SIZE);
        addBird();
    }

    public Bird getBirdByCountId(int countId) {
        for (Bird b : birds) {
            if (b.birdId == countId)
                return b;
        }
        return null;
    }

    public void deleteBirdByCountId(int countId) {
        for (int i = 0; i < birds.size(); i++) {
            var b = birds.get(i);
            if (b.birdId == countId) {
                birds.remove(i);
                frame.remove(this);
            }
        }
    }

    public void moveForward(int distance) {
        double angleRad = Math.toRadians(90 - this.r);

        // Calculate dx and dy
        int dx = (int) (Math.cos(angleRad) * distance);
        int dy = (int) (Math.sin(angleRad) * distance);

        // Change the location accordingly
        this.x += dx;
        this.y -= dy;

        this.setLocation(this.x, this.y);
        addToHistory();
    }

    public void steerTowards(int targetX, int targetY) {
        double desiredAngle = Math.toDegrees(Math.atan2(targetY - this.y, targetX - this.x)) + 90;

        // How close birds should be before repelling each other.
        double desiredSeparation = 10.0;
        double separationAngleSum = 0;
        int count = 0;

        for (Bird other : birds) {
            if (other == this)
                continue;
            // Calculate distance to the other bird
            double distance = Math.hypot(other.x - this.x, other.y - this.y);
            if (distance < desiredSeparation) {
                // Compute the angle from the neighbor toward this bird.
                double angleFromNeighbor = Math.toDegrees(Math.atan2(this.y - other.y, this.x - other.x));
                separationAngleSum += angleFromNeighbor;
                count++;
            }
        }

        if (count > 0) {
            double separationAngle = separationAngleSum / count;
            desiredAngle = 0.5 * desiredAngle + 0.3 * separationAngle;
        }

        double angleDiff = desiredAngle - this.r;
        angleDiff = ((angleDiff + 540) % 360) - 180;

        // Apply a smoothing factor
        this.r += angleDiff * 0.03 + random.nextDouble(-3, 3);
    }

    private void addToHistory() {
        pathHistory.add(new Point(this.x + 15, this.y + 20)); // add the center point of our mesh

        int len = pathHistory.size();
        if (len > HISTORY_LENGTH / Main.BIRD_SPEED)
            pathHistory.removeFirst();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        int[] x = { 0, 15, 30 };
        int[] y = { 30, 0, 30 };

        int centerX = (x[0] + x[1] + x[2]) / 3;
        int centerY = (y[0] + y[1] + y[2]) / 3;

        // Apply rotation around the center
        g2d.rotate(Math.toRadians(r), centerX, centerY);

        // Draw filled triangle
        g2d.setColor(Color.YELLOW);
        g2d.fillPolygon(x, y, 3);

        // Draw black border
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(x, y, 3);

        // Draw ID in the center
        String id = String.valueOf(this.birdId);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(id);
        int textHeight = fm.getAscent();
        g2d.drawString(id, centerX - textWidth / 2, centerY + textHeight / 2);

        g2d.dispose();
    }

    public static int[] getGlobalCenterOfMass() {
        if (!birds.isEmpty()) {
            int centerX = 0, centerY = 0;
            for (Bird bird : birds) {
                centerX += bird.x;
                centerY += bird.y;
            }
            return new int[] { centerX / birdsCount, centerY / birdsCount };
        }
        return null;
    }

}
