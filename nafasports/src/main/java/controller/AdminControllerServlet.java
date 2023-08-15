/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josiah
 */
public class AdminControllerServlet extends HttpServlet {
   
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
               case "Admin-Login":{
                    getServletContext().getRequestDispatcher("/AdminLoginServlet").forward(request, response);
                    break;
                }
               case "addAdmin_New":{
                    getServletContext().getRequestDispatcher("/AdminUserServlet").forward(request, response);
                    break;
                }
               case "add_new_profile":{
                    getServletContext().getRequestDispatcher("/AdminProfileServlet").forward(request, response);
                    break;
                }
               case "assign_admin_role":{
                    getServletContext().getRequestDispatcher("/AdminRoleAssignementServlet").forward(request, response);
                    break;
                }
               case "revoke_admin_role":{
                    getServletContext().getRequestDispatcher("/AdminRoleRevokeServlet").forward(request, response);
                    break;
                }
               case "add_new_team":{
                    getServletContext().getRequestDispatcher("/AdminNewTeamServlet").forward(request, response);
                    break;
                }
               case "add_new_coach":{
                    getServletContext().getRequestDispatcher("/CoachServlet").forward(request, response);
                    break;
                }
               case "add_new_player":{
                    getServletContext().getRequestDispatcher("/PlayersTeamNewServlet").forward(request, response);
                    break;
                }
               case "and_new_group":{
                    getServletContext().getRequestDispatcher("/AdminGroupServlet").forward(request, response);
                    break;
                }
                case "assign_team":{
                    getServletContext().getRequestDispatcher("/AssignTeanToGroupServlet").forward(request, response);
                    break;
                }
                case "assign_coach":{
                    getServletContext().getRequestDispatcher("/AssignCoachToTeamServlet").forward(request, response);
                    break;
                }
                case "add_Game_fixtures":{
                    getServletContext().getRequestDispatcher("/GamesFixturesServlet").forward(request, response);
                    break;
                }
                case "add_lineups":{
                    getServletContext().getRequestDispatcher("/AddLineupPlayerServlet").forward(request, response);
                    break;
                }
                case "add_new_player_pool":{
                    getServletContext().getRequestDispatcher("/PlayersPoolNewServlet").forward(request, response);
                    break;
                }
                case "add_Game_playoff_fixtures":{
                    getServletContext().getRequestDispatcher("/GamesPlayoffsFixturesServlet").forward(request, response);
                    break;
                }
                case "add_Game_fixtures_result":{
                    getServletContext().getRequestDispatcher("/GamesResultServlet").forward(request, response);
                    break;
                }
                case "add_Game_playoff_fixtures_result":{
                    getServletContext().getRequestDispatcher("/GamesPlayOffsFixtureResultServlet").forward(request, response);
                    break;
                }
                case "add_scorer":{
                    getServletContext().getRequestDispatcher("/GamesResultScorerServlet").forward(request, response);
                    break;
                }
                case "add_stats":{
                    getServletContext().getRequestDispatcher("/GameStatisticsServlet").forward(request, response);
                    break;
                }
                case "update_standing":{
                    getServletContext().getRequestDispatcher("/GroupStandingUpdateServlet").forward(request, response);
                    break;
                }
                case "LeagueNew":{
                    getServletContext().getRequestDispatcher("/AdminLeagueNewServlet").forward(request, response);
                    break;
                }
                case "league_division":{
                    getServletContext().getRequestDispatcher("/AdminLeagueDivisionServlet").forward(request, response);
                    break;
                }
                case "district_new":{
                    getServletContext().getRequestDispatcher("/AdminStateDistrictServlet").forward(request, response);
                    break;
                }
                case "teamEmble_ms":{
                    getServletContext().getRequestDispatcher("/TeamEmblemServlet").forward(request, response);
                    break;
                }
                case "GAmes_Official":{
                    getServletContext().getRequestDispatcher("/GamesOfficialsServlet").forward(request, response);
                    break;
                }
                case "NewsPostManager":{
                    getServletContext().getRequestDispatcher("/NewsPostManagerServlet").forward(request, response);
                    break;
                }
                case "NewsManage":{
                    getServletContext().getRequestDispatcher("/NewsPostRemovalServlet").forward(request, response);
                    break;
                }
                case "postVideo":{
                    getServletContext().getRequestDispatcher("/NewsVideoPostManagerServlet").forward(request, response);
                    break;
                }
                case "post_commentaries":{
                    getServletContext().getRequestDispatcher("/GamesCommentaryServlet").forward(request, response);
                    break;
                }
                case "approveCandidate":{
                    getServletContext().getRequestDispatcher("/CampCandidateApprovalServlet").forward(request, response);
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
