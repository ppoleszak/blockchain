package com.poleszak.blockchain.service;

import com.poleszak.blockchain.model.Block;
import com.poleszak.blockchain.model.Transaction;
import com.poleszak.blockchain.repository.BlockRepository;
import com.poleszak.blockchain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static java.math.BigInteger.ZERO;

@Service
@RequiredArgsConstructor
public class BlockchainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

    private final BlockRepository blockRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void addBlock(Transaction transaction) {
        validateTransaction(transaction);

        Block newBlock = createNewBlock(transaction);
        updateBalances(transaction);

        LOGGER.info("Block created successfully with ID: {}", newBlock.getId());
    }

    public BigInteger getBalance(String address) {
        LOGGER.info("Calculating balance for address: {}", address);
        List<Transaction> transactions = transactionRepository.findAllByReceiver(address);

        if (transactions.isEmpty()) {
            LOGGER.info("No transactions found for address: {}", address);
            return BigInteger.ZERO;
        }

        BigInteger balance = transactions.stream().map(Transaction::getValue).reduce(BigInteger.ZERO, BigInteger::add);
        LOGGER.info("Calculated balance for address {}: {}", address, balance);
        return balance;
    }


    public List<Block> getChain() {
        LOGGER.info("Retrieving blockchain");
        return blockRepository.findAll();
    }

    public int getBlockCount() {
        LOGGER.info("Counting blocks in chain");
        int count = (int) blockRepository.count();
        LOGGER.info("Counted {} blocks in chain", count);
        return count;
    }

    @Transactional
    public void executeTransaction(Transaction transaction) {
        validateTransaction(transaction);

        transactionRepository.save(transaction);
        updateBalances(transaction);

        LOGGER.info("Transaction executed successfully with ID: {}", transaction.getId());
    }

    private void validateTransaction(Transaction transaction) {
        // TODO: Here include real validations, such as checking signatures, checking double spending, etc.
        if (transaction.getValue().compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Transaction value cannot be negative");
        }
    }

    private Block createNewBlock(Transaction transaction) {
        Block block = new Block();
        block.setTransaction(transaction);
        block.setHash(calculateBlockHash());

        transactionRepository.save(transaction);

        return blockRepository.save(block);
    }

    private String calculateBlockHash(/*Block block*/) {
        // TODO: Here include a real hash calculation, based on the block data and possibly previous block's hash.
        long newHash = blockRepository.count() + 1;
        return String.valueOf(newHash);
    }

    private void updateBalances(Transaction transaction) {
        // TODO: Need to handle the full complexity of updating balances based on the transaction.
        // TODO: This could include checking the sender's balance, adding the mining reward, etc.
        BigInteger currentBalance = getBalance(transaction.getReceiver());
        BigInteger newBalance = currentBalance.add(transaction.getValue());
        transaction.setBalance(newBalance);
    }
}