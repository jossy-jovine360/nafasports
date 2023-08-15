/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import java.sql.Connection;

/**
 *
 * @author josiah
 */
public class ReportDynaDAO {
    public static DataAccessObject loadRecord(String query){
        DataAccessObject dac = null;
        Connection connect = Konnect.getNAFAConnection();
        dac = ReportDAO.loadRecord(query, connect);
        
        return dac;
    }
    public static String getCustomValue(String query){
        String val = "";
        Connection connect = Konnect.getNAFAConnection();
        val = ReportDAO.getCustomValue(query, connect);
        
        return val;
    }
    public static String getFieldValue(String column, String id, String id_val, String tablename){
        String val = "";
        Connection connect = Konnect.getNAFAConnection();
        val = ReportDAO.getFieldValue(column, id, id_val, tablename, connect);
        
        return val;
    }
}
