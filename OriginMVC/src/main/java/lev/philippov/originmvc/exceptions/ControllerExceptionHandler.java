package lev.philippov.originmvc.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

//        @ExceptionHandler
//        public ResponseEntity<ErrorResponce> handleError(ServerException ex){
//            ErrorResponce e = new ErrorResponce();
//            e.setDate(new Date(System.currentTimeMillis()));
//            e.setMessage(ex.getMessage());
//            e.setStatus(HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
//        }

        @ExceptionHandler(value = RuntimeException.class)
        public ModelAndView handleWebControllerErrors(RuntimeException ex) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("ex",ex.getClass().getSimpleName());
            modelAndView.addObject("message", ex.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }


}
