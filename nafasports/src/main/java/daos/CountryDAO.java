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
public class CountryDAO {
    public static LinkedHashMap loadAllCountries(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select iCountryID, vCountry from "+ AppData.COUNTRY +" order by(vCountry) asc";
            link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn_link1);
            
            try {
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(CountryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static String getCountryName(String countryid){
        String name = "";
        String query = "select * from "+AppData.COUNTRY+" where iCountryID = '"+countryid+"'";
        
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
            Logger.getLogger(CountryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getCountryID(String country){
        String name = "";
        String query = "select * from "+AppData.COUNTRY+" where vCountry = '"+country+"'";
        
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
}
