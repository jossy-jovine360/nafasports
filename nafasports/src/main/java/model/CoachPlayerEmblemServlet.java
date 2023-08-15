/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.io.Files;
import daos.AdminDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author josiah
 */
public class CoachPlayerEmblemServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String[] value = new String[50];           
            InputStream is = null;
            int counter = 0;
            File file = null;
            String location = "";
            String image_field = "";
            String image_flag = "";
            String type = "";
            FileItem fileItem = null;

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                out.print("Not a multipart request");
                request.setAttribute("portal", "1");
                request.setAttribute("msg", "Not a multipart request. Thanks");
                getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward( request,  response);
            } else {
                DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload((FileItemFactory) diskFileItemFactory);
//                Long filesize = upload.getFileSizeMax();
                String flag = "";

                List items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                LinkedHashMap<Object, Object> link = new LinkedHashMap<>();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        value[counter] = item.getString();
                        link.put(name, value[counter]);
                        flag = "yes";
                    } else {
                        image_field = item.getFieldName();
                        location = item.getName();
                        type = item.getContentType();
                        switch (type) {
                            case "image/jpeg":
                            case "image/jpg":
                            case "image/png":
                                break;
                            default:
                                flag = "";
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Image Type must be either jpeg/jpg/png. Thanks");
                                getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward( request,  response);
                                break;
                        }
                        if(!flag.equals("")){
                            Long sizes = item.getSize();
                            if (sizes > 1024000) {
                                flag = "";
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Image size must not be greater than 1MB. Image Size[" + sizes + "] Thanks");
                                getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward( request,  response);
                            } else {
                                if (location.equals("")) {
                                    image_flag = "";
                                    flag = "no";
                                    continue;
                                }else{
                                    fileItem = item;

                                    link.put(image_field, location);
//                                        link.put("StockID", stockid);
                                    image_flag = "1";
                                    flag = "yes";
//                                        conn1.close();
                                }
                            }
                        }                         
                    }
                    counter++;
                }
                if(flag.equals("yes")){
                    String playerid = link.get("playerid").toString();
                    String emblem = link.get("emblem").toString();
                    
                    if(image_flag.equals("")){
                        request.setAttribute("msg", "This team emblem was not uploaded properly. kindly try again or contact support");
                        request.setAttribute("portal", "1");
                        getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward(request, response);
                    }else{
                        if(fileItem == null){
                            request.setAttribute("msg", "I couldn't commit the team emblem. kindly notify support.");
                            request.setAttribute("portal", "1");
                            getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward(request, response);
                        }else{
                            String path = getServletContext().getRealPath("/admin_nafa/");  // for online use

                            String folder = path +"images/players/";
                            Files createFolder = new Files();
                            createFolder.createFolder(folder);

                            String directory = "images/players//";
                            String complete_path = path + directory; 

                            file = new File(complete_path, location);                            
                            fileItem.write(file);
                            
                            Connection conn4 = Konnect.getNAFAConnection();
                            DataAccessObject obj = null;
                            obj = new DataAccessObject();
                            obj.setColumnNames("vImageName");
                            obj.setColumnValues(emblem);
                            int upd = DataAccessObject.updateSecuredRecord("cPlayerID", playerid, AppData.PLAYERS, conn4);
                            if(upd == 0){
                                request.setAttribute("msg", "Player profile uploaded successfully.");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("msg", "Player profile could not be uploaded at this moment.");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/coaches/players_profile.jsp?i="+username).forward(request, response);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CoachTeamEmblemServlet.class.getName()).log(Level.SEVERE, null, ex);
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
