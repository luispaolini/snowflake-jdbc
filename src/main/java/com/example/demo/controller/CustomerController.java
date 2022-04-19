package com.example.demo.controller;

import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Proc;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Procedure;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String getCustomers() {
        Procedure procedure = customerService.getCustomerProcJson();
        return "Procedure";
    }

    @PostMapping
    public String createCustomer(@RequestBody String procedureJson) throws JsonProcessingException {
        Procedure procedure = customerService.convertToCustomer(procedureJson);
        return "Procedure";

    }
}
