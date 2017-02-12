package deathbycode.hackpoly2017;
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

    public PlayControl (int bpm, ToneSynth player) {
        this.bpm = bpm;
        this.player = player;
    }

    public int getBeatInMillisecond() {
        return MILLISECOND_PER_MINUTE / bpm;
    }

    public void playQuarterNote(int... noteIndex) {
        player.playNotes(this.getBeatInMillisecond(), noteIndex);
    }
    
    public void stopMusic() {
        player.stopNotes();
    }

    /*
    Generates a 15 note scale starting with the startingMidiIndex with index 60 as middle C
    @param startingMidiIndex: The starting note of the scale
    @return: A 15 note major scale
     */
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

    /*
    Generates a 15 note minor scale starting with the startingMidiIndex with index 60 as middle C
    @param startingMidiIndex: The starting note of the scale
    @return: A 15 note major scale
     */
    public static int[] generateMinorScale(int startingMidiIndex) {
        int scaleDegrees[] = new int[15];
        scaleDegrees[0] = startingMidiIndex;
        scaleDegrees[1] = startingMidiIndex + 2;
        scaleDegrees[2] = startingMidiIndex + 3;
        scaleDegrees[3] = startingMidiIndex + 5;
        scaleDegrees[4] = startingMidiIndex + 7;
        scaleDegrees[5] = startingMidiIndex + 8;
        scaleDegrees[6] = startingMidiIndex + 10;
        scaleDegrees[7] = startingMidiIndex + 12; // octave
        scaleDegrees[8] = startingMidiIndex + 14;
        scaleDegrees[9] = startingMidiIndex + 15;
        scaleDegrees[10] = startingMidiIndex + 17;
        scaleDegrees[11] = startingMidiIndex + 19;
        scaleDegrees[12] = startingMidiIndex + 20;
        scaleDegrees[13] = startingMidiIndex + 22;
        scaleDegrees[14] = startingMidiIndex + 24;

        return scaleDegrees;
    }
}
