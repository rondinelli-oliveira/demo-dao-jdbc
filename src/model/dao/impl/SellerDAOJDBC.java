package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO {

    private Connection conn;

    public SellerDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try {

            st = conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalery());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try {

            st = conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalery());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void delete(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);
            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DbException("Id not exists! ");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getLocalizedMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setBaseSalery(rs.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()) {

                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellerList.add(seller);

            }
            return sellerList;
        } catch (SQLException e) {
            throw new DbException(e.getLocalizedMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()) {

                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellerList.add(seller);

            }
            return sellerList;
        } catch (SQLException e) {
            throw new DbException(e.getLocalizedMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
