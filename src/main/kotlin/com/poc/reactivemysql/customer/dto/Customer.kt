package com.poc.reactivemysql.customer.dto

import org.springframework.data.annotation.Id
import javax.persistence.*

data class Customer(
        @Column(name = "first_name") var firstName: String = "",
        @Column(name = "last_name") var lastName: String = "",
        var email: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "unused")
    var id: Int? = null
}