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
public class DivisionDAO {
    public static LinkedHashMap loadLeagueDivisions(String leagueid){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.DIVISIONS+" where cLeagueID = '"+leagueid+"'";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String league = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String leagid = dacs.getString(2);
                        String div = dacs.getString(3);

                        league = LeagueDAO.getLeagueName(leagid);
                        value = div + " - " +league;

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
    public static String getDivisionName(String divid){
        String name = "";
        String query = "select * from "+AppData.DIVISIONS+" where iDivID = '"+divid+"'";
        
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
            Logger.getLogger(DistrictDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    
}
