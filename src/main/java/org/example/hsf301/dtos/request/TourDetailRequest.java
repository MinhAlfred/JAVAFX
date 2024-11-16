package org.example.hsf301.dtos.request;

import lombok.Data;

@Data
public class TourDetailRequest {
    private Long tourID;
    private Long farmID;
    private String description;
}
