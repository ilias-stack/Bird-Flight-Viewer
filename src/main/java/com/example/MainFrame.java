package com.example;

import javax.swing.JFrame;

import lombok.Getter;

@Getter
public class MainFrame extends JFrame  {
    private int h, w;

    public MainFrame(int width, int height) {
        this.w = width;
        this.h = height;
        this.setSize(this.w, this.h);
        this.setLocationRelativeTo(rootPane);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainFrame(){
        this.setLocationRelativeTo(rootPane);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Bird addBird() {
        Bird bird = new Bird(true);
        bird.setBounds(bird.getX(), bird.getY(), Bird.BIRD_SIZE, Bird.BIRD_SIZE);
        this.add(bird);
        return bird;
    }

}
