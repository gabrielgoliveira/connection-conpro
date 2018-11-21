package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.ConnectionFactory;

/**Classe responsavel por realizar a verificao do login no BD.
 * @author Gabriel Oliveira
 * */

public class LoginDAO {
	/** Realiza a verificacao de login e senha no banco de dados
	 * @param cnpj
	 * @param senha
	 * @return boolean
	 */
	public static boolean checkLogin(String cnpj, String senha) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean check = false;
		try {
			stmt = con.prepareStatement("select * from lojas where cnpj = ? and senha = ?");
			stmt.setString(1, cnpj);
			stmt.setString(2, senha);
			rs = stmt.executeQuery();
			if (rs.next()) {
				check = true;
			}
		} catch (SQLException ex) {
			System.out.print("ERRO");
		} finally {
			//fechando a conexao
			ConnectionFactory.closeConnection(con, stmt);
			return check;
		}
		
	}
}
