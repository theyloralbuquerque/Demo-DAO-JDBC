package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerdao(); // Chama o método que cria um Dao e armazena em sellerDao.
		
		System.out.println("====TEST 01====");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);	
	}
}