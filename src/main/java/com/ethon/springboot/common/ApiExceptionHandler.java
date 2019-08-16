package com.ethon.springboot.common;

import com.ethon.springboot.common.model.ApiResponseModel;
import com.ethon.springboot.common.model.ParamFieldValidModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel paramFieldValidExceptionHandler(BindException ex) throws Exception {

        log.debug("ParamFieldValidException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        setInvalidGlobal(apiResponse, ex.getGlobalError());
        setInvalidFieldList(apiResponse, ex.getFieldErrors());

        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) throws Exception {

        log.debug("MethodArgumentNotValidException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        setInvalidGlobal(apiResponse, ex.getBindingResult().getGlobalError());
        setInvalidFieldList(apiResponse, ex.getBindingResult().getFieldErrors());

        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel methodArgumentNotValidExceptionHandler(MethodArgumentTypeMismatchException ex) throws Exception {

        log.debug("MethodArgumentTypeMismatchException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        String message = "Failed to convert value of required type [" + ClassUtils.getShortName(ex.getRequiredType()) + "]";

        apiResponse.put("invalidMessages", Collections.singletonList(new ParamFieldValidModel(ex.getName(), ex.getErrorCode(), message)));

        return apiResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel constraintViolationExceptionHandler(ConstraintViolationException ex) throws Exception {

        Set<String> messages = null;

        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();

        if (constraintViolationSet != null && !constraintViolationSet.isEmpty()) {
            messages = new HashSet<>(constraintViolationSet.size());
            messages.addAll(constraintViolationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        }

        log.debug("ConstraintViolationException Handling : {}", messages);

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        apiResponse.put("invalidMessages", messages);

        return apiResponse;
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            HttpMediaTypeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel badRequestExceptionHandler(Exception ex) throws Exception {

        log.debug(ex.getClass().getSimpleName() + " Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        apiResponse.put(ex.getMessage());

        return apiResponse;
    }

//    @ExceptionHandler(BasedException.class)
//    public ResponseEntity<ApiResponseModel> basedExceptionHandler(BasedException ex) throws Exception {
//
//        log.debug("BasedException Handling : {}", ex.getMessage());
//
//        ExceptionTypeable exceptionType = ex.getExceptionType();
//
//        ApiResponseModel apiResponse = new ApiResponseModel(exceptionType.getResultCode(), exceptionType.getResultMessage());
//
//        Object exceptionData = ex.getExceptionData();
//
//        if (exceptionData != null) {
//            apiResponse.put(exceptionData);
//        }
//
//        return new ResponseEntity<>(apiResponse, exceptionType.getStatusCode());
//    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseModel errorExceptionHandler(Throwable ex) throws Exception {

        log.error("Exception Handling...", ex);

        return new ApiResponseModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
    }

    private void setInvalidGlobal(ApiResponseModel apiResponse, ObjectError objectError) throws Exception {

        if (apiResponse != null && objectError != null) {

            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("validCode", objectError.getCode());
            resultMap.put("message", objectError.getDefaultMessage());

            apiResponse.put("invalidGlobal", resultMap);
        }
    }

    private void setInvalidFieldList(ApiResponseModel apiResponse, List<FieldError> fieldErrorList) throws Exception {

        if (apiResponse != null && fieldErrorList != null && !fieldErrorList.isEmpty()) {

            List<ParamFieldValidModel> invalidFiledList = new ArrayList<>();

            fieldErrorList.forEach(fieldError -> invalidFiledList.add(
                    new ParamFieldValidModel(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())
            ));

            apiResponse.put("invalidMessages", invalidFiledList);
        }
    }

    private ApiResponseModel createBadRequestApiResponse() throws Exception {
        return new ApiResponseModel(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}

