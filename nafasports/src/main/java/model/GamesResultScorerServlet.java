/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import daos.AdminDAO;
import daos.PlayerDAO;
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
public class GamesResultScorerServlet extends HttpServlet {
   
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
                        String quarter = request.getParameter("quarter").trim();
                        String gameid = request.getParameter("gameid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        String playerid = request.getParameter("playerid").trim();
                        String time = request.getParameter("time").trim();
                        
                        String query_player = "select * from "+AppData.GAME_SCORES_STATS+" where iGameID = '"+gameid+"' and iTeamID = '"+teamid+"' and "
                                + "cPlayerID = '"+playerid+"' and tTime = '"+time+"'";
                        DataAccessObject play = ReportDynaDAO.loadRecord(query_player);
                        if(play.next()){
                            String player_teamid = PlayerDAO.getPlayerTeamID(gameid, playerid);
                            String team = TeamDAO.getTeamNameInfo(player_teamid);
                            request.setAttribute("msg", "A score stat for this player has been registered for this Team: "+team);
                            request.setAttribute("portal", "1");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_results_scorers.jsp?i="+username).forward(request, response);
                        }else{
                            Connection add = Konnect.getNAFAConnection();
                            DataAccessObject dac = new DataAccessObject();
                            dac.setColumnNames("iGameID","iTeamID","cPlayerID","iQuarter","tTime");
                            dac.setColumnValues(gameid, teamid, playerid, quarter, time);
                            int insert = DataAccessObject.insertSecuredSelectedRecord(AppData.GAME_SCORES_STATS, add);
                            if(insert == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Player's score has been added successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_results_score.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Player's score could not be added to lineup at this time.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_results_scorers.jsp?i="+username).forward(request, response);
                            }add.close();
                        }
                        
                        break;
                    }
                    case "edit":{
                        String scoreid = request.getParameter("scoreid").trim();
                        String quarter = request.getParameter("quarter").trim();
                        String gameid = request.getParameter("gameid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        String playerid = request.getParameter("playerid").trim();
                        String time = request.getParameter("time").trim();
                        
                        Connection edit = Konnect.getNAFAConnection();
                        DataAccessObject dac = new DataAccessObject();
                        dac.setColumnNames("iGameID","iTeamID","cPlayerID","iQuarter","tTime");
                        dac.setColumnValues(gameid, teamid, playerid, quarter, time);
                        int insert = DataAccessObject.updateSecuredRecord("iScoreID", scoreid, AppData.GAME_SCORES_STATS, edit);
                        if(insert == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Player's score has been edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_results_score.jsp?i="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Player's score could not be edited to lineup at this time.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_results_scorers.jsp?i="+username).forward(request, response);
                        }edit.close();
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
            Logger.getLogger(GamesResultScorerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
