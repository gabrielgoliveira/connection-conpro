package model;

import java.util.ArrayList;

public class Loja {
	private int id;
	private String razaoSocial;
	private String cnpj;
	private String senha;
	private Endereco endereco;
	// private ArrayList<Produto> listaProdutos = new ArrayList();

	// construtor
	public Loja() {
	};

	public Loja(String razaoSocial, String cnpj, String senha, Endereco endereco) {
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.senha = senha;
		this.endereco = endereco;
	}

	// setters e getters
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	// metodos
	public static void validarCNPJ(String cnpj) throws CNPJException {
		if (cnpj.length() != 14)
			throw new CNPJException("O cnpj digitado não possui um formato válido");

		int[] primeira_etapa = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };// 12
																		// digitos
		int[] segunda_etapa = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };// 13
																		// digitos
		int primeiro_digito = 0;
		int segundo_digito = 0;
		// Calculo do primeiro digito
		for (int i = 0; i < 12; i++) {
			primeiro_digito += (Integer.parseInt(Character.toString(cnpj.charAt(i))) * primeira_etapa[i]);
		}
		primeiro_digito %= 11;

		if (primeiro_digito < 2) {
			primeiro_digito = 0;
		} else {
			primeiro_digito = 11 - primeiro_digito;
		}
		// Fim do cálculo do primeiro digito

		// Calculo do segudo dígito
		for (int i = 0; i < 13; i++) {
			segundo_digito += (Integer.parseInt(Character.toString(cnpj.charAt(i))) * segunda_etapa[i]);
		}

		segundo_digito %= 11;

		if (segundo_digito < 2) {
			segundo_digito = 0;
		} else {
			segundo_digito = 11 - segundo_digito;
		}

		// Fim do cálculo do segundo dígito

		// Verificando se o CNPJ é válido
		if (Integer.parseInt(Character.toString(cnpj.charAt(12))) != primeiro_digito
				|| Integer.parseInt(Character.toString(cnpj.charAt(13))) != segundo_digito) {
			throw new CNPJException("O CNPJ informado não é válido");

		}

	}

}
