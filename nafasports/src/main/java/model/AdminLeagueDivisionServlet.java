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
public class AdminLeagueDivisionServlet extends HttpServlet {
   
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
                        String leagueid = request.getParameter("leagueid").trim();
                        String division = request.getParameter("division").trim();
                        
                        Connection konn_div = Konnect.getNAFAConnection();
                        DataAccessObject obj = new DataAccessObject();
                        obj.setColumnNames("cLeagueID","vDivision");
                        obj.setColumnValues(leagueid, division);
                        int add = DataAccessObject.insertSecuredSelectedRecord(AppData.DIVISIONS, konn_div);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "New league division created successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "League division could not be created at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions_new.jsp?username="+username).forward(request, response);
                        }
                        break;
                    }
                    case "edit":{
                        String divid = request.getParameter("divid").trim();
                        String leagueid = request.getParameter("leagueid").trim();
                        String division = request.getParameter("division").trim();
                        
                        Connection konn_div = Konnect.getNAFAConnection();
                        DataAccessObject obj = new DataAccessObject();
                        obj.setColumnNames("cLeagueID","vDivision");
                        obj.setColumnValues(leagueid, division);
                        int edit = DataAccessObject.updateSecuredRecord("iDivID", divid, AppData.DIVISIONS, konn_div);
                        if(edit == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league division edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league division could not be edited at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions_new.jsp?username="+username).forward(request, response);
                        }
                        
                        break;
                    }
                    case "delete":{
                        String divid = request.getParameter("divid").trim();
                        
                        Connection konn2 = Konnect.getNAFAConnection();
                        int edit = DataAccessObject.deleteRecord(AppData.DIVISIONS, konn2, "iDivID", divid);
                        if(edit == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "division deleted successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "league divsion could not be deleted at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/divisions.jsp?username="+username).forward(request, response);
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
            Logger.getLogger(AdminLeagueDivisionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
