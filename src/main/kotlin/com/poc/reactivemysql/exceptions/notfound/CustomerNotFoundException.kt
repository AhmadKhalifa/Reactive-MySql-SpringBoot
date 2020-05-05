package com.poc.reactivemysql.exceptions.notfound

class CustomerNotFoundException(message: String? = "Customer not found") : ResourceNotFoundException(message)