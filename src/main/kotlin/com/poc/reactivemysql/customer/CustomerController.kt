package com.poc.reactivemysql.customer

import com.poc.reactivemysql.customer.dto.Customer
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerController {

    fun getAllCustomers(): ResponseEntity<Flux<Customer>>

    fun getCustomer(id: Int): ResponseEntity<Mono<Customer>>

    fun createCustomer(customer: Customer): ResponseEntity<Mono<Void>>

    fun deleteCustomer(id: Int): ResponseEntity<Mono<Void>>

    fun updateCustomer(customer: Customer): ResponseEntity<Mono<Void>>
}