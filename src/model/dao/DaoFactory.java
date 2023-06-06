package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory { // Esta classe vai ter operações estáticas para instanciar os Daos.
	
	public static SellerDao createSellerdao() { // Método que cria/instancia um novo SellerDaoJDBC.
		return new SellerDaoJDBC();
	}

}