package com.clearsolutionsltd.testtask.dto;

import com.clearsolutionsltd.testtask.validation.ValidateDateRange;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ValidateDateRange(start = "to", end = "from")
@Data
public class UserRangeDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
}
