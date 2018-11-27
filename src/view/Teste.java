package view;

import java.util.ArrayList;
import java.util.Scanner;

import connection.*;
import controller.*;
import model.*;



public class Teste {
	
	public static void login(){
		String cnpj;
		String senha;
		Loja loja;
		int flag;
		Scanner ler = new Scanner(System.in);
		
		System.out.print("Ja tem uma conta ?\n 1 - Login\n 2 - Criar uma nova conta\n 0 - Sair\n:");
		flag = ler.nextInt();
		ler.nextLine();
		if(flag == 1){
			System.out.print("Login\nCNPJ: ");
			cnpj = ler.nextLine();
			System.out.print("Senha");
			senha = ler.nextLine();
			if(LoginDAO.checkLogin(cnpj, senha)){
				loja = LojaDAO.obterLoja(cnpj);
				telaInicial(loja);
			} else {
				System.out.println("Erro\nEntrada Invalida !!\n");
				login();
			}
		} else if (flag == 2) {
			 cadastrarLoja();
			 login();
		} else {
			return ;
		}
		
	}
	public static void cadastrarLoja(){
		Loja loja;
		Endereco endereco;
		String razaoSocial, cnpj, senha;
		String rua, bairro, cidade, estado;
		Scanner ler = new Scanner(System.in);
		
		System.out.print("Digite a Razao Social: ");
		razaoSocial = ler.nextLine();
		System.out.print("Digite o CNPJ: ");
		cnpj = ler.nextLine();
		System.out.print("Digite a Senha ");
		senha = ler.nextLine();
		
		System.out.print("Endereco: \n");
		System.out.print("Digite a Rua: ");
		rua = ler.nextLine();
		
		System.out.print("Digite o Bairro: ");
		bairro = ler.nextLine();
		
		System.out.print("Digite a Cidade: ");
		cidade = ler.nextLine();
		
		System.out.print("Digite o Estado: ");
		estado = ler.nextLine();
	
		endereco = new Endereco (rua, bairro, cidade, estado);
		loja = new Loja(razaoSocial, cnpj, senha, endereco);

		LojaDAO.createLoja(loja);
		
		return ;
		
	}
	public static void telaInicial(Loja loja){
		Scanner ler = new Scanner(System.in);
		int flag;
		System.out.println("Bem Vindo : " + loja.getRazaoSocial() + "\n 1 - Cadastrar Produto\n" + 
		" 2 - Alterar Loja\n" + 
		" 3 - Alterar Produto\n" + 
		" 4 - Apagar Produto\n" + 
		" 5 - Apagar Loja\n: ");
		flag = ler.nextInt();

		if(flag == 1){
			cadastrarProduto(loja);
		} else if (flag == 2){
			alterarLoja(loja);
		} else if (flag == 3){
			alterarProduto();
		} else if (flag == 4){
			apagarProduto();
		} else if (flag == 5){
			apagarLoja();
		}
	}
	public static void cadastrarProduto(Loja loja){
		Scanner ler = new Scanner (System.in);
		
		Produto produto;
		String nome;
		int qtde;
		double preco;
		
		System.out.print("Digite o nome do produto : ");
		nome = ler.nextLine();
		
		System.out.print("Digite a Quantidade do produto : ");
		qtde = ler.nextInt();
		ler.nextLine();
		
		System.out.print("Digite o preco do produto : ");
		preco = ler.nextDouble();
		ler.nextLine();
		
		produto = new Produto(nome, qtde, preco);
		
		if(ProdutoDAO.cadastrarProduto(loja, produto)){
			System.out.print("Produto Cadastrado com Sucesso");
		} else {
			System.out.print("Produto ja Cadastrado");
		}
		
		
		telaInicial(loja);
		
	}
	public static void alterarLoja(Loja loja){
		
	}
	public static void alterarProduto(){
		
	}
	public static void apagarLoja(){
		
	}
	public static void apagarProduto(){
		
	}
	
    public static void main(String[] args){
    	login();
    	//int flag = ProdutoDAO.codigoProduto("Produto");
    	//System.out.println(flag); '1', 'Loja 1', '89.223.734/0001-43', '123', 'Rua 1', 'Setor Bueno', 'Goiania', 'Goias'


    }
    	
}
