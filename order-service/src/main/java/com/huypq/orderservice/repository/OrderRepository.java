package com.huypq.orderservice.repository;
import com.huypq.ordermodel.entity.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends R2dbcRepository<Order, Long> {
}
