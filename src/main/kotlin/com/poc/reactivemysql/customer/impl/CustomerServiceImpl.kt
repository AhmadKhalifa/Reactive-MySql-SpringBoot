@file:Suppress("RemoveExplicitTypeArguments")

package com.poc.reactivemysql.customer.impl

import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.CustomerRepository
import com.poc.reactivemysql.customer.CustomerService
import com.poc.reactivemysql.exceptions.conflict.CustomerAlreadyExistsException
import com.poc.reactivemysql.exceptions.invalid.InvalidCustomerException
import com.poc.reactivemysql.exceptions.notfound.CustomerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class CustomerServiceImpl : CustomerService {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Transactional
    override fun getAllCustomers() = customerRepository.findAll()

    @Transactional
    override fun getCustomer(id: Int) = customerRepository.findById(id)
            .switchIfEmpty(Mono.error(CustomerNotFoundException()))

    @Transactional
    override fun createCustomer(customer: Customer) = customer.id?.let { customerId ->
        customerRepository
                .findById(customerId)
                .flatMap { customer ->
                    customer?.run { Mono.error<Void>(CustomerAlreadyExistsException()) }
                            ?: customerRepository.save(customer.apply { id = null })
                }
    } ?: run {
        customerRepository.save(customer)
    }

    @Transactional
    override fun deleteCustomer(id: Int) = customerRepository.findById(id)
            .switchIfEmpty(Mono.error(CustomerNotFoundException()))
            .then(customerRepository.delete(id))
            .then()

    @Transactional
    override fun updateCustomer(customer: Customer) = customer.id?.let {
        customerRepository.update(customer)
    } ?: run {
        Mono.error<Void>(InvalidCustomerException("Customer id is required"))
    }
}