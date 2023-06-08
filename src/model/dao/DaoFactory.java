package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory { // Esta classe vai ter operações estáticas para instanciar os Daos.
	
	public static SellerDao createSellerDao() { // Método que cria/instancia um novo SellerDaoJDBC.
		return new SellerDaoJDBC(DB.getConnection()); // Instancia um novo SellerDaoJDBC passando uma conexão como parâmetro.
	}

	public static DepartmentDao createDepartmentDao() { // Método que cria/instancia um novo SellerDaoJDBC.
		return new DepartmentDaoJDBC(DB.getConnection()); // Instancia um novo SellerDaoJDBC passando uma conexão como parâmetro.
	}
}