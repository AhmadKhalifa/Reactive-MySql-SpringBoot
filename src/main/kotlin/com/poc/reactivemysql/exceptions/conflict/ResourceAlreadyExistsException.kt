package com.poc.reactivemysql.exceptions.conflict

import java.lang.RuntimeException

open class ResourceAlreadyExistsException(message: String? = "Resource already exists") : RuntimeException(message)