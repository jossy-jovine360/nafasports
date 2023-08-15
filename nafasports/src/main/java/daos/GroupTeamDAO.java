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
public class GroupTeamDAO {
    public static LinkedHashMap loadAllTeamGroups(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUP_TEAMS +" group by cGroupID asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String group = "";
                String team = "";
                String league = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String groupid = dacs.getString(2);
                    String teamid = dacs.getString(3);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    league = GroupDAO.getGroupLeague(groupid);
                    team = TeamDAO.getTeamName(teamid);
                    value = team+" - "+group+" - "+league;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllGroupTeams(String leagueid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUP_TEAMS +" where cGroupID IN(select cGroupID from "+AppData.GROUPS+" where cLeagueID = '"+leagueid+"')";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String group = "";
                String team = "";
                String league = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String groupid = dacs.getString(2);
                    String teamid = dacs.getString(3);
                    
                    group = GroupDAO.getTeamGroup(groupid);
                    league = GroupDAO.getGroupLeague(groupid);
                    team = TeamDAO.getTeamName(teamid);
                    value = team+" - "+group+" - "+league;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static String getTeamID(String groupteamid){
        String name = "";
        String query = "select * from "+AppData.GROUP_TEAMS+" where iGroupTeamID = '"+groupteamid+"'";
        
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
            Logger.getLogger(GroupTeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getTeam(String groupteamid){
        String name = "";
        String query = "select * from "+AppData.GROUP_TEAMS+" where iGroupTeamID = '"+groupteamid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String teamid  = dac.getString(3);
                name = TeamDAO.getTeamName(teamid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupTeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
