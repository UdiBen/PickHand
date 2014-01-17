package Servlets;

import Search.SearchResult;
import Search.Searcher;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 17/01/14
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class SearchServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Searcher searcher = new Searcher();
        PrintWriter writer = servletResponse.getWriter();
        String player = servletRequest.getParameter("player");
        String hand = servletRequest.getParameter("hand");
        List<SearchResult> searchResults = searcher.Search(player, hand);

        writer.write(searchResults.toString());
    }
}