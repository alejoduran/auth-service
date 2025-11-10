package org.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "phones")
@Builder
@Getter
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;
    private Integer cityCode;
    private String countryCode;

    public Phone() {

    }
}
