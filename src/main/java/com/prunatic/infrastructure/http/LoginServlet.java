package com.prunatic.infrastructure.http;

import com.prunatic.domain.authentication.CredentialsAuthenticationService;
import com.prunatic.domain.authentication.InvalidArgumentException;
import com.prunatic.domain.authentication.UserSession;
import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserCredentials;
import com.prunatic.domain.user.UserRepository;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet implements Servlet {

    private UserRepository userRepository;
    private CredentialsAuthenticationService credentialsAuthenticationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userRepository = (UserRepository) config.getServletContext().getAttribute("userRepository");
        credentialsAuthenticationService = (CredentialsAuthenticationService) config.getServletContext().getAttribute("credentialsAuthenticationService");

        assert userRepository != null;
        assert credentialsAuthenticationService != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("user");
        User user = userRepository.userByUsername(username);
        try {
            if (user != null) {
                UserCredentials credentials = user.credentials();
                UserSession session = credentialsAuthenticationService.authenticate(username, credentials);
                request.getSession().setAttribute("userSession", session);
                response.sendRedirect("");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
