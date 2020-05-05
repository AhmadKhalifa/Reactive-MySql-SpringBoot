package com.poc.reactivemysql.exceptions.invalid

open class InvalidPayloadException(message: String? = "Invalid payload") : IllegalArgumentException(message)