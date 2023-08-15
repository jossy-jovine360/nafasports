/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package model;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import com.jovine.date.DateTime;
import daos.NewsDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author josiah
 */
public class NewsPostManagerServlet extends HttpServlet {
   
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
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
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
//            FileItem fileItem = null;
            FileItem fileItem[] = new FileItem[10];
            int count = 0;
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                out.print("Not a multipart request");
                request.setAttribute("portal", "1");
                request.setAttribute("msg", "Not a multipart request. Thanks");
                getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward( request,  response);
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
                                getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward( request,  response);
                                break;
                        }
                        if(!flag.equals("")){
                            Long sizes = item.getSize();
                            if (sizes > 5242880) {
                                flag = "";
                                request.setAttribute("portal", "1");
                                request.setAttribute("msg", "Image size must not be greater than 1MB. Image Size[" + sizes + "] Thanks");
                                getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward( request,  response);
                            } else {
                                if (location.equals("")) {
                                    image_flag = "";
                                    flag = "no";
                                    continue;
                                }else{
                                    fileItem[count] = item;

                                    link.put(image_field, location);
//                                        link.put("StockID", stockid);
                                    image_flag = "1";
                                    flag = "yes";
                                    count++;
//                                        conn1.close();
                                }
                            }
                        }                         
                    }
                    counter++;
                }
                if(flag.equals("yes")){
                    String function = link.get("function").toString();
                    String category = link.get("category").toString();
                    String title = link.get("title").toString();
                    String msgPar1 = link.get("msgPar1").toString();
                    String msgPar2 = link.get("msgPar2").toString();
                    String msgPar3 = link.get("msgPar3").toString();
                    String filename = link.get("newsImage").toString();
                    String imagename = "";
                    String imagenames = "";
                    
                    switch (function) {
                        case "new":{
                            if(image_flag.equals("")){
                                request.setAttribute("msg", "This news image was not uploaded properly. kindly try again or contact support");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward(request, response);
                            }else{
                                if(fileItem == null){
                                    request.setAttribute("msg", "I couldn't commit the news image. kindly notify support.");
                                    request.setAttribute("portal", "1");
                                    getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward(request, response);
                                }else{
                                    String file_name = "";
                                    String file_names = "";
                                    String file_flag = "";
                                    for (int i = 0; i < count; i++) {
                                        imagename = fileItem[i].getName();
                                        imagenames += imagename + "#";
                                        
                                        int val = NewsDAO.checkNewsImage(imagename);
                                        if(val == 0){
                                            file_flag = "yes";
                                            file_name += imagename+", ";
                                        }
                                    }
                                    if(file_flag.equals("yes")){
                                        file_names = file_name.substring(0, file_name.length()-2);
                                        request.setAttribute("msg", "The following file names already exist ["+file_names+"]. Change image names and post again.");
                                        request.setAttribute("portal", "1");
                                        getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward(request, response);
                                    }else{
                                        imagenames = imagenames.substring(0, imagenames.length()-1);
                                        String path = getServletContext().getRealPath("/admin_nafa/");  // for online use
                                        
                                        //                            String folder = path +"images/emblem/";
                                        //                            Files createFolder = new Files();
                                        //                            createFolder.createFolder(folder);
                                        
                                        String directory = "images/news//";
                                        String complete_path = path + directory;
                                        
                                        file = new File(complete_path, location);
                                        //                            fileItem.write(file);
                                        for (int i = 0; i < count; i++) {
                                            imagename = fileItem[i].getName();
                                            file = new File(complete_path, imagename);
                                            fileItem[i].write(file);
                                        }
                                        
                                        String datetime = DateTime.getCurrentDateTime();
                                        Connection conn4 = Konnect.getNAFAConnection();
                                        DataAccessObject obj = null;
                                        obj = new DataAccessObject();
                                        
                                        if(msgPar2.equals("") && msgPar3.equals("")){
                                            obj.setColumnNames("vCategory","vTitle","vMessageParOne","vImage","dtDate");
                                            obj.setColumnValues(category, title, msgPar1, imagenames, datetime);
                                        }else if(msgPar2.equals("")){
                                            obj.setColumnNames("vCategory","vTitle","vMessageParOne","vMessageParThree","vImage","dtDate");
                                            obj.setColumnValues(category, title, msgPar1, msgPar3, imagenames, datetime);
                                        }else if(msgPar3.equals("")){
                                            obj.setColumnNames("vCategory","vTitle","vMessageParOne","vMessageParTwo","vImage","dtDate");
                                            obj.setColumnValues(category, title, msgPar1, msgPar2, imagenames, datetime);
                                        }else{
                                            obj.setColumnNames("vCategory","vTitle","vMessageParOne","vMessageParTwo","vMessageParThree","vImage","dtDate");
                                            obj.setColumnValues(category, title, msgPar1, msgPar2, msgPar3, imagenames, datetime);
                                        }
                                        
                                        int upd = DataAccessObject.insertSecuredSelectedRecord(AppData.NEWS, conn4);
                                        if(upd == 0){
                                            request.setAttribute("msg", "News posted successfully.");
                                            request.setAttribute("portal", "1");
                                            getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                                        }else{
                                            for (int i = 0; i < count; i++) {
                                                String filenames = fileItem[i].getName();
                                                String paths = path+"images/news/"+filenames;
                                                Path url = Paths.get(paths);
                                                Files.deleteIfExists(url);
                                            }
                                            request.setAttribute("msg", "News post could not be posted at this moment.");
                                            request.setAttribute("portal", "1");
                                            getServletContext().getRequestDispatcher("/admin_nafa/news_post.jsp?i="+username).forward(request, response);
                                        }conn4.close();
                                    }
                                }
                            }   break;
                        }
                        case "edit":{
                            String newsid = link.get("newsid").toString();
                            
                            Connection conn4 = Konnect.getNAFAConnection();
                            DataAccessObject obj = null;
                            obj = new DataAccessObject();
                            obj.setColumnNames("vCategory","vTitle","vMessageParOne","vMessageParTwo","vMessageParThree");
                            obj.setColumnValues(category, title, msgPar1, msgPar2, msgPar3);
                            int edit = DataAccessObject.updateSecuredRecord("iNewsID", newsid, AppData.NEWS, conn4);
                            if(edit == 0){
                                request.setAttribute("msg", "News updated successfully.");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                            }else{
                                request.setAttribute("msg", "News post could not be updated at this moment.");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                            }conn4.close();
                            break;
                        }
                        case "edit_image":{
                            String newsid = link.get("newsid").toString();
                            
                            if(image_flag.equals("")){
                                request.setAttribute("msg", "This image(s) was not uploaded properly. kindly try again or contact support");
                                request.setAttribute("portal", "1");
                                getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                            }else{
                                if(fileItem == null){
                                    request.setAttribute("msg", "I couldn't commit the news image. kindly notify support.");
                                    request.setAttribute("portal", "1");
                                    getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                                }else{
                                    String image_name = "";
                                    String image_names = "";
                                    String file_name = "";
                                    String file_names = "";
                                    String file_flag = "";
                                    for (int i = 0; i < count; i++) {
                                        image_name = fileItem[i].getName();
                                        image_names += image_name + "#";
                                        
                                        int val = NewsDAO.checkNewsImage(image_name);
                                        if(val == 0){
                                            file_flag = "yes";
                                            file_name += image_name+", ";
                                        }
                                    }
                                    if(file_flag.equals("yes")){
                                        file_names = file_name.substring(0, file_name.length()-2);
                                        request.setAttribute("msg", "The following file names already exist ["+file_names+"]. Change image names and post again.");
                                        request.setAttribute("portal", "1");
                                        getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                                    }else{
                                        String path = getServletContext().getRealPath("/admin_nafa/");  // for online use
                                        
                                        String folder = path +"images/news/";
                                        com.jovine.io.Files createFolder = new com.jovine.io.Files();
                                        createFolder.createFolder(folder);
                                        
                                        String[] images = NewsDAO.getNewsImages(newsid);
                                        Boolean state = false;
                                        String pathss = getServletContext().getRealPath("/admin_nafa/"); 
                                        for (int i = 0; i < images.length; i++) {
                                            String filenames = images[i];
                                            System.out.println("image: "+filenames);
                                            String path1 = pathss+"images/news/"+filenames;
                                            Path url = Paths.get(path1);
                                            state = Files.deleteIfExists(url);
                                        }
                                        
                                        image_names = image_names.substring(0, image_names.length()-1);
                                        
                                        String directory = "images/news//";
                                        String complete_path = path + directory;
                                        
//                                        file = new File(complete_path, location);
                                        //                            fileItem.write(file);
                                        for (int i = 0; i < count; i++) {
                                            image_name = fileItem[i].getName();
                                            file = new File(complete_path, image_name);
                                            fileItem[i].write(file);
                                        }
                                        
                                        Connection conn4 = Konnect.getNAFAConnection();
                                        DataAccessObject obj = null;
                                        obj = new DataAccessObject();
                                        obj.setColumnNames("vCategory","vTitle","vMessageParOne","vMessageParTwo","vMessageParThree","vImage");
                                        obj.setColumnValues(category, title, msgPar1, msgPar2, msgPar3, image_names);
                                        int edit = DataAccessObject.updateSecuredRecord("iNewsID", newsid, AppData.NEWS, conn4);
                                        if(edit == 0){
                                            request.setAttribute("msg", "News updated successfully.");
                                            request.setAttribute("portal", "1");
                                            getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                                        }else{
                                            for (int i = 0; i < count; i++) {
                                                String filenames = fileItem[i].getName();
                                                String paths = path+"images/news/"+filenames;
                                                Path url = Paths.get(paths);
                                                Files.deleteIfExists(url);
                                            }
                                            request.setAttribute("msg", "News post could not be updated at this moment.");
                                            request.setAttribute("portal", "1");
                                            getServletContext().getRequestDispatcher("/admin_nafa/news.jsp?i="+username).forward(request, response);
                                        }conn4.close();
                                    }
                                }
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    
                    
                }
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(NewsPostManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NewsPostManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
