/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
import com.jovine.password.GeneratePassword;
import com.jovine.validate.CheckDAO;
import daos.AdminDAO;
import email.EmailData;
import email.MessagesData;
import email.SendMail;
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
public class AdminUserServlet extends HttpServlet {
   
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
                    String user = request.getParameter("username").trim();
                    String email = request.getParameter("textEmail").trim();
                    String first = request.getParameter("textFirstName").trim();
                    String last = request.getParameter("textLastName").trim();
                    String roleid = request.getParameter("comboRole").trim();
                    
                    Connection conn_check = Konnect.getNAFAConnection();
                    String query = "select * from "+AppData.ADMINS+" where vEmail = '"+email+"'";
                    int check = CheckDAO.check(query, conn_check);
                    if(check == 0){
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "Admin with this email '"+email+"' has been registered already.");
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_user_new.jsp?i="+user).forward(request, response);
                    }else{
                        String admin_user = AdminDAO.generateUsername(first, last);
                        String pass = GeneratePassword.generatePassword(8);
                        String date = DateTime.getCurrentDate();
                        String status = "active";
                        
                        Connection konn = null;
                        DataAccessObject dao = null;
                        if(konn == null){
                            konn = Konnect.getNAFAConnection();
                            dao = new DataAccessObject();
////                            dao.setColumnNames("vEmail","cUserID","vUsername","vPassword","vFirstname","vLastName","dDate","cOTP","cEmailConfirmStatus","cStatus");
//                            dao.setColumnValues(email, userid, admin_user, pass, first, last, date, otp, email_status, status);
//                            int add_admin = DataAccessObject.insertSecuredRecord(AppData.ADMINS, konn);
                            
                            String query1 = "insert into "+AppData.ADMINS+" values('"+email+"','"+admin_user+"','"+pass+"','"+first+"','"+last+"','"+date+"','"+status+"')";
                            String query2 = "insert into "+AppData.ADMINROLES+"(vEmail,iRoleID,dDateAssigned) values('"+email+"','"+roleid+"','"+date+"')";
                            dao.setQuerys(query1);
                            dao.setQuerys(query2);
                            int add_admin = DataAccessObject.batchRecord(konn);
                            if(add_admin == 0){
                                String name = first + " " + last;
                                String receipient = email;
                                String subject = "New Admin Registration Notification";
                                String msg = MessagesData.getNewAdminNotification(name, email, pass);

                                EmailData.setEmailAttributes(receipient, subject, msg);
                                String val = SendMail.sendEmailHTML();
                                if(val.equals("yes")){
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Admin user added successfully. A notification has been sent to the admin's email.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/admins.jsp?i="+user).forward(request, response);
                                }else{
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Admin user added successfully");
                                    getServletContext().getRequestDispatcher("/admin_nafa/admins.jsp?i="+user).forward(request, response);
                                }
//                                request.setAttribute("portal", "1");
//                                request.setAttribute("msg", "Admin user added successfully");
//                                getServletContext().getRequestDispatcher("/admin_nafa/admins.jsp?i="+user).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Am sorry, admin user can't be added at this time. Kindly try again or contact support.");
                                getServletContext().getRequestDispatcher("/admin_nafa/admin_user_new.jsp?i="+user).forward(request, response);
                            }konn.close();
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_user_new.jsp?i="+user).forward(request, response);
                        }
                    }conn_check.close();
                }else if(function.equals("edit")){
                    String user = request.getParameter("username").trim();
                    String email = request.getParameter("textEmail").trim();
                    String emailid = request.getParameter("emailid").trim();
                    String first = request.getParameter("textFirstName").trim();
                    String last = request.getParameter("textLastName").trim();
                    String roleid = request.getParameter("comboRole").trim();
                    
                    Connection konn1 = null;
                    DataAccessObject dao = null;
                    if(konn1 == null){
                        konn1 = Konnect.getNAFAConnection();
                        dao = new DataAccessObject();
                        
                        String query1 = "update "+AppData.ADMINS+" set vEmail = '"+email+"', "
                                + "vFirstname = '"+first+"', "
                                + "vLastName = '"+last+"' where vEmail = '"+emailid+"'";
                        String query2 = "update "+AppData.ADMINROLES+" set vEmail = '"+email+"', "
                                + "iRoleID = '"+roleid+"' "
                                + "where vEmail = '"+emailid+"'";
                        dao.setQuerys(query1);
                        dao.setQuerys(query2);
                        int edit = DataAccessObject.batchRecord(konn1);
                        if(edit >= 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Admin user changed successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/admins.jsp?i="+user).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Am sorry, admin user can't be changed at this time. Kindly try again or contact support.");
                            getServletContext().getRequestDispatcher("/admin_nafa/admin_user_new.jsp?i="+user).forward(request, response);
                        }
                    }else{
                        request.setAttribute("portal", "1");
                        request.setAttribute("msg", "Kindly refresh your page or check your internet connection. Then try again");
                        getServletContext().getRequestDispatcher("/admin_nafa/admin_user_new.jsp?i="+user).forward(request, response);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
