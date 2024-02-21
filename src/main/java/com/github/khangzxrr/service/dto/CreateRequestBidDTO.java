package com.github.khangzxrr.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class CreateRequestBidDTO implements Serializable {

    @NotNull
    private String description;

    @NotNull
    @Min(value = 1L, message = "The value must be larger than 1")
    private Double price;

    @NotNull
    @Min(value = 1L, message = "The value must be larger than 1")
    private Long duration;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
