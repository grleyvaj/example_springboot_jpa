package cu.example.application.exception;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gloria R. Leyva Jerez
 * Create responses to handle exceptions thrown in the system
 */
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExceptionResponseHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionResponseHandler.class);

    /*This are internal error code*/
    private static final int ERROR_CODE_NotValidIDException = 1;
    private static final int ERROR_CODE_ConstraintViolationException_MODEL = 2;
    private static final int ERROR_CODE_ConstraintViolationException_DB = 3;
    private static final int ERROR_CODE_ResourceNotFoundException = 4;
    private static final int ERROR_CODE_HttpMessageNotReadableException = 5;
    private static final int ERROR_CODE_HttpRequestMethodNotSupportedException = 6;
    private static final int ERROR_CODE_MethodArgumentTypeMismatchException = 7;

    private final ErrorContentHandler contentHandler;

    @Resource
    private final Environment env;

    /**
     * Create response to handle an exception thrown by invalid ID
     *
     * @param e HttpRequestMethodNotSupportedException Information for create a response to this exception
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(NotValidIDException.class)
    public ResponseEntity<ErrorInfo> methodNotValidIDHandler(HttpServletRequest request, NotValidIDException e) {
        LOG.error("Se ha producido una excepción NotValidIDException porque el id especificado en el header es incorrecto");

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String errorMessage = contentHandler.unProcessableEntityAlertMessage(e.getResourceName(), e.getFieldName(), String.valueOf(e.getFieldValue()));
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_NotValidIDException,
                "NotValidIDException");

        return new ResponseEntity<>(errorInfo, contentHandler.unProcessableEntityAlert(errorMessage), status);
    }

    /**
     * Create response to handle an exception thrown by resource not found
     *
     * @param e ResourceNotFoundException Information for create a response to this exception
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> methodNotFoundHandler(HttpServletRequest request, ResourceNotFoundException e) {
        LOG.error("Se ha producido una excepción ResourceNotFoundException porque el recurso " + e.getResourceName() +
                " con " + e.getFieldName() + " = " + e.getFieldValue() + " no existe");

        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorMessage = contentHandler.notFoundAlertMessage(e.getResourceName(), e.getFieldName(), String.valueOf(e.getFieldValue()));
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ResourceNotFoundException,
                "ResourceNotFoundException");

        return new ResponseEntity<>(errorInfo, contentHandler.notFoundAlert(errorMessage), status);
    }

//    /**
//     * Create response to handle an exception thrown by constraint violation in validation DTO process
//     *
//     * @param e ConstraintViolationException Information for create a response to this exception
//     * @return ResponseEntity The response created
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> methodValidationViolationConstraintHandler(MethodArgumentNotValidException e) {
//
//        // get spring errors
//        BindingResult result = e.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//
//        // convert errors to standard string
//        StringBuilder errorMessage = new StringBuilder();
//
//        AtomicInteger s = new AtomicInteger(fieldErrors.size());
//        fieldErrors.forEach(f -> {
//            errorMessage.append(f.getDefaultMessage());
//            s.getAndDecrement();
//            if (s.getPlain() != 0) {
//                errorMessage.append(", ");
//            }
//        });
//
//        // return message in the header
//        return ResponseEntity.badRequest()
//                .headers(contentHandler.validationInModelAlert(errorMessage.toString()))
//                .build();
//    }

    /**
     * Create response to handle an exception thrown by constraint violation in validation DTO process
     *
     * @param e ConstraintViolationException Information for create a response to this exception
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> methodValidationViolationConstraintHandler(HttpServletRequest request, ConstraintViolationException e) {
        // get spring errors
        Set<ConstraintViolation<?>> result = e.getConstraintViolations();

        // convert errors to standard string
        StringBuilder error = new StringBuilder();

        AtomicInteger s = new AtomicInteger(result.size());
        result.forEach(f -> {
            error.append(f.getMessage());
            s.getAndDecrement();
            if (s.getPlain() != 0) {
                error.append(", ");
            }
        });

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String errorMessage = contentHandler.validationInModelAlertMessage(error.toString());
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ConstraintViolationException_MODEL,
                "ConstraintViolationException-javax-validation");

        return new ResponseEntity<>(errorInfo, contentHandler.validationInModelAlert(errorMessage), status);
    }


    /**
     * Create response to handle an exception thrown by constraint violation in database manipulation process. For example: UK o FK violation
     *
     * @param e ConstraintViolationException Information for create a response to this exception
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> methodConstraintViolationHandler(HttpServletRequest request, org.hibernate.exception.ConstraintViolationException e) {
        LOG.error("Se ha producido una excepción " + "ConstraintViolationException" + " durante la manipulación  de la base de datos (DML)");

        int state = Integer.parseInt(e.getSQLState());
        String errorMessage = "";
        String detail = e.getSQLException().getLocalizedMessage().split("Detail: ")[1];
        if (state != -1) {
            switch (state) {
                case 23503:
                    LOG.error("Violación de llave foránea. Detalles: " + detail);
                    errorMessage = env.getProperty("violation.fk") + " " + env.getProperty("detail") + " " + detail;
                    break;
                case 23505:
                    LOG.error("Violación de llave única. Detalles: " + detail);
                    errorMessage = env.getProperty("violation.uk") + " " + env.getProperty("detail") + " " + detail;
                    break;
                default:
                    errorMessage = env.getProperty("unresolved.db");
                    break;
            }
        }

        HttpStatus status = HttpStatus.CONFLICT;
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ConstraintViolationException_DB,
                "ConstraintViolationException-hibernate");

        return new ResponseEntity<>(errorInfo, contentHandler.validationConstrainAlert(errorMessage), status);
    }

    /**
     * Create response to handle an exception thrown by not readable HTTP
     *
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> methodNotReadableHandler(HttpServletRequest request) {
        LOG.error("Se ha producido una excepción HttpMessageNotReadableException porque el header es incorrecto para el método requerido");

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = contentHandler.notReadableAlertMessage();
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_HttpMessageNotReadableException,
                "HttpMessageNotReadableException");

        return new ResponseEntity<>(errorInfo, contentHandler.notReadableAlert(errorMessage), status);
    }

    /**
     * Create response to handle an exception thrown by request to unsupported method
     *
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorInfo> methodNotSupportedHandler(HttpServletRequest request) {
        LOG.error("Se ha producido una excepción HttpRequestMethodNotSupportedException porque el método solicitado por el recurso no está soportado");

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        String errorMessage = contentHandler.methodNotSupportedMessage();
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_HttpRequestMethodNotSupportedException,
                "HttpRequestMethodNotSupportedException");

        return new ResponseEntity<>(errorInfo, contentHandler.methodNotSupported(errorMessage), status);
    }

    /**
     * Create response to handle an exception thrown by unsupported string in URL request
     *
     * @return ResponseEntity The response created
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorInfo> methodMethodArgumentTypeMismatchException(HttpServletRequest request) {
        LOG.error("Se ha producido una excepción MethodArgumentTypeMismatchException porque se ha especificado una cadena en el id que no machea con el tipo esperado");

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        String errorMessage = contentHandler.notMethodArgumentTypeAlertMessage();
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_MethodArgumentTypeMismatchException,
                "MethodArgumentTypeMismatchException");

        return new ResponseEntity<>(errorInfo, contentHandler.notMethodArgumentTypeAlert(errorMessage), status);
    }
}

