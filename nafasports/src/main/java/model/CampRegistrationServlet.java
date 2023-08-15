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
import daos.CountryDAO;
import daos.StateDAO;
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

/**
 *
 * @author josiah
 */
public class CampRegistrationServlet extends HttpServlet {
   
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
            switch (function) {
                case "new":{
                    String email = request.getParameter("email").trim();
                    String fullname = request.getParameter("fullname").trim();
                    String phone = request.getParameter("phone").trim();
                    String countryid_origin = request.getParameter("countryid").trim();
                    String country_residence = request.getParameter("country_residence").trim();
                    String stateid = request.getParameter("stateid").trim();
                    String state_resid = request.getParameter("state_resid").trim();
                    String lgaid = request.getParameter("lgaid").trim();
                    String gender = request.getParameter("gender").trim();
                    String height = request.getParameter("height").trim();
                    String weight = request.getParameter("weight").trim();
                    String religion = request.getParameter("religion").trim();
                    String tattoo = request.getParameter("tattoo").trim();
                    String nafa_member = request.getParameter("nafa-member").trim(); 
                    String education = request.getParameter("education").trim();
                    String occupation = request.getParameter("occupation").trim();
                    String otherSport = request.getParameter("otherSport").trim();
                    String currentSport = request.getParameter("currentSport").trim();
                    String position = request.getParameter("position").trim();
                    String team = request.getParameter("team").trim();
                    String sponsor = request.getParameter("sponsor").trim();
                    String sponsor_occupation = request.getParameter("sponsor_occupation").trim();
                    String sponsor_relationship = request.getParameter("sponsor_relationship").trim();
                    String sponsor_member = request.getParameter("sponsor_member").trim();
                    String finance = request.getParameter("finance").trim();
                    String remark = request.getParameter("remark").trim();
                    
                    if(countryid_origin.equals("specify")){
                        String country_new = request.getParameter("countryid_new").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        int check = CheckDAO.checkSecured("vCountry", country_new, AppData.COUNTRY, conn_check);
                        if(check == 0){
                            countryid_origin = country_new;
                        }else{
                            Connection conn = Konnect.getNAFAConnection();
                            DataAccessObject dao = new DataAccessObject();
                            dao.setColumnNames("vCountry");
                            dao.setColumnValues(country_new);
                            int add_lga = DataAccessObject.insertSecuredSelectedRecord(AppData.COUNTRY, conn);
                            countryid_origin = CountryDAO.getCountryID(country_new);
                            conn.close();
                        }
                        conn_check.close();
                    }
                    if(country_residence.equals("specify")){
                        String country_new = request.getParameter("country_residence_new").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        int check = CheckDAO.checkSecured("vCountry", country_new, AppData.COUNTRY, conn_check);
                        if(check == 0){
                            country_residence = country_new;
                        }else{
                            Connection conn = Konnect.getNAFAConnection();
                            DataAccessObject dao = new DataAccessObject();
                            dao.setColumnNames("vCountry");
                            dao.setColumnValues(country_new);
                            int add_lga = DataAccessObject.insertSecuredSelectedRecord(AppData.COUNTRY, conn);
                            country_residence = CountryDAO.getCountryID(country_new);
                            conn.close();
                        }
                        conn_check.close();
                    }
                    if(state_resid.equals("specify")){
                        String state_new = request.getParameter("state_resid_new").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        int check = CheckDAO.checkSecured("vState", state_new, AppData.STATES, conn_check);
                        if(check == 0){
                            state_resid = state_new;
                        }else{
                            Connection connect = Konnect.getNAFAConnection();
                            String statecode = AutoIdDAO.getNextUniqueAutoIDByCount("cStateCode", "S", "3", AppData.STATES, connect);

                            Connection conn = Konnect.getNAFAConnection();
                            DataAccessObject dao = new DataAccessObject();
                            dao.setColumnNames("cStateCode","vState","iCountryID");
                            dao.setColumnValues(statecode, state_new, country_residence);
                            int add_lga = DataAccessObject.insertSecuredSelectedRecord(AppData.STATES, conn);
                            state_resid = CountryDAO.getCountryID(state_new);
                            conn.close();
                            connect.close();
                        }
                        conn_check.close();
                    }
                    if(stateid.equals("specify")){
                        String stateid_new = request.getParameter("stateid_new").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        int check = CheckDAO.checkSecured("vState", stateid_new, AppData.STATES, conn_check);
                        if(check == 0){
                            state_resid = stateid_new;
                        }else{
                            Connection connect = Konnect.getNAFAConnection();
                            String statecode = AutoIdDAO.getNextUniqueAutoIDByCount("cStateCode", "S", "3", AppData.STATES, connect);

                            Connection conn = Konnect.getNAFAConnection();
                            DataAccessObject dao = new DataAccessObject();
                            dao.setColumnNames("cStateCode","vState","iCountryID");
                            dao.setColumnValues(statecode ,stateid_new, countryid_origin);
                            int add_lga = DataAccessObject.insertSecuredSelectedRecord(AppData.STATES, conn);
                            stateid = StateDAO.getLgaID(stateid_new);
                            conn.close();
                            connect.close();
                        }
                        conn_check.close();
                    }
                    if(lgaid.equals("specify")){
                        String lgaidnew = request.getParameter("lgaid_new").trim();
                        
                        Connection conn_check = Konnect.getNAFAConnection();
                        int check = CheckDAO.checkSecured("vLocalGovtArea", lgaidnew, AppData.LGAS, conn_check);
                        if(check == 0){
                            state_resid = lgaidnew;
                        }else{
                            Connection conn = Konnect.getNAFAConnection();
                            DataAccessObject dao = new DataAccessObject();
                            dao.setColumnNames("vLocalGovtArea","cStateCode");
                            dao.setColumnValues(lgaidnew, stateid);
                            int add_lga = DataAccessObject.insertSecuredSelectedRecord(AppData.LGAS, conn);
                            lgaid = StateDAO.getLgaID(lgaidnew);
                            conn.close();
                        }
                        conn_check.close();
                    }
                    
                    Connection konn = Konnect.getNAFAConnection();
                    Connection konn1 = Konnect.getNAFAConnection();
                    
                    
                    int check = CheckDAO.checkSecured("vEmail", email, AppData.CAMPS, konn);
                    if(check == 0){
                        request.setAttribute("msg", "This email has been registered already. Thank you.");
                        getServletContext().getRequestDispatcher("/camp_registration.jsp").forward(request, response);
                    }else{
                        DataAccessObject dac = new DataAccessObject();
                        dac.setColumnValues(email, fullname, phone, countryid_origin, country_residence, stateid, lgaid, state_resid, gender, height, weight, religion, tattoo, nafa_member, education, occupation, otherSport, currentSport, position, team, sponsor, sponsor_occupation, sponsor_relationship, sponsor_member, finance, remark, "tobe Procesed");
                        int add = DataAccessObject.insertSecuredRecord(AppData.CAMPS, konn1);
                        if(add == 0){
                            request.setAttribute("msg", "Registration has been submitted succesfully. Upon successful review, you will be contacted. Thank you.");
                            getServletContext().getRequestDispatcher("/camp_registration.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "We could not submit you registration at this time. kindly confirm your entry and internet connection to try again or contact Support on: +234 810 203 5882");
                            getServletContext().getRequestDispatcher("/camp_registration.jsp").forward(request, response);
                        }
                        konn.close();
                        konn1.close();
                    }
                    
                    break;
                }
                case "edit":{

                    break;
                }
                case "":{

                    break;
                }

                default:{
                    getServletContext().getRequestDispatcher("/").forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CampRegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
