package com.ridango.assignment.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cocktail")
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_id")
    private Long cocktailId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "glass", nullable = false)
    private String glass;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "api_id", nullable = false)
    private String apiId;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients;
}
