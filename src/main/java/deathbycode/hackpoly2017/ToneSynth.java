import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Created by Acer Customer on 2/11/2017.
 */
public class ToneSynth {
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
    public void playNotes(int len, int... notes) {
        for(int i = 0; i < notes.length; i++) {
            this.midichannel[2].noteOn(notes[i], len);
        }

        try {
            Thread.sleep(len);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.midichannel[2].allNotesOff();
    }
}
