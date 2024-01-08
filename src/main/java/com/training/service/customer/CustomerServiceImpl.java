package com.training.service.customer;

import com.training.common.constant.Errors;
import com.training.dto.customer.CustomerDto;
import com.training.dto.customer.CustomerRequest;
import com.training.entity.Customer;
import com.training.exception.BadRequestException;
import com.training.exception.ErrorParam;
import com.training.exception.SysError;
import com.training.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void createNewCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setAge(request.getAge());
        customer.setGender(request.getGender());

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto getInfoCustomer(Integer id) {
        Customer customer = findCustomerById(id);

        return convertToDto(customer);
    }

    @Override
    public List<CustomerDto> getListCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = findCustomerById(id);
        customer.setFullName(request.getFullName());
        customer.setAge(request.getAge());
        customer.setGender(request.getGender());
        customerRepository.save(customer);

        return convertToDto(customer);
    }

    @Override
    @Transactional
    public void deleteCustomers(List<Integer> ids) {
        List<Customer> customers = customerRepository.findCustomerByIds(ids);
        customerRepository.deleteAll(customers);
    }

    @Override
    public Customer findCustomerById(Integer id) {

        return customerRepository.findById(id).orElseThrow(
                () -> new BadRequestException(new SysError(Errors.ERROR_CUSTOMER_NOT_FOUND, new ErrorParam(Errors.CUSTOMER)))
        );
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setAge(customer.getAge());
        customerDto.setGender(customer.getGender());

        return customerDto;
    }
}
