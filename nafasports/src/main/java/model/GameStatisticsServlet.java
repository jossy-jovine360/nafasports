/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import daos.AdminDAO;
import daos.GameDAO;
import daos.ReportDynaDAO;
import daos.TeamDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josiah
 */
public class GameStatisticsServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            if(username == null){
                String user = request.getParameter("username").trim();
                String email = AdminDAO.getAdminDataByUsername(username, "vEmail");
                int val = AdminDAO.addLogoutEvent(email);
                
                request.setAttribute("portal", "1");
                request.setAttribute("msg", "You have been successfully logged out.");
                getServletContext().getRequestDispatcher("/ims/logout.jsp?username="+user).forward(request, response);
            }else{
//                todo output your page here
                String function = request.getParameter("function").trim();
                switch (function) {
                    case "new":{
                        String gameid = request.getParameter("gameid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        String firstdown = request.getParameter("firstdown").trim();
                        String totalyard = request.getParameter("totalyard").trim();
                        String passingyard = request.getParameter("passingyard").trim();
                        String rushingyard = request.getParameter("rushingyard").trim();
                        String penalties = request.getParameter("penalties").trim();
                        String penaltiesyard = request.getParameter("penaltiesyard").trim();
                        String turnovers = request.getParameter("turnovers").trim();
                        String punts = request.getParameter("punts").trim();
                        String touchdowns = request.getParameter("touchdowns").trim();
                        String rushingtouchdowns = request.getParameter("rushingtouchdowns").trim();
                        String passingtouchdowns = request.getParameter("passingtouchdowns").trim();
                        String turnovertouchdown = request.getParameter("turnovertouchdown").trim();
                        String fieldgoal_succeeded = request.getParameter("fieldgoal_succeeded").trim();
                        String fieldgoal_attempted = request.getParameter("fieldgoal_attempted").trim();
                        String interception_thrown = request.getParameter("interception_thrown").trim();
                        String fumblelost = request.getParameter("fumblelost").trim();
                        String sacksallowed = request.getParameter("sacksallowed").trim();
                        String safeties = request.getParameter("safeties").trim();
                        String twopoints_conversion = request.getParameter("twopoints_conversion").trim();
                        
                        String query_player = "select * from "+AppData.GAME_STATS+" where iGameID = '"+gameid+"' and iTeamID = '"+teamid+"'";
                        DataAccessObject play = ReportDynaDAO.loadRecord(query_player);
                        if(play.next()){
                            String team = TeamDAO.getTeamNameInfo(teamid);
                            String game = GameDAO.getGameOnly(gameid);
                            request.setAttribute("msg", "A game stat for this game: "+game+" has been registered for this Team: "+team);
                            request.setAttribute("portal", "1");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_stats_new.jsp?i="+username).forward(request, response);
                        }else{
                            Connection add = Konnect.getNAFAConnection();
                            DataAccessObject dac = new DataAccessObject();
                            dac.setColumnNames("iGameID","iTeamID","iFirstDown","iTotalYard","iPassingYard","iRushingYard","iPenalties","iPenaltiesYard","iTurnovers",
                                    "iPunts","iTouchdowns","iRushingTouchdown","iPassingTouchdowns","iTurnoverTouchdowns","iFieldGoalSucceeded","iFieldGoalAttempted",
                                    "iInterceptionThrown","iFumblesLost","iSacksAllowed","iSafeties","iTwoPointConversion");
                            dac.setColumnValues(gameid, teamid, firstdown, totalyard, passingyard, rushingyard, penalties, penaltiesyard, turnovers, punts, 
                                    touchdowns, rushingtouchdowns, passingtouchdowns, turnovertouchdown, fieldgoal_succeeded, fieldgoal_attempted, interception_thrown,
                                    fumblelost, sacksallowed, safeties, twopoints_conversion);
                            int insert = DataAccessObject.insertSecuredSelectedRecord(AppData.GAME_STATS, add);
                            if(insert == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Game Stats has been added successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_stats.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Game Stats could not be added at this time.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_stats_new.jsp?i="+username).forward(request, response);
                            }add.close();
                        }
                        break;
                    }
                    case "edit":{
                        String statsid = request.getParameter("statsid").trim();
                        String gameid = request.getParameter("gameid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        String firstdown = request.getParameter("firstdown").trim();
                        String totalyard = request.getParameter("totalyard").trim();
                        String passingyard = request.getParameter("passingyard").trim();
                        String rushingyard = request.getParameter("rushingyard").trim();
                        String penalties = request.getParameter("penalties").trim();
                        String penaltiesyard = request.getParameter("penaltiesyard").trim();
                        String turnovers = request.getParameter("turnovers").trim();
                        String punts = request.getParameter("punts").trim();
                        String touchdowns = request.getParameter("touchdowns").trim();
                        String rushingtouchdowns = request.getParameter("rushingtouchdowns").trim();
                        String passingtouchdowns = request.getParameter("passingtouchdowns").trim();
                        String turnovertouchdown = request.getParameter("turnovertouchdown").trim();
                        String fieldgoal_succeeded = request.getParameter("fieldgoal_succeeded").trim();
                        String fieldgoal_attempted = request.getParameter("fieldgoal_attempted").trim();
                        String interception_thrown = request.getParameter("interception_thrown").trim();
                        String fumblelost = request.getParameter("fumblelost").trim();
                        String sacksallowed = request.getParameter("sacksallowed").trim();
                        String safeties = request.getParameter("safeties").trim();
                        String twopoints_conversion = request.getParameter("twopoints_conversion").trim();
                        
                        Connection add = Konnect.getNAFAConnection();
                        DataAccessObject dac = new DataAccessObject();
                        dac.setColumnNames("iGameID","iTeamID","iFirstDown","iTotalYard","iPassingYard","iRushingYard","iPenalties","iPenaltiesYard","iTurnovers",
                                "iPunts","iTouchdowns","iRushingTouchdown","iPassingTouchdowns","iTurnoverTouchdowns","iFieldGoalSucceeded","iFieldGoalAttempted",
                                "iInterceptionThrown","iFumblesLost","iSacksAllowed","iSafeties","iTwoPointConversion");
                        dac.setColumnValues(gameid, teamid, firstdown, totalyard, passingyard, rushingyard, penalties, penaltiesyard, turnovers, punts, 
                                touchdowns, rushingtouchdowns, passingtouchdowns, turnovertouchdown, fieldgoal_succeeded, fieldgoal_attempted, interception_thrown,
                                fumblelost, sacksallowed, safeties, twopoints_conversion);
                        int insert = DataAccessObject.updateSecuredRecord("iStatID", statsid,AppData.GAME_STATS, add);
                        if(insert == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Game Stats has been updated successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_stats.jsp?i="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Game Stats could not be updated at this time.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_stats_new.jsp?i="+username).forward(request, response);
                        }add.close();
                        
                        break;
                    }
                    case "":{
                        
                        break;
                    }
                        
                    default:
                        throw new AssertionError();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameStatisticsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
