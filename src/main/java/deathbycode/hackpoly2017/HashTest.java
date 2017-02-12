import java.io.*;
import java.nio.*;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

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
