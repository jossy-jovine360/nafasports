/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.validate.CheckDAO;
import daos.AdminDAO;
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
public class GamesFixturesServlet extends HttpServlet {
   
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
                getServletContext().getRequestDispatcher("/admin_nafa/logout.jsp?username="+user).forward(request, response);
            }else{
//                todo output your page here
                String function = request.getParameter("function").trim();
                switch (function) {
                    case "new":{
                        String matchday = request.getParameter("matchday").trim();
                        String groupid = request.getParameter("groupid").trim();
                        String hometeamid = request.getParameter("hometeamid").trim();
                        String awayteamid = request.getParameter("awayteamid").trim();
                        String date = request.getParameter("date").trim();
                        String time = request.getParameter("time").trim();
                        
                        Connection konn = Konnect.getNAFAConnection();
                        String query_check = "select * from "+AppData.GAMES+" where cMatchDay = '"+matchday+"' and cGroupID = '"+groupid+"' and iHomeTeamID = '"+hometeamid+"' and iAwayTeamID = '"+awayteamid+"'";
                        int check = CheckDAO.check(query_check, konn);
                        if(check == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "This games fixture has been created already.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                        }else{
                            if(hometeamid.equals(awayteamid)){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "A team cannot play itself. Kindy select appropriate teams.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                            }else{
                                DataAccessObject dao = new DataAccessObject();
                                Connection konn1 = Konnect.getNAFAConnection();
                                dao.setColumnNames("cMatchDay","cGroupID","iHomeTeamID","iAwayTeamID","dDate","tTime","cStatus","cStage");
                                dao.setColumnValues(matchday, groupid, hometeamid, awayteamid, date, time, "pending","groups");
                                int add = DataAccessObject.insertSecuredSelectedRecord(AppData.GAMES, konn1);
                                if(add == 0){
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Game fixture created successfully successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                                }else{
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Game fixture could not be created at the point. Refresh and try again or contact support.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                                }konn1.close();
                            }
                        }
                        konn.close();
                        break;
                    }
                    case "edit":{
                        String fixtureid = request.getParameter("fixtureid").trim();
                        String matchday = request.getParameter("matchday").trim();
                        String groupid = request.getParameter("groupid").trim();
                        String hometeamid = request.getParameter("hometeamid").trim();
                        String awayteamid = request.getParameter("awayteamid").trim();
                        String date = request.getParameter("date").trim();
                        String time = request.getParameter("time").trim();
                        
                        if(hometeamid.equals(awayteamid)){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "A team cannot play itself. Kindy select appropriate teams.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                        }else{
                            DataAccessObject dao = new DataAccessObject();
                            Connection konn1 = Konnect.getNAFAConnection();
                            dao.setColumnNames("cMatchDay","cGroupID","iHomeTeamID","iAwayTeamID","dDate","tTime","cStatus");
                            dao.setColumnValues(matchday, groupid, hometeamid, awayteamid, date, time, "pending");
                            int add = DataAccessObject.updateSecuredRecord("iGameID", fixtureid, AppData.GAMES, konn1);
                            if(add == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Game fixture changes saved successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Game fixture changes could not be edited at the point. Refresh and try again or contact support.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_fixtures_new.jsp?i="+username).forward(request, response);
                            }konn1.close();
                        }
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
            Logger.getLogger(GamesFixturesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
