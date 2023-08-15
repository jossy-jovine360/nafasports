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
public class LeagueDAO {
    public static LinkedHashMap loadAllLeagues(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.LEAGUES+" order by vLeagueName asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    String tourn = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String tournid = dacs.getString(2);
                        String league = dacs.getString(3);
                        String stateid = dacs.getString(4);
                        tourn = TournamentDAO.getTournamentName(tournid);
                        state = StateDAO.getStateName(stateid);
                        value = league + ", "+tourn+" tournament - "+state;

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static String getLeagueName(String leagueid){
        String name = "";
        String query = "select * from "+AppData.LEAGUES+" where cLeagueID = '"+leagueid+"'";
        
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
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLeagueCategory(String leagueid){
        String name = "";
        String query = "select * from "+AppData.LEAGUES+" where cLeagueID = '"+leagueid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = dac.getString(8);            
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLeagueTournamentID(String leagueid){
        String name = "";
        String query = "select * from "+AppData.LEAGUES+" where cLeagueID = '"+leagueid+"'";
        
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
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLeagueTournament(String leagueid){
        String name = "";
        String query = "select * from "+AppData.LEAGUES+" where cLeagueID = '"+leagueid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String tournid = dac.getString(2);            
               name = TournamentDAO.getTournamentName(tournid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLeagueIDByGroupID(String groupid){
        String name = "";
        String query = "select * from "+AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        
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
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLeagueNameByGroupID(String groupid){
        String name = "";
        String query = "select * from "+AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String leagueid = dac.getString(2);   
               name = LeagueDAO.getLeagueName(leagueid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static int getTotalLeagues(){
        int name = -1;
        String query = "select COUNT(cLeagueID) from "+AppData.LEAGUES+"";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalActiveLeagues(){
        int name = -1;
        String query = "select COUNT(cLeagueID) from "+AppData.LEAGUES+" where cStatus = 'open'";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LeagueDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static DataAccessObject getTwoLeagueIDs(){
        DataAccessObject dac = null;
        String query = "select cLeagueID from "+AppData.LEAGUES+" where cStatus = 'open' limit 0,2";
        dac = ReportDynaDAO.loadRecord(query);
        
        return dac;
    }
}
