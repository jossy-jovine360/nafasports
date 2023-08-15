/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class PlayerDAO {
    public static LinkedHashMap loadAllPlayers(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.PLAYERS +" order by vFullName asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String fullname = dacs.getString(3);
                    value = fullname;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllTeamPlayers(String teamid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.PLAYERS +" where iTeamID = '"+teamid+"' order by vFullName asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String fullname = dacs.getString(3);
                    value = fullname;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static int getTotalPlayers(){
        int name = -1;
        String query = "select COUNT(cPlayerID) from "+AppData.PLAYERS+"";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalTeamPlayers(String teamid){
        int name = -1;
        String query = "select COUNT(cPlayerID) from "+AppData.PLAYERS+" where iTeamID = '"+teamid+"'";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalPlayers(String coachno){
        int name = -1;
        String query = "select COUNT(cPlayerID) from "+AppData.PLAYERS+" where iTeamID IN(select iTeamID from "+AppData.TEAM_COACHES+" where cCoachID = '"+coachno+"')";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static String getPlayerName(String playerid){
        String name = "";
        String query = "select * from "+AppData.PLAYERS+" where cPlayerID = '"+playerid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String fullname = dac.getString(3);            
               name = fullname;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getPlayerAge(String playerid){
        String name = "";
        String date = DateTime.getCurrentDate();
        String query = "select DATEDIFF('"+date+"', dDOB) as 'age' from "+AppData.PLAYERS+" where cPlayerID = '"+playerid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               int days = dac.getInt(1);   
               
               int year = days / 365;
               
               int daysleft = days - (year * 365);
               int leap = year / 4;
               daysleft = daysleft - leap;    
               if(daysleft > 30){
                   int mon = daysleft / 30;
                   int daysleft2 = daysleft - (mon * 30);
                   
                   daysleft2 = daysleft2 - leap;
                   name = String.valueOf(year)+" years, "+ daysleft2+" days";
               }else{
                   name = String.valueOf(year)+" years, "+ daysleft+" days";
               }
               
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getPlayerTeamID(String gameid, String playerid){
        String name = "";
        String query = "select * from "+AppData.GAME_LINEUPS+" where iGameID = '"+gameid+"' and cPlayerID = '"+playerid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String teamid = dac.getString(4);            
               name = teamid;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
