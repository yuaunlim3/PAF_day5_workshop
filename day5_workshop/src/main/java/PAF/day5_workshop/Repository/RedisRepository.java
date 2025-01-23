package PAF.day5_workshop.Repository;

import java.io.StringReader;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import PAF.day5_workshop.Model.Orders;
import PAF.day5_workshop.Model.OrdersDetails;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class RedisRepository {
    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    public void addAppName(String appName) {
        if(!checkName(appName)){
            template.opsForList().leftPush("registrations", appName);
        }
    }

    private Boolean checkName(String appName) {
        List<String> registeredNames = template.opsForList().range("registrations", 0, -1);
        return registeredNames != null && registeredNames.contains(appName);
    }

    public List<String> getAllCustomers(){
        return template.opsForList().range("registrations", 0, -1);
    }

    public void addOrderInfo(Orders order){
        JsonObject json = order.toJson();
        template.opsForList().leftPush(order.getCustomer_name(), json.toString());
        template.expire(order.getCustomer_name(), Duration.ofMinutes(5));
    }

    public List<OrdersDetails> addOrder(OrdersDetails ordersDetails,String name){
        String data = template.opsForList().leftPop(name);
        StringReader reader = new StringReader(data);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();
        Orders order = Orders.fromJson(jsonObject);
        ordersDetails.setOrder_id(order.getOrder_id());
        List<OrdersDetails> currentOrdersDetails = order.getOrdersDetails();
        currentOrdersDetails.add(ordersDetails);
        order.setOrdersDetails(currentOrdersDetails);
        JsonObject newObject = order.toJson();
        template.opsForList().leftPush(name, newObject.toString());

        return currentOrdersDetails;
        
    }

    public Orders getDetails(String name){
        String data = template.opsForList().leftPop(name);
        StringReader reader = new StringReader(data);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();
        Orders order = Orders.fromJson(jsonObject);
        return order;
    }
}
