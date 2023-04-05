package ecommerce.controller;

import ecommerce.models.Payment;
import ecommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAllPaymentMethods")
    public List<Payment> getAllPaymentMethods(){
        return paymentService.getAllPaymentMethods();
    }
}
