/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appData;

/**
 *
 * @author josiah
 */
public class AppData {
//    public static String offline_url = "jdbc:mysql://localhost:3306/jovine360malldb?useUnicode=true&characterEncoding=utf8&character_set_server=utf8mb4&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String offline_url_nafa = "jdbc:mysql://localhost:8889/nafadb?useUnicode=true&characterEncode=UTF-8&character_set_server=utf8mb4&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull";
    public static String username_nafa = "root";
    public static String password_nafa = "root";
    
//    public static String online_url_nafa = "jdbc:mysql://localhost/nafafoot_nafadb?useUnicode=true&characterEncode=UTF-8&character_set_server=utf8mb4&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull";
//    public static String online_username_nafa = "nafafoot_nafaLaw101com";
//    public static String online_password_nafa = "t4VQcQCZPKR)";
    
//    login details for aws
    public static String online_url_nafa = "jdbc:mysql://localhost/Java2EEAppDB?useUnicode=true&characterEncode=UTF-8&character_set_server=utf8mb4&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull";
    public static String online_username_nafa = "root";
    public static String online_password_nafa = "JavaApp##@6622";
    

//    public static String online_url = "jdbc:mysql://localhost/zincoinv_zincoinvestmentdb?useUnicode=true&characterEncode=UTF-8";
//    public static String online_username = "zincoinv_zincoinv";
//    public static String online_password = "1234@admin.com";
    
    public static String domain_email = "";
    public static String domain_email_pwd = "";
    
//    ******************* NAFA Table Schemas *************************
    
    public static final String ADMIN_LOGINS = "admin_logins";
    public static final String ADMIN_MENUS = "admin_menus";
    public static final String ADMINROLES = "adminroles";
    public static final String ADMINS = "admins";
    public static final String CAMPS = "camps";
    public static final String COACHES = "coaches";
    public static final String COUNTRY = "country";
    public static final String DISTRICTS = "districts";
    public static final String DIVISIONS = "divisions";
    public static final String GAME_LINEUPS = "game_lineups";
    public static final String GAME_SCORES_STATS = "game_scores_stats";
    public static final String GAME_STATS = "game_stats";
    public static final String GAMES = "games";
    public static final String GAMES_COMMENTARY = "games_commentary";
    public static final String GAMES_OFFICIALS = "games_officials";
    public static final String GAMES_PLAYOFFS = "games_playoffs";
    public static final String GROUP_STANDINGS = "group_standings";
    public static final String GROUP_TEAMS = "group_teams";
    public static final String GROUPS = "groups";
    public static final String LEAGUES = "leagues";
    public static final String MENU_SUBS = "menu_subs";
    public static final String MENUS = "menus";
    public static final String NEWS = "news";
    public static final String NEWS_VIDEOS = "news_videos";
    public static final String PLAYERS = "players";
    public static final String ROLES = "roles";
    public static final String STATES = "states";
    public static final String LGAS = "lgas";
    public static final String TEAMS = "teams";
    public static final String TEAM_COACHES = "team_coaches";
    public static final String TOURNAMENTS = "tournaments";
    
//    ################### NAFA Table Schemas #########################
    
    
//    ******************** JNAFA Offline **************************************
    public static final String CONTROLLER = "ControllerServlet?action=";
    public static final String ADMIN_CONTROLLER = "AdminControllerServlet?action=";
    public static final String COACH_CONTROLLER = "CoachControllerServlet?action=";
    
    public static final String CONTROLLER_SUBDOMAIN = "http://localhost:8080/nafasports/ControllerServlet?action=";
    public static final String ADMIN_CONTROLLER_SUBDOMAIN = "http://localhost:8080/nafasports/AdminControllerServlet?action=";
    public static final String COACH_CONTROLLER_SUBDOMAIN = "http://localhost:8080/nafasports/CoachControllerServlet?action=";
    
    public static final String DOMAIN = "http://localhost:8080/nafasports/";
    public static final String DOMAIN_ADMIN = "http://localhost:8080/nafasports/admin_nafa/";
    public static final String DOMAIN_COACH = "http://localhost:8080/nafasports/coaches/";
    
//    
//    ################### NAFA Table Schemas #########################

    
    
//    ******************** Jovine360 NAFA Online **************************************   
    
//    public static final String CONTROLLER_SUBDOMAIN = "https://www.nafafootball.com/ControllerServlet?action=";
//    public static final String ADMIN_CONTROLLER_SUBDOMAIN = "https://www.nafafootball.com/AdminControllerServlet?action=";
//    public static final String COACH_CONTROLLER_SUBDOMAIN = "https://www.nafafootball.com/CoachControllerServlet?action=";
//   
//    public static String DOMAIN = "https://www.nafafootball.com";
//    public static String DOMAIN_ADMIN = "https://www.nafafootball.com/admin_nafa/";
//    public static String DOMAIN_COACH = "https://www.nafafootball.com/coaches/";
//    
//    ################### Jovine360 NAFA Online end ########################################    
    
//    SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
}
