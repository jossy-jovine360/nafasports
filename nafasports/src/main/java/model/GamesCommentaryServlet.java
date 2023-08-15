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
public class GamesCommentaryServlet extends HttpServlet {
   
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
                        String gameid = request.getParameter("gameid").trim();
                        String playoffid = request.getParameter("playoffid").trim();
                        String comment = request.getParameter("comment").trim();
                        String time = request.getParameter("time").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        String query_check = "select * from "+AppData.GAMES_COMMENTARY+" where (iGameID = '"+gameid+"' or iPlayOffID = '"+playoffid+"') and "
                                + "vComment = '"+comment+"' and tTime = '"+time+"'";
                        int check = CheckDAO.check(query_check, conn_check);
                        if(check == 0){
                            request.setAttribute("portal", "1");
                                request.setAttribute("msg", "This comment has already been uploaded. If you refreshed the page kindly ignore message and continue.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_commentary.jsp?i="+username+"&gameid="+gameid+"&playoffid="+playoffid+"&flag=yes").forward(request, response);
                        }else{
                            Connection conn2 = Konnect.getNAFAConnection();
                            DataAccessObject obj = null;
                            obj = new DataAccessObject();
                            if(playoffid.equals("none")){
                                obj.setColumnNames("iGameID","vComment","tTime");
                                obj.setColumnValues(gameid, comment, time);
                            }else{
                                obj.setColumnNames("iPlayOffID","vComment","tTime");
                                obj.setColumnValues(playoffid, comment, time);
                            }
                            int add = DataAccessObject.insertSecuredSelectedRecord(AppData.GAMES_COMMENTARY, conn2);
                            if(add == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Commentary posted successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_commentary.jsp?i="+username+"&gameid="+gameid+"&playoffid="+playoffid+"&flag=yes").forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Commentary could not be posted at this time.");
                                getServletContext().getRequestDispatcher("/admin_nafa/games_commentary.jsp?i="+username+"&gameid="+gameid+"&playoffid="+playoffid+"&flag=yes").forward(request, response);
                            }
                        }
                        
                        
                        break;
                    }
                    case "edit":{
                        String commentid = request.getParameter("commentid").trim();
                        String gameid = request.getParameter("gameid").trim();
                        String playoffid = request.getParameter("playoffid").trim();
                        String comment = request.getParameter("comment").trim();
                        String time = request.getParameter("time").trim();
                        
                        Connection conn2 = Konnect.getNAFAConnection();
                        DataAccessObject obj = null;
                        obj = new DataAccessObject();
                        if(playoffid.equals("none")){
                            obj.setColumnNames("iGameID","vComment","tTime");
                            obj.setColumnValues(gameid, comment, time);
                        }else{
                            obj.setColumnNames("iPlayOffID","vComment","tTime");
                            obj.setColumnValues(playoffid, comment, time);
                        }
                        int add = DataAccessObject.updateSecuredRecord("iCommentID", commentid,AppData.GAMES_COMMENTARY, conn2);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Commentary edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_commentary.jsp?i="+username+"&gameid="+gameid+"&playoffid="+playoffid+"&flag=yes").forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Commentary could not be edited at this time.");
                            getServletContext().getRequestDispatcher("/admin_nafa/games_commentary.jsp?i="+username+"&gameid="+gameid+"&playoffid="+playoffid+"&flag=yes").forward(request, response);
                        }
                        break;
                    }
                    case "":{
                        
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
            Logger.getLogger(GamesCommentaryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
