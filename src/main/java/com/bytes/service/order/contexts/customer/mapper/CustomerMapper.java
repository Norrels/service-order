package com.bytes.service.order.contexts.customer.mapper;

import com.bytes.service.order.contexts.customer.adapters.inbound.dtos.CustomerReq;
import com.bytes.service.order.contexts.customer.adapters.outbound.persistence.entity.CustomerEntity;
import com.bytes.service.order.contexts.customer.domain.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer toCustomer(CustomerEntity entity);
    Customer toCustomer(CustomerReq customerReq);
    CustomerEntity toCustomerEntity(Customer customer);
}
