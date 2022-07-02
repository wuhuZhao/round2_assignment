package com.example.round2_assignment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CarVo {
    @ApiModelProperty("primary key")
    private Integer id;
    @ApiModelProperty("the type of car")
    private String carModel;
}
