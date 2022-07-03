package com.example.round2_assignment.dao;

import com.example.round2_assignment.vo.BookingVo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BookingDao {
    // mock db
    private Map<Integer, List<BookingVo>> db = new HashMap<>();
    // mock db booking id
    private Integer primaryKey = 0;

    /**
     * 根据carId返回订单列表
     * @param carId
     * @return
     */
    public List<BookingVo> queryBookingListByCarId(Integer carId) {
        return db.getOrDefault(carId, new ArrayList<>());
    }

    /***
     * 预定，需要提供carId和开始时间和结束时间，然后判断是否存在一个交集，如果不存在交集即可以预定
     * @param carId
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public Integer booking(Integer carId, Date startTime, Date endTime) throws Exception {
        Integer id = ++primaryKey;
        if (!db.containsKey(carId)) {
            db.put(carId, new ArrayList<BookingVo>(Arrays.asList(new BookingVo()
                    .setId(id)
                    .setCarId(carId)
                    .setStartTime(startTime)
                    .setEndTime(endTime)
            )));
            return carId;
        }
        List<BookingVo> bookingVos = db.get(carId);
        bookingVos.add(new BookingVo()
                .setId(id)
                .setCarId(carId)
                .setEndTime(startTime)
                .setEndTime(endTime));
        return id;
    }

    /**
     * 查询db中的booking id,如果存在即可以删除
     * @param id
     * @return
     * @throws Exception
     */
    public Integer cancel(Integer id) {
        BookingVo bookingVo = null;
        for (Map.Entry<Integer, List<BookingVo>> bookingVoEntry : db.entrySet()) {
            bookingVo = bookingVoEntry.getValue().stream().filter(booking -> booking.getId().equals(id))
                    .findFirst().orElse(null);
            if (bookingVo != null) {
                break;
            }
        }
        db.put(bookingVo.getCarId(),db.get(bookingVo.getCarId()).stream().filter(booking -> !booking.getId().equals(id)).collect(
                Collectors.toList()));
        return id;
    }

    /**
     * 获取预定的汽车订单
     * @return
     */
    public List<BookingVo> bookingVoList() {
        List<BookingVo> result = new ArrayList<>();
        for (Map.Entry<Integer, List<BookingVo>> bookingVoList : db.entrySet()) {
            result.addAll(bookingVoList.getValue().stream().filter(booking -> booking.getStartTime().getTime() >= System.currentTimeMillis()).collect(
                    Collectors.toList()));
        }
        return result;
    }
}
