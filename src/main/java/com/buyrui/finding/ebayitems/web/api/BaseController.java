package com.buyrui.finding.ebayitems.web.api;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.buyrui.finding.ebayitems.web.DefaultExceptionAttributes;
import com.buyrui.finding.ebayitems.web.ExceptionAttributes;

/**
 * The BaseController class implements common functionality for all Controller
 * classes. The <code>@ExceptionHandler</code> methods provide a consistent
 * response when Exceptions are thrown from <code>@RequestMapping</code>
 * annotated Controller methods.
 * 
 * @author Matt Warman
 */
public class BaseController {

    /**
     * The Logger for this class.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with Exception Attributes as JSON and HTTP
     * status code 404, not found.
     * 
     * @param noResultException A NoResultException instance.
     * @param request The HttpServletRequest in which the NoResultException was
     *        raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     *         and HTTP status code 404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(
            NoResultException noResultException, HttpServletRequest request) {

        logger.info("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(noResultException, request,
                        HttpStatus.NOT_FOUND);

        logger.info("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods. Creates a response with the
     * Exception Attributes in the response body as JSON and a HTTP status code
     * of 500, internal server error.
     * 
     * @param exception An Exception instance.
     * @param request The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     *         and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception exception, HttpServletRequest request) {

        logger.error("> handleException");
        logger.error("- Exception: ", exception);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(exception, request,
                        HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("< handleException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
//    @Autowired
//    private TraceRepository traceRepo;
//    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> handleValidationError(MethodArgumentNotValidException ex) {
//        Map<String, String> fieldErrorMap = new HashMap<>();
//        
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//        for (FieldError fieldError: fieldErrors) {
//            fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//
//        return fieldErrorMap;
//    }
//    
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleUnexpectedException(Exception ex) {
//        logger.error("Error during process request", ex);
//        return ex.getMessage();
//    }
//    
//    protected void traceAction(String action) {
//        String user = "anonymous";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            user = authentication.getName();
//        }
//        
//        Map<String, Object> trace = new LinkedHashMap<>();
//        trace.put("user", user);
//        trace.put("action", action);
//        
//        traceRepo.add(trace);
//    }
}
