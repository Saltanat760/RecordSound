/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recordsound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author burak
 */
public class RecordSound {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            
            System.out.println("Test Başladı");
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100, 16, 2,4,44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if(!AudioSystem.isLineSupported(info)){System.err.println("hat desteklenmiyor");}
            
            TargetDataLine target= (TargetDataLine) AudioSystem.getLine(info);
            target.open();
            System.out.println("Kaydetme Başlıyor");
            target.start(); 
            
            Thread kaydedici= new Thread(){
                @Override public void run(){
                    try { 
                        AudioInputStream audioStream = new AudioInputStream(target);
                        File sesDosyası= new File("ses.wav");
                        AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, sesDosyası);
                        System.out.println("Kaydetme Bitti");
                    } catch (IOException ex) {
                        Logger.getLogger(RecordSound.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            kaydedici.start(); 
            Thread.sleep(10000); 
            target.stop();
            target.close();
            System.out.println("Test Bitti");
        } catch (LineUnavailableException ex) {
            Logger.getLogger(RecordSound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RecordSound.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
}
