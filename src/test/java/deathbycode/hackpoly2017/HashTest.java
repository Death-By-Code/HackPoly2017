package deathbycode.hackpoly2017;

import java.util.Arrays;

public class HashTest 
{
    public static void main( String[] args )
    {
        try
        {
            SHA256Hash file = new SHA256Hash( "test.txt" );
            System.out.println( Arrays.toString( file.hashFile() ) );
            byte[] thing = file.hashFile();
            System.out.println( thing.length );
            String hashHex = file.hashFileHex();
            System.out.println( hashHex );
            System.out.println( hashHex.length() );
        }
        catch (Exception e)
        {
            System.out.println("apple");
        }
    }
}
