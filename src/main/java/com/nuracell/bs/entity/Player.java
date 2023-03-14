package com.nuracell.bs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import java.math.BigInteger;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor // remove soon
@SequenceGenerators(
        @SequenceGenerator(
                name = "player_sequence_generator",
                sequenceName = "player_seq",
                allocationSize = 1
        )
)
@GenericGenerators(
        @GenericGenerator(
                name = "IdOrGenerated",
                strategy = "com.nracell.bs.entity.customgen.MyGenerator"
        )
)
@TableGenerators(
        @TableGenerator(
                name = "player_table_generator",
                table = "player_id_generator",
                pkColumnName = "gen_name",
                valueColumnName = "gen_value",
                allocationSize = 1
        )
)
public class Player {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence_generator"
//            strategy = GenerationType.IDENTITY,
//            generator = "idOrGenerated"
//            strategy = GenerationType.TABLE,
//            generator = "player_table_generator"
    )
    Long id;
    BigInteger score;
    String name;

    public Player(BigInteger score, String name) {
        this.score = score;
        this.name = name;
    }
}
