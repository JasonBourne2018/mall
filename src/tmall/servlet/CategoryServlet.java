package tmall.servlet;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Miracle- on 2020/8/17 18:50
 */
public class CategoryServlet extends BaseBackServlet {
    
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        String name = params.get("name");
        Category category = new Category();
        category.setName(name);
        categoryDAO.add(category);
        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");

        try {
            if (null!=is && 0!=is.available()) {
                try(FileOutputStream fos = new FileOutputStream(file)) {
                    byte b[] = new byte[1024*1024];
                    int length = 0;
                    while (-1!=(length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    // 保存为jpg
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img,"jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDAO.get(id);
        request.setAttribute("c",category);
        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        Category category = new Category();
        category.setId(Integer.parseInt(params.get("id")));
        category.setName(params.get("name"));
        categoryDAO.update(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        file.getParentFile().mkdirs();
        try {
            if (null!=is && 0!=is.available()) {
                try(FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length=is.read(bytes))) {
                        fos.write(bytes, 0 ,length);
                    }
                    fos.flush();

                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", cs);
        request.setAttribute("page", page);
        return "admin/listCategory.jsp";
    }
}
