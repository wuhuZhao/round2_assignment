package com.example.round2_assignment.dto;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookDTO {

    private Integer carId;

    private Date startTime;

    private Date endTime;

}
