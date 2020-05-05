package com.poc.reactivemysql.customer.impl

import com.poc.reactivemysql.customer.dto.Customer
import com.poc.reactivemysql.customer.CustomerRepository
import com.poc.reactivemysql.exceptions.CustomerNotFoundException
import com.poc.reactivemysql.exceptions.InvalidCustomerException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class CustomerRepositoryImpl : CustomerRepository {

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    override fun findAll() = databaseClient
            .select()
            .from(Customer::class.java)
            .fetch()
            .all()

    override fun findById(id: Int) = databaseClient
            .select()
            .from(Customer::class.java)
            .matching(Criteria.where("id").`is`(id))
            .fetch()
            .one()

    override fun save(customer: Customer) = databaseClient
            .insert()
            .into(Customer::class.java)
            .using(customer)
            .then()

    override fun delete(id: Int) = findById(id)
            .switchIfEmpty(Mono.error(CustomerNotFoundException()))
            .then(
                    databaseClient
                            .delete()
                            .from(Customer::class.java)
                            .matching(Criteria.where("id").`is`(id))
                            .then()
            )

    override fun update(customer: Customer) = customer.id?.let { customerId ->
        databaseClient
                .update()
                .table(Customer::class.java)
                .using(customer)
                .matching(Criteria.where("id").`is`(customerId))
                .then()
    } ?: run {
        Mono.error<Void>(InvalidCustomerException("User id is required"))
    }
}