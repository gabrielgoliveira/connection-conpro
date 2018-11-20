package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.ConnectionFactory;

import model.Endereco;
import model.Loja;
import model.Produto;

/**Realiza a conversação relacionadas a Produtos entre a interface grafica e o banco de dados
 * @author Gabriel Oliveira
 * */

public class ProdutoDAO {
	
	/**Cadastro o produto no banco de dados, e relaciona o produto com a loja
	 * @param Loja
	 * @param Produto
	 * @return void
	 * */
	
	public static void cadastrarProduto(Loja loja, Produto produto){
		/*Tratar erro caso o produto ja esteja cadastrado
		 * */
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		String sql;
		int id_loja, id_produto, qtde;
		double preco;
		try {
			//inserir na tabela produtos
			sql = "insert into produtos (nome_produto) values (?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.executeUpdate();
			stmt = null;
			
			//recuperando o id do produto
			sql = "select id from produtos where nome_produto = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			//executeQuery retorna um ResultSet (diferentemente de executeUpdate)
			rs = stmt.executeQuery();
			rs.next();
			id_produto = rs.getInt("id");
			produto.setCodigo(id_produto);
			stmt = null;
		
			//inserindo na tabela relacional
			sql = "insert into loja_produto (id_loja, id_produto, qtde, preco) values (?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			
			//recuperando os dados
			id_loja = loja.getId();
			qtde = produto.getQuantidade();
			preco = produto.getPreco();
			
			//setando o comando sql
			stmt.setInt(1, id_loja);
			stmt.setInt(2, id_produto);
			stmt.setInt(3, qtde);
			stmt.setDouble(4, preco);
			
			//executando comando sql
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			new ConnectionFactory().closeConnection(con);
		}
		
	}
		
	/**Busca a lista de produtos cadastros em todas as lojas, sem nenhum filtro
	 * @param String
	 * @return ArrayList
	 * */
	
	public static ArrayList<Produto> obterProdutos(String nome) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt;
		ResultSet rs;
		Produto p;
		ArrayList<Produto> produtos = new ArrayList<>();
		try {
			stmt = con.prepareStatement("SELECT * FROM lojas_produtos WHERE nome_produto = ?");
			stmt.setString(1, nome);
			rs = stmt.executeQuery();

			// Percorre a lista de produtos do BD para adicionar em um array
			// list
			while (!rs.isLast()) {
				rs.next();
				p = new Produto(rs.getString("nome_produto"), rs.getInt("qtde"), rs.getDouble("preco"));
				p.setCodigo(rs.getInt("id_produto"));
				produtos.add(p);
			}
		} catch (SQLException e) {
			e.getMessage();
		}

		return produtos;
	}
	
}
