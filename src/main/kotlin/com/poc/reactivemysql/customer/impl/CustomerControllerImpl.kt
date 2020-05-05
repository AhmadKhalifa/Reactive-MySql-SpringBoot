package com.poc.reactivemysql.customer.impl

import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.CustomerController
import com.poc.reactivemysql.customer.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerControllerImpl : CustomerController {

    @Autowired
    private lateinit var customerService: CustomerService

    @GetMapping
    override fun getAllCustomers() =
            customerService.getAllCustomers()

    @GetMapping("/{id}")
    override fun getCustomer(@PathVariable id: Int) =
            customerService.getCustomer(id)

    @PostMapping
    override fun createCustomer(@RequestBody customer: Customer) =
            customerService.createCustomer(customer)

    @DeleteMapping("/{id}")
    override fun deleteCustomer(@PathVariable id: Int) =
            customerService.deleteCustomer(id)

    @PutMapping
    override fun updateCustomer(customer: Customer) =
            customerService.updateCustomer(customer)
}