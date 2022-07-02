package com.example.round2_assignment.controller;

import com.example.round2_assignment.common.CommonResult;
import com.example.round2_assignment.dto.BookDTO;
import com.example.round2_assignment.dto.CancelDTO;
import com.example.round2_assignment.service.RentalService;
import com.example.round2_assignment.vo.BookingVo;
import com.example.round2_assignment.vo.CarVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api("rental apis")
@RestController("/rental")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @ApiOperation("get all cars in stock")
    @GetMapping("/cars")
    public CommonResult<List<CarVo>> rentalCarsLists() {
        return CommonResult.SuccessResult(rentalService.rentalCarsLists());
    }

    @ApiOperation("get cars by type")
    @GetMapping("/cars/{type}")
    public CommonResult<List<CarVo>> queryCarsByType(@PathVariable("type")String type) {
        if(type == null || type.length() == 0 ) {
            return CommonResult.ExceptionResult(new Exception("path can not be null"));
        }
        return CommonResult.SuccessResult(rentalService.queryCarsByType(type));
    }

    @ApiOperation("book car")
    @PostMapping("/book")
    public CommonResult<Integer> book(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getCarId() == null || bookDTO.getStartTime() == null || bookDTO== null) {
            return CommonResult.ExceptionResult(new Exception("parameter can not be null"));
        }
        try {
            return CommonResult.SuccessResult(rentalService.book(bookDTO.getCarId(), bookDTO.getStartTime(), bookDTO.getEndTime()));
        } catch (Exception e) {
            return CommonResult.ExceptionResult(e);
        }
    }

    @ApiOperation("cancel the car had been book")
    @PostMapping("/cancel")
    public CommonResult<Integer> cancel(@RequestBody CancelDTO cancelDTO) {
        if (cancelDTO.getId() == null) {
            return CommonResult.ExceptionResult(new Exception("parameter cat not be null"));
        }
        try {
            return CommonResult.SuccessResult(rentalService.cancel(cancelDTO.getId()));
        } catch (Exception e) {
            return CommonResult.ExceptionResult(e);
        }
    }

    @ApiOperation("get all bookings")
    @GetMapping("/book/lists")
    public CommonResult<List<BookingVo>> bookingLists() {
        return CommonResult.SuccessResult(rentalService.bookingList());
    }
}
