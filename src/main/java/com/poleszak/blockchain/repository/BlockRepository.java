package com.poleszak.blockchain.repository;

import com.poleszak.blockchain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
