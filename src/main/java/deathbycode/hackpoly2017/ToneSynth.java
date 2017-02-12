package deathbycode.hackpoly2017;

import net.year4000.utilities.scheduler.Scheduler;
import net.year4000.utilities.scheduler.SchedulerManager;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Acer Customer on 2/11/2017.
 */
public class ToneSynth {
    private Scheduler scheduler;
    private Synthesizer syn;
    private MidiChannel[] midichannel;

    public ToneSynth() {
        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midichannel = syn.getChannels();




        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }


    /*
    Plays sequence all notes passed as arguments
    @param notes: Piano keys in the range 0 - 127 with 60 as middle C
    @param length: The duration in milliseconds that the notes will play
     */
    public void playNotes(int len, int... notes) {


        int[] scale;
        if(notes[notes.length - 1] % 2 == 0) {
            scale = PlayControl.generateMinorScale(notes[0]);
        }
        else {
            scale = PlayControl.generateMajorScale(notes[0]);
        }


//        for(int i = 0; i< minor.length; i++) {
//            System.out.println(minor[i]);
//        }
        AtomicInteger beatCounter = new AtomicInteger(0);
        AtomicInteger i = new AtomicInteger(0);
        if( scheduler != null )
            stopNotes();
        scheduler = SchedulerManager.builder().build();
        scheduler.repeat(task -> {
            int beatCount = beatCounter.getAndIncrement();
            int j = i.getAndIncrement();
            if (j >= notes.length) {
                task.stop();
            } else {
                // Harmonize notes every 4 beats
                if(beatCount % 4 == 0){
                    this.midichannel[2].noteOn(scale[notes[j] % 13], 100);
                }

                this.midichannel[2].noteOn(scale[notes[j] % 15], 100);
                scheduler.run(() -> this.midichannel[2].noteOff(scale[Math.abs(notes[j]) % 15]), len, TimeUnit.MILLISECONDS);
            }
        }, len, TimeUnit.MILLISECONDS);
    }
    
    public void stopNotes() {
        scheduler.endAll();
    }
}
