package com.poc.reactivemysql.exceptions

class InvalidCustomerException(message: String? = "Invalid customer") : IllegalArgumentException(message)