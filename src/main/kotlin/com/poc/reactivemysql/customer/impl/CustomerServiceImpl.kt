package com.poc.reactivemysql.customer.impl

import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.CustomerRepository
import com.poc.reactivemysql.customer.CustomerService
import com.poc.reactivemysql.exceptions.CustomerNotFoundException
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
    override fun createCustomer(customer: Customer) = customerRepository.save(customer)

    @Transactional
    override fun deleteCustomer(id: Int) = customerRepository.delete(id)

    @Transactional
    override fun updateCustomer(customer: Customer) = customerRepository.update(customer)
}