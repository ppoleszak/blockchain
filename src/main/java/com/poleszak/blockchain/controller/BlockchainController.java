package com.poleszak.blockchain.controller;

import com.poleszak.blockchain.model.Block;
import com.poleszak.blockchain.model.Transaction;
import com.poleszak.blockchain.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
@RequiredArgsConstructor
public class BlockchainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainController.class);

    private final BlockchainService blockchainService;

    @PostMapping("/addBlock")
    public ResponseEntity<String> addBlock(@RequestBody Transaction transaction) {
        LOGGER.info("Adding new block to the blockchain with transaction: {}", transaction);
        blockchainService.addBlock(transaction);
        LOGGER.info("Block added successfully");
        return ResponseEntity.ok("Block added successfully");
    }

    @GetMapping("/getBalance/{address}")
    public ResponseEntity<BigInteger> getBalance(@PathVariable String address) {
        LOGGER.info("Retrieving balance for address: {}", address);
        BigInteger balance = blockchainService.getBalance(address);
        LOGGER.info("Balance retrieved: {}", balance);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/getChainState")
    public ResponseEntity<List<Block>> getChainState() {
        LOGGER.info("Retrieving blockchain state");
        List<Block> state = blockchainService.getChain();
        LOGGER.info("Blockchain state retrieved: {}", state);
        return ResponseEntity.ok(state);
    }

    @GetMapping("/getBlockCount")
    public ResponseEntity<Integer> getBlockCount() {
        LOGGER.info("Retrieving block count");
        int count = blockchainService.getBlockCount();
        LOGGER.info("Block count retrieved: {}", count);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/executeTransaction")
    public ResponseEntity<String> executeTransaction(@RequestBody Transaction transaction) {
        LOGGER.info("Executing transaction: {}", transaction);
        blockchainService.executeTransaction(transaction);
        LOGGER.info("Transaction executed successfully");
        return ResponseEntity.ok("Transaction executed successfully");
    }
}