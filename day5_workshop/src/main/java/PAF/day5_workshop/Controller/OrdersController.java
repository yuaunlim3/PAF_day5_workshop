package PAF.day5_workshop.Controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import PAF.day5_workshop.Model.Orders;
import PAF.day5_workshop.Model.OrdersDetails;
import PAF.day5_workshop.Model.exception.LimitException;
import PAF.day5_workshop.Service.OrderServices;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "/", "index.html" })
public class OrdersController {
    @Autowired
    private OrderServices orderServices;

    @GetMapping()
    public ModelAndView start() {
        ModelAndView mav = new ModelAndView("homepage");
        List<Orders> orderList = orderServices.getOrders();
        mav.addObject("orderList", orderList);
        return mav;
    }

    @GetMapping("/order")
    public ModelAndView goToAddPage(HttpSession session) {
        List<OrdersDetails> orders = new LinkedList<>();
        ModelAndView mav = new ModelAndView("orderpage");
        List<String> customersName = orderServices.getAllCustomers();
        mav.addObject("customerNames", customersName);
        session.setAttribute("orderList", orders);
        return mav;

    }

    @PostMapping("/addDetails")
    public ModelAndView addDetails(@RequestBody MultiValueMap<String, String> form) {
        ModelAndView mav = new ModelAndView("orderpage");
        Orders order = new Orders();
        LocalDate order_date = LocalDate.parse(form.getFirst("order_date"));
        String name = form.getFirst("customer_name");
        double tax = Double.parseDouble(form.getFirst("tax"));
        String notes = form.getFirst("notes");
        String address = form.getFirst("ship_address");
        order.setCustomer_name(name);
        order.setNotes(notes);
        order.setOrder_date(order_date);
        order.setTax(tax);
        order.setShip_address(address);
        List<OrdersDetails> ordersDetails = new LinkedList<>();
        order.setOrdersDetails(ordersDetails);

        orderServices.addOrderInfo(order);

        mav.addObject("orderInfo", true);
        mav.addObject("name", name);

        return mav;
    }

    @PostMapping("/order")
    public ModelAndView addOrder(@RequestBody MultiValueMap<String, String> form, @RequestParam String name) {
        ModelAndView mav = new ModelAndView("orderpage");
        String product = form.getFirst("product");
        double unit_price = Double.parseDouble(form.getFirst("unit_price"));
        double discount = Double.parseDouble(form.getFirst("discount"));
        int quantity = Integer.parseInt(form.getFirst("quantity"));
        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setProduct(product);
        ordersDetails.setUnit_price(unit_price);
        ordersDetails.setDiscount(discount);
        ordersDetails.setQuantity(quantity);
        List<OrdersDetails> orderList = orderServices.addOrderDetails(ordersDetails, name);
        mav.addObject("name", name);
        mav.addObject("orderList", orderList);
        mav.addObject("orderInfo", true);

        return mav;
    }

    @PostMapping("/submit")
    public ModelAndView addOrderDetails(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("submit");
        int id = orderServices.saveToSQL(name);
        System.out.println("DATA SAVE TO MYSQL");
        double total = orderServices.getTotal(id);
        mav.addObject("total", total);
        return mav;
    }

    @GetMapping("/orderInfo/{id}")
    public ModelAndView getInfo(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("orderInfo");
        List<OrdersDetails> allDetails = orderServices.getAllDetails(id);
        mav.addObject("detailsList", allDetails);
        return mav;
    }

}
