package connection;
import java.sql.*;

/**Classe responsavel por manipular as conexões com o BD.
 * @author Gabriel Oliveira
 */

public class ConnectionFactory {
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/conpro?useTimezone=true&serverTimezone=UTC";
	private static String USER = "root";
	private static String PASS = "";
	
	/**Metodo responsavel por estabelecer uma conexão com o BD.
	 * @param void
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			//Carrega a classe com o endereço da string DRIVER
			Class.forName(DRIVER); 
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.printf("Erro de Conexao");
			throw new RuntimeException("Erro de Conexao", e);
		} 			
	}
	
	/**Metodo encerrar a conexão com o banco de dados.
	 * @param Connection
	 * @return void
	 */
	public static void closeConnection(Connection con){
		
		try {
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
	
	/**Metodo encerrar a conexão com o banco de dados.
	 * @param Connection
	 * @param
	 * @return void
	 */
	public static void closeConnection(Connection con, PreparedStatement stmt){
		//sobreescrita de metodo
		closeConnection(con);
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
