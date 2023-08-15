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
public class AdminRoleRevokeServlet extends HttpServlet {
   
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
                
                request.setAttribute("msg", "You have been successfully logged out.");
                getServletContext().getRequestDispatcher("/admin_nafa/logout.jsp?username="+user).forward(request, response);
            }else{
//                todo output your page here
                String email = request.getParameter("textEmail").trim();
                String subid = request.getParameter("textSubID").trim();
                String flag = request.getParameter("flag").trim();

                String query = "select * from "+AppData.ADMIN_MENUS+" where vEmail = '"+email+"'and iSubMenuID = '"+subid+"'";
                Connection konn_check = Konnect.getNAFAConnection();
                int checks = CheckDAO.check(query, konn_check);
                if(checks == 0){
                    Connection konn1 = null;
                    if(konn1 == null){
                        DataAccessObject dac = null;
                        konn1 = Konnect.getNAFAConnection();
                        dac = new DataAccessObject();
                        
                        String query1 = "delete from "+AppData.ADMIN_MENUS+" where vEmail = '"+email+"' and iSubMenuID = '"+subid+"'";
                        dac.setQuerys(query1);
                        int revoke = DataAccessObject.batchRecord(konn1);
                        if(revoke == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "admin role revoked successfully.");
                            if(flag.equals("no")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                            }else if(flag.equals("yes")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                            }
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "admin role could not be revoked at this time.");
                            if(flag.equals("no")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                            }else if(flag.equals("yes")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                            }
                        }konn1.close();
                    }else{
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                        if(flag.equals("no")){
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                        }else if(flag.equals("yes")){
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                        }
                    }
                }else{
                    request.setAttribute("portal", "1");
                    if(flag.equals("no")){
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                    }else if(flag.equals("yes")){
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                    }
                }konn_check.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminRoleRevokeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
