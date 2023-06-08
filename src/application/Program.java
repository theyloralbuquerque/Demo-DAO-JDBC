package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerdao(); // Chama o m�todo que cria um Dao e armazena em sellerDao.
		
		System.out.println("====TEST 01: Seller findById====");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);	
		
		System.out.println("\n====TEST 02: Seller findByDepartment====");
		Department department = new Department(2, null);   // Cria��o da vari�vel department do tipo department recebendo o id 2 e o name null.
		List<Seller> list = sellerDao.findByDepartment(department); // Cria��o da Lista list do tipo Seller recebendo o retorno do m�todo .findByDepartment().
		for (Seller obj : list) {    // for each para exibir os elementos da Lista list.
			System.out.println(obj);
		}
	}
}