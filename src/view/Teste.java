package view;

import java.util.ArrayList;
import java.util.Scanner;

import connection.*;
import controller.*;
import model.*;

public class Teste {
/*
    public static void cadastraLoja(){
        Loja novaLoja;
        Endereco novoEndereco;
        String razaoSocial, cnpj, senha;
        String rua, bairro, cidade, estado;
        Cadastrar cadastro = new Cadastrar();
        Scanner ler = new Scanner(System.in);

        System.out.println("Cadastrar Loja");
        System.out.print("Razao Social: ");
        razaoSocial = ler.nextLine();
        System.out.print("CNPJ: ");
        cnpj = ler.nextLine();
        System.out.print("Senha: ");
        senha = ler.nextLine();
            
        System.out.print("Rua: ");
        rua = ler.nextLine();
        System.out.print("Bairro: ");
        bairro = ler.nextLine();
        System.out.print("Cidade: ");
        cidade = ler.nextLine();
        System.out.print("Estado: ");
        estado = ler.nextLine();
        novoEndereco = new Endereco(rua, bairro, cidade, estado);
        novaLoja = new Loja(razaoSocial, cnpj, senha, novoEndereco);
            
        cadastro.createLoja(novaLoja);

    }
    
    public static void login(){
    	
        String cnpj = "000.000.000-00";
        String senha = "123";
        Loja logLoja = null;
        Produto produto;
        Scanner ler = new Scanner(System.in);
        Cadastrar cadastrar = new Cadastrar();
        System.out.print("LOGIN\nCNPJ: ");
        //cnpj = ler.nextLine();
        System.out.print("Senha: ");
        //senha = ler.nextLine();
        if(cadastrar.checkLogin(cnpj, senha)){
        	logLoja = cadastrar.obterLoja(cnpj);
        	if(logLoja != null){
        		produto = new Produto("pão", 10, 6.50);
            	cadastrar.cadastrarProduto(logLoja, produto);
        	} else {
        		
        		 System.out.println("Falha ao realizar o Login");
        	}
        	
        	System.out.println(logLoja.getCnpj());
       } else {
           System.out.println("Falha ao realizar o Login");
       }

    }
    
*/    
    public static void main(String[] args){
 
    	ArrayList<Loja> listaLojas;
    	Loja loja;
    	listaLojas = ProdutoDAO.produtosCidade("Goiania", "Garavelo", "moto G6");
    	for(int i = 0; i<listaLojas.size(); i++) {
    		loja = listaLojas.get(i);
    		System.out.println(loja.getRazaoSocial());
    	}
 
    }
    	
}
