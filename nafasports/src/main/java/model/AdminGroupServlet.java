/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.id.AutoIdDAO;
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
public class AdminGroupServlet extends HttpServlet {
   
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
                        String stateid = request.getParameter("stateid").trim();
                        String districtid = request.getParameter("districtid").trim();
                        String group = request.getParameter("group").trim();
                        
                        Connection con = Konnect.getNAFAConnection();
                        String query = "select * from "+AppData.GROUPS+" where cLeagueID = '"+leagueid+"' and cStateID = '"+stateid+"' and iDistrict = '"+districtid+"' and cGroup = '"+group+"'";
                        int check_group = CheckDAO.check(query, con);
                        if(check_group == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "The group has been created already. Click the add button to create a new group.");
                            getServletContext().getRequestDispatcher("/admin_nafa/groups.jsp?i="+username).forward(request, response);
                        }else{
                            Connection con1 = null;
                            if(con1 == null){
                                con1 = Konnect.getNAFAConnection();
                                Connection con2 = Konnect.getNAFAConnection();
                                String groupid = AutoIdDAO.getNextUniqueAutoID("cGroupID", "GP", "5", AppData.GROUPS, con2);
                                DataAccessObject dac = new DataAccessObject();
                                dac.setColumnNames("cGroupID","cLeagueID","cStateID","iDistrict","cGroup");
                                dac.setColumnValues(groupid, leagueid, stateid, districtid, group);
                                int add = DataAccessObject.insertSecuredRecord(AppData.GROUPS, con1);
                                if(add == 0){
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "League Group created successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/groups.jsp?i="+username).forward(request, response);
                                }else{
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "League Group could not be created successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/groups_new.jsp?i="+username).forward(request, response);
                                }
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Kindly refresh your page and try again.");
                                getServletContext().getRequestDispatcher("/admin_nafa/groups_new.jsp?i="+username).forward(request, response);
                            }
                        }
                        con.close();
                        break;
                    }
                    case "edit":{
                        String leagueid = request.getParameter("leagueid").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String districtid = request.getParameter("districtid").trim();
                        String group = request.getParameter("group").trim();
                        String groupid = request.getParameter("groupid").trim();
                        
                        Connection con3 = null;
                        if(con3 == null){
                            con3 = Konnect.getNAFAConnection();
                            DataAccessObject dac = new DataAccessObject();
                            dac.setColumnNames("cLeagueID","cStateID","iDistrict","cGroup");
                            dac.setColumnValues(leagueid, stateid, districtid, group);
                            int edit = DataAccessObject.updateSecuredRecord("cGroupID", groupid, AppData.GROUPS, con3);
                            if(edit == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Group info updated successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/groups_new.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Group info changes could not be made at this point. Try again or contact support.");
                                getServletContext().getRequestDispatcher("/admin_nafa/groups_new.jsp?i="+username).forward(request, response);
                            }
                        }else{
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "Kindly refresh your page and try again.");
                            getServletContext().getRequestDispatcher("/admin_nafa/groups_new.jsp?i="+username).forward(request, response);
                        }
                        
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
            Logger.getLogger(AdminGroupServlet.class.getName()).log(Level.SEVERE, null, ex);
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
