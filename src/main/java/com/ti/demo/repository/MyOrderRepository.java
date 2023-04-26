package com.ti.demo.repository;

import com.ti.demo.controller.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyOrderRepository extends JpaRepository<MyOrder,Long> {
    public MyOrder findByOrderId(String orderId);
}
