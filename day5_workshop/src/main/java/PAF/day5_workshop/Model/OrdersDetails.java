package PAF.day5_workshop.Model;

import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class OrdersDetails {
    private String product;
    private double unit_price;
    private double discount;
    private int quantity;
    private int order_id;
    private int id;

    public OrdersDetails() {   }

    public String getProduct() { return product;    }
    public void setProduct(String product) {this.product = product;   }

    public double getUnit_price() { return unit_price;  }
    public void setUnit_price(double unit_price) {   this.unit_price = unit_price;   }

    public double getDiscount() {    return discount;   }
    public void setDiscount(double discount) {     this.discount = discount;   }

    public int getQuantity() {     return quantity;   }
    public void setQuantity(int quantity) {    this.quantity = quantity;   }

    public int getOrder_id() {  return order_id;   }
    public void setOrder_id(int order_id) {this.order_id = order_id;}

    public int getId() {return id;}
    public void setId(int id) {  this.id = id; }
    

    public static OrdersDetails fromJson(JsonObject json){
        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setProduct(json.getString("product"));
        ordersDetails.setQuantity(json.getInt("quantity"));
        ordersDetails.setUnit_price(json.getJsonNumber("unit_price").doubleValue());
        ordersDetails.setDiscount(json.getJsonNumber("discount").doubleValue());
        ordersDetails.setOrder_id(json.getInt("order_id"));

        return ordersDetails;
    }
    
    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("product", product)
                .add("unit_price",unit_price)
                .add("discount",discount)
                .add("quantity",quantity)
                .add("order_id",order_id)
                .build();
    }
}
