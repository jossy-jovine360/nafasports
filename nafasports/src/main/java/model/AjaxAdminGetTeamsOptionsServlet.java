/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import daos.AdminDAO;
import daos.DistrictDAO;
import daos.GameDAO;
import daos.GroupDAO;
import daos.PlayerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josiah
 */
public class AjaxAdminGetTeamsOptionsServlet extends HttpServlet {
   
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
            String function = request.getParameter("action").trim();
                switch (function) {
                    case "getTeamsFromGroup":{
                        String groupid = request.getParameter("groupid").trim();
                        String teamid = request.getParameter("teamid").trim();
                        String flag = request.getParameter("flag").trim();
                        
                        LinkedHashMap link = GroupDAO.loadAllTeamsAssignedToGroups(groupid);
                        if(link == null || link.isEmpty()){
                                out.println("<option value=''>You have no registered teams</option>");
                        }else{
                            if(flag.equals("home")){
                                out.println("<option value=''>Select Home Teams</option>");
                            }else{
                                out.println("<option value=''>Select Away Teams</option>");
                            }
                            Set keys = link.keySet();
                            Iterator itr = keys.iterator();
                            for(int i = 0; i < keys.size(); i++){
                                String id = itr.next().toString();
                                String names = link.get(id).toString();
                                out.println("<option value = '"+ id +"'>"+ names +"</option>");  
                            }
                        }
                        out.print("$");
                        break;
                    }
                    case "getTeamsPlayers":{
                        String teamid = request.getParameter("teamid").trim();
                        LinkedHashMap link = PlayerDAO.loadAllTeamPlayers(teamid); 
                        if(link == null || link.isEmpty()){
                                out.println("<option value=''>You have no registered player</option>");
                        }else{
                            out.println("<option value=''>Select Player</option>");
                            Set keys = link.keySet();
                            Iterator itr = keys.iterator();
                            for(int i = 0; i < keys.size(); i++){
                                String id = itr.next().toString();
                                String names = link.get(id).toString();
                                out.println("<option value = '"+ id +"'>"+ names +"</option>");  
                            }
                        }
                        out.print("$");
                        
                        break;
                    }
                    case "getGameTeams":{
                        String gameid = request.getParameter("gameid").trim();
                        LinkedHashMap link = GameDAO.loadAllGameTeams(gameid);
                        if(link == null || link.isEmpty()){
                                out.println("<option value=''>You have no registered teams</option>");
                        }else{
                            out.println("<option value=''>Select Team</option>");
                            Set keys = link.keySet();
                            Iterator itr = keys.iterator();
                            for(int i = 0; i < keys.size(); i++){
                                String id = itr.next().toString();
                                String names = link.get(id).toString();
                                out.println("<option value = '"+ id +"'>"+ names +"</option>");  
                            }
                        }
                        out.print("$");
                        break;
                    }
                    case "getTeamsDistrict":{
                        String stateid = request.getParameter("stateid").trim();
                        LinkedHashMap link = DistrictDAO.loadAllDistricts(stateid);
                        if(link == null || link.isEmpty()){
                            out.println("<option value='all'>No registered district</option>");
                        }else{
                            out.println("<option value='all'>Select District</option>");
                            Set keys = link.keySet();
                            Iterator itr = keys.iterator();
                            for(int i = 0; i < keys.size(); i++){
                                String id = itr.next().toString();
                                String names = link.get(id).toString();
                                out.println("<option value = '"+ id +"'>"+ names +"</option>");  
                            }
                        }
                        out.print("$");
                        break;
                    }
                        
                    default:
                        throw new AssertionError();
                }
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
