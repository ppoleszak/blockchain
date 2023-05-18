package com.poleszak.blockchain.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String hash;
    @OneToOne
    private Transaction transaction;
}
