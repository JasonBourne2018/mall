package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle- on 2020/8/16 14:06
 */
public class CategoryDAO {
    public int getTotal() {
        int total = 0;
        try(Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from category";
            final ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public void add(Category category) {
        String sql = "insert into category values(null,?,?)";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,category.getName());
            ps.execute();
            final ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                category.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Category category) {
        String sql = "update category set name=? where id=?";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,category.getName());
            preparedStatement.setInt(2,category.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try(final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            String sql = "delete from category where id="+id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Category get(int id) {
        Category category = null;
        try(final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            String sql = "select * from category where id="+id;
            final ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }
    public List<Category> list(int start, int count) {
        List<Category> categories = new ArrayList<>();
        String sql = "select * from category order by id desc limit ?,?";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,start);
            ps.setInt(2,count);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
