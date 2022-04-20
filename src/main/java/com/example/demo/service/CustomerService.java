package com.example.demo.service;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;
    final String SQL = "SELECT C_CUSTKEY, C_NAME, C_ADDRESS FROM TABLE(GETCUSTOMERS(60001))";

    public List<Customer> getCustomerTableFunction() {
        return jdbcTemplate.query(SQL, new ResultSetExtractor<List<Customer>>() {
            @Override
            public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Customer> list = new ArrayList<>();

                try {
                    while (rs.next()) {
                        Customer customer = new Customer();
                        customer.setCCustkey(rs.getLong(1));
                        customer.setCName(rs.getString(2));
                        customer.setCAddress(rs.getString(3));

                        list.add(customer);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return list;
            }
        });
    }
    public List<Customer> getCustomerProcJson() {
        return jdbcTemplate.query(SQL, new ResultSetExtractor<List<Customer>>() {
            @Override
            public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Customer> list = new ArrayList<>();

                try {
                    if (rs.next()) {
                        list = convertToCustomer(rs.getString(1));
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return list;
            }
        });
    }

    public List<Customer> convertToCustomer(String customerData) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(customerData, new TypeReference<List<Customer>>() {});
    }
}

