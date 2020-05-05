package com.poc.reactivemysql.exceptions.handler

import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.*

@Suppress("unused", "MemberVisibilityCanBePrivate")
class ValidationExceptionResponse(bindingResult: BindingResult) {

    val objectName = bindingResult.objectName
    val message: String = "One or more fields are invalid in object '$objectName'"
    val timeStamp: Date = Date()

    val invalidArguments = bindingResult.allErrors
            .groupBy { error ->
                (error.run { arguments?.get(0) } as? DefaultMessageSourceResolvable)?.defaultMessage
            }.map { (fieldName, errors) ->
                val rejectedValue = (errors[0] as FieldError).rejectedValue
                InvalidArgument(
                        fieldName,
                        rejectedValue,
                        messages = errors.mapNotNull { error -> error.defaultMessage }
                )
            }

    inner class InvalidArgument(val name: String?, val rejectedValue: Any?, val messages: List<String>)
}