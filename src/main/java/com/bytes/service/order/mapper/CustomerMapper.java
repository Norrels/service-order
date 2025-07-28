package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.inbound.dtos.CustomerReq;
import com.bytes.service.order.adapters.outbound.persistence.entities.CustomerEntity;
import com.bytes.service.order.domain.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer toCustomer(CustomerEntity entity);
    Customer toCustomer(CustomerReq customerReq);
    CustomerEntity toCustomerEntity(Customer customer);
}
