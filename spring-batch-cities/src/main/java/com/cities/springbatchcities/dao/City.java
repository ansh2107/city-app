package com.cities.springbatchcities.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    private Long id;
    private String name;

    @Column(columnDefinition="text")
    private String photo;
}
