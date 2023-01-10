package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("#### TEST 1: seller findById ####");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("\n#### TEST 2: seller findByDepartment ####");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDAO.findByDepartment(department);
        for (Seller seller1 : sellerList) {
            System.out.println(seller1);
        }

        System.out.println("\n#### TEST 3: seller findAll ####");
        sellerList = sellerDAO.findAll();
        for (Seller seller1 : sellerList) {
            System.out.println(seller1);
        }
    }

}
