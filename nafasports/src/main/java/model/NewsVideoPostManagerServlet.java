/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
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
public class NewsVideoPostManagerServlet extends HttpServlet {
   
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
                        String newsid = request.getParameter("newsid").trim();
                        String videos = request.getParameter("videos").trim();
                        String datetime = DateTime.getCurrentDateTime();
                        
                        Connection conn = Konnect.getNAFAConnection();
                        DataAccessObject obj = null;
                        obj = new DataAccessObject();
                        obj.setColumnNames("iNewsID","vYTVideo","dtDate");
                        obj.setColumnValues(newsid, videos, datetime);
                        int add = DataAccessObject.insertSecuredSelectedRecord(AppData.NEWS_VIDEOS, conn);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "YouTube video added successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/news_videos.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "YouTube video could not be added at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/news_videos.jsp?username="+username).forward(request, response);
                        }conn.close();
                        
                        break;
                    }
                    case "edit":{
                        String videoid = request.getParameter("videoid").trim();
                        String newsid = request.getParameter("newsid").trim();
                        String videos = request.getParameter("videos").trim();
                        
                        Connection conn = Konnect.getNAFAConnection();
                        DataAccessObject obj = null;
                        obj = new DataAccessObject();
                        obj.setColumnNames("iNewsID","vYTVideo");
                        obj.setColumnValues(newsid, videos);
                        int upd = DataAccessObject.updateSecuredRecord("iVideoID", videoid, AppData.NEWS_VIDEOS, conn);
                        if(upd == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "YouTube video edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/videos.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "YouTube video could not be edited at this moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/news_videos.jsp?username="+username).forward(request, response);
                        }conn.close();
                        break;
                    }
                    case "delete":{
                        String videoid = request.getParameter("videoid").trim();
                        
                        Connection konn = Konnect.getNAFAConnection();
                        int del = DataAccessObject.deleteRecord(AppData.NEWS_VIDEOS, konn, "iVideoID", videoid);
                        if(del == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "News deleted successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/videos.jsp?username="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "News could not be deleted at the moment.");
                            getServletContext().getRequestDispatcher("/admin_nafa/news_videos.jsp?username="+username).forward(request, response);
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
            Logger.getLogger(NewsVideoPostManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
