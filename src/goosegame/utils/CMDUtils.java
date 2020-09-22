package goosegame.utils;

import java.util.Scanner;

public class CMDUtils {
    
    public static String getLine(String prompt) {
        if(prompt != null && prompt.length() > 0) {
            System.out.println(prompt);
        }else {
            System.out.println("> ");
        }
        
        return new Scanner(System.in).nextLine();
    }
    
}
