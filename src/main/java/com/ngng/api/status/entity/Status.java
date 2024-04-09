package com.ngng.api.status.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    private String name;

}
