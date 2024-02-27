package br.com.babuska.demo.dto;

import br.com.babuska.demo.model.Address;
import br.com.babuska.demo.model.Customer;
import br.com.babuska.demo.model.Order;
import br.com.babuska.demo.model.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
