package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.PropertyDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Miracle- on 2020/8/19 15:01
 */
public class PropertyServlet extends BaseBackServlet{
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        String name = request.getParameter("name");
        Property property = new Property();
        property.setCategory(category);
        property.setName(name);
        propertyDAO.add(property);

        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyDAO.get(id);
        propertyDAO.delete(id);

        return "@admin_property_list?cid="+property.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyDAO.get(id);
        request.setAttribute("p", property);
        return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Category category = categoryDAO.get(cid);
        Property property = propertyDAO.get(id);
        property.setName(name);
        property.setCategory(category);
        property.setId(id);
        propertyDAO.update(property);
        return "@admin_property_list?cid="+property.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);
        int total = propertyDAO.getTotal(cid);
        List<Property> list = propertyDAO.list(cid, page.getStart(), page.getCount());

        page.setTotal(total);
        page.setParam("&cid="+category.getId());

        request.setAttribute("ps",list);
        request.setAttribute("c",category);
        request.setAttribute("page",page);

        return "admin/listProperty.jsp";
    }
}
