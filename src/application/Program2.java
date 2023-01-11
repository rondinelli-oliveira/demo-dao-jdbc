package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

        System.out.println("#### TEST 1: department findById ####");
        Department department = departmentDAO.findById(3);
        System.out.println(department);

        System.out.println("\n#### TEST 2: seller findAll ####");
        List<Department> departmentList = departmentDAO.findAll();
        for (Department department1 : departmentList) {
            System.out.println(department1);
        }

//        System.out.println("\n#### TEST 3: department insert ####");
//        Department newDepartment = new Department(null, "Food");
//        departmentDAO.insert(newDepartment);
//        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n#### TEST 4: department update ####");
        department = departmentDAO.findById(7);
        department.setName("Shoes");
        departmentDAO.update(department);
        System.out.println("Updated completed! " + department.getId());

        System.out.println("\n#### TEST 5: department delete ####");
        System.out.print("Enter id for delete test: ");
        int id = scanner.nextInt();
        departmentDAO.deleteById(id);
        System.out.println("Delete completed! ");

        scanner.close();

    }

}
