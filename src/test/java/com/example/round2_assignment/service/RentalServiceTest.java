package com.example.round2_assignment.service;

import static org.junit.Assert.*;

import com.example.round2_assignment.dao.BookingDao;
import com.example.round2_assignment.dao.CarDao;
import com.example.round2_assignment.vo.BookingVo;
import com.example.round2_assignment.vo.CarVo;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {

    @Mock
    private BookingDao bookingDao;

    @Mock
    private CarDao carDao;

    @InjectMocks
    private RentalService rentalService;

    @Test
    public void rentalCarsLists() {
        CarVo toyota = new CarVo().setId(1).setCarModel("toyota");
        Mockito.when(carDao.getCars()).thenReturn(Arrays.asList(toyota));
        Assert.assertEquals(toyota,rentalService.rentalCarsLists().get(0));
    }

    @Test
    public void queryCarsByType() {
        CarVo toyota = new CarVo().setId(1).setCarModel("toyota");
        Mockito.when(carDao.getCarsByType(Mockito.eq("toyota"))).thenReturn(Arrays.asList(toyota));
        Mockito.when(carDao.getCarsByType(Mockito.eq("bmw"))).thenReturn(Collections.emptyList());
        Assert.assertEquals(rentalService.queryCarsByType("toyota").get(0), toyota);
        Assert.assertEquals(rentalService.queryCarsByType("bmw").size(), 0);
    }

    @Test
    public void book() throws Exception {
        CarVo toyota = new CarVo().setId(1).setCarModel("toyota");
        Mockito.when(carDao.getCarById(Mockito.anyInt())).thenReturn(toyota);
        Date cur = new Date();
        Date next = new Date();
        next.setTime(cur.getTime()-10000000);
        // 1. start > end
        Assert.assertThrows(Exception.class, ()-> rentalService.book(1,cur,next));

        // 2. error case
        next.setTime(cur.getTime()+10000);
        BookingVo bookingVo = new BookingVo().setId(1).setCarId(1)
                .setStartTime(cur).setEndTime(next);
        Mockito.when(bookingDao.queryBookingListByCarId(Mockito.anyInt())).thenReturn(Arrays.asList(bookingVo));
        Mockito.when(bookingDao.booking(Mockito.anyInt(), Mockito.any(), Mockito.any())).thenReturn(1);
        Date from  = new Date();
        Date to = new Date();
        from.setTime(cur.getTime()-1000);
        to.setTime(cur.getTime()+1000);
        Assert.assertThrows(Exception.class, ()-> rentalService.book(1,from,to));
        from.setTime(cur.getTime()+100);
        to.setTime(cur.getTime()+10001);
        Assert.assertThrows(Exception.class, ()-> rentalService.book(1,from,to));
        from.setTime(cur.getTime()-100);
        Assert.assertThrows(Exception.class, ()-> rentalService.book(1,from,to));
        to.setTime(cur.getTime()+200);
        Assert.assertThrows(Exception.class, ()-> rentalService.book(1,from,to));
        // normal
        from.setTime(next.getTime()+1);
        to.setTime(from.getTime()+10000);
        Assert.assertEquals(new Integer(1), rentalService.book(1, from,to));
    }

    @Test
    public void cancel() throws Exception {
        Integer cancelId = 1;
        Date cur = new Date();
        cur.setTime(System.currentTimeMillis()+10000);
        BookingVo bookingVo = new BookingVo()
                .setStartTime(cur)
                .setEndTime(cur)
                .setId(1)
                .setCarId(cancelId);
        Mockito.when(bookingDao.bookingVoList()).thenReturn(Arrays.asList(bookingVo));
        Mockito.when(bookingDao.cancel(Mockito.anyInt())).thenReturn(cancelId);
        Assert.assertThrows(Exception.class, () -> rentalService.cancel(2));
        Assert.assertEquals(rentalService.cancel(1), cancelId);
    }

    @Test
    public void bookingList() {
        BookingVo bookingVo = new BookingVo()
                .setStartTime(new Date())
                .setEndTime(new Date())
                .setId(1)
                .setCarId(1);
        Mockito.when(bookingDao.bookingVoList()).thenReturn(Arrays.asList(bookingVo));
        assertEquals(rentalService.bookingList().get(0), bookingVo);
    }
}