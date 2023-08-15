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
public class AssignCoachToTeamServlet extends HttpServlet {
   
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
                        String coachid = request.getParameter("coachid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        
                        Connection konn = Konnect.getNAFAConnection();
                        String query_check = "select * from "+AppData.TEAM_COACHES+" where iTeamID = '"+teamid+"' and cCoachID = '"+coachid+"'";
                        int check = CheckDAO.check(query_check, konn);
                        if(check == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "This coach has been assigned already");
                            getServletContext().getRequestDispatcher("/admin_nafa/coaches_team_new.jsp?i="+username).forward(request, response);
                        }else{
                            DataAccessObject dao = new DataAccessObject();
                            Connection konn1 = Konnect.getNAFAConnection();
                            dao.setColumnNames("iTeamID","cCoachID");
                            dao.setColumnValues(teamid, coachid);
                            int add = DataAccessObject.insertSecuredSelectedRecord(AppData.TEAM_COACHES, konn1);
                            if(add == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Coach assigned to team successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/coaches_team.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Team coach could not be assigned. Refresh and try again or contact support.");
                                getServletContext().getRequestDispatcher("/admin_nafa/coaches_team_new.jsp?i="+username).forward(request, response);
                            }konn1.close();
                        }
                        konn.close();
                        break;
                    }
                    case "edit":{
                        String teamcoachid = request.getParameter("teamcoachid").trim();
                        String coachid = request.getParameter("coachid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        
                        DataAccessObject dao = new DataAccessObject();
                        Connection konn1 = Konnect.getNAFAConnection();
                        dao.setColumnNames("iTeamID","cCoachID");
                        dao.setColumnValues(teamid, coachid); 
                        int add = DataAccessObject.updateSecuredRecord("iTeamCoachID", teamcoachid, AppData.TEAM_COACHES, konn1);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Team Coach changed successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/coaches_team.jsp?i="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Team assigned could not be change at this point. Refresh and try again or contact support.");
                            getServletContext().getRequestDispatcher("/admin_nafa/coaches_team_new.jsp?i="+username).forward(request, response);
                        }konn1.close();
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
            Logger.getLogger(AssignCoachToTeamServlet.class.getName()).log(Level.SEVERE, null, ex);
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
