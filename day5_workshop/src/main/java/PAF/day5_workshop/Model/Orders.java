package PAF.day5_workshop.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Orders {
    private int order_id;
    private LocalDate order_date;
    private String customer_name;
    private String ship_address;
    private String notes;
    private double tax;
    private List<OrdersDetails> ordersDetails;
    

    public int getOrder_id() {        return order_id;}
    public void setOrder_id(int order_id) {    this.order_id = order_id; }

    public LocalDate getOrder_date() {  return order_date; }
    public void setOrder_date(LocalDate order_date) {    this.order_date = order_date;  }

    public String getCustomer_name() {    return customer_name; }
    public void setCustomer_name(String customer_name) {    this.customer_name = customer_name; }

    public String getShip_address() {     return ship_address; }
    public void setShip_address(String ship_address) {   this.ship_address = ship_address;  }

    public String getNotes() {   return notes;}
    public void setNotes(String notes) {  this.notes = notes; }

    public double getTax() {  return tax;    }
    public void setTax(double tax) {  this.tax = tax; }


    public List<OrdersDetails> getOrdersDetails() {   return ordersDetails;   }
    public void setOrdersDetails(List<OrdersDetails> ordersDetails) {  this.ordersDetails = ordersDetails; }


    public static Orders fromJson(JsonObject json){
        Orders order = new Orders();
        order.setCustomer_name(json.getString("customer_name"));
        order.setOrder_date(LocalDate.parse(json.getString("order_date")));
        order.setShip_address(json.getString("ship_address"));
        order.setTax(json.getJsonNumber("tax").doubleValue());
        order.setOrder_id(json.getInt("order_id"));
        JsonArray details = json.getJsonArray("line_items");
        List<OrdersDetails> ordersDetails = new LinkedList<>();
        for(int i = 0; i < details.size();i++){
            JsonObject jsonObj = details.getJsonObject(i);
            OrdersDetails orders = OrdersDetails.fromJson(jsonObj);
            ordersDetails.add(orders);
        }
        order.setOrdersDetails(ordersDetails);
        order.setNotes(json.getString("notes"));
        return order;
    }

    public JsonObject toJson(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JsonArrayBuilder orderDetails = Json.createArrayBuilder();
        for(OrdersDetails details: this.ordersDetails){
            orderDetails.add(details.toJson());
        }

        return Json.createObjectBuilder()
                .add("order_id",order_id)
                .add("customer_name",customer_name)
                .add("ship_address",ship_address)
                .add("notes",notes)
                .add("tax", tax)
                .add("order_date",this.order_date.format(formatter))
                .add("line_items", orderDetails)
                .build();
    }

    
}
