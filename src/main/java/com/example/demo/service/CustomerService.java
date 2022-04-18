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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;
    final String SQL = "CALL SP_GET_CUSTOMER_JSON(60001)";

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

