package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {

    public static void main(String[] args) {


        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

        System.out.println("#### TEST 1: department findById ####");
        Department department = departmentDAO.findById(3);
        System.out.println(department);

        System.out.println("\n#### TEST 2: seller findAll ####");
        List<Department> departmentList = departmentDAO.findAll();
        for (Department department1 : departmentList) {
            System.out.println(department1);
        }

        System.out.println("\n#### TEST 3: department insert ####");
        Department newDepartment = new Department(null, "Food");
        departmentDAO.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

    }

}
