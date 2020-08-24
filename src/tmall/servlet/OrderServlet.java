package tmall.servlet;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Miracle- on 2020/8/24 9:59
 */
public class OrderServlet extends BaseBackServlet{
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
        final int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.get(id);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderDAO.waitConfirm);
        orderDAO.update(order);
        return "@admin_order_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        final List<Order> list = orderDAO.list(page.getStart(), page.getCount());
        orderItemDAO.fill(list);
        final int total = orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("os", list);
        request.setAttribute("page",page);
        return "admin/listOrder.jsp";
    }
}
