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
public class GameDAO {
    public static LinkedHashMap loadAllGames(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES +" order by cGroupID asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String teamh = "";
                String teama = "";
                String group = "";
                String game = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String md = dacs.getString(2);
                    String groupid = dacs.getString(3);
                    String hometeamid = dacs.getString(4);
                    String awayteamid = dacs.getString(6);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    teamh = TeamDAO.getTeamNameInfo(hometeamid);
                    teama = TeamDAO.getTeamNameInfo(awayteamid);
                    game = teamh+" VS "+teama;
                    value = "MD"+md+" - "+game +", "+group;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllPendingGames(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES +" where cStatus = 'pending' order by cGroupID asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String teamh = "";
                String teama = "";
                String group = "";
                String game = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String md = dacs.getString(2);
                    String groupid = dacs.getString(3);
                    String hometeamid = dacs.getString(4);
                    String awayteamid = dacs.getString(6);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    teamh = TeamDAO.getTeamNameInfo(hometeamid);
                    teama = TeamDAO.getTeamNameInfo(awayteamid);
                    game = teamh+" VS "+teama;
                    value = "MD"+md+" - "+game +", "+group;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllPendingGamesPlayOffs(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES_PLAYOFFS +" where cStatus = 'pending' order by dDate desc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String teamh = "";
                String teama = "";
                String game = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String hometeamid = dacs.getString(3);
                    String awayteamid = dacs.getString(6);
                    String date = dacs.getString(8);
                    
                    teamh = TeamDAO.getTeamNameInfo(hometeamid);
                    teama = TeamDAO.getTeamNameInfo(awayteamid);
                    game = teamh+" VS "+teama;
                    value = game+" - "+date;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllPlayedGames(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES +" where cStatus = 'closed' order by cGroupID asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String teamh = "";
                String teama = "";
                String group = "";
                String game = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String md = dacs.getString(2);
                    String groupid = dacs.getString(3);
                    String hometeamid = dacs.getString(4);
                    String homescore = dacs.getString(5);
                    String awayteamid = dacs.getString(6);
                    String awayscore = dacs.getString(7);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    teamh = TeamDAO.getTeamNameInfo(hometeamid);
                    teama = TeamDAO.getTeamNameInfo(awayteamid);
                    game = teamh+"["+homescore+"] VS ["+awayscore+"] "+teama;
                    value = "MD"+md+" - "+game +", "+group;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllGameTeams(String gameid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES +" where iGameID = '"+gameid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                String teamh = "";
                String teama = "";
                
                String id = dacs.getString(1);
                String hometeamid = dacs.getString(4);
                String awayteamid = dacs.getString(6);

                teamh = TeamDAO.getTeamNameInfo(hometeamid);
                teama = TeamDAO.getTeamNameInfo(awayteamid);

                link.put(hometeamid, teamh);
                link.put(awayteamid, teama);
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllGames(String leagueid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GAMES +" where cGroupID IN(select cGroupID from "+AppData.GROUPS+" where cLeagueID = '"+leagueid+"')";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String teamh = "";
                String teama = "";
                String group = "";
                String game = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String md = dacs.getString(2);
                    String groupid = dacs.getString(3);
                    String hometeamid = dacs.getString(4);
                    String awayteamid = dacs.getString(6);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    teamh = TeamDAO.getTeamNameInfo(hometeamid);
                    teama = TeamDAO.getTeamNameInfo(awayteamid);
                    game = teamh+" VS "+teama;
                    value = "MD"+md+" - "+game +", "+group;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static String getGame(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String md = dac.getString(2);
                String groupid = dac.getString(3);
                String hometeamid = dac.getString(4);
                String awayteamid = dac.getString(6);  
                
                String group = GroupDAO.getTeamGroup(groupid);
                String teamh = TeamDAO.getTeamNameInfo(hometeamid);
                String teama = TeamDAO.getTeamNameInfo(awayteamid);
                String game = teamh+" VS "+teama;
                name = "MD"+md+" - "+game +", "+group;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getPlayOffGame(String playoffid){
        String name = "";
        String query = "select * from "+AppData.GAMES_PLAYOFFS+" where iPlayOffID = '"+playoffid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String hometeamid = dac.getString(3);
                String awayteamid = dac.getString(6);
                String date = dac.getString(8);

                String teamh = TeamDAO.getTeamNameInfo(hometeamid);
                String teama = TeamDAO.getTeamNameInfo(awayteamid);
                String game = teamh+" VS "+teama;
                name = game+" - "+date;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameOnly(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String md = dac.getString(2);
                String groupid = dac.getString(3);
                String hometeamid = dac.getString(4);
                String awayteamid = dac.getString(6);  
                
                String group = GroupDAO.getTeamGroup(groupid);
                String teamh = TeamDAO.getTeamNameInfo(hometeamid);
                String teama = TeamDAO.getTeamNameInfo(awayteamid);
                String game = teamh+" VS "+teama;
                name =  game;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameOnlyWithDate(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String md = dac.getString(2);
                String groupid = dac.getString(3);
                String hometeamid = dac.getString(4);
                String awayteamid = dac.getString(6);  
                String date = dac.getString(8);  
                
                String group = GroupDAO.getTeamGroup(groupid);
                String teamh = TeamDAO.getTeamNameInfo(hometeamid);
                String teama = TeamDAO.getTeamNameInfo(awayteamid);
                String game = teamh+" VS "+teama;
                name =  game + " - "+date;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameGroupNmatchDay(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String md = dac.getString(2);
                String groupid = dac.getString(3);
                
                String group = GroupDAO.getTeamGroup(groupid);
                name = "Match Day "+md+" - "+group;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameDateTime(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String date = dac.getString(8);
                String time = dac.getString(9);
                
                String datetimes = date+" "+time;
                String datetime = DateTime.getFormatShortDateTime(datetimes, "ddmmyyyy");
                
                name = datetime;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameDate(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String date = dac.getString(8);
                
                String datetime = DateTime.getShortDateFormat(date, "ddmmyyyy");
                
                name = datetime;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGamePlayOffDate(String playoffid){
        String name = "";
        String query = "select * from "+AppData.GAMES_PLAYOFFS+" where iPlayOffID = '"+playoffid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String id = dac.getString(1);
                String date = dac.getString(8);
                
                String datetime = DateTime.getShortDateFormat(date, "ddmmyyyy");
                
                name = datetime;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameHomeTeam(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                name  = dac.getString(4);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameAwayTeam(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                name  = dac.getString(6);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameGroupID(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                name  = dac.getString(3);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getGameLeagueID(String gameid){
        String name = "";
        String query = "select * from "+AppData.GAMES+" where iGameID = '"+gameid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String groupid  = dac.getString(3);
                name = GroupDAO.getGroupLeague(groupid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
