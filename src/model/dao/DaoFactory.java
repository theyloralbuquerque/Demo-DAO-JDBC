package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory { // Esta classe vai ter opera��es est�ticas para instanciar os Daos.
	
	public static SellerDao createSellerDao() { // M�todo que cria/instancia um novo SellerDaoJDBC.
		return new SellerDaoJDBC(DB.getConnection()); // Instancia um novo SellerDaoJDBC passando uma conex�o como par�metro.
	}

	public static DepartmentDao createDepartmentDao() { // M�todo que cria/instancia um novo SellerDaoJDBC.
		return new DepartmentDaoJDBC(DB.getConnection()); // Instancia um novo SellerDaoJDBC passando uma conex�o como par�metro.
	}
}