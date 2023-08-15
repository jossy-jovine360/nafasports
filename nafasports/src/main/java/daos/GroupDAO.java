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
public class GroupDAO {
    public static LinkedHashMap loadAllGroups(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.GROUPS+" order by cStateID, cGroup asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    String league = "";
                    String district = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String leagueid = dacs.getString(2);
                        String stateid = dacs.getString(3);
                        String districtid = dacs.getString(4);
                        String group = dacs.getString(5);
                        
                        league = LeagueDAO.getLeagueName(leagueid);
                        state = StateDAO.getStateName(stateid);
                        district = DistrictDAO.getDistrictName(districtid);
                        value = group + ", "+district+", "+state+", "+league;

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllAssignedGroups(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.GROUP_TEAMS+" group by(cGroupID) order by cGroupID asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String group = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String groupid = dacs.getString(2);
                        String teamid = dacs.getString(3);
                        
                        group = getTeamGroup(groupid);
                        value = group;

                        link.put(groupid, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllTeamsAssignedToGroups(String groupid){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.GROUP_TEAMS+" where cGroupID = '"+groupid+"'";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String team = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String groupids = dacs.getString(2);
                        String teamid = dacs.getString(3);
                        
                        team = TeamDAO.getTeamNameInfo(teamid);
                        value = team;

                        link.put(teamid, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static String getTeamGroup(String groupid){
        String value = "";
        
        Connection conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
        try {
            if(dacs.next()){
                dacs.previous();
                String state = "";
                String league = "";
                String district = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String leagueid = dacs.getString(2);
                    String stateid = dacs.getString(3);
                    String districtid = dacs.getString(4);
                    String group = dacs.getString(5);

                    league = LeagueDAO.getLeagueName(leagueid);
                    state = StateDAO.getStateName(stateid);
                    district = DistrictDAO.getDistrictName(districtid);
                    value = group + ", "+district+", "+state+", "+league;

                }
            }
            conn_link1.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    public static String getTeamGroupOnly(String groupid){
        String value = "";
        
        Connection conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
        try {
            if(dacs.next()){
                dacs.previous();
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String group = dacs.getString(5);

                    value = group;

                }
            }
            conn_link1.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    public static String getGroupName(String groupid){
        String value = "";
        
        Connection conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
        try {
            if(dacs.next()){
                dacs.previous();
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String group = dacs.getString(5);

                    value = group;

                }
            }
            conn_link1.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    public static String getGroupLeagueID(String groupid){
        String value = "";
        
        Connection conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
        try {
            if(dacs.next()){
                dacs.previous();
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String league = dacs.getString(2);

                    value = league;

                }
            }
            conn_link1.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    public static String getGroupLeague(String groupid){
        String value = "";
        
        Connection conn_link1 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.GROUPS+" where cGroupID = '"+groupid+"'";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
        try {
            if(dacs.next()){
                dacs.previous();
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String leagueid = dacs.getString(2);

                    value = LeagueDAO.getLeagueName(leagueid);

                }
            }
            conn_link1.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
}
