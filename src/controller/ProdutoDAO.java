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

/**Realiza as operacoes relacionadas a produtos no banco de dados
 * @author Gabriel Oliveira
 * */

public class ProdutoDAO {
	
	/**Cadastro o produto no banco de dados, e relaciona o produto com a loja
	 * @param Loja
	 * @param Produto
	 * @return boolean
	 * */
	public static boolean cadastrarProduto(Loja loja, Produto produto){
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		String sql;
		int id_loja, id_produto, qtde;
		double preco;
		boolean check = false;
		id_loja = loja.getId();
		
		try {
			//checando se o produto está ou não cadastrado
			id_produto = codigoProduto(produto.getNome());
			
			//caso o produto não esteja cadastrado tentar
			if(id_produto == -1){
				//inserir na tabela produtos
				sql = "insert into produtos (nome_produto) values (?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, produto.getNome());
				stmt.executeUpdate();
				stmt = null;
				
				
				//recupera o codigo gerado pelo mysql
				id_produto = codigoProduto(produto.getNome());
			}
			
			//passando o codigo para o objeto
			produto.setCodigo(id_produto);
			
			if(!verificaProduto(id_loja, id_produto)){
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
				check = true;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//caso o produto ja esteja cadastrado stmt vai ser null
			if(stmt == null){
				ConnectionFactory.closeConnection(con);
			} else {
				ConnectionFactory.closeConnection(con, stmt);
			}
			
			return check;
		}
		
	}
	/** Altera quantidade e preco de um produto na tabela
	 * @param Loja - loja
	 * @param Produto - produto
	 * @return int - check
	 * */
	public static int alterarProduto(Loja loja, Produto produto){
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		String sql;
		int id_loja, id_produto, qtde, check = 0;
		double preco;
		
		try {

			sql = "update loja_produto set qtde = ?, preco = ? where id_loja = ? and id_produto = ?";
			
			id_loja = loja.getId();
			id_produto = produto.getCodigo();
			qtde = produto.getQuantidade();
			preco = produto.getPreco();
			
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, qtde);
			stmt.setDouble(2, preco);
			stmt.setInt(3, id_loja);
			stmt.setInt(4, id_produto);
		
			//1 para sucesso e 0 para erro
			check = stmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			return check;
		}
	}
	/** Apaga determinado produto da tabela loja_produto, e caso tenha sucesso retorna 1 caso contrario 0
	 * @param Loja - loja
	 * @param Produto - produto
	 * @return int - check
	 * */
	
	public static int deletarProduto(Loja loja, Produto produto){
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		String sql;
		
		int flag, check = 0;
		int id_loja = loja.getId();
		int id_produto = produto.getCodigo();
		
		try {
			sql = "delete from loja_produto where id_loja = ? and id_produto = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id_loja);
			stmt.setInt(2, id_produto);
			flag = stmt.executeUpdate();
			if(flag == 1){
				//sucesso em apagar a linha
				check = 1;
			} else {
				//linha nao encontrada
				check = 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			return check;
		}
	}
	/**Busca a lista de produtos cadastros em todas as lojas, sem nenhum filtro
	 * @param String - nome
	 * @return ArrayList - Produtos
	 * */
	
	public static ArrayList<Produto> obterProdutos(String nome) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
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
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			return produtos;
		}

		
	}
	
	
	/**Dado o nome de um produto cadastrado no BD retorna o codigo do mesmo, e caso nao exista
	 * retorna -1.
	 * @param String - nomeProduto
	 * @return int - codigoProduto
	 * */
	public static int codigoProduto(String nomeProduto) {
		
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs;
		String sql = "select * from produtos where nome_produto = ?";
		int codigoProduto = -1;
		
		try {
			stmt  = con.prepareStatement(sql);
			stmt.setString(1, nomeProduto);
			rs = stmt.executeQuery();
			if(rs.next()){
				codigoProduto = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			if(codigoProduto >= 0) {
				return codigoProduto;
			} else {
				return -1;
			}
		}
	}
	
	/**Verifica se determinada loja possui determinado produto cadastrado
	 * @param int - idLoja
	 * @param int - idProduto
	 * @return boolean
	 * */
	public static boolean verificaProduto(int id_loja, int id_produto) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs;
		String sql;
		boolean flag = false;
		try {
			sql = "select * from loja_produto where id_loja = ? and id_produto = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id_loja);
			stmt.setInt(2, id_produto);
			rs = stmt.executeQuery();
			if(rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			return flag;
			
		}
	}
	/**Verifica qual loja possui um determinado produto dados Cidade, Bairro e o Produto.
	 * @param String - cidade
	 * @param String - bairro
	 * @param String - nomeProduto
	 * @return ArrayList - listaLojas
	 * */
	
	public static ArrayList<Loja> produtosCidade(String cidade, String bairro, String nomeProduto){
	//variaveis
		String sql;
		String razaoSocial, cnpj, senha;
		String atualRua, atualBairro, atualCidade, atualEstado;
		Endereco endereco;
		Loja loja;
		int id_produto = codigoProduto(nomeProduto);
		int id_loja;
		
		
	//variaveis do pacote JDBC
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs;
		
		ArrayList<Loja> listaLojas = new ArrayList<Loja>();
		
		
		try {
			sql = "select * from lojas where cidade = ? and bairro = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cidade);
			stmt.setString(2, bairro);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				//recupera o id da loja no ResultStatement
				id_loja = rs.getInt("id");
			
				//verifica se o produto pertence a loja
				if(verificaProduto(id_loja, id_produto)) {
			
					//recuperando endereco
					atualRua = rs.getString("rua");
					atualBairro = rs.getString("bairro");
					atualCidade = rs.getString("cidade");
					atualEstado = rs.getString("estado");
					endereco = new Endereco(atualRua, atualBairro, atualCidade, atualEstado);
					
					//recuperando e montando um objeto loja
					razaoSocial = rs.getString("razao_social");
					cnpj = rs.getString("cnpj");
					senha = rs.getString("senha");
					loja = new Loja(razaoSocial, cnpj, senha, endereco);
					
					//salvando no arrayList
					listaLojas.add(loja);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
			return listaLojas;
		}
	}

}
