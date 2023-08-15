/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class CoachDAO {
    public static int getTotalCoaches(){
        int name = -1;
        String query = "select COUNT(cCoachNo) from "+AppData.COACHES+"";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static LinkedHashMap loadAllCoaches(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.COACHES +" order by vFullName asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String fullname = dacs.getString(2);
                    String email = dacs.getString(3);
                    value = fullname +", "+email;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllCoachesTeams(String coachid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.TEAM_COACHES +" where cCoachID = '"+coachid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String team = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String teamid = dacs.getString(2);
                    String coachids = dacs.getString(3);
                    team = TeamDAO.getTeamNameInfo(teamid);
                    value = team;
                    
                    link.put(teamid, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllCoachesTeamsPlayers(String coachid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.PLAYERS +" where iTeamID IN(select iTeamID from "+AppData.TEAM_COACHES+" where cCoachID = '"+coachid+"') order by vFullName asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String team = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String teamid = dacs.getString(2);
                    String fullname = dacs.getString(3);
                    team = TeamDAO.getTeamNameInfo(teamid);
                    value = fullname + " - "+team;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static String getCoachPassword(String coachid){
        String name = "";
        String query = "select * from "+AppData.COACHES+" where cCoachNo = '"+coachid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(5);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCoachName(String coachid){
        String name = "";
        String query = "select * from "+AppData.COACHES+" where cCoachNo = '"+coachid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(2);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCoachTeamID(String coachid){
        String name = "";
        String query = "select * from "+AppData.TEAM_COACHES+" where cCoachNo = '"+coachid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(2);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCoachTeam(String coachid){
        String name = "";
        String query = "select * from "+AppData.TEAM_COACHES+" where cCoachNo = '"+coachid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String teamid = dac.getString(2);  
               name = TeamDAO.getTeamName(teamid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCoachEmail(String coachid){
        String name = "";
        String query = "select * from "+AppData.COACHES+" where cCoachNo = '"+coachid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(3);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCoachIDByName(String coach){
        String name = "";
        String query = "select * from "+AppData.COACHES+" where cCoachNo = '"+coach+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(1);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CoachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
