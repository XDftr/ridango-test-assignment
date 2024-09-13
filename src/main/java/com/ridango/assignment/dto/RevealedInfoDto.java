package com.ridango.assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RevealedInfoDto {
    private String category;
    private String glass;
    private List<String> ingredients;
    private String picture;
}
