package com.example.wangwangmicro.dao;

import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import com.example.wangwangmicro.constant.RoomType;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    //        以下是用户在购买界面需要调用的接口
    //  创建订单
    //  在创建reservation之后，如果reservation创建失败则不能创建订单。
    @Insert("INSERT INTO order (user_id, reservation_id, order_type, payment, order_create_time) " +
            "VALUES (#{userId}, #{reservationId}, #{orderType}, #{payment}, #{orderCreateTime}")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", before = false, resultType = int.class)
    int createOrder(Order Order);

    @Insert("INSERT INTO hotel_order (hotel_id, room_type, check_in_date, check_out_date) " +
            "VALUES (#{hotelId}, #{roomType}, #{checkInDate}, #{checkOutDate})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", before = false, resultType = int.class)
    int createHotelOrder(@Param("hotelId") int hotelId,
                         @Param("roomType") RoomType roomType,
                         @Param("checkInDate") LocalDate checkInDate,
                         @Param("checkOutDate") LocalDate checkOutDate);

    // 支付时调用
    @Update("UPDATE order SET state = 'PAID', pay_time = NOW() WHERE id = #{id}")
    int payOrder(@Param("id") int id);
    
    
    // 支付前选择支付方式时调用
    @Update("UPDATE order SET payment_method = #{paymentMethod} WHERE id = #{id}")
    int setOrderPaymentMethod(int id, PaymentMethod paymentMethod);
    
    
    // 以下是查询接口（用于用户界面查询/管理员界面查询）
    @Select("SELECT * FROM order WHERE id = #{id}")
    Order getOrder(int id);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId}")
    List<Order> getAllOrders(int userId);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_type = 'TRAIN_TICKET'")
    List<Order> getAllTrainTicketOrders(int userId);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_type = 'HOTEL'")
    List<Order> getAllHotelOrders(int userId);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_type = 'TRAIN_MEAL'")
    List<Order> getAllTrainMealOrders(int userId);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_create_time > NOW() - INTERVAL 10 DAY")
    List<Order> getLast10DaysOrders(int userId);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_type = #{orderType} AND order_create_time BETWEEN #{startDate} AND #{endDate}")
    List<Order> getOrdersByDateAndType(@Param("userId") int userId, @Param("orderType") OrderType orderType, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    
    @Select("SELECT * FROM order WHERE user_id = #{userId} AND order_type = #{orderType}")
    List<Order> getOrdersByType(@Param("userId") int userId, @Param("orderType") OrderType orderType);

    
    // 用户在（我的订单界面可选操作）（可能还有其他需求）
    @Delete("DELETE FROM order WHERE id = #{id}")
    int deleteOrder(int id);

    
    @Update("UPDATE order SET state = 'CANCELLED', finish_time = NOW() WHERE id = #{id}")
    int cancelOrder(@Param("id") int id);

    
    @Update("UPDATE order SET state = 'PAID', finish_time = NOW() WHERE id = #{id}")
    int confirmOrder(@Param("id") int id);
    
    
    @Select("select * from order where id = #{id} limit 1")
    Order selectOrderById(int id);
    
    
    // 在时间到达后自动调用（暂时不知道怎么用）
    @Update("UPDATE order SET state = 'FINISHED', finish_time = NOW() WHERE id = #{id}")
    int finishOrder(@Param("id") int id);
    // ，。。。
    
    
    @Update("UPDATE order SET user_id = #{userId}, reservation_id = #{reservationId}, order_type = #{orderType}, state = #{state}, payment = #{payment}, payment_method = #{paymentMethod}, pay_time = #{payTime}, finish_time = #{finishTime}, order_create_time = #{orderCreateTime} WHERE id = #{id}")
    void updateOrder(Order order);

}
