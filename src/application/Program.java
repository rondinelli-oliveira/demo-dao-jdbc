package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

        System.out.println("\n#### TEST 4: seller insert ####");
        Seller newSeller = new Seller(null, "Greg Green", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDAO.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n#### TEST 5: seller update ####");
        seller = sellerDAO.findById(8);
        seller.setName("Martha Waine");
        seller.setEmail("marthawaine@gmail.com");
        seller.setBirthDate(new Date());
        seller.setBaseSalery(5000.0);
        sellerDAO.update(seller);
        System.out.println("Updated completed! " + seller.getId());

        System.out.println("\n#### TEST 6: seller delete ####");
        System.out.print("Enter id for delete test: ");
        int id = scanner.nextInt();
        sellerDAO.delete(id);
        System.out.println("Delete completed! ");

        scanner.close();
    }

}
