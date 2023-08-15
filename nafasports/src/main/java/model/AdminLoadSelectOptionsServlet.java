/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import daos.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
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
public class AdminLoadSelectOptionsServlet extends HttpServlet {
   
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
            String action = request.getParameter("action").trim();
            switch (action) {
                case "getLeagueGames":{
                    String leagueid = request.getParameter("leagueid").trim();
                    Connection conn_lga = Konnect.getNAFAConnection();
                    String query = "select * from "+ AppData.GROUP_TEAMS +" where cGroupID IN(select cGroupID from "+AppData.GROUPS+" where cLeagueID = '"+leagueid+"')";
                    LinkedHashMap link1 = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn_lga);
                    if (link1.isEmpty()) {
                        out.println("<option value=''>No team has been registered</option>");
//                        out.println("<option value='specify'>specify your lga</option>");
                    } else {
                        out.println("<option value=''>Select team</option>");
//                        out.println("<option value='specify'>specify your lga</option>");
                        Set keys = link1.keySet();
                        Iterator itr = keys.iterator();
                        for (int i = 0; i < keys.size(); i++) {
                            String id = itr.next().toString();
                            String name = link1.get(id).toString();
                            if (!id.equals("0")) {
                                out.println("<option value = '" + id + "'>" + name + "</option>");
                            }
                        }
                    }
                    conn_lga.close();
                    out.println("$");
                    
                    break;
                }
                
                case "":{
                    break;
                }
                default:
                    throw new AssertionError();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminLoadSelectOptionsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
