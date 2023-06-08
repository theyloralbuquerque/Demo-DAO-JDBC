package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO seller "
										+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
										+ "VALUES "
										+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());  // Primeiro navega pelo obj e depois chama o getName() a partir de obj.
			st.setString(2, obj.getEmail()); // Primeiro navega pelo obj e depois chama o getEmail()  a partir de obj.
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // Primeiro navega pelo obj e depois chama o getBirthDate().
			st.setDouble(4, obj.getBaseSalary()); 	   // Primeiro navega pelo obj e depois chama o getBaseSalary()  a partir de obj.
			st.setInt(5, obj.getDepartment().getId()); // Primeiro navega pelo obj e depois chama o getDepartment() para entrar na dependência department e depois pega o .getId() da classe Department, tudo isso a partir de obj.
			
			int rowsAffected = st.executeUpdate(); // Executa o comando SQL armazenado em st e retorna a qtde de linhas afetadas.
			
			if (rowsAffected > 0) { // Se rowsAffected tiver um valor maior que 0.
				ResultSet rs = st.getGeneratedKeys(); // .getGeneratedKeys() retorna as chaves geradas automaticamente após a execução de uma instrução SQL de inserção.
				if (rs.next()) { 	// Se a próxima linha (linha 1) de rs tiver algum valor.
					int id = rs.getInt(1); // id vai receber o valor da coluna 1, que é a coluna id e primeira coluna das chaves retornadas.
					obj.setId(id);  // obj.setId(id) atribui o id gerado ao objeto obj.
				}
				DB.closeResultSet(rs); // Fecha o ResultSet.
			}
			else { // Se nenhuma linha for alterada.
				throw new DbException("Unexpected error! No rows affected!"); // Lança uma DbException.
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) { // Método responsável por atualizar dados no BD.
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE seller "
									   + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
									   + "WHERE Id = ?");
			
			st.setString(1, obj.getName());  // Primeiro navega pelo obj e depois chama o getName() a partir de obj.
			st.setString(2, obj.getEmail()); // Primeiro navega pelo obj e depois chama o getEmail()  a partir de obj.
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // Primeiro navega pelo obj e depois chama o getBirthDate().
			st.setDouble(4, obj.getBaseSalary()); 	   // Primeiro navega pelo obj e depois chama o getBaseSalary()  a partir de obj.
			st.setInt(5, obj.getDepartment().getId()); // Primeiro navega pelo obj e depois chama o getDepartment() para entrar na dependência department e depois pega o .getId() da classe Department, tudo isso a partir de obj.
			st.setInt(6, obj.getId());  	 // Primeiro navega pelo obj e depois chama o getId()  a partir de obj.
			
			st.executeUpdate(); // Executa o comando SQL armazenado em st.
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?"); // Pega o comando sql dentro dos parêntes e retorna o resultado para um objeto PreparedStatement.
			
			st.setInt(1, id); // Define o primeiro placeholder como o valor de id que foi passado como parâmentro.
			
			st.executeUpdate(); // Executa o comando SQL armazenado em st.
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
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
				Department dep = instantiateDepartment(rs);		// Criação da variável dep do tipo Department() recebendo o retorno do método instantiateDepartment(rs).
				Seller obj = instantiateSeller(rs, dep);		// Criação da variável obj do tipo Seller() recebendo o retorno do método instantiateSeller(rs, dep).
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));				// Chama o método setId() passando como parâmetro o valor da coluna Id.
		obj.setName(rs.getString("Name"));		// Chama o método setName() passando como parâmetro o valor da coluna Name.
		obj.setEmail(rs.getString("Email"));	// Chama o método setEmail() passando como parâmetro o valor da coluna Email.
		obj.setBaseSalary(rs.getDouble("BaseSalary")); // Chama o método setBaseSalary() passando como parâmetro o valor da coluna BaseSalary.
		obj.setBirthDate(rs.getDate("BirthDate"));	   // Chama o método setBirthDate() passando como parâmetro o valor da coluna BirthDate.
		obj.setDepartment(dep);						   // Chama o método setDepartment() passando como parâmetro o valor do objeto dep.
		return obj;	
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();		// Criação da variável dep do tipo Department().
		dep.setId(rs.getInt("DepartmentId"));	// Chama o método setId() passando como parâmetro o valor da coluna DepartmentId.
		dep.setName(rs.getString("DepName"));	// Chama o método setName() passando como parâmetro o valor da coluna DepName.
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null; // Permite utilizar consultas sql utilizando placeholders. 
		ResultSet rs = null;         // Permite acessar e manipular os dados retornados pela consulta.
		try {
			st = conn.prepareStatement(     // Pega o comando sql dentro dos parêntes e retorna o resultado para um objeto PreparedStatement.
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name ");
			
			rs = st.executeQuery();			       // .executeUpdate() executa o comando sql armazenado em st e retorna o n° de linhas afetadas.
			
			List<Seller> list = new ArrayList<>(); // Lista list do tipo Seller para armazenar cada Seller que retornar da consulta.
			Map<Integer, Department> map = new HashMap<>();  // Criação de um map que tem uma chave tipo Integer e o valor tipo Department.
			
			while (rs.next()) {					   // Enquanto  a próxima linha armazenada em rs for verdadeira.
				
				Department dep = map.get(rs.getInt("DepartmentId")); // Pega o valor do map pela chave Integer (que é a coluna DepartmentId).
				
				if (dep == null) {  // Se o map.get() retornar null esse if será executado, pois a variável dep receberá null.
					dep = instantiateDepartment(rs);	// A variável dep do tipo Department() recebendo o retorno do método instantiateDepartment(rs).
					map.put(rs.getInt("DepartmentId"), dep); // Adiciona no map um novo funcionário como valor e o DepartmentId como chave.
				}
				
				Seller obj = instantiateSeller(rs, dep);	 // Criação da variável obj do tipo Seller() recebendo o retorno do método instantiateSeller(rs, dep).
				list.add(obj);  				        // Adiciona um novo funcionário à list. 
				
			}
			return list;
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null; // Permite utilizar consultas sql utilizando placeholders. 
		ResultSet rs = null;         // Permite acessar e manipular os dados retornados pela consulta.
		try {
			st = conn.prepareStatement(     // Pega o comando sql dentro dos parêntes e retorna o resultado para um objeto PreparedStatement.
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			
			st.setInt(1, department.getId());      // O primeiro placeholder recebe o valor do id do department passado como parâmetro.
			
			rs = st.executeQuery();			       // .executeUpdate() executa o comando sql armazenado em st e retorna o n° de linhas afetadas.
			
			List<Seller> list = new ArrayList<>(); // Lista list do tipo Seller para armazenar cada Seller que retornar da consulta.
			Map<Integer, Department> map = new HashMap<>();  // Criação de um map que tem uma chave tipo Integer e o valor tipo Department.
			
			while (rs.next()) {					   // Enquanto  a próxima linha armazenada em rs for verdadeira.
				
				Department dep = map.get(rs.getInt("DepartmentId")); // Pega o valor do map pela chave Integer (que é a coluna DepartmentId).
				
				if (dep == null) {  // Se o map.get() retornar null esse if será executado, pois a variável dep receberá null.
					dep = instantiateDepartment(rs);	// A variável dep do tipo Department() recebendo o retorno do método instantiateDepartment(rs).
					map.put(rs.getInt("DepartmentId"), dep); // Adiciona no map um novo funcionário como valor e o DepartmentId como chave.
				}
				
				Seller obj = instantiateSeller(rs, dep);	 // Criação da variável obj do tipo Seller() recebendo o retorno do método instantiateSeller(rs, dep).
				list.add(obj);  				        // Adiciona um novo funcionário à list. 
				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}