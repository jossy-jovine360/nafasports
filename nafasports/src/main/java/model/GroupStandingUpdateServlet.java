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
public class GroupStandingUpdateServlet extends HttpServlet {
   
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
                        String leagueid = request.getParameter("leagueid").trim();
                        String groupteamid = request.getParameter("groupteamid").trim();
                        String gp = request.getParameter("gp").trim();
                        String gw = request.getParameter("gw").trim();
                        String gd = request.getParameter("gd").trim();
                        String gl = request.getParameter("gl").trim();
                        String gf = request.getParameter("gf").trim();
                        String ga = request.getParameter("ga").trim();
                        String gdf = "";
                        String pt = request.getParameter("pts").trim();
                        
                        String query = "insert into "+AppData.GROUP_STANDINGS+"(cLeagueID,iGroupTeamID,iGP,iGW,iGD,iGL,iGF,iGA,iGDF,iPT) "
                                    + "values('"+leagueid+"','"+groupteamid+"','"+gp+"','"+gw+"','"+gd+"','"+gl+"','"+gf+"','"+ga+"','"+gdf+"','"+pt+"')";
                        Connection conn = Konnect.getNAFAConnection();
                        DataAccessObject dac = new DataAccessObject();
                        dac.setQuerys(query);
                        int add = DataAccessObject.batchRecord(conn);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Group standing updated successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/groups_standings.jsp?i="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Group standing could not be updated at this point.");
                            getServletContext().getRequestDispatcher("/admin_nafa/group_standings_update.jsp?i="+username).forward(request, response);
                        }
                        conn.close();
                        break;
                    }
                    case "edit":{
                        String leagueid = request.getParameter("leagueid").trim();
                        String groupteamid = request.getParameter("groupteamid").trim();
                        String gp = request.getParameter("gp").trim();
                        String gw = request.getParameter("gw").trim();
                        String gd = request.getParameter("gd").trim();
                        String gl = request.getParameter("gl").trim();
                        String gf = request.getParameter("gf").trim();
                        String ga = request.getParameter("ga").trim();
                        String gdf = "";
                        String pt = request.getParameter("pts").trim();
                        
//                        String goalforward = GroupStandingDAO.getGroupStandingDataByGroupTeamID(groupteamid, "iGF");
//                        String goal_accepted = GroupStandingDAO.getGroupStandingDataByGroupTeamID(groupteamid, "iGA");
                        
                        int glf = 0;
                        int gla = 0;
                        int gdff = 0;
//                        if(goalforward != null && !goalforward.equals("")){
//                            glf = Integer.parseInt(goalforward) + Integer.parseInt(gf);
//                        }else{
//                            glf = Integer.parseInt(gf);
//                        }
//                        if(goal_accepted != null && !goal_accepted.equals("")){
//                            gla = Integer.parseInt(goal_accepted) + Integer.parseInt(ga);
//                        }else{
//                            gla = Integer.parseInt(ga);
//                        }
                        glf = Integer.parseInt(gf);
                        gla = Integer.parseInt(ga);
                        gdff = glf - gla;
                        gdf = String.valueOf(gdff);
                        
                        Connection check = Konnect.getNAFAConnection();
                        String query = "";
                        String query_check = "select * from "+AppData.GROUP_STANDINGS+" where cLeagueID = '"+leagueid+"' and iGroupTeamID = '"+groupteamid+"'";
                        int chk = CheckDAO.check(query_check, check);
                        if(chk == 0){
                            query = "update "+AppData.GROUP_STANDINGS+" set iGP = '"+gp+"', iGW = '"+gw+"', iGD = '"+gd+"', iGL = '"+gl+"', iGF = '"+gf+"', iGA = '"+ga+"', iGDF = '"+gdf+"', iPT = '"+pt+"' "
                                    + "where cLeagueID = '"+leagueid+"' and iGroupTeamID = '"+groupteamid+"'";
                        }else{
                            query = "insert into "+AppData.GROUP_STANDINGS+"(cLeagueID,iGroupTeamID,iGP,iGW,iGD,iGL,iGF,iGA,iGDF,iPT) "
                                    + "values('"+leagueid+"','"+groupteamid+"','"+gp+"','"+gw+"','"+gd+"','"+gl+"','"+gf+"','"+ga+"','"+gdf+"','"+pt+"')";
                        }
                        Connection conn = Konnect.getNAFAConnection();
                        DataAccessObject dac = new DataAccessObject();
                        dac.setQuerys(query);
                        int add = DataAccessObject.batchRecord(conn);
                        if(add == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Group standing edited successfully.");
                            getServletContext().getRequestDispatcher("/admin_nafa/groups_standings.jsp?i="+username).forward(request, response);
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Group standing could not be uploaded at this point.");
                            getServletContext().getRequestDispatcher("/admin_nafa/group_standings_update.jsp?i="+username).forward(request, response);
                        }
                        check.close();
                        conn.close();
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
            Logger.getLogger(GroupStandingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
