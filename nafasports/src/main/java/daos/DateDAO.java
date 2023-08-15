/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import java.util.StringTokenizer;

/**
 *
 * @author josiah
 */
public class DateDAO {
    public static String getDateFromDateTime(String datetime){
        String date = "";
        StringTokenizer token = new StringTokenizer(datetime, " ");
        if(token.hasMoreTokens()){
            date = token.nextToken();
        }
        
        return date;
    }
}
