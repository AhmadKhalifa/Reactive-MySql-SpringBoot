package com.poc.reactivemysql.exceptions.handler

import java.util.*

@Suppress("unused")
class ExceptionResponse(val message: String, val details: String?, val timeStamp: Date = Date())