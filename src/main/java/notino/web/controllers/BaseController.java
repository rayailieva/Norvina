package notino.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    public ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.addObject("view", viewName);

        modelAndView.setViewName("fragments/base-layout");

        return modelAndView;
    }

    public ModelAndView view(String viewName) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("view", viewName);

        modelAndView.setViewName("fragments/base-layout");

        return modelAndView;
    }

    public ModelAndView redirect(String url) {
        return this.view("redirect:" + url);
    }
}