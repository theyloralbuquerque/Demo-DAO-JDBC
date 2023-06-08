package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); // Chama o método que cria um Dao e armazena em sellerDao.
		
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
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department); // Instanciação de um novo seller.
		sellerDao.insert(newSeller); // Chamada do método insert, passando como argumento o newSeller.
		System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("\n====TEST 05: Seller update====");
		seller = sellerDao.findById(1); // .findById(1) vai retornar o vendedor que tem o id 1 e armazenar em seller. 
		seller.setName("Martha Waine"); // Vai alterar o nome do vendedor armazenado em seller.  
		sellerDao.update(seller);	    // Vai atualizar os dados do vendedor com base no id armazenado em seller e com os dados de seller. 
		System.out.println("Updated completed!");
		
		System.out.println("\n====TEST 06: Seller delete====");
		System.out.print("Digite um id para ser deletado: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id); // .deleteById(id) vai deletar uma linha do BC com base no id passado como parâmetro. 
		System.out.println("Delete completed!");
	}
}