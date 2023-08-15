/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.jovine.dao.DataAccessObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OMEN
 */
public class ReportDAO {

    public static DataAccessObject loadRecord(String query, Connection connect) {
        DataAccessObject dac = null;
        Connection conn = connect;
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return dac;
    }

    public static String getCustomValue(String query, Connection connect) {
        String status = "";
        DataAccessObject dac = null;
        Connection conn = connect;
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if (dac.next()) {
                status = dac.getString(1);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return status;
    }

    public static String getFieldValue(String column, String id, String id_val, String tablename, Connection connect) {
        String status = "";
        Connection conn = connect;
        status = DataAccessObject.getFieldValue(column, id, id_val, tablename, conn);
        return status;
    }
}
