package com.example.hms.controller;

import com.example.hms.model.Billing;
import com.example.hms.repository.BillingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingRepository billingRepository;

    public BillingController(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @GetMapping
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    @PostMapping
    public Billing addBill(@RequestBody Billing billing) {
        return billingRepository.save(billing);
    }

    @GetMapping("/{id}")
    public Billing getBillById(@PathVariable Long id) {
        return billingRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Billing updateBill(@PathVariable Long id, @RequestBody Billing updatedBill) {
        updatedBill.setId(id);
        return billingRepository.save(updatedBill);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billingRepository.deleteById(id);
    }
}