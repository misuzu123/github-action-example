package com.training.controller;

import com.training.common.constant.Constant;
import com.training.dto.ResponseJson;
import com.training.dto.customer.CustomerDto;
import com.training.dto.customer.CustomerRequest;
import com.training.service.customer.CustomerService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseJson<Boolean>> createNewCustomer(@RequestBody CustomerRequest request) {
        customerService.createNewCustomer(request);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseJson<CustomerDto>> getInfoCustomer(@PathVariable("id") Integer id) {
        CustomerDto customer = customerService.getInfoCustomer(id);
        return ResponseEntity.ok().body(new ResponseJson<>(customer, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping()
    public ResponseEntity<ResponseJson<List<CustomerDto>>> getListCustomer() {
        List<CustomerDto> customers = customerService.getListCustomer();
        return ResponseEntity.ok().body(new ResponseJson<>(customers, HttpStatus.OK, Constant.SUCCESS));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseJson<CustomerDto>> updateCustomer(@PathVariable("id") Integer id, @RequestBody CustomerRequest request) {
        CustomerDto customer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok().body(new ResponseJson<>(customer, HttpStatus.OK, Constant.SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseJson<Boolean>> deleteCustomers(@RequestBody List<Integer> ids) {
        customerService.deleteCustomers(ids);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

}
