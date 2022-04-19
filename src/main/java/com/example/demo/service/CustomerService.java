package com.example.demo.service;

import ca.uhn.fhir.parser.IParser;
import com.example.demo.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private IParser parser;

    private final JdbcTemplate jdbcTemplate;
    final String SQL = "CALL SP_GET_CUSTOMER_JSON(60001)";

    public Procedure getCustomerProcJson() {
        return jdbcTemplate.query(SQL, new ResultSetExtractor<Procedure>() {
            @Override
            public Procedure extractData(ResultSet rs) throws SQLException, DataAccessException {
                try {
                    return convertToCustomer(
                            "  {\n" +
                                    "    \"code\": null,\n" +
                                    "    \"identifier\": [{\"value\": \"9399600000000029\"}],\n" +
                                    "    \"performedDateTime\": \"2019-03-08T00:00:00.000\",\n" +
                                    "    \"resourceType\": \"Procedure\",\n" +
                                    "    \"status\": \"completed\",\n" +
                                    "    \"subject\": {\"identifier\": {\"value\": \"9399600000000029\"}}\n" +
                                    "  }");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
    }

    public Procedure convertToCustomer(String customerData) throws JsonProcessingException {

        Procedure bundle = parser.parseResource(Procedure.class, customerData);
        return bundle;

        //return objectMapper.readValue(customerData, new TypeReference<List<Customer>>() {});
    }
}

