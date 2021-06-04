package com.app.exceptions;

import com.app.enums.MessageKeys;
import com.app.models.ObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle Any business/ API validation exception
     *
     *A
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ObjectResult<Void> handleValidationException(final ValidationException ex, final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(ex.getMessageKeys().getCode());
        error.setResultMessage(ex.getMessageKeys().getKey());
        return error;
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex MissingServletRequestParameterException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private @ResponseBody ObjectResult<Void> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                                  final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(6);
        error.setResultMessage(ex.getParameterName() + "_parameter_is_missing");

        return error;
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex HttpMediaTypeNotSupportedException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    private @ResponseBody ObjectResult<Void> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                             final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(6);
        error.setResultMessage("media_type_is_not_supported");

        return error;

    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex HttpMessageNotReadableException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected @ResponseBody ObjectResult<Void> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                            final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(5);
        error.setResultMessage("json_format_is_invalid");

        return error;
    }


    /**
     * Handle any argument type mismatch type such as string to long
     *
     * @param ex MethodArgumentTypeMismatchException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected @ResponseBody ObjectResult<Void> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                                                                                final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(4);
        error.setResultMessage("parameter_" + ex.getName() + "_value_could_not_converted_to_" + ex.getRequiredType().getSimpleName());

        return error;
    }

    /**
     * Handle illegal arguments in the request such as {
     *
     * @param ex IllegalArgumentException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ObjectResult<Void> handleBadRequest(final IllegalArgumentException ex, final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(3);
        error.setResultMessage("illegal_request_argument");

        return error;
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex ConstraintViolationException
     * @param request HttpServletRequest
     * @return the ErrorResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ObjectResult<Void> handleValidation(final ConstraintViolationException ex, final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(2);
        error.setResultMessage(ex.getMessage());

        return error;

    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex MethodArgumentNotValidException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ObjectResult<Void> handleValidation2(final MethodArgumentNotValidException ex, final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(1);
        error.setResultMessage(ex.getBindingResult().getFieldError().getDefaultMessage());

        return error;
    }

    /**
     * Handle MaxUploadSizeExceededException. Triggered when an the file exceeds the max size
     *
     * @param ex MaxUploadSizeExceededException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ObjectResult<Void> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException ex, final HttpServletRequest request) {

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(1);
        error.setResultMessage("maximum_upload_size_exceeded");

        return error;
    }

    /**
     * Handle MissingServletRequestPartException. Triggered when required part is not present
     *
     * @param ex MissingServletRequestPartException
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ObjectResult<Void> handleMissingServletRequestPartException(final MissingServletRequestPartException ex, final HttpServletRequest request) {
        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(1);
        error.setResultMessage(ex.getRequestPartName()+"_is_not_present");

        return error;
    }

    /**
     * Handle Exception, handle generic Exception
     *
     * @param ex Exception
     * @param request HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ObjectResult<Void> handleException(final Exception ex, final HttpServletRequest request) {
        log.error(ex.getMessage(), ex);

        ObjectResult<Void> error = new ObjectResult<Void>();
        error.setSuccessful(Boolean.FALSE);
        error.setResultCode(MessageKeys.INTERNAL_SERVER_ERROR.getCode());
        error.setResultMessage(MessageKeys.INTERNAL_SERVER_ERROR.getKey());
        return error;

    }


}
