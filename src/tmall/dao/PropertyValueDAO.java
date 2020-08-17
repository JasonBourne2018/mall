package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle- on 2020/8/17 7:38
 */
public class PropertyValueDAO {
    public int total() {
        int total = 0;
        String sql = "select count(*) from propertyValue";
        try(final Connection connection = DBUtil.getConnection(); final Statement statement = connection.createStatement()) {
            final ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue propertyValue) {
        String sql = "insert into property values(null,?,?)";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,propertyValue.getProduct().getId());
            preparedStatement.setInt(2,propertyValue.getProperty().getId());
            preparedStatement.setString(3,propertyValue.getValue());
            preparedStatement.execute();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                propertyValue.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PropertyValue propertyValue) {
        String sql = "update propertyValue set pid=?, ptid=?,value=? where id=?";
        try(final Connection connection = DBUtil.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,propertyValue.getProduct().getId());
            preparedStatement.setInt(2,propertyValue.getProperty().getId());
            preparedStatement.setString(3,propertyValue.getValue());
            preparedStatement.setInt(4,propertyValue.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "delete from PropertyValue where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public PropertyValue get(int id) {
        PropertyValue bean = new PropertyValue();

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from PropertyValue where id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }
    public PropertyValue get(int ptid, int pid ) {
        PropertyValue bean = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from PropertyValue where ptid = " + ptid + " and pid = " + pid;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                bean= new PropertyValue();
                int id = rs.getInt("id");

                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public void init(Product product) {
        List<Property> pts = new PropertyDAO().list(product.getCategory().getId());
        for (Property pt : pts) {
            PropertyValue pv = get(pt.getId(), product.getId());
            if (null==pv) {
                pv = new PropertyValue();
                pv.setProduct(product);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue where pid = ? order by ptid desc";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
}
