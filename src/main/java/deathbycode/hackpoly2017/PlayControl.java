/**
 * Created by Acer Customer on 2/11/2017.
 */
public class PlayControl {
    // note values
    final private static double QUARTER = 1.0;
    final private static double EIGHTH = 0.5;
    final private static double HALF = 2.0;
    final private static double WHOLE = 4.0;
    final private static int MILLISECOND_PER_MINUTE = 60000;
    final private static int MILLISECOND_PER_SECOND = 1000;

    private ToneSynth player;

    private int bpm;


    public PlayControl (int Bpm, ToneSynth Player) {
        bpm = Bpm;
        player = Player;
    }

    public int getBeatInMillisecond() {
        return MILLISECOND_PER_MINUTE / bpm;
    }

    public void playQuarterNote(int noteIndex) {
        player.playNotes(this.getBeatInMillisecond(), noteIndex);
    }

    public static int[] generateMajorScale(int startingMidiIndex) {
        int scaleDegrees[] = new int[15];
        scaleDegrees[0] = startingMidiIndex;
        scaleDegrees[1] = startingMidiIndex + 2;
        scaleDegrees[2] = startingMidiIndex + 4;
        scaleDegrees[3] = startingMidiIndex + 5;
        scaleDegrees[4] = startingMidiIndex + 7;
        scaleDegrees[5] = startingMidiIndex + 9;
        scaleDegrees[6] = startingMidiIndex + 11;
        scaleDegrees[7] = startingMidiIndex + 12; // octave
        scaleDegrees[8] = startingMidiIndex + 14;
        scaleDegrees[9] = startingMidiIndex + 16;
        scaleDegrees[10] = startingMidiIndex + 17;
        scaleDegrees[11] = startingMidiIndex + 19;
        scaleDegrees[12] = startingMidiIndex + 21;
        scaleDegrees[13] = startingMidiIndex + 23;
        scaleDegrees[14] = startingMidiIndex + 24;

        return scaleDegrees;
    }

}
