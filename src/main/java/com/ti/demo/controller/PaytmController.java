package com.ti.demo.controller;

import com.paytm.pg.merchant.PaytmChecksum;
import kotlin.random.URandomKt;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Map;

import java.util.Random;


@RestController
@RequestMapping("/api")
public class PaytmController {
    Random random =new Random();
  @PostMapping("/startpayment")
    public Map<String,Object>paytmHandle(@RequestBody Map<String,Object>data){

      String orderId ="ORDER"+ random.nextInt(1000000);

      JSONObject paytmParams =new JSONObject();

      JSONObject body = new JSONObject();
      body.put("requestType", "Payment");
      body.put("mid", "YOUR_MID_HERE");
      body.put("websiteName", "YOUR_WEBSITE_NAME");
      body.put("orderId", orderId);
      body.put("callbackUrl", "https://localhost:3000/paymentsgateway");

      JSONObject txnAmount = new JSONObject();
      txnAmount.put("value", data.get("amount"));
      txnAmount.put("currency", "INR");

      JSONObject userInfo = new JSONObject();
      userInfo.put("custId", "CUST_001");

      body.put("txnAmount", txnAmount);
      body.put("userInfo", userInfo);

        String responseData ="";
      ResponseEntity<Map>response =null;

        try
        {
            String checksum = PaytmChecksum.generateSignature(body.toString(), "YOUR_MERCHANT_KEY");

            JSONObject head = new JSONObject();
            head.put("signature", checksum);

            paytmParams.put("body", body);
            paytmParams.put("head", head);

            String post_data =paytmParams.toString();

            URL url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> httpEntity =new HttpEntity<>(paytmParams.toMap(),headers);

            RestTemplate restTemplate =new RestTemplate();
            response =restTemplate.postForEntity(url.toString(),httpEntity,Map.class);
            System.out.println(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map body1 = response.getBody();
        body1.put("orderId",orderId);
        body1.put("amount",txnAmount.get("value"));
        return body1;
  }

  public void capturePayment(){

  }

}
