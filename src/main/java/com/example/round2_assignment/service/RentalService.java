package com.example.round2_assignment.service;

import com.example.round2_assignment.dao.BookingDao;
import com.example.round2_assignment.dao.CarDao;
import com.example.round2_assignment.vo.BookingVo;
import com.example.round2_assignment.vo.CarVo;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private CarDao carDao;

    /**
     * 返回db中存在car
     * @return
     */
    public List<CarVo> rentalCarsLists() {
        return carDao.getCars();
    }

    /**
     * 根据car的类型返回所有的car
     * @param type
     * @return
     */
    public List<CarVo> queryCarsByType(String type) {
        return carDao.getCarsByType(type);
    }

    /**
     * 预定
     * @param carId
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public Integer book(Integer carId, Date startTime, Date endTime) throws Exception {
        CarVo carById = carDao.getCarById(carId);
        if (startTime.getTime() >= endTime.getTime()) {
            throw new Exception("endTime must be larger than startTime");
        }
        List<BookingVo> bookingVos = bookingDao.queryBookingListByCarId(carById.getId());
        long haveBooked = bookingVos.stream().filter(bookingVo -> {
            // [1,3] 和 [0,4] 覆盖的情况
            if (startTime.getTime() <= bookingVo.getStartTime().getTime() && endTime.getTime() >= bookingVo.getEndTime()
                    .getTime()) {
                return true;
            }
            // [1,3] 和 [2,4] 交集的情况
            if (startTime.getTime() >= bookingVo.getStartTime().getTime()
                    && startTime.getTime() <= bookingVo.getEndTime().getTime()) {
                return true;
            }
            // [1,3] 和 [0,2] 交集的情况
            if (startTime.getTime() <= bookingVo.getStartTime().getTime()
                    && endTime.getTime() >= bookingVo.getStartTime().getTime()) {
                return true;
            }
            // [1,5] 和 [0,6] 覆盖的情况
            if (startTime.getTime() >= bookingVo.getStartTime().getTime() && endTime.getTime() <= bookingVo.getEndTime()
                    .getTime()) {
                return true;
            }
            return false;
        }).count();
        if (haveBooked > 0) {
            throw new Exception("the car between this time have already booked");
        }
        return bookingDao.booking(carById.getId(), startTime, endTime);
    }

    /**
     * 取消订单
     * @param id
     * @return
     * @throws Exception
     */
    public Integer cancel(Integer id) throws Exception {
        if (bookingDao.bookingVoList().stream().noneMatch(bookingVo -> bookingVo.getId().equals(id))) {
            throw  new Exception("booking id is not exists");
        }
        return bookingDao.cancel(id);
    }

    /**
     * 返回当前时间之后存在的订单
     * @return
     */
    public List<BookingVo> bookingList() {
        return bookingDao.bookingVoList();
    }
}
