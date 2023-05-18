package com.poleszak.blockchain.repository;

import com.poleszak.blockchain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByReceiver(String receiver);
}
