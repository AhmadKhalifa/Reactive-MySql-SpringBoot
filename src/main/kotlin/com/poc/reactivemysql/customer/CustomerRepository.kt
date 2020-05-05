package com.poc.reactivemysql.customer

import com.poc.reactivemysql.customer.dto.Customer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerRepository {

    fun findAll(): Flux<Customer>

    fun findById(id: Int): Mono<Customer>

    fun save(customer: Customer): Mono<Void>

    fun delete(id: Int): Mono<Void>

    fun update(customer: Customer): Mono<Void>
}