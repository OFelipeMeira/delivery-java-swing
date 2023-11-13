package db_files.JavaBD.src.jmysql;

import com.mysql.cj.conf.PropertyDefinitions;

import java.sql.*;

import java.util.Scanner;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static Connection conectar(){
		String USUARIO = "root";
		String SENHA = "senai";
		String DATABASE = "jmysql";

		String CLASSE_DRIVER = "com.mysql.cj.jdbc.Driver";
		String URL_SERVIDOR = "jdbc:mysql://127.0.0.1:3306/"+DATABASE+"?useSSL=false";

		try {
			Class.forName(CLASSE_DRIVER);
			return DriverManager.getConnection(URL_SERVIDOR, USUARIO,SENHA);
		}catch (Exception e){
			if (e instanceof ClassNotFoundException){
				System.out.println("ERRO NO DRIVER DE CONEXÃO");
			}else {
				System.out.println("VERIFIQUE SE O SERVIDOR ESTA ATIVO");
			}
			System.exit(-42);
			return null;
		}
	}

	public static void desconectar(Connection conn) throws SQLException {
		if (conn != null){
			conn.close();
		}
	}

	public static void listar() {
		try {
			Connection conn = conectar();
			PreparedStatement nomes  = conn.prepareStatement("SELECT * FROM teste");
			ResultSet resp = nomes.executeQuery();

			// verifica se a resposta esta vazia
			if (resp.isBeforeFirst()){

				System.out.println("list nomes");
				System.out.println("----------");

				// itera sobre todas as respostas
				while (resp.next()){
					// resposta . get`tipo_variavel`(`numero_coluna_iniciando_em_1`)
					System.out.println("ID: "+ resp.getInt(1));
					System.out.println("nome: "+ resp.getString(2));
					System.out.println("----------");
				}

			}else{
				System.out.println("BAnco vazio");
			}

			nomes.close();
			desconectar(conn);

		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Erro buscando nomes");
			System.exit(-42);
		}
	}
	
	public static void inserir() {
		String nome = "nomão top";

		String sql = "INSERT INTO teste (nome) VALUES (?)";

		try{
			Connection conn = conectar();
			PreparedStatement salvar  = conn.prepareStatement(sql);

			salvar.setString(1, nome);

			salvar.executeUpdate();

			salvar.close();
			desconectar(conn);
			System.out.println("INSERIDO COM SUCESSO");
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Erro salvaldo nome");
			System.exit(-42);
		}
	}
	
	public static void atualizar() {
		int id = 2;

		String BUSCAR_POR_ID = "SELECT * FROM teste WHERE id=?";
		try {
			Connection conn = conectar();
			PreparedStatement teste = conn.prepareStatement(BUSCAR_POR_ID);
			teste.setInt(1, id);
			ResultSet res = teste.executeQuery();

			if (res.isBeforeFirst()) {
				String nome = "Felipe Atualizado";

				String ATUALIZAR = "UPDATE teste SET nome=? WHERE id=?";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);

				upd.setString(1, nome);
				upd.setInt(2,id);

				upd.executeUpdate();

				upd.close();
				desconectar(conn);
				System.out.println(nome + "FOI MUDADO");

			}else{
				System.out.println("n tem ngm com esse id");
			}

		}catch (Exception e ){
			e.printStackTrace();
			System.err.println("Erro ao atualizar o produto");
			System.exit(-42);
		}
	}
	
	public static void deletar() {
		String DELETAR = "DELETE FROM teste WHERE id=?";
		String BUSCAR_POR_ID  = "SELECT * FROM teste WHERE id=?";

		int id = 1;
		try	{
			Connection conn = conectar();
			PreparedStatement nome = conn.prepareStatement(BUSCAR_POR_ID);
			nome.setInt(1, id);
			ResultSet res = nome.executeQuery();

			if (res.isBeforeFirst()) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, id);
				del.executeUpdate();

				del.close();
				desconectar(conn);
				System.out.println("nome deletado");
			}else{
				System.out.println("Não existe esse id");
			}

		}catch (Exception e ){
			e.printStackTrace();
			System.err.println("Erro ao deletar");
			System.exit(-42);
		}

	}
	
	public static void menu() {
		System.out.println("==================Gerenciamento de Produtos===============");
		System.out.println("Selecione uma opção: ");
		System.out.println("1 - Listar produtos.");
		System.out.println("2 - Inserir produtos.");
		System.out.println("3 - Atualizar produtos.");
		System.out.println("4 - Deletar produtos.");
		System.out.println("5 - Criar tabelas.");


		int opcao = Integer.parseInt(teclado.nextLine());
		switch (opcao){
			case 1 -> listar();
			case 2 -> inserir();
			case 3 -> atualizar();
			case 4 -> deletar();
			default -> System.out.println("Opção inválida.");
		}
	}
}
