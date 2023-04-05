package ecommerce.services;

import ecommerce.models.Payment;
import ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPaymentMethods(){
        return paymentRepository.findAll();
    }
}
