/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.id.AutoIdDAO;
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
public class AdminLeagueNewServlet extends HttpServlet {
   
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
                        String tournid = request.getParameter("tournid").trim();
                        String category = request.getParameter("category").trim();
                        String league = request.getParameter("league").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String start = request.getParameter("start").trim();
                        String end = request.getParameter("end").trim();
                        String status = "open";
                        
                        Connection konn_leagueid = Konnect.getNAFAConnection();
                        String leagueid = AutoIdDAO.getNextUniqueAutoID("cLeagueID", "LGE", "6", AppData.LEAGUES, konn_leagueid);
                        Connection konn_league = Konnect.getNAFAConnection();
                        DataAccessObject obj = new DataAccessObject();
                        obj.setColumnValues(leagueid, tournid, league, stateid, start, end, status, category);
                        int add = DataAccessObject.insertSecuredRecord(AppData.LEAGUES, konn_league);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "New league created successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "League could not be created at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues_new.jsp?username="+username).forward(request, response);
                        }
                        break;
                    }
                    case "edit":{
                        String leagueid = request.getParameter("leagueid").trim();
                        String tournid = request.getParameter("tournid").trim();
                        String category = request.getParameter("category").trim();
                        String league = request.getParameter("league").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String start = request.getParameter("start").trim();
                        String end = request.getParameter("end").trim();
                        
                        Connection konn2 = Konnect.getNAFAConnection();
                        DataAccessObject dao = new DataAccessObject();
                        dao.setColumnNames("cTournamentID","vLeagueName","cStateID","dStartDate","dEndDate","cCategory");
                        dao.setColumnValues(tournid, league, stateid, start, end, category);
                        int edit = DataAccessObject.updateSecuredRecord("cLeagueID", leagueid, AppData.LEAGUES, konn2);
                        if(edit == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league could not be edited at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues.jsp?username="+username).forward(request, response);
                        }
                        break;
                    }
                    case "close":{
                        String leagueid = request.getParameter("leagueid").trim();
                        
                        Connection konn2 = Konnect.getNAFAConnection();
                        DataAccessObject dao = new DataAccessObject();
                        dao.setColumnNames("cStatus");
                        dao.setColumnValues("close");
                        int edit = DataAccessObject.updateSecuredRecord("cLeagueID", leagueid, AppData.LEAGUES, konn2);
                        if(edit == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league closed successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league could not be closed at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/leagues.jsp?username="+username).forward(request, response);
                        }
                        break;
                    }
                        
                    default:{
                        int val = AdminDAO.addLogoutEvent(username);
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "You have been successfully logged out.");
                        getServletContext().getRequestDispatcher("/admin_nafa/logout.jsp?username="+username).forward(request, response);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminLeagueNewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
