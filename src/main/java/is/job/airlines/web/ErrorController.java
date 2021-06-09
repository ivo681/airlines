package is.job.airlines.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null){
            Integer statusCode = Integer.parseInt(String.valueOf(status));
            if (statusCode == HttpStatus.NOT_FOUND.value()){
                return "error404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()){
                return "error403";
            }
        }
        return "error";
    }
}
