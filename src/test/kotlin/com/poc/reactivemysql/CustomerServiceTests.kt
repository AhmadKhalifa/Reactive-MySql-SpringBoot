package com.poc.reactivemysql

import com.poc.reactivemysql.customer.CustomerRepository
import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.impl.CustomerServiceImpl
import com.poc.reactivemysql.exceptions.conflict.CustomerAlreadyExistsException
import com.poc.reactivemysql.exceptions.invalid.InvalidCustomerException
import com.poc.reactivemysql.exceptions.notfound.CustomerNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
class CustomerServiceTests {

    @InjectMocks
    private lateinit var customerService: CustomerServiceImpl

    @Mock
    private lateinit var customerRepository: CustomerRepository

    // customerService.findAll()

    @Test
    fun `test getAllCustomers() against success (with data)`() {
        val customers = listOf(
                Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com"),
                Customer("Mohammad", "Khalid", "am.khalid@outlook.com"),
                Customer("Walter", "White", "w.w@outlook.com")
        )
        `when`(customerRepository.findAll()).thenReturn(Flux.fromIterable(customers))
        StepVerifier
                .create(customerService.getAllCustomers())
                .expectNext(customers[0], customers[1], customers[2])
                .verifyComplete()
    }

    @Test
    fun `test getAllCustomers() against success (with no data)`() {
        val customers = listOf<Customer>()
        `when`(customerRepository.findAll()).thenReturn(Flux.fromIterable(customers))
        StepVerifier
                .create(customerService.getAllCustomers())
                .verifyComplete()
    }

    // customerService.getCustomer(customerId)

    @Test
    fun `test getCustomer(customerId) against success`() {
        val customer =  Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
        `when`(customerRepository.findById(anyInt())).thenReturn(Mono.just(customer))
        StepVerifier
                .create(customerService.getCustomer(1))
                .expectNext(customer)
                .verifyComplete()
    }

    @Test
    fun `test getCustomer(customerId) against failure (not found)`() {
        `when`(customerRepository.findById(anyInt())).thenReturn(Mono.error(CustomerNotFoundException()))
        StepVerifier
                .create(customerService.getCustomer(1))
                .verifyError(CustomerNotFoundException::class.java)
    }

    // customerService.createCustomer(customer)

    @Test
    fun `test createCustomer(customer) against success`() {
        val customer =  Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
        `when`(customerRepository.save(customer)).thenReturn(Mono.empty())
        StepVerifier
                .create(customerService.createCustomer(customer))
                .verifyComplete()
    }

    @Test
    fun `test createCustomer(customer) against failure (customer already exists)`() {
        val requestCustomer =  Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
                .apply { id = 1 }
        val savedCustomer = Customer("Ahmed", "Kamal", "ahmed.kamal@outlook.com")
                .apply { id = 1 }
        `when`(customerRepository.findById(anyInt())).thenReturn(Mono.just(savedCustomer))
        StepVerifier
                .create(customerService.createCustomer(requestCustomer))
                .verifyError(CustomerAlreadyExistsException::class.java)
    }

    // customerService.deleteCustomer(customerId)

    @Test
    fun `test deleteCustomer(customerId) against success`() {
        val customer = Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
        `when`(customerRepository.findById(anyInt())).thenReturn(Mono.just(customer))
        `when`(customerRepository.delete(anyInt())).thenReturn(Mono.just(Any()).then())
        StepVerifier
                .create(customerService.deleteCustomer(1))
                .verifyComplete()
    }

    @Test
    fun `test deleteCustomer(customerId) against failure (not found)`() {
        `when`(customerRepository.findById(anyInt())).thenReturn(Mono.empty())
        StepVerifier
                .create(customerService.deleteCustomer(1))
                .verifyError(CustomerNotFoundException::class.java)
    }

    // customerService.updateCustomer(customer)

    @Test
    fun `test updateCustomer(customer) against success`() {
        val customer = Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
                .apply { id = 1 }
        `when`(customerRepository.update(customer)).thenReturn(Mono.just(Any()).then())
        StepVerifier
                .create(customerService.updateCustomer(customer))
                .verifyComplete()
    }

    @Test
    fun `test updateCustomer(customer) failure (missing id)`() {
        val customer = Customer("Ahmed", "Khalifa", "ahmed.akhalifa@outlook.com")
        `when`(customerRepository.update(customer)).thenReturn(Mono.just(Any()).then())
        StepVerifier
                .create(customerService.updateCustomer(customer))
                .verifyError(InvalidCustomerException::class.java)
    }
}