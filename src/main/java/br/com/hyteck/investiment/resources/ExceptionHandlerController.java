package br.com.hyteck.investiment.resources;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Hidden
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest request) {

        String messageUser = "File too large";
        //messageSource.getMessage("parametro.invalido", null, LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Collections.singletonList(new Error(messageDev, messageUser));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.PAYLOAD_TOO_LARGE, request);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String messageUser = messageSource.getMessage("mensagem.invalida", null,
                LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Collections.singletonList(new Error(messageDev, messageUser));

        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @ExceptionHandler({NoSuchFieldException.class})
    protected ResponseEntity<Object> handleNoSuchFieldException(NoSuchFieldException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("parametro.invalido", null, LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Collections.singletonList(new Error(messageDev, messageUser));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error> errors = createErrorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Collections.singletonList(new Error(messageDev, messageUser));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Error> createErrorsList(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            errors.add(new Error(mensagemDesenvolvedor, mensagemUsuario));
        }

        return errors;
    }


    /**
     *
     */
    public class Error {
        private final String messageDeveloper;
        private final String messageUser;


        Error(String messageDeveloper, String messageUser) {
            this.messageDeveloper = messageDeveloper;
            this.messageUser = messageUser;
        }

        public String getMessageDeveloper() {
            return messageDeveloper;
        }

        public String getMessageUser() {
            return messageUser;
        }
    }

}