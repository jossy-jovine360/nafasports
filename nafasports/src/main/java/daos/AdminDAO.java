/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
import com.jovine.validate.CheckDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class AdminDAO {
    
    public static String generateUsername(String firstname, String lastname){
        String user = "";
        String flag = "no";
        String digit = "";
        Connection con_check = Konnect.getNAFAConnection();
        
        while (flag.equals("no")) {
            String usernamePool = firstname.concat(lastname);
            for (int i = 0; i < 8; i++) {
                Random rand = new Random();
                int index = rand.nextInt(usernamePool.length());
                user = user + usernamePool.charAt(index);
            }

            String query = "select * from "+AppData.ADMINS+" where vUsername = '"+user+"'";
            int stat = CheckDAO.check(query, con_check);
            if (stat == 1) {
                flag = "yes";
                try {
                    con_check.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, (String) null, ex);
                }
            }
        }
        
        return user;
    }
    public static LinkedHashMap loadAllAssignedAdmins() {
        LinkedHashMap link = null;
        Connection conn = Konnect.getNAFAConnection();
        String query = "select * from " + AppData.ADMIN_MENUS + " group by vEmail order by(vEmail) asc";
        DataAccessObject dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                dac.previous();
                String name = "";
                link = new LinkedHashMap();
                while(dac.next()){
                    String email = dac.getString(2);
                    name = getAdminFullnameByEmail(email);
                    link.put(email, name);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn);
        return link;
    }
    
    public static LinkedHashMap loadActiveAdmins() {
        LinkedHashMap link = null;
        Connection conn = Konnect.getNAFAConnection();
        String query = "select * from " + AppData.ADMINS + " where vEmail IN(select vEmail from "+AppData.ADMINROLES+" where iAdminRoleID NOT IN('1')) and cStatus = 'active'";
        DataAccessObject dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                dac.previous();
                String name = "";
                link = new LinkedHashMap();
                while(dac.next()){
                    String email = dac.getString(1);
                    name = getAdminFullnameByEmail(email);
                    link.put(email, name);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn);
        return link;
    }
    public static LinkedHashMap loadUnassignedActiveAdmins(String subid) {
        LinkedHashMap link = null;
        Connection conn = Konnect.getNAFAConnection();
        String query = "select * from " + AppData.ADMINS + " where vEmail NOT IN(select vEmail from "+AppData.ADMIN_MENUS+" where iSubMenuID = '"+subid+"') and vEmail IN(select vEmail from "+AppData.ADMINROLES+" where iAdminRoleID NOT IN('1')) and cStatus = 'active'";
        DataAccessObject dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                dac.previous();
                String name = "";
                link = new LinkedHashMap();
                while(dac.next()){
                    String email = dac.getString(1);
                    name = getAdminFullnameByEmail(email);
                    link.put(email, name);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn);
        return link;
    }
    public static String checkDefaultPasswordStatus(String email){
        String status = "no";
        String query = "select cChangePassStatus from "+AppData.ADMINS+" where vEmail = '"+email+"'";
        Connection konn = null;
        konn = Konnect.getNAFAConnection();
        
        String val = ReportDAO.getCustomValue(query, konn);
        status = val;
        try {
            konn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    public static String checkAdminSuperuser(String email){
        String status = "no";
        
        Connection konn = Konnect.getNAFAConnection();
        int val = CheckDAO.checkSecured("vEmail", email, AppData.ADMINS, konn);
        if(val == 0){
            status = "yes";
        }
        try {
            konn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    public static String checkAssignedAdmin(String email, String subid){
        String status = "no";
        String query = "select * from "+AppData.ADMIN_MENUS+" where vEmail = '"+email+"' and iSubMenuID = '"+subid+"'";
        Connection konn = Konnect.getNAFAConnection();
        int val = CheckDAO.check(query, konn);
        if(val == 0){
            status = "yes";
        }
        try {
            konn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    public static String getAdminFullnameByEmail(String email){
        String name = "";
        String query = "select * from "+AppData.ADMINS+" where vEmail = '"+email+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String first = dac.getString(4);
                String last = dac.getString(5);
                name = first + " " + last;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminFullnameByUsername(String username){
        String name = "";
        String query = "select * from "+AppData.ADMINS+" where vUsername = '"+username+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String first = dac.getString(4);
                String last = dac.getString(5);
                name = first + " " + last;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminDataByUsername(String username, String column){
        String name = "";
        String query = "select "+column+" from "+AppData.ADMINS+" where vUsername = '"+username+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String first = dac.getString(1);
                name = first;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminDataByEmail(String email, String column){
        String name = "";
        String query = "select "+column+" from "+AppData.ADMINS+" where vEmail = '"+email+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String first = dac.getString(1);
                name = first;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminPassword(String email){
        String val = "";
        String query = "select vPassword from "+AppData.ADMINS+" where vEmail = '"+email+"'";
        
        Connection conn2 = null;
        DataAccessObject dac = null;
        
        conn2 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn2);
        try {
            if(dac.next()){
                String profile = dac.getString(1);
                val = profile;
            }
            conn2.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getAdminUsernameByEmail(String email){
        String val = "";
        String query = "select vUsername from "+AppData.ADMINS+" where vEmail = '"+email+"'";
        
        Connection conn2 = null;
        DataAccessObject dac = null;
        
        conn2 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn2);
        try {
            if(dac.next()){
                String profile = dac.getString(1);
                val = profile;
            }
            conn2.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getAdminProfileRole(String roleid){
        String val = "";
        String query = "select vRole from "+AppData.ROLES+" where iRoleID = '"+roleid+"'";
        
        Connection conn2 = null;
        DataAccessObject dac = null;
        
        conn2 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn2);
        try {
            if(dac.next()){
                String profile = dac.getString(1);
                val = profile;
            }
            conn2.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getAdminRoleID(String email){
        String name = "";
        String query = "select * from "+AppData.ADMINROLES+" where vEmail = '"+email+"'";
        
        Connection conn3 = null;
        DataAccessObject dac = null;
        
        conn3 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn3);
        try {
            if(dac.next()){
                String profileid = dac.getString(3);
                name = profileid;
//                name = getAdminProfile(profileid);
            }
            conn3.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminProfileByUsername(String username){
        String name = "";
        String email = getAdminDataByUsername(username, "vEmail");
        String query = "select * from "+AppData.ADMINROLES+" where vEmail = '"+email+"'";
        
        Connection conn3 = null;
        DataAccessObject dac = null;
        
        conn3 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn3);
        try {
            if(dac.next()){
                String profileid = dac.getString(3);
                name = getAdminProfileRole(profileid);
            }
            conn3.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getAdminProfileByEmail(String email){
        String name = "";
        String query = "select * from "+AppData.ADMINROLES+" where vEmail = '"+email+"'";
        
        Connection conn3 = null;
        DataAccessObject dac = null;
        
        conn3 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn3);
        try {
            if(dac.next()){
                String profileid = dac.getString(3);
                name = getAdminProfileRole(profileid);
            }
            conn3.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static void addLoginEvent(String email){
        int val = -1;
        DataAccessObject dac1 = null;
        Connection conn4 = null;
        String datetime = DateTime.getCurrentDateTime();
        
        try {
            conn4 = Konnect.getNAFAConnection();
            dac1 = new DataAccessObject();
            dac1.setColumnNames("vEmail", "dtLastLogin");
            dac1.setColumnValues(email, datetime);
            int insert = DataAccessObject.insertSecuredSelectedRecord(AppData.ADMIN_LOGINS, conn4);
            if(insert == 0){
                val = insert;
            }
            conn4.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        return val;
        
//        return val;
        
//        return val;
        
//        return val;
    }
    public static int addLogoutEvent(String email){
        int val = -1;
        DataAccessObject dac1 = null;
        Connection conn4 = null;
        String datetime = DateTime.getCurrentDateTime();
        
        try {
            conn4 = Konnect.getNAFAConnection();
            dac1 = new DataAccessObject();
            dac1.setColumnNames("vEmail", "dtLastLogout");
            dac1.setColumnValues(email, datetime);
            int insert = DataAccessObject.insertSecuredSelectedRecord(AppData.ADMIN_LOGINS, conn4);
            if(insert == 0){
                val = insert;
            }
            conn4.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getLastLogin(String email){
        String val = "";
        DataAccessObject dac1 = null;
        Connection conn5 = null;
        conn5 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.ADMIN_LOGINS +" where vEmail = '"+email+"' order by(dtLastLogin) desc limit 1,1";
        DataAccessObject dacll = DataAccessObject.loadCustomRecord(query, conn5);
        try {
            if(dacll.next()){
                val = dacll.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getLastLoginByUsername(String username){
        String email = getAdminDataByUsername(username, "vEmail");
        String val = "";
        DataAccessObject dac1 = null;
        Connection conn5 = null;
        conn5 = Konnect.getNAFAConnection();
        String query = "select * from "+ AppData.ADMIN_LOGINS +" where vEmail = '"+email+"' order by(dtLastLogin) desc limit 1,1";
        DataAccessObject dacll = DataAccessObject.loadCustomRecord(query, conn5);
        try {
            if(dacll.next()){
                val = dacll.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static int getTotalRecords(String columnCount, String table){
        int val = -1;
        String query = "select COUNT("+columnCount+") from "+table+"";
        
        Connection connect = Konnect.getNAFAConnection();
        val = CounterDAO.CountCustomRecordsWithCOUNT(query, connect);
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static int getTotalAdmins(){
        int val = -1;
        String query = "select COUNT(vEmail) from "+AppData.ADMINS+" where cStatus = 'active' or cStatus = 'inactive'";
        
        Connection connect = Konnect.getNAFAConnection();
        val = CounterDAO.CountCustomRecordsWithCOUNT(query, connect);
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static DataAccessObject loadAllAdmin(String sender){
        DataAccessObject daos = null;
        String query = "select * from " + AppData.ADMINS + " where vEmail IN(select vEmail from "+AppData.ADMINROLES+" where iAdminRoleID NOT IN('1') and vEmail NOT IN('"+sender+"') group by vEmail) and cStatus = 'active'";
        
        Connection konnAll = null;
        if(konnAll == null){
            konnAll = Konnect.getNAFAConnection();
            daos = DataAccessObject.loadCustomRecord(query, konnAll);
            
            try {
                konnAll.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return daos;
    }
}
