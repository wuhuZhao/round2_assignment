package com.example.round2_assignment.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BookingVo {
    @ApiModelProperty("primary key")
    private Integer id;
    @ApiModelProperty("carId")
    private Integer carId;
    @ApiModelProperty("startTime")
    private Date startTime;
    @ApiModelProperty("endTime")
    private Date endTime;
}
