package PAF.day5_workshop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PAF.day5_workshop.Model.Orders;
import PAF.day5_workshop.Model.OrdersDetails;
import PAF.day5_workshop.Repository.OrdersRepository;
import PAF.day5_workshop.Repository.RedisRepository;

@Service
public class OrderServices {
    @Autowired private RedisRepository redisRepository;
    @Autowired private OrdersRepository ordersRepository;

    public List<String> getAllCustomers(){
        return redisRepository.getAllCustomers();
    }

    public List<Orders> getOrders(){
        return ordersRepository.getOrders();
    }

    public void addOrderInfo(Orders order){
        int id = ordersRepository.getID();
        order.setOrder_id(id);
        redisRepository.addOrderInfo(order);
    }

    public List<OrdersDetails> addOrderDetails(OrdersDetails ordersDetails, String name){
        return redisRepository.addOrder(ordersDetails, name);
    }

    public int saveToSQL(String name){
        Orders order = redisRepository.getDetails(name);
        ordersRepository.saveToSQL(order);
        return order.getOrder_id();

    }
  
    public double getTotal(int id){
        return ordersRepository.getTotal(id);
    }

    
    public List<OrdersDetails> getAllDetails(int id){
        return ordersRepository.getDetails(id);
    }



}
