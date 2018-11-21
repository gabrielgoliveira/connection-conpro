package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;
import model.Endereco;
import model.Loja;
import model.Produto;

/**Realiza as operacoes relacionadas a loja no banco de dados
 * @author Gabriel Oliveira
 * */

public class LojaDAO {
	
	/**Cadastro de lojas no banco de dados
	 * @param Loja
	 * @return void
	 * */
	public static void createLoja(Loja novaLoja) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		Endereco novoEndereco = novaLoja.getEndereco();
		String sql;
		try {
			sql = "insert into lojas (razao_social, cnpj, senha, rua, bairro, cidade, estado) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, novaLoja.getRazaoSocial());
			stmt.setString(2, novaLoja.getCnpj());
			stmt.setString(3, novaLoja.getSenha());
			stmt.setString(4, novoEndereco.getRua());
			stmt.setString(5, novoEndereco.getBairro());
			stmt.setString(6, novoEndereco.getCidade());
			stmt.setString(7, novoEndereco.getEstado());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//fecha a conexao
			ConnectionFactory.closeConnection(con, stmt);
		}

	}
	/**Sobreescreve uma determinada loja no BD
	 * @param Loja loja
	 * @return void
	 * */

	public static void alterarLoja(Loja loja) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		Endereco endereco = loja.getEndereco();
		int id_loja;
		String sql, razaoSocial, cnpj, senha, rua, bairro, cidade, estado;
		try {
			sql = "update lojas set razao_social = ?, cnpj = ?, senha = ?, rua = ?, bairro = ?, cidade = ?, estado = ? where id = ?";
			stmt = con.prepareStatement(sql);
			
			//recuperando dados no objeto
			id_loja = loja.getId();
			razaoSocial = loja.getRazaoSocial();
			cnpj = loja.getCnpj();
			senha = loja.getSenha();
			rua = endereco.getRua();
			bairro = endereco.getBairro();
			cidade = endereco.getCidade();
			estado = endereco.getEstado();
			
			//setando a string mysql
			stmt.setString(1, razaoSocial);
			stmt.setString(2, cnpj);
			stmt.setString(3, senha);
			stmt.setString(4, rua);
			stmt.setString(5, bairro);
			stmt.setString(6, cidade);
			stmt.setString(7, estado);
			stmt.setInt(8, id_loja);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("Falha");
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	/**Obter loja apartir de um cnpj, em caso de erro retorna null
	 * @param String cnpj
	 * @return Loja loja
	 * */
	public static Loja obterLoja(String cnpj) {
		/*Tratar erro não achou loja
		 * */
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Loja novaLoja = null;
		Endereco novoEndereco;
		String sql;
		try {
			sql = "select * from lojas where cnpj = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cnpj);
			rs = stmt.executeQuery();
			rs.next();
			novoEndereco = new Endereco(rs.getString("rua"), rs.getString("bairro"), rs.getString("cidade"),
					rs.getString("estado"));
			novaLoja = new Loja(rs.getString("razao_social"), rs.getString("cnpj"), rs.getString("senha"),
					novoEndereco);
			novaLoja.setId(rs.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			//fecha a conexao
			ConnectionFactory.closeConnection(con, stmt);
			return novaLoja;
		}
		
	}
		
}
