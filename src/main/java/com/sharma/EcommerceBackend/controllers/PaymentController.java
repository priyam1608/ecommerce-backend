package com.sharma.EcommerceBackend.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sharma.EcommerceBackend.Exceptions.OrderException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Order;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.OrderRepository;
import com.sharma.EcommerceBackend.responses.ApiResponse;
import com.sharma.EcommerceBackend.responses.PaymentLinkResponse;
import com.sharma.EcommerceBackend.serviceInterfaces.OrderService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    @Value("${razorpay.api.key}")
    private String razorpayKey;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLinkHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException, RazorpayException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        if(user.isPresent() && order!= null){
            try {
                RazorpayClient razorpayClient = new RazorpayClient(razorpayKey,razorpaySecret);

                JSONObject paymentLinkJsonObject = new JSONObject();
                paymentLinkJsonObject.put("amount",order.getTotalDiscountedPrice()*100);
                paymentLinkJsonObject.put("currency","INR");

                JSONObject customer = new JSONObject();
                customer.put("name",order.getUser().getFirstName());
                customer.put("email",order.getUser().getEmail());

                paymentLinkJsonObject.put("customer",customer);

                JSONObject notify = new JSONObject();
                notify.put("sms",true);
                notify.put("email",true);

                paymentLinkJsonObject.put("notify",notify);
                paymentLinkJsonObject.put("callback_url","https://ecommerce-backend-production-3930.up.railway.app/payments/"+orderId);
                paymentLinkJsonObject.put("callback_method","get");

                PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkJsonObject);

                String paymentLinkId = paymentLink.get("id");
                String paymentLinkUrl = paymentLink.get("short_url");

                PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
                paymentLinkResponse.setPayment_link_id(paymentLinkId);
                paymentLinkResponse.setPayment_link_url(paymentLinkUrl);

                return new ResponseEntity<>(paymentLinkResponse,HttpStatus.CREATED);
            }catch (Exception e){
                throw new RazorpayException(e.getMessage());
            }
        }
        return null;
    }

    @GetMapping("payments")
    public ResponseEntity<ApiResponse> redirectHandler(@RequestParam(name = "payment_id") String paymentId, @RequestParam(name = "order_id") Long orderId) throws RazorpayException, OrderException {

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKey,razorpaySecret);
        try {
            Payment payment = razorpayClient.payments.fetch(paymentId);

            if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("Payment Completed");
                order.setOrderStatus("Order Placed");
                orderRepository.save(order);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Your Order Get Placed");
            apiResponse.setStatus(true);

            return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
        }catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }
    }
}
