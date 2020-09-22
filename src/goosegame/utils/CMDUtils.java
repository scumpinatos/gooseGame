package goosegame.utils;

import java.util.Scanner;

/**
 *
 * @author Postazione B3
 */
public class CMDUtils {
    
    public static String getLine(String prompt) {
        if(prompt != null && prompt.length() > 0) {
            System.out.println(prompt);
        }
        
        return new Scanner(System.in).nextLine();
    }
    
}
