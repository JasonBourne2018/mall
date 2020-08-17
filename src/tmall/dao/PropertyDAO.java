package tmall.dao;

import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle- on 2020/8/16 23:34
 */
public class PropertyDAO {
    public int getTotal(int cid) {
        int total = 0;
        try(final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            String sql = "select count(*) from property where cid=" + cid;
            final ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Property property) {
        String sql = "insert into property values(null,?,?)";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,property.getCategory().getId());
            preparedStatement.setString(2,property.getName());
            preparedStatement.execute();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                final int id = rs.getInt(1);
                property.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Property property) {
        String sql = "update property set cid=?,name=? where id=?";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,property.getCategory().getId());
            preparedStatement.setString(2,property.getName());
            preparedStatement.setInt(3, property.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "delete from propery where id="+id;
        try (final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Property get(int id) {
        Property property = null;
        String sql = "select * from property where id=" + id;
        try(final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            final ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                property = new Property();
                property.setId(id);
                property.setCategory(new CategoryDAO().get(rs.getInt("cid")));
                property.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    public List<Property> list(int cid) {
        return list(cid,0, Short.MAX_VALUE);
    }
    public List<Property> list(int cid,int start, int count) {
        String sql = "select * from property where cid=? order by id desc limit ?,?";
        List<Property> properties = new ArrayList<>();
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,cid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                final Property property = new Property();
                property.setName(rs.getString("name"));
                property.setCategory(new CategoryDAO().get(cid));
                property.setId(rs.getInt("id"));
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
