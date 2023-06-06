package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn; // Implementação da dependência com o Connection.
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null; // Permite utilizar consultas sql utilizando placeholders. 
		ResultSet rs = null;         // Permite acessar e manipular os dados retornados pela consulta.
		try {
			st = conn.prepareStatement(     // Pega o comando sql dentro dos parêntes e retorna o resultado para um objeto PreparedStatement.
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);               // O primeiro placeholder recebe o valor de id.
			rs = st.executeQuery();			// .executeUpdate() executa o comando sql armazenado em st e retorna o n° de linhas afetadas.
			if (rs.next()) {				// Se a próxima linha armazenada em rs for verdadeira.
				Department dep = new Department();		// Criação da variável dep do tipo Department().
				dep.setId(rs.getInt("DepartmentId"));	// Chama o método setId() passando como parâmetro o valor da coluna DepartmentId.
				dep.setName(rs.getString("DepName"));	// Chama o método setName() passando como parâmetro o valor da coluna DepName.
				Seller obj = new Seller();				// Criação da variável obj do tipo Seller().
				obj.setId(rs.getInt("Id"));				// Chama o método setId() passando como parâmetro o valor da coluna Id.
				obj.setName(rs.getString("Name"));		// Chama o método setName() passando como parâmetro o valor da coluna Name.
				obj.setEmail(rs.getString("Email"));	// Chama o método setEmail() passando como parâmetro o valor da coluna Email.
				obj.setBaseSalary(rs.getDouble("BaseSalary")); // Chama o método setBaseSalary() passando como parâmetro o valor da coluna BaseSalary.
				obj.setBirthDate(rs.getDate("BirthDate"));	   // Chama o método setBirthDate() passando como parâmetro o valor da coluna BirthDate.
				obj.setDepartment(dep);						   // Chama o método setDepartment() passando como parâmetro o valor do objeto dep.
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}