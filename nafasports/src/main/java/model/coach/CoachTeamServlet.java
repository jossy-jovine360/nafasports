/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model.coach;

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
public class CoachTeamServlet extends HttpServlet {
   
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
            String email = (String)session.getAttribute("email");
            if(email == null){
                request.setAttribute("portal", "1");
                request.setAttribute("msg", "You have been successfully logged out.");
                getServletContext().getRequestDispatcher("/coaches/logout.jsp?i=hjashj").forward(request, response);
            }else{
//                todo output your page here
                String function = request.getParameter("function").trim();
                switch (function) {
                    case "new":{
                        request.setAttribute("portal", "1");
                        getServletContext().getRequestDispatcher("/coaches/teams.jsp?i="+email).forward(request, response);
                        break;
                    }
                    case "edit":{
                        String teamid = request.getParameter("teamid").trim();
                        String team = request.getParameter("team").trim();
                        String code = request.getParameter("teamcode").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String gender = request.getParameter("gender").trim();
                        
                        Connection conn_edit = null;
                        if(conn_edit == null){
                            conn_edit = Konnect.getNAFAConnection();
                             DataAccessObject dac = new DataAccessObject();
                             dac.setColumnNames("vTeam","cTeamCode","cStateID","cGender");
                             dac.setColumnValues(team, code, stateid, gender);
                             int edit = DataAccessObject.updateSecuredRecord("iTeamID", teamid, AppData.TEAMS, conn_edit);
                             if(edit == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Changes saved successfully.");
                                getServletContext().getRequestDispatcher("/coaches/teams.jsp?i="+email).forward(request, response);
                             }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Changes coauld not be saved successfully. kindly ensure the correct info is submitted.");
                                getServletContext().getRequestDispatcher("/coaches/team_new.jsp?i="+email).forward(request, response);
                             }
                        }
                        conn_edit.close();
                        
                        break;
                    }
                        
                    default:
                        throw new AssertionError();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoachTeamServlet.class.getName()).log(Level.SEVERE, null, ex);
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
