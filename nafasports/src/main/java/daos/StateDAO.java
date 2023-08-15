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
public class StateDAO {
    public static LinkedHashMap loadAllStatesOnly(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select cStateCode, vState from "+ AppData.STATES +" order by(vState) asc";
            link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn_link1);
            
            try {
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllCountryStates(String countryid){
        LinkedHashMap link = null;
        Connection conn_link = null;
        
        if(conn_link == null){
            conn_link = Konnect.getNAFAConnection();
            String query = "select cStateCode, vState from "+ AppData.STATES +" where iCountryID = '"+countryid+"' order by(vState) asc";
            link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn_link);
            
            try {
                conn_link.close();
            } catch (SQLException ex) {
                Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllStatesInfo(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.STATES +" order by(vState) asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String country = "";
                while(dacs.next()){
                    String code = dacs.getString(1);
                    String state = dacs.getString(2);
                    String countryid = dacs.getString(3);
                    country = CountryDAO.getCountryName(countryid);
                    value = state + ", " +country;
                    
                    link.put(code, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllLGAs(){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.LGAS +" order by(vLocalGovtArea) asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String country = "";
                while(dacs.next()){
                    String code = dacs.getString(1);
                    String lga = dacs.getString(2);
                    String stateis = dacs.getString(3);
                    country = StateDAO.getStateName(stateis);
                    value = lga + ", " +country+" State";
                    
                    link.put(code, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static LinkedHashMap loadAllStatesInfo(String countryid){
        LinkedHashMap link = null;
        Connection conn_link = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.STATES +" where iCountryID = '"+countryid+"' order by(vState) asc";
        DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link);
        try {
            if(dacs.next()){
                link = new LinkedHashMap();
                dacs.previous();
                String value = "";
                String country = "";
                while(dacs.next()){
                    String code = dacs.getString(1);
                    String state = dacs.getString(2);
                    
                    country = CountryDAO.getCountryName(countryid);
                    value = state + ", " +country;
                    
                    link.put(code, value);
                }
            }
            conn_link.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return link;
    }
    public static String getStateName(String statecode){
        String name = "";
        String query = "select * from "+AppData.STATES+" where cStateCode = '"+statecode+"'";
        
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
    public static String getLgaID(String lga){
        String name = "";
        String query = "select * from "+AppData.LGAS+" where vLocalGovtArea = '"+lga+"'";
        
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
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getLga(String lgaid){
        String name = "";
        String query = "select * from "+AppData.LGAS+" where iLocalGovtID = '"+lgaid+"'";
        
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
    public static String getCountryIDByState(String statecode){
        String name = "";
        String query = "select * from "+AppData.STATES+" where cStateCode = '"+statecode+"'";
        
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
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCountryNameByState(String statecode){
        String name = "";
        String query = "select * from "+AppData.STATES+" where cStateCode = '"+statecode+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               String id = dac.getString(3);   
               name = CountryDAO.getCountryName(id);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    
}
