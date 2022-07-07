/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.servlets;

import fu.daos.ArticleDAO;
import fu.daos.ArticleHashtagDAO;
import fu.daos.ItemTypeDAO;
import fu.entities.Article;
import fu.entities.ArticleHashTag;
import fu.entities.Item;
import fu.entities.Member;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thien Le
 */
@WebServlet(name = "PaginControl2", urlPatterns = {"/paging2"})
public class PaginControl2 extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            if (session.getAttribute("userdata") != null) {
                Member memberLogin = (Member) session.getAttribute("userdata");
                String index2 = request.getParameter("index2");
                ArticleDAO dao = new ArticleDAO();
                ItemTypeDAO itDao = new ItemTypeDAO();
                ArticleHashtagDAO ahDao = new ArticleHashtagDAO();
                if (index2 == null) {
                    index2 = "1";
                    int indexPage = Integer.parseInt(index2);
                    
                    ArrayList<Article> list = dao.getPagingNotice(indexPage);
                    request.setAttribute("articlesShare", list);
                    request.setAttribute("indexPage2", indexPage);
                    
                    List<Item> listI = itDao.getAllItems();
                    request.setAttribute("ListItemType", listI);
                    
                    List<ArticleHashTag> listAH = ahDao.getAllArticleHashtag();
                    request.setAttribute("listAH", listAH);
                    request.getRequestDispatcher("notification.jsp").forward(request, response);
                } else if (index2 != null) {
                    int indexPage = Integer.parseInt(index2);
                   
                    ArrayList<Article> list = dao.getPagingNotice(indexPage);
                    request.setAttribute("articlesShare", list);
                    request.setAttribute("indexPage2", indexPage);
                    
                    List<Item> listI = itDao.getAllItems();
                    request.setAttribute("ListItemType", listI);
                    
                    List<ArticleHashTag> listAH = ahDao.getAllArticleHashtag();
                    request.setAttribute("listAH", listAH);
                    request.getRequestDispatcher("notification.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
