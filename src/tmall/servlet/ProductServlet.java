package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Miracle- on 2020/8/19 18:24
 */
public class ProductServlet extends BaseBackServlet{
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        String name = request.getParameter("name");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        String subTitle = request.getParameter("subTitle");
        Integer stock = Integer.getInteger(request.getParameter("stock"));

        Product product = new Product();
        product.setName(name);
        product.setOriginalPrice(originalPrice);
        product.setCategory(category);
        product.setPromotePrice(promotePrice);
        product.setSubTitle(subTitle);
        product.setStock(stock);
        product.setCreateDate(new Date());
        productDAO.add(product);
        return "@admin_product_list?cid="+product.getCategory().getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);
        productDAO.delete(id);

        return "@admin_product_list?cid="+product.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);

        request.setAttribute("p",product);
        return "admin/editProduct.jsp";
    }

    public String editPropertyValue(HttpServletRequest request,HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);
        request.setAttribute("p",product);

        List<PropertyValue> pvs = propertyValueDAO.list(product.getId());
        request.setAttribute("pvs", pvs);
        return "admin/editProductValue.jsp";
    }

    public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue propertyValue = propertyValueDAO.get(pvid);
        propertyValue.setValue(value);
        propertyValueDAO.update(propertyValue);
        return "%success";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        String subTitle = request.getParameter("subTitle");
        Integer stock = Integer.getInteger(request.getParameter("stock"));

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setOriginalPrice(originalPrice);
        product.setCategory(category);
        product.setPromotePrice(promotePrice);
        product.setSubTitle(subTitle);
        product.setStock(stock);
        productDAO.update(product);
        return "@admin_product_list?cid="+product.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);
        List<Product> list = productDAO.list(cid, page.getStart(), page.getCount());
        page.setTotal(productDAO.getTotal(cid));
        page.setParam("&cid="+ category.getId());

        request.setAttribute("ps", list);
        request.setAttribute("c", category);
        request.setAttribute("page",page);

        return "admin/listProduct.jsp";
    }
}
