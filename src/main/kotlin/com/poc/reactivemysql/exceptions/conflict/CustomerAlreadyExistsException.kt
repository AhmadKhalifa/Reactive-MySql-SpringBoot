package com.poc.reactivemysql.exceptions.conflict

class CustomerAlreadyExistsException(message: String? = "Customer already exists") :
        ResourceAlreadyExistsException(message)