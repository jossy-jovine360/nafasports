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
public class AdminProfileServlet extends HttpServlet {
   
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
                String function = request.getParameter("function").trim();
                if(function.equals("add")){
                    String profile = request.getParameter("textProfile").trim();
                    String user = request.getParameter("username").trim();

                    Connection conn_check = Konnect.getNAFAConnection();
                    String query_check = "select * from "+AppData.ROLES+" where vRole = '"+profile+"'";
                    int check = CheckDAO.check(query_check, conn_check);
                    if(check == 0){
                        request.setAttribute("portal", "1");
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                    }else{
                        Connection conn = null;
                        if(conn == null){
                            conn = Konnect.getNAFAConnection();
                            DataAccessObject dac1 = null;
                            dac1 = new DataAccessObject();
                            dac1.setColumnNames("vRole");
                            dac1.setColumnValues(profile);
                            int add = DataAccessObject.insertSecuredSelectedRecord(AppData.ROLES, conn);
                            if(add == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Role added successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Am sorry! We could not add admin profile at this time. Try again or contact support.");
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                            }conn.close();
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                        }
                    }
                    conn_check.close();
                }else if(function.equals("edit")){
                    String roleid = request.getParameter("roleid").trim();
                    String profile = request.getParameter("textProfile").trim();
                    String user = request.getParameter("username").trim();
                    
                    Connection conn = null;
                    if(conn == null){
                        conn = Konnect.getNAFAConnection();
                        DataAccessObject dac1 = null;
                        dac1 = new DataAccessObject();
                        dac1.setColumnNames("vRole");
                        dac1.setColumnValues(profile);
                        int edit = DataAccessObject.updateSecuredRecord("iRoleID", roleid, AppData.ROLES, conn);
                        if(edit == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Role changed successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Am sorry! We could not changed admin profile at this time. Try again or contact support.");
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                        }
                    }else{
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_profiles.jsp?i="+user).forward(request, response);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
