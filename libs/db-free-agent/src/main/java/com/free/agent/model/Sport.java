package com.free.agent.model;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SPORT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME_EN", "NAME_RU"})})
public class Sport extends AbstractTable<Long> {

    @Id
    @Column(name = "SPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME_EN", unique = true)
    private String nameEn;

    @Column(name = "NAME_RU", unique = true)
    private String nameRu;

    @ManyToMany(mappedBy = "sports", fetch = FetchType.LAZY)
    private Set<User> users = Sets.newHashSet();

}
