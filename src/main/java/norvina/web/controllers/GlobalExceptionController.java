package norvina.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController{

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView getException(RuntimeException re) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/unauthorized");
        modelAndView.addObject("message", re.getClass().isAnnotationPresent(ResponseStatus.class)
                ? re.getClass().getAnnotation(ResponseStatus.class).reason()
                : "You don’t have the proper credentials to access this page.");

        return modelAndView;

    }
}
