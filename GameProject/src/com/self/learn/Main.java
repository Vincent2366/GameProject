package com.self.learn;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
<<<<<<< HEAD
Test note
=======

>>>>>>> ceca44d (GameProject/oop)
public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("BG_Music.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();   
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loops the background music

            Panel panel = new Panel();
            Frame frame = new Frame(panel);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
