/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.validate.CheckDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class NewsDAO {
    public static LinkedHashMap loadAllNews(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.NEWS+"  order by vTitle asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String cate = dacs.getString(2);
                        String title = dacs.getString(3);

                        value = title+" - "+cate+" News";

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static int checkNewsImage(String imagename){
        int val = -1;
        String query = "select * from "+AppData.NEWS+" where vImage like '%"+imagename+"%'";
        
        Connection conn = Konnect.getNAFAConnection();
        val = CheckDAO.check(query, conn);
        
        return val;
    }
    public static int checkNewsVideos(String newsid){
        int val = -1;
        String query = "select COUNT(iVideoID) from "+AppData.NEWS_VIDEOS+" where iNewsID = '"+newsid+"'";
        
        Connection conn = Konnect.getNAFAConnection();
        String vals = ReportDynaDAO.getCustomValue(query);
        val = Integer.parseInt(vals);
        
        return val;
    }
    public static String[] getNewsImages(String newsid){
        String[] vals = null;
        String query = "select * from "+AppData.NEWS+" where iNewsID = '"+newsid+"'";
        
        DataAccessObject load = ReportDynaDAO.loadRecord(query);
        try {
            if(load.next()){
                String images = load.getString(7);
                
                StringTokenizer token = new StringTokenizer(images, "#");
                int count = token.countTokens();
                vals = new String[count];
                int num = 0;
                while(token.hasMoreTokens()){
                    vals[num] = token.nextToken();
                    num++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        return vals;
    }
    public static String getNewsFirstImages(String images){
        String vals = null;
       
        StringTokenizer token = new StringTokenizer(images, "#");
        vals = token.nextToken();
        
        return vals;
    }
    public static String getNewsTitle(String newsid){
        String name = "";
        String query = "select * from "+AppData.NEWS+" where iNewsID = '"+newsid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String title = dac.getString(3);
                name = title;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getNewsVideo(String newsid){
        String name = "";
        String query = "select * from "+AppData.NEWS_VIDEOS+" where iNewsID = '"+newsid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String title = dac.getString(3);
                name = title;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static String getNewsDataByID(String newsid, String column){
        String name = "";
        String query = "select "+column+" from "+AppData.NEWS+" where iNewsID = '"+newsid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String title = dac.getString(3);
                name = title;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
    public static int getTotalNewsSummary(){
        int total = -1;
        String query = "select COUNT(iNewsID) from "+AppData.NEWS+" where vCategory = 'Latest' limit 0,4";
        Connection conn = null;
        
        conn = Konnect.getNAFAConnection();
        total = CounterDAO.CountCustomRecordsWithCOUNT(query);
        
        return total;
    }
    public static int getTotalNewsSummary(String category, int total){
        int tot = -1;
        String query = "select COUNT(iNewsID) from "+AppData.NEWS+" where vCategory = '"+category+"' limit 0,"+total+"";
        Connection conn = null;
        
        conn = Konnect.getNAFAConnection();
        tot = CounterDAO.CountCustomRecordsWithCOUNT(query);
        
        return tot;
    }
}
