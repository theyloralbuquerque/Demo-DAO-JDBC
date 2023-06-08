package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerdao(); // Chama o método que cria um Dao e armazena em sellerDao.
		
		System.out.println("====TEST 01: Seller findById====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);	
		
		System.out.println("\n====TEST 02: Seller findByDepartment====");
		Department department = new Department(2, null);   // Criação da variável department do tipo department recebendo o id 2 e o name null.
		List<Seller> list = sellerDao.findByDepartment(department); // Criação da Lista list do tipo Seller recebendo o retorno do método .findByDepartment().
		for (Seller obj : list) {    // for each para exibir os elementos da Lista list.
			System.out.println(obj);
		}
		
		System.out.println("\n====TEST 03: Seller findAll====");
		list = sellerDao.findAll(); // Chamada do método findAll() a partir do objeto sellerDao, armazenando o retorno em list.
		for (Seller obj : list) {   // for each para exibir os elementos da Lista list.
			System.out.println(obj);
		}
		
		System.out.println("\n====TEST 04: Seller insert====");
		Seller newseller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newseller);
		System.out.println("Inserted! New id = " + newseller.getId());
	}
}