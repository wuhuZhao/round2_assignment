package com.example.round2_assignment.dao;


import com.example.round2_assignment.vo.CarVo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CarDao {
    private List<CarVo> db = new ArrayList<>(Arrays.asList(new CarVo().setCarModel("Toyota Camry").setId(1) ,
            new CarVo().setCarModel("Toyota Camry").setId(2),
            new CarVo().setCarModel("BMW 650").setId(3),
            new CarVo().setCarModel("BMW 650").setId(4)));

    /***
     * 获取所有的汽车list
     * @return
     */
    public List<CarVo> getCars() {
        List<CarVo> result = new ArrayList<>();
        result.addAll(db);
        return result;
    }

    /**
     * 通过car的主表id获取car
     * @param id
     * @return
     * @throws Exception
     */
    public CarVo getCarById(Integer id) throws Exception {
        return db.stream().filter(d -> d.getId().equals(id)).findFirst().orElseThrow(() -> new Exception("car id not found"));
    }

    /**
     * 通过car的类型获取cars
     * @param type
     * @return
     */
    public List<CarVo> getCarsByType(String type) {
        return db.stream().filter(d -> d.getCarModel().equals(type)).collect(Collectors.toList());
    }

}
