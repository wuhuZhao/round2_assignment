# round2 Assignment
## Design
### Mock Table
the ddl is mock by CarVo.java And BookingVo.java
```sql
CREATE TABLE IF NOT EXISTS `carVo`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `carModel` VARCHAR(100) NOT NULL,
   PRIMARY KEY ( `runoob_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```sql
CREATE TABLE IF NOT EXISTS `bookingVo`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `carId` INT(11) NOT NULL,
   `startTime` DATETIME NOT NULL,
   `endTime` DATETIME NOT NULL,
   PRIMARY KEY ( `runoob_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
### API
[swagger][swagger]

### Procedure
#### book
```json
 // GET  http://42.194.133.175:10111/cars
 {
  "code": 200,
  "data": [
    {
      "id": 1,
      "carModel": "Toyota Camry"
    },
    {
      "id": 2,
      "carModel": "Toyota Camry"
    },
    {
      "id": 3,
      "carModel": "BMW 650"
    },
    {
      "id": 4,
      "carModel": "BMW 650"
    }
  ],
  "msg": ""
}
```
```json
// POST http://42.194.133.175:10111/book
// req
{
  "carId": 1,
  "endTime": "2022-07-03T07:51:26.618Z",
  "startTime": "2022-07-03T07:59:26.618Z"
}
// resp
{
  "code": 200,
  "data": 1,
  "msg": null
}
```
#### cancle
```json
// GET http://42.194.133.175:10111/book/lists
// resp
{
  "code": 200,
  "data": [
  	  "id":1,
		"carId": 1,
		"endTime": "2022-07-03T07:51:26.618Z",
		"startTime": "2022-07-03T07:59:26.618Z"
  ],
  "msg": ""
}
```
```
// POST http://42.194.133.175:10111/cancel
// req
{
  "id": 1
}
//resp
{
  "code": 200,
  "data": 1,
  "msg": ""
}
```
#### valid request
```
// POST http://42.194.133.175:10111/cancel
// req
{
  "id": 1
}
{
  "code": -1,
  "data": null,
  "msg": "booking id is not exists"
}
```
### deploy(Tencent Cloud)
```
mvn clean && mvn package
docker run -itd --name assignment -v /root/assignment:/root/ -p 10111:10111 --net brpc loblaw/java8:latest
nohup java -jar root/round2_assignment-0.0.1-SNAPSHOT.jar
```
[swagger]: http://42.194.133.175:10111/swagger-ui.html#/rental-controller "swagger"