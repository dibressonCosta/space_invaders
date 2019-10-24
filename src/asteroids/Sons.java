package asteroids;

import java.applet.*;
import javax.sound.sampled.*;
import sun.audio.*;

public class Sons {

    AudioClip explosaoSound;
    AudioClip laserSound;
    AudioClip fundoSound;
    Clip clip;
    public Sons() {
        try {
            clip = AudioSystem.getClip();
            explosaoSound = Applet.newAudioClip(getClass().getResource("/explosaoAst.wav"));
            laserSound = Applet.newAudioClip(getClass().getResource("/laser1.wav"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void music(boolean play) {
        AudioStream BGM;
        AudioData MD;
        try {
            
            AudioInputStream test = AudioSystem.getAudioInputStream(getClass().getResource("/fundoSound.wav"));
            if (play) {
                clip.open(test);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                
            }else{
                clip.stop();
                clip.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
