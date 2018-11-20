package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;
import model.Endereco;
import model.Loja;
import model.Produto;

/**Realiza a conversação relacionadas a Lojas entre a interface grafica e o banco de dados
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
		try {
			stmt = con.prepareStatement("insert into lojas (razao_social, cnpj, senha, rua, bairro, cidade, estado) values (?, ?, ?, ?, ?, ?, ?)");
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
			//fecha a conexao
			new ConnectionFactory().closeConnection(con);
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
		}
	}

	/**Obter loja apartir de um cnpj
	 * @param String cnpj
	 * @return Loja loja
	 * */
	public static Loja obterLoja(String cnpj) {
		/*Tratar erro n�o achou loja
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
			//fecha a conexao
			new ConnectionFactory().closeConnection(con);
		}
		return novaLoja;
	}
	
	/**Obter o ID da loja apartir de um produto
	 * @param Produto p
	 * @return int id
	 * */
	public static int obterIdLoja(Produto p) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id_loja = 0;

		try {
			stmt = con.prepareStatement("SELECT id_loja FROM lojas_produtos WHERE id_produto = ?");
			stmt.setInt(1, p.getCodigo());
			rs = stmt.executeQuery();
			rs.next();
			id_loja = rs.getInt("id_loja");
		} catch (SQLException e) {
			e.getMessage();
			//fecha a conexao
			new ConnectionFactory().closeConnection(con);
		}

		return id_loja;
	}	
}
