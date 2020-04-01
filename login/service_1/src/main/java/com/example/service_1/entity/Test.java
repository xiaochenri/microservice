package com.example.service_1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hqc
 * @date 2020/4/1 10:49
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "TB_TEST")
public class Test {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private Integer count;

}
