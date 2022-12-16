import java.security.SecureRandom;
//import java.security.Timestamp;
import java.sql.Timestamp;

public class Utils{
    public static String generateRandomString(int stringLength){
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(stringLength);
        for(int i = 0; i < stringLength; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static Timestamp generateTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
}