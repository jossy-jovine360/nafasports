/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model.coach;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
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
public class CoachLoginServlet extends HttpServlet {
   
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
            String function = request.getParameter("function").trim();
            if(function.equals("login-1")){
                String email = request.getParameter("textEmail").trim();
                String password = request.getParameter("textPassword").trim();

                Connection konn_login = null;
                if(konn_login == null){
                    konn_login = Konnect.getNAFAConnection();
                    String query = "select * from "+AppData.COACHES+" where vEmail = '"+email+"' and vPassword = '"+password+"'";
                    DataAccessObject obj = DataAccessObject.loadCustomRecord(query, konn_login);
                    if(obj.next()){
                        String coachno = obj.getString(1);
                        String fullname = obj.getString(2);
                        String emailid = obj.getString(3);
                        String flag_user = "";
                        
                        HttpSession session = request.getSession();
                        session.setAttribute("coachno", coachno);
                        session.setAttribute("email", emailid);
                        session.setAttribute("name", fullname);
                        request.setAttribute("name", fullname);
                        request.setAttribute("email", email);
                        request.setAttribute("portal", "1");
                        getServletContext().getRequestDispatcher("/coaches/dashboard.jsp?i="+email).forward(request, response);

                    }else{
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "invalid login details. Access denied.");
                        getServletContext().getRequestDispatcher("/coaches/").forward(request, response);
                    }
                }else{
                    request.setAttribute("portal", "1");
                    request.setAttribute("msg", "Kindly refresh your page and try again.");
                    getServletContext().getRequestDispatcher("/coaches/").forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoachLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
