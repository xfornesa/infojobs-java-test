package com.prunatic.infrastructure.http;

import com.prunatic.domain.authentication.UserSession;
import com.prunatic.domain.authorization.LoginRequiredException;
import com.prunatic.domain.authorization.PageAuthorizationService;
import com.prunatic.domain.authorization.SessionExpiredException;
import com.prunatic.domain.cms.Page;
import com.prunatic.domain.cms.PageRepository;
import com.prunatic.infrastructure.persistence.cms.InMemoryPageRepository;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageServlet extends HttpServlet implements Servlet {

    private PageRepository pageRepository;
    private PageAuthorizationService pageAuthorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        pageRepository = (PageRepository) config.getServletContext().getAttribute("pageRepository");
        pageAuthorizationService = (PageAuthorizationService) config.getServletContext().getAttribute("pageAuthorizationService");

        assert pageRepository != null;
        assert pageAuthorizationService != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageName = request.getParameter("page");
        Page page = pageRepository.pageByName(pageName);
        UserSession currentSession = (UserSession) request.getSession().getAttribute("userSession");
        try {
            if (page != null) {
                boolean isAuthorized = pageAuthorizationService.authorize(page, currentSession);
                if (isAuthorized) {
                    request.setAttribute("username", currentSession.username());
                    request.setAttribute("pageName", page.name());
                    request.getRequestDispatcher("/page.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/error403.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
            }
        } catch (LoginRequiredException | SessionExpiredException e) {
            response.sendRedirect("login");
        }
    }
}
