package com.sliit.order_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private List<Map<String,Object>> orders = new ArrayList<>();
    private int idCounter = 1;

    @GetMapping
    public List<Map<String,Object>> getOrders() {
        return orders;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> placeOrder(@RequestBody Map<String,Object> order) {
        order.put("id", idCounter++);
        order.put("status", "PENDING");
        orders.add(order);
        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        return orders.stream()
                .filter(o -> o.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}