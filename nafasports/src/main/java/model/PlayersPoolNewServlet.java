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
public class PlayersPoolNewServlet extends HttpServlet {
   
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
                        String teamid = request.getParameter("teamid").trim();
                        String fullname = request.getParameter("fullname").trim();
                        String email = request.getParameter("email").trim();
                        String phone = request.getParameter("phone").trim();
                        String gender = request.getParameter("gender").trim();
                        String dob = request.getParameter("dob").trim();
                        String address = request.getParameter("address").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String grade = request.getParameter("grade").trim();
                        String position = request.getParameter("position").trim();
                        String height = request.getParameter("height").trim();
                        String weight = request.getParameter("weight").trim();
                        String special = request.getParameter("special").trim();
                        String remark = request.getParameter("remark").trim();
                        String date = DateTime.getCurrentDate();
                        
                        Connection conn_check_player = Konnect.getNAFAConnection();
                        String query_player = "select * from "+AppData.PLAYERS+" where vFullName = '"+fullname+"' and vEmail = '"+email+"' and cPhone = '"+phone+"'";
                        int check_player = CheckDAO.check(query_player, conn_check_player);
                        if(check_player == 0){
                            request.setAttribute("portal", "1");
                            request.setAttribute("msg", "A Player with this credentials has already been registered to a team.");
                            getServletContext().getRequestDispatcher("/admin_nafa/players_new.jsp?username="+username).forward(request, response);
                        }else{
                            Connection conn_add = null;
                            if(conn_add == null){
                                Connection conn_id = Konnect.getNAFAConnection();
                                conn_add = Konnect.getNAFAConnection();
                                String playerid = AutoIdDAO.getNextUniqueAutoIDByCount("cPlayerID", "PLY", "8", AppData.PLAYERS, conn_id);
                                DataAccessObject dac = new DataAccessObject();
                                dac.setColumnNames("cPlayerID","vFullName","vEmail","cPhone","cGender","dDOB","vAddress","cState","vClass","vPosition","iHeight","iWeight","cSpecial","vAdditionalInfo","dDate","vImageName");
                                dac.setColumnValues(playerid, fullname, email, phone, gender, dob, address, stateid, grade, position, height, weight, special, remark, date,"player_profile.png");
                                int add = DataAccessObject.insertSecuredRecord(AppData.PLAYERS, conn_add);
                                if(add == 0){
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Player has been registered successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/players_pool.jsp?username="+username).forward(request, response);
                                }else{
                                    request.setAttribute("portal", "1");
                                    request.setAttribute("msg", "Player could not be registered successfully.");
                                    getServletContext().getRequestDispatcher("/admin_nafa/players_pool_new.jsp?username="+username).forward(request, response);
                                }
                                conn_id.close();
                                conn_add.close();
                            }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Refresh your page and try again");
                                getServletContext().getRequestDispatcher("/admin_nafa/players_pool_new.jsp?username="+username).forward(request, response);
                            }
                        }
                        conn_check_player.close();
                        break;
                    }
                    case "edit":{
                        String playerid = request.getParameter("playerid").trim();
                        String fullname = request.getParameter("fullname").trim();
                        String email = request.getParameter("email").trim();
                        String phone = request.getParameter("phone").trim();
                        String gender = request.getParameter("gender").trim();
                        String dob = request.getParameter("dob").trim();
                        String address = request.getParameter("address").trim();
                        String stateid = request.getParameter("stateid").trim();
                        String grade = request.getParameter("grade").trim();
                        String position = request.getParameter("position").trim();
                        String height = request.getParameter("height").trim();
                        String weight = request.getParameter("weight").trim();
                        String special = request.getParameter("special").trim();
                        String remark = request.getParameter("remark").trim();
                        
                        Connection conn_edit = null;
                        if(conn_edit == null){
                            conn_edit = Konnect.getNAFAConnection();
                             DataAccessObject dac = new DataAccessObject();
                             dac.setColumnNames("vFullName","vEmail","cPhone","cGender","dDOB","vAddress","cState","vClass","vPosition","iHeight","iWeight","cSpecial","vAdditionalInfo");
                             dac.setColumnValues(fullname, email, phone, gender, dob, address, stateid, grade, position, height, weight, special, remark);
                             int edit = DataAccessObject.updateSecuredRecord("cPlayerID", playerid, AppData.PLAYERS, conn_edit);
                             if(edit == 0){
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Player's Changes saved successfully.");
                                getServletContext().getRequestDispatcher("/admin_nafa/players_pool.jsp?i="+username).forward(request, response);
                             }else{
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Player's Changes coauld not be saved successfully. kindly ensure the correct info is submitted.");
                                getServletContext().getRequestDispatcher("/admin_nafa/players_pool_new.jsp?i="+username).forward(request, response);
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
            Logger.getLogger(PlayersPoolNewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
