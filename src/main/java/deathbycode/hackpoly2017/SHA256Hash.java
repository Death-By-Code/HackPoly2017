package deathbycode.hackpoly2017;

import java.io.*;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class SHA256Hash
{
    private File fileToHash;
    private MessageDigest hasher;
    
    
    public SHA256Hash( String pathToFile )
    {
        try
        {
            setFileToHash( pathToFile );
            hasher = MessageDigest.getInstance( "SHA-256" );
        }
        catch ( Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void setFileToHash( String pathToFile )
    {
        try
        {
            fileToHash = new File( pathToFile );
        }
        catch( NullPointerException e )
        {
            e.printStackTrace();
        }
    }
    
    public File getPathToFile()
    {
        return fileToHash;
    }
    
    public byte[] hashFile() throws Exception
    {
        InputStream fileStream = new FileInputStream( fileToHash );
        byte[] buffer = new byte[1024];
        int counter;
        
        do
        {
            counter = fileStream.read( buffer );
            if( counter > 0 )
            {
                hasher.update( buffer, 0, counter );
            }
        }
        while( counter != -1 );
        
        fileStream.close();
        
        return hasher.digest();
    }
    
    public String hashFileHex() throws Exception
    {
        String hashInHex = DatatypeConverter.printHexBinary( hashFile() );
        return hashInHex;
    }
    
}
