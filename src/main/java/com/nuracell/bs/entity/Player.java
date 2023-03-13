package com.nuracell.bs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor // remove soon
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigInteger score;
    String name;

    public Player(BigInteger score, String name) {
        this.score = score;
        this.name = name;
    }
}
