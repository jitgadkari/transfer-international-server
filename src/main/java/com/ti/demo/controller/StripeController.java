package com.ti.demo.controller;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class StripeController {
//    Stripe.apiKey = "sk_test_tR3PYbcVNZZ796tH88S4VQ2u";
//    staticFiles.externalLocation(
//            Paths.get("public").toAbsolutePath().toString());

    private static Gson gson = new Gson();

    static class CreatePayment {
        @SerializedName("items")
        Object[] items;

        public Object[] getItems() {
            return items;
        }
    }

    static class CreatePaymentResponse {
        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
    @CrossOrigin
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment)throws StripeException {
//        post("/create-payment-intent", (request, response) -> {
//            response.type("application/json");

//            CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);
            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
//                          .setAmount(new Long(calculateOrderAmount(postBody.getItems())))
                            .setAmount(15 * 100L)
                            .setCurrency("inr")
                            .setAutomaticPaymentMethods(

                                    PaymentIntentCreateParams.AutomaticPaymentMethods
                                            .builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new CreatePaymentResponse(paymentIntent.getClientSecret());

        };
    }


