package com.mvcFramework.interfaces;


import com.mvcFramework.controller.ControllerActionPair;

import javax.servlet.http.HttpServletRequest;

public interface Dispatcher {

    ControllerActionPair dispatchRequest(HttpServletRequest request);

    String dispatchAction(HttpServletRequest request, ControllerActionPair controllerActionPair);
}
