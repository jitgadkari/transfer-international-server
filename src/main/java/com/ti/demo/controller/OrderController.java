package com.ti.demo.controller;

//import com.ti.demo.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.ti.demo.repository.MyOrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {
    //    private OrderService oService;
    @Autowired
    private MyOrderRepository myOrderRepository;

    @CrossOrigin
    @PostMapping("/orders")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        System.out.println(data);
        int amt = Integer.parseInt(data.get("amount").toString());

        var client = new RazorpayClient("rzp_test_yAbw3tTnVxxsiy", "p8IhNgl3CQthryQIjY2NP4pZ");
        JSONObject ob = new JSONObject();
        ob.put("amount", amt * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_235425");

        Order order = client.orders.create(ob);
        System.out.println(order);

        MyOrder myOrder = new MyOrder();
        myOrder.setAmount(order.get("amount"));
        myOrder.setOrderId(order.get("id"));
        myOrder.setPaymentId(null);
        myOrder.setStatus("created");
        myOrder.setReceipt(order.get("receipt"));

        this.myOrderRepository.save(myOrder);
        return order.toString();

    }

    @CrossOrigin
    @PostMapping("/updateorders")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
       MyOrder myOrder = this.myOrderRepository.findByOrderId(data.get("orderId").toString());
       myOrder.setPaymentId(data.get("paymentId").toString());
       myOrder.setStatus(data.get("status").toString());
       this.myOrderRepository.save(myOrder);
       System.out.println(data);
        return  ResponseEntity.ok(Map.of("message","updated"));
    }

}

