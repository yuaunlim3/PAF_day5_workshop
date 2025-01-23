package PAF.day5_workshop.Utils;

public class SQL {
    public static final String insertOrder = """
            INSERT INTO orders(order_date,customer_name,ship_address,notes,tax)
                    values(?,?,?,?,?)
            """;

    public static final String insertOrderDetails = """
            INSERT INTO order_details(order_id,product,unit_price,discount,quantity)
                    values(?,?,?,?,?)
            """;

    public static final String getAllOrders = """
            SELECT
            o.order_id AS order_id,
            DATE_FORMAT(o.order_date,'%d/%m/%Y') AS order_date,
            o.customer_name as customer_name,
            o.ship_address as ship_address,
            o.notes as notes,
            o.tax as tax
            from orders as o
            """;

    public static final String getAllDetails = """
            SELECT * from order_details where order_id = ?;
            """;

    public static final String delete = """
            delete from orders where order_id = ?;
                            """;
    public static final String resetOrder = """
            alter table orders
            auto_increment = 1;
                    """;

    public static final String getTotal = """
                    SELECT SUM(unit_price) AS sum FROM order_details WHERE order_id = ?;
                    """;

}
