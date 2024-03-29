package com.dailypractice.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailypractice.orderservice.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

}
