package br.com.babuska.demo.service;

import br.com.babuska.demo.dto.Purchase;
import br.com.babuska.demo.dto.PurchaseResponse;
import br.com.babuska.demo.model.Customer;
import br.com.babuska.demo.model.Order;
import br.com.babuska.demo.model.OrderItem;
import br.com.babuska.demo.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Retrieve the order info from dto
        Order order = purchase.getOrder();

        // Generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add); // same thing as item -> order.add(item)

        // Populate order with billing and shipping addresses
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // Populate customer with order
        Customer customer = purchase.getCustomer();

        Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());

        if (customerFromDB != null) {
            customer = customerFromDB;
        }

        customer.add(order);

        // Save to the database
        customerRepository.save(customer);

        // Return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // Generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
