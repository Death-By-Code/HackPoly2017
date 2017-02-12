package deathbycode.hackpoly2017;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Created by Acer Customer on 2/11/2017.
 */
public class ToneSynth {
    Synthesizer syn;
    MidiChannel[] midichannel;
    Instrument[] instruments;

    public ToneSynth() {
        try {
            syn = MidiSystem.getSynthesizer();
            syn.open();
            midichannel = syn.getChannels();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void play(int... notes) {
        for(int i = 0; i < notes.length; i++) {
            this.midichannel[5].noteOn(notes[i], 1000);
        }
    }
}
