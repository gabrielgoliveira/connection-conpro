package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.ConnectionFactory;



/**Classe responsavel por realizar a verificação do login no BD.
 * @author Gabriel Oliveira
 * */


public class LoginDAO {
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
		}
		return check;
	}
}
