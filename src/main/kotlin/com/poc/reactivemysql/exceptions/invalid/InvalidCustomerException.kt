package com.poc.reactivemysql.exceptions.invalid

class InvalidCustomerException(message: String? = "Invalid customer") : InvalidPayloadException(message)