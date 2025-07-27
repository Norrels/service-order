package com.bytes.bytes.contexts.customer.mapper;

import com.bytes.bytes.contexts.customer.adapters.inbound.dtos.CustomerReq;
import com.bytes.bytes.contexts.customer.adapters.outbound.persistence.entity.CustomerEntity;
import com.bytes.bytes.contexts.customer.domain.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer toCustomer(CustomerEntity entity);
    Customer toCustomer(CustomerReq customerReq);
    CustomerEntity toCustomerEntity(Customer customer);
}
