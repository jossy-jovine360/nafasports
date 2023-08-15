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
public class TeamDAO {
    public static LinkedHashMap loadAllTeams(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.TEAMS +" order by vTeam, cGender asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String state = "";
                String district = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String stateid = dacs.getString(4);
                    String cate = dacs.getString(5);
                    String dist = dacs.getString(7);
                    district = DistrictDAO.getDistrictName(dist);
                    state = StateDAO.getStateName(stateid);
                    value = team +", "+district+", "+state+"-"+cate;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllTeamsByStateNCategory(String statecode, String cates){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.TEAMS +" where cStateID = '"+statecode+"' and cGender = '"+cates+"' order by vTeam, cGender asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String state = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String stateid = dacs.getString(4);
                    String cate = dacs.getString(5);
                    state = StateDAO.getStateName(stateid);
                    value = team +" - "+state+"-"+cate;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllTeamsByCategory(String cates){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.TEAMS +" where cGender = '"+cates+"' order by vTeam, cGender asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String state = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String stateid = dacs.getString(4);
                    String cate = dacs.getString(5);
                    state = StateDAO.getStateName(stateid);
                    value = team +" - "+state+"-"+cate;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllTeamsByState(String statecode){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.TEAMS +" where cStateID = '"+statecode+"' order by vTeam, cGender asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String state = "";
                while(dacs.next()){
                    String id = dacs.getString(1);
                    String team = dacs.getString(2);
                    String cate = dacs.getString(5);
                    value = team +" - "+cate;
                    
                    link.put(id, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static String getTeamName(String teamid){
        String name = "";
        String query = "select * from "+AppData.TEAMS+" where iTeamID = '"+teamid+"'";
        
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
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getTeamNameInfo(String teamid){
        String name = "";
        String query = "select * from "+AppData.TEAMS+" where iTeamID = '"+teamid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String team = dac.getString(2);            
               String cate = dac.getString(5); 
               name = team+" - "+cate;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getTeamCoach(String teamid){
        String name = "";
        String query = "select * from "+AppData.TEAM_COACHES+" where iTeamID = '"+teamid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String coachid = dac.getString(3);            
               name = CoachDAO.getCoachName(coachid);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static int getTotalTeams(){
        int name = -1;
        String query = "select COUNT(iTeamID) from "+AppData.TEAMS+"";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalLimtedTeams(int num){
        int name = -1;
        String query = "select COUNT(iTeamID) from "+AppData.TEAMS+" limit 0, "+num+"";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalMensTeams(){
        int name = -1;
        String query = "select COUNT(iTeamID) from "+AppData.TEAMS+" where cGender = 'Men'";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static int getTotalWomensTeams(){
        int name = -1;
        String query = "select COUNT(iTeamID) from "+AppData.TEAMS+" where cGender = Women'";
//        name = CounterDAO.CountCustomRecordsNOTCOUNT(query);
        Connection conn = null;
        if(conn == null){
            conn = Konnect.getNAFAConnection();
            name = DataAccessObject.getCustomRowCount(query, conn);
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(TeamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return name;
    }
    public static DataAccessObject getTeamQuery(String stateid, String districtid, String category){
        DataAccessObject dac = null;
        String query = "";
        if(!(stateid == null || stateid.equals("all")) && !(category == null || category.equals("all")) && !(districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cStateID = '"+stateid+"' and cGender = '"+category+"' and iDistrictID = '"+districtid+"' order by(vTeam) asc";
        }else if(!(stateid == null || stateid.equals("all")) && !(category == null || category.equals("all")) && (districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cStateID = '"+stateid+"' and cGender = '"+category+"' order by(vTeam) asc";
        }else if(!(stateid == null || stateid.equals("all")) && (category == null || category.equals("all")) && !(districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cStateID = '"+stateid+"' and iDistrictID = '"+districtid+"' order by(vTeam) asc";
        }else if((stateid == null || stateid.equals("all")) && !(category == null || category.equals("all")) && !(districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cGender = '"+category+"' and iDistrictID = '"+districtid+"' order by(vTeam) asc";
        }else if((stateid == null || stateid.equals("all")) && (category == null || category.equals("all")) && (districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" order by(vTeam) asc";
        }else if((stateid == null || stateid.equals("all")) && (category == null || category.equals("all")) && !(districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where iDistrictID = '"+districtid+"' order by(vTeam) asc";
        }else if((stateid == null || stateid.equals("all")) && !(category == null || category.equals("all")) && (districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cGender = '"+category+"' order by(vTeam) asc";
        }else if(!(stateid == null || stateid.equals("all")) && (category == null || category.equals("all")) && (districtid == null || districtid.equals("all"))){
            query = "select * from "+AppData.TEAMS+" where cStateID = '"+stateid+"' order by(vTeam) asc";
        }
        dac = ReportDynaDAO.loadRecord(query);
        
        return dac;
    }
}
