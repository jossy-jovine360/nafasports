/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
import com.jovine.id.AutoIdDAO;
import com.jovine.password.GeneratePassword;
import com.jovine.validate.CheckDAO;
import daos.AdminDAO;
import daos.CoachDAO;
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
public class CoachServlet extends HttpServlet {
   
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
                switch (function) {
                    case "new":{
                        String fullname = request.getParameter("fullname").trim();
                        String email = request.getParameter("email").trim();
                        String phone = request.getParameter("phone").trim();
                        String address = request.getParameter("address").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        String query_check = "select * from "+AppData.COACHES+" where vFullName = '"+fullname+"' and vEmail = '"+email+"'";
                        int check = CheckDAO.check(query_check, conn_check);
                        if(check == 0){
                            request.setAttribute("msg", "A coach with this name has already been registered for this team.");
                            getServletContext().getRequestDispatcher("/admin_nafa/coaches_new.jsp?i="+username).forward(request, response);
                        }else{
                            Connection conn_email = Konnect.getNAFAConnection();
                            String query_email = "select * from "+AppData.COACHES+" where vEmail = '"+email+"'";
                            int check_email = CheckDAO.check(query_email, conn_email);
                            if(check_email == 0){
                                request.setAttribute("msg", "This email is not available, as it has been used.");
                                getServletContext().getRequestDispatcher("/admin_nafa/coaches_new.jsp?i="+username).forward(request, response);
                            }else{
                                Connection conn_id = Konnect.getNAFAConnection();
                                String coachno = AutoIdDAO.getNextUniqueAutoIDByCount("cCoachNo", "CH", "6", AppData.COACHES, conn_id);
                                String pass = GeneratePassword.generatePassword(8);
                                String date = DateTime.getCurrentDate();
                                
                                Connection conn_add = null;
                                if(conn_add == null){
                                    conn_add = Konnect.getNAFAConnection();
                                    DataAccessObject dac = new DataAccessObject();
                                    dac.setColumnValues(coachno, fullname, email, phone, pass, address, date);
//                                Generate and insert licenses too
//                                    String query = "";
//                                    String query2 = "";
                                    int add = DataAccessObject.insertSecuredRecord(AppData.COACHES, conn_add);
                                    if(add == 0){
                                        String msg = MessagesData.getNewCoachNotification(fullname, email, pass);
                                        String receipient = email;
                                        String subject = "NAFA Coach Registration Notification";

                                        EmailData.setEmailAttributes(receipient, subject, msg);
                                        String val = SendMail.sendEmailHTML();
                                        if(val.equals("yes")){
                                            request.setAttribute("portal", "1");
                                            request.setAttribute("msg", "Coach user added successfully. A notification has been sent to the admin's email.");
                                            getServletContext().getRequestDispatcher("/admin_nafa/coaches.jsp?i="+username).forward(request, response);
                                        }else{
                                            request.setAttribute("portal", "1");
                                            request.setAttribute("msg", "Coach user added successfully");
                                            getServletContext().getRequestDispatcher("/admin_nafa/coaches.jsp?i="+username).forward(request, response);
                                        }
                                    }else{
                                        request.setAttribute("portal", "1");
                                        request.setAttribute("msg", "Coaches information could not be added at this time.");
                                        getServletContext().getRequestDispatcher("/admin_nafa/coaches_new.jsp?i="+username).forward(request, response);
                                    }
                                }conn_id.close(); conn_add.close();
                            }conn_email.close();
                        }
                        conn_check.close();
                        break;
                    }
                    case "edit":{
                        String coachno = request.getParameter("coachno").trim();
                        String fullname = request.getParameter("fullname").trim();
                        String email = request.getParameter("email").trim();
                        String phone = request.getParameter("phone").trim();
                        String address = request.getParameter("address").trim();
                        
                        Connection conn_edit = null;
                        if(conn_edit == null){
                            conn_edit = Konnect.getNAFAConnection();
                            DataAccessObject dac = new DataAccessObject();
                            dac.setColumnNames("vFullName","vEmail","cPhone","vAddress");
                            dac.setColumnValues(fullname, email, phone, address);
                            int edit = DataAccessObject.updateSecuredRecord("cCoachNo", coachno, AppData.COACHES, conn_edit);
                            if(edit == 0){
                                String pass = CoachDAO.getCoachPassword(coachno); 
                                String msg = MessagesData.getNewCoachNotification(fullname, email, pass);
                                String receipient = email;
                                String subject = "NAFA Coach Registration Notification";

                                EmailData.setEmailAttributes(receipient, subject, msg);
                                String val = SendMail.sendEmailHTML();
                                if(val.equals("yes")){
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Changes saved successfully. Notification has been sent to the email submitted.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/coaches.jsp?i="+username).forward(request, response);
                                }else{
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Changes saved successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/coaches.jsp?i="+username).forward(request, response);
                                }
                            }else{
                               request.setAttribute("portal", "1");
                               request.setAttribute("msg", "Changes could not be saved successfully. kindly ensure the correct info is submitted.");
                               getServletContext().getRequestDispatcher("/admin_nafa/coaches.jsp?i="+username).forward(request, response);
                            }
                        }
                        conn_edit.close();
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
            Logger.getLogger(CoachServlet.class.getName()).log(Level.SEVERE, null, ex);
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
