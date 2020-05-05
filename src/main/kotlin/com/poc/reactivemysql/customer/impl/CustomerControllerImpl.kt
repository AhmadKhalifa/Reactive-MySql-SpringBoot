package com.poc.reactivemysql.customer.impl

import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.CustomerController
import com.poc.reactivemysql.customer.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerControllerImpl : CustomerController {

    @Autowired
    private lateinit var customerService: CustomerService

    @GetMapping
    override fun getAllCustomers() =
            ResponseEntity(customerService.getAllCustomers(), HttpStatus.OK)

    @GetMapping("/{id}")
    override fun getCustomer(@PathVariable id: Int) =
            ResponseEntity(customerService.getCustomer(id), HttpStatus.OK)

    @PostMapping
    override fun createCustomer(@RequestBody customer: Customer) =
            ResponseEntity(customerService.createCustomer(customer), HttpStatus.CREATED)

    @DeleteMapping("/{id}")
    override fun deleteCustomer(@PathVariable id: Int) =
            ResponseEntity(customerService.deleteCustomer(id), HttpStatus.NO_CONTENT)

    @PutMapping
    override fun updateCustomer(@RequestBody customer: Customer) =
            ResponseEntity(customerService.updateCustomer(customer), HttpStatus.NO_CONTENT)
}