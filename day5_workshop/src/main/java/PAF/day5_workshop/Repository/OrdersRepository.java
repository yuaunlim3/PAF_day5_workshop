package PAF.day5_workshop.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import PAF.day5_workshop.Model.Orders;
import PAF.day5_workshop.Model.OrdersDetails;
import PAF.day5_workshop.Utils.SQL;

@Repository
public class OrdersRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Orders> getOrders() {
        List<Orders> orderList = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SqlRowSet rs = template.queryForRowSet(SQL.getAllOrders);
        while (rs.next()) {
            Orders orders = new Orders();
            orders.setOrder_id(rs.getInt("order_id"));
            orders.setOrder_date(LocalDate.parse(rs.getString("order_date"), formatter));
            orders.setCustomer_name(rs.getString("customer_name"));
            orders.setNotes(rs.getString("notes"));
            orders.setShip_address(rs.getString("ship_address"));
            orders.setTax(rs.getDouble("tax"));
            orderList.add(orders);
        }

        return orderList;
    }

    public int getID() {
        SqlRowSet rw = template.queryForRowSet(SQL.getAllOrders);
        int id = 0;
        while (rw.next()) {
            id = rw.getInt("order_id");
        }

        return id + 1;
    }

    public void saveToSQL(Orders orders){
        template.update(SQL.insertOrder,orders.getOrder_date(),orders.getCustomer_name(),orders.getShip_address(),orders.getNotes(),orders.getTax());

        List<OrdersDetails> ordersDetails = orders.getOrdersDetails();
        for(OrdersDetails order: ordersDetails){
            template.update(SQL.insertOrderDetails,order.getOrder_id(),order.getProduct(),order.getUnit_price(),order.getDiscount(),order.getQuantity());
        }
    }

    public double getTotal(int id){
        double total = 0;
        SqlRowSet rs = template.queryForRowSet(SQL.getTotal,id);
        while(rs.next()){
            total = rs.getDouble("sum");

        }

        return total;
    }

    public List<OrdersDetails> getDetails(int id) {
        List<OrdersDetails> ordersDetails = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL.getAllDetails, id);

        while (rs.next()) {
            OrdersDetails details = new OrdersDetails();
            details.setId(rs.getInt("id"));
            details.setOrder_id(rs.getInt("order_id"));
            details.setProduct(rs.getString("product"));
            details.setUnit_price(rs.getDouble("unit_price"));
            details.setDiscount(rs.getDouble("discount"));
            details.setQuantity(rs.getInt("quantity"));

            ordersDetails.add(details);

        }

        return ordersDetails;
    }
}
