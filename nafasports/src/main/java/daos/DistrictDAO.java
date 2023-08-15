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
public class DistrictDAO {
    public static LinkedHashMap loadAllDistricts(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select iDistrictID, vDistrict, cStateID from "+ AppData.DISTRICTS+"  order by vDistrict, cStateID asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String dist = dacs.getString(2);
                        String stateid = dacs.getString(3);

                        state = StateDAO.getStateName(stateid);
                        value = dist + ", " +state;

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(DistrictDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllDistricts(String stateid){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select iDistrictID, vDistrict, cStateID from "+ AppData.DISTRICTS+" where cStateID = '"+stateid+"'  order by vDistrict asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String dist = dacs.getString(2);
                        String statecode = dacs.getString(3);

                        state = StateDAO.getStateName(statecode);
                        value = dist + ", " +state;

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(DistrictDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static String getDistrictName(String districtid){
        String name = "";
        String query = "select * from "+AppData.DISTRICTS+" where iDistrictID = '"+districtid+"'";
        
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
            Logger.getLogger(DistrictDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String checkDistrictStatus(String distid){
        String name = "no";
        String query = "select * from "+AppData.GROUPS+" where iDistrict = '"+distid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
               name = "yes";
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DistrictDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
