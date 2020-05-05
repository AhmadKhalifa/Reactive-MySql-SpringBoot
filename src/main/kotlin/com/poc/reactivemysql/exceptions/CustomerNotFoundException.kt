package com.poc.reactivemysql.exceptions

class CustomerNotFoundException(message: String? = "Customer not found") : ResourceNotFoundException(message)