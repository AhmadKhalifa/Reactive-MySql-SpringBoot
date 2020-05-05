package com.poc.reactivemysql.customer

import com.poc.reactivemysql.customer.dto.Customer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerController {

    fun getAllCustomers(): Flux<Customer>

    fun getCustomer(id: Int): Mono<Customer>

    fun createCustomer(customer: Customer): Mono<Void>

    fun deleteCustomer(id: Int): Mono<Void>

    fun updateCustomer(customer: Customer): Mono<Void>
}