package com.ti.demo.controller;

//import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString

@Entity
@Table(name="tbl_order")
public class MyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="myOrderId")
    private Long myOrderId;
    @Column(name="orderId")
    private String orderId;
    @Column(name="amount")
    private int amount;
    @Column(name="receipt")
    private  String receipt;
    @Column(name="status")
    private  String status;
    @Column(name="attempts")
    private  String attempts;
    @Column(name="paymentId")
    private  String paymentId;
}
