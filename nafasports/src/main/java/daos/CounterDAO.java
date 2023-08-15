/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OMEN
 */
public class CounterDAO {

    public static int CountCustomRecordsWithCOUNT(String query) {
        int status = -1;
        Connection conn = Konnect.getNAFAConnection();
        DataAccessObject dac = null;
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if (dac.next()) {
                status = dac.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CounterDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return status;
    }
    public static int CountCustomRecordsWithCOUNT(String query, Connection connect) {
        int status = -1;
        Connection conn = connect;
        DataAccessObject dac = null;
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if (dac.next()) {
                status = dac.getInt(1);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CounterDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return status;
    }

    public static int CountCustomRecordsNOTCOUNT(String query, Connection connect) {
        int status = -1;
        Connection conn = connect;
        DataAccessObject dac = null;
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if (dac.next()) {
                dac.previous();
                int counter = 0;
                while (dac.next()) {
                    counter++;
                }
                status = counter;
            } else {
                status = 0;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CounterDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return status;
    }
}
