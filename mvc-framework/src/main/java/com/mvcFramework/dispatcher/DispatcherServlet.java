package com.mvcFramework.dispatcher;

import com.mvcFramework.controller.ControllerActionPair;
import com.mvcFramework.handlers.HandlerActionImpl;
import com.mvcFramework.handlers.HandlerMappingImpl;
import com.mvcFramework.interfaces.Dispatcher;
import com.mvcFramework.interfaces.HandlerAction;
import com.mvcFramework.interfaces.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet implements Dispatcher {

    private HandlerMapping handlerMapping;

    private HandlerAction handlerAction;

    public DispatcherServlet() {
        this.handlerMapping = new HandlerMappingImpl();
        this.handlerAction = new HandlerActionImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isResource(req)) {
            this.sendResourceResponse(req, resp);
            return;
        }

        this.handleRequest(req, resp);
    }

    private void sendResourceResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String directory = URLDecoder.decode(request.getServletContext().getResource("/").getPath(), "UTF-8");
        File file = new File(directory + url);
        ///bootstrap/css/b.min.css
        try (
                BufferedReader bfr = new BufferedReader(new FileReader(file))
        ) {
            String line;
            while ((line = bfr.readLine()) != null) {
                response.getWriter().print(line);
            }
        }
    }

    private boolean isResource(HttpServletRequest request) {
        boolean isResource = false;
        String url = request.getRequestURI();
        if (url.contains(".")) {
            isResource = true;
        }

        return isResource;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.handleRequest(req, resp);
    }

    @Override
    public ControllerActionPair dispatchRequest(HttpServletRequest request) {
        ControllerActionPair controllerActionPair = null;
        try {
            controllerActionPair = this.handlerMapping.findController(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controllerActionPair;
    }

    @Override
    public String dispatchAction(HttpServletRequest request, HttpServletResponse response, ControllerActionPair controllerActionPair) {
        String view = null;
        try {
            view = this.handlerAction.executeControllerAction(request,response, controllerActionPair);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ControllerActionPair controllerActionPair = this.dispatchRequest(request);
        if (controllerActionPair != null) {
            String view = this.dispatchAction(request, response, controllerActionPair);
            try {
                if (view.startsWith("redirect:")) {
                    String redirectPath = view.replace("redirect:", "");
                    response.sendRedirect(redirectPath);
                } else {
                    request.getRequestDispatcher("/" + view + ".jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}