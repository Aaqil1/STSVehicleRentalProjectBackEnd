package com.vehicle.payrent.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FeedbackRequest {

    @NotBlank
    private String feedback;

    @NotBlank
    private String username;
}
