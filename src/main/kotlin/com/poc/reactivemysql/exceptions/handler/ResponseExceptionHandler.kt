package com.poc.reactivemysql.exceptions.handler

import com.poc.reactivemysql.exceptions.conflict.ResourceAlreadyExistsException
import com.poc.reactivemysql.exceptions.invalid.InvalidCustomerException
import com.poc.reactivemysql.exceptions.notfound.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
@RestController
class ResponseExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception, request: WebRequest) =
            ResponseEntity(
                    ExceptionResponse(
                            message = exception.message ?: "Internal server error",
                            details = request.getDescription(false)
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            )

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleAllExceptions(exception: ResourceNotFoundException, request: WebRequest) =
            ResponseEntity(
                    ExceptionResponse(
                            message = exception.message ?: "Resource not found",
                            details = request.getDescription(false)
                    ),
                    HttpStatus.NOT_FOUND
            )

    @ExceptionHandler(InvalidCustomerException::class)
    fun handleAllExceptions(exception: InvalidCustomerException, request: WebRequest) =
            ResponseEntity(
                    ExceptionResponse(
                            message = exception.message ?: "Resource not found",
                            details = request.getDescription(false)
                    ),
                    HttpStatus.BAD_REQUEST
            )

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleAllExceptions(exception: ResourceAlreadyExistsException, request: WebRequest) =
            ResponseEntity(
                    ExceptionResponse(
                            message = exception.message ?: "Resource already exists",
                            details = request.getDescription(false)
                    ),
                    HttpStatus.CONFLICT
            )

    override fun handleMethodArgumentNotValid(
            exception: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> = ResponseEntity(
            ValidationExceptionResponse(exception.bindingResult),
            HttpStatus.BAD_REQUEST
    )
}