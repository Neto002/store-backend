package br.com.babuska.demo.service;

import br.com.babuska.demo.dto.PaymentInfo;
import br.com.babuska.demo.dto.Purchase;
import br.com.babuska.demo.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

}
