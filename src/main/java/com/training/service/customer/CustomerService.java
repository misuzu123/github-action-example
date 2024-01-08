package com.training.service.customer;

import com.training.dto.customer.CustomerDto;
import com.training.dto.customer.CustomerRequest;
import com.training.entity.Customer;
import java.util.List;

public interface CustomerService {

    void createNewCustomer(CustomerRequest request);

    CustomerDto getInfoCustomer(Integer id);

    List<CustomerDto> getListCustomer();

    CustomerDto updateCustomer(Integer id, CustomerRequest request);

    void deleteCustomers(List<Integer> ids);

    Customer findCustomerById(Integer id);

}
