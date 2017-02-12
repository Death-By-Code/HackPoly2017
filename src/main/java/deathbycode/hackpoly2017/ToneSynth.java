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
    private final Scheduler scheduler = SchedulerManager.builder().build();
    private Synthesizer syn;
    private MidiChannel[] midichannel;

    public ToneSynth() {
        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midichannel = syn.getChannels();

            for(int i = 0; i < syn.getAvailableInstruments().length; i++){
                System.out.println(Integer.toString(i) + ": " + syn.getAvailableInstruments()[i].getName());
            }


        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }


    /*
    Plays sequence all notes passed as arguments
    @param notes: Piano keys in the range 0 - 127 with 60 as middle C
    @param length: The duration in milliseconds that the notes will play
     */
    public void playNotes(int len, byte... notes) {
        byte[] minor = PlayControl.generateMinorScale(notes[0]);
        AtomicInteger i = new AtomicInteger(0);
        scheduler.repeat(task -> {
            int j = i.getAndIncrement();
            if (j >= notes.length) {
                task.stop();
            } else {
                this.midichannel[2].noteOn(minor[Math.abs(notes[j]) % 15], 100);
                scheduler.run(() -> this.midichannel[2].noteOff(minor[Math.abs(notes[j]) % 15]), len, TimeUnit.MILLISECONDS);
            }
        }, len, TimeUnit.MILLISECONDS);
    }
}
