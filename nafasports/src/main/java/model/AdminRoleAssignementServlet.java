/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
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
public class AdminRoleAssignementServlet extends HttpServlet {
   
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
                Connection konn = null;
                if(konn == null){
                    String email = request.getParameter("comboEmail").trim();
                    String subid = request.getParameter("textSubID").trim();
                    String flag = request.getParameter("flag").trim();
                    
                    String query = "select * from "+AppData.ADMIN_MENUS+" where vEmail = '"+email+"'and iSubMenuID = '"+subid+"'";
                    Connection konn_check = Konnect.getNAFAConnection();
                    int checks = CheckDAO.check(query, konn_check);
                    if(checks == 0){
                        request.setAttribute("portal", "1");
                        if(flag.equals("no")){
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                        }else if(flag.equals("yes")){
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                        }
                    }else{
                        String date = DateTime.getCurrentDate();

                        DataAccessObject dac = null;
                        konn = Konnect.getNAFAConnection();
                        dac = new DataAccessObject();
                        dac.setColumnNames("vEmail","iSubMenuID","dDate");
                        dac.setColumnValues(email, subid, date);
                        int assign = DataAccessObject.insertSecuredSelectedRecord(AppData.ADMIN_MENUS, konn);
                        if(assign == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Role assigned sucessfully.");
                            if(flag.equals("no")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                            }else if(flag.equals("yes")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                            }
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Role could not be assigned sucessfully.");
                            if(flag.equals("no")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                            }else if(flag.equals("yes")){
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username+"&email="+email).forward(request, response);
                            }
                        }konn.close();
                    }konn_check.close();
                }else{
                    request.setAttribute("portal", "1");
                    request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                    getServletContext().getRequestDispatcher("/admin_nafa/admin_assignrole.jsp?i="+username).forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminRoleAssignementServlet.class.getName()).log(Level.SEVERE, null, ex);
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
