package norvina.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    public ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.addObject("view", viewName);
        modelAndView.setViewName(viewName);

        return modelAndView;
    }

    public ModelAndView view(String viewName) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("view", viewName);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    public ModelAndView redirect(String url) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + url);

        return modelAndView;
    }
}