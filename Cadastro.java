package br.com.escola;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


	public class Cadastro {
		private Connection connection;
		public Cadastro() {
			this.connection = new ConnectionFactory().getConnection();
		}
	public void inserir(Matricula matricula) {
		matricula.setNome(JOptionPane.showInputDialog("Insira o nome"));
		matricula.setNumDaMatricula(Double.parseDouble(JOptionPane.showInputDialog("Insira o numero da matricula (apenas números)")));
		matricula.setCidade(Integer.parseInt(JOptionPane.showInputDialog("Insira id da cidade \n 1 - ATALAIA-AL \n 2 - ARAPIRACA-AL \n 3 - MACEIÓ-AL")));
		matricula.setBairro(JOptionPane.showInputDialog("Insira o bairro"));
		matricula.setRua(JOptionPane.showInputDialog("Insira a rua"));
		matricula.setCpf(Double.parseDouble(JOptionPane.showInputDialog("Insira o rg (apenas números)")));
		matricula.setRg(Double.parseDouble(JOptionPane.showInputDialog("Insira o cpf (apenas números)")));
		matricula.setIdTurmaTurma(Integer.parseInt(JOptionPane.showInputDialog("Insira o id da turma \n (1) -  1º Ano - manhã \n (2) - 2º Ano - manhã \n (3) - 3º Ano - manhã \n"
																			+ "	(4) -  1º Ano - tarde \n (5) - 2º Ano - tarde \n (6) - 3º Ano - tarde \n "
																			+ "	(7) -  1º Ano - noite \n (8) - 2º Ano - noite \n (9) - 3º Ano - noite ")));

	}

	public void adiciona(Matricula matricula) {
		String sql = "insert into mydb.matricula (nome,numeroDaMatricula,cidade_idCidade,rua,bairro,cpf,rg,Turma_idTurma) values (?,?,?,?,?,?,?,?)";

		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores

			stmt.setString(1, matricula.getNome());
			stmt.setDouble(2, matricula.getNumDaMatricula());
			stmt.setInt(3, matricula.getCidade());
			stmt.setString(4, matricula.getRua());
			stmt.setString(5, matricula.getBairro());
			stmt.setDouble(6, matricula.getCpf());
			stmt.setDouble(7, matricula.getRg());
			stmt.setDouble(8, matricula.getIdTurmaTurma());
		
			// executa
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Inserir!");
			throw new RuntimeException(e);
		}
	}
	
	//mostrandoLista
	public void mostraLista() {
		List<Cadastrados> cadastrados = new Cadastro().getListaC();
		int contador = 0;
		JOptionPane.showMessageDialog(null, "Listando ...");
		for (Cadastrados c: cadastrados){
			  String id = "ID: " + c.getId()+"\n";
			  String nome = "NOME: " + c.getNome()+"\n";
			  String matricula = "NUMERO DA MATRICULA: " + Math.round(c.getNumeroDaMatricula())+"\n";
			  String nomeCidade = "CIDADE: "+ c.getNomeCidade()+"\n";
			  String uf = "UF: " + c.getUf()+"\n";
			  String nomeEstado ="ESTADO: " + c.getNomeEstado()+"\n";
			  String rua = "RUA: " + c.getRua()+"\n";
			  String bairro ="BAIRRO: "+ c.getBairro()+"\n";
			  String cpf ="CPF: "+ Math.round(c.getCpf())+"\n";
			  String rg = "RG: "+ Math.round(c.getRg())+"\n";
			  String serie ="SÉRIE: " + c.getNomeSerie()+"\n";
			  String turno ="TURNO: " + c.getTurno()+"\n";
			  String espaco = "**********************************\n";
			  String listar = id+nome+matricula+nomeCidade+uf+nomeEstado+rua+bairro+cpf+rg+serie+turno+espaco;
			  JOptionPane.showMessageDialog(null,listar);
			  contador ++;
		}
		if (contador==0){
			JOptionPane.showMessageDialog(null,"VAZIO");
		}

	}
	
	//lista
	public List<Cadastrados> getListaC(){
		List<Cadastrados> c = new ArrayList<Cadastrados>();
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("SELECT matricula.id,matricula.nome,matricula.numeroDaMatricula,cidade.nomeCidade,estado.uf,estado.nomeEstado,matricula.rua,matricula.bairro,matricula.cpf,matricula.rg,turma.nomeSerie,turno.turno "
									+"FROM "
									+"MATRICULA,TURMA,TURNO,CIDADE,ESTADO "
									+"WHERE (matricula.Turma_idTurma = turma.idTurma) "
									+ "AND "
									+"(turma.turno_idTurno = turno.idTurno) "
									+ "AND "
									+ "(matricula.cidade_idCidade = cidade.idCidade) "
									+ "AND "
									+ "(cidade.estado_idEstado = estado.idEstado)");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				// criando o objeto "Cadastrados"
				Cadastrados ex = new Cadastrados();
				
				ex.setId(rs.getInt("id"));
				ex.setNome(rs.getString("nome"));
				ex.setNumeroDaMatricula(rs.getDouble("numeroDaMatricula"));
				ex.setNomeCidade(rs.getString("nomeCidade"));
				ex.setUf(rs.getString("uf"));
				ex.setNomeEstado(rs.getString("nomeEstado"));
				ex.setRua(rs.getString("rua"));
				ex.setBairro(rs.getString("bairro"));
				ex.setCpf(rs.getDouble("cpf"));
				ex.setRg(rs.getDouble("rg"));
				ex.setNomeSerie(rs.getString("nomeSerie"));
				ex.setTurno(rs.getString("turno"));
				
				c.add(ex);
				
			}
			
			
			rs.close();
			stmt.close();
			return c;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Selecionar!");
			throw new RuntimeException(e);
		}
	}

	public void alterar(Matricula matricula) {
		String sql = "update matricula set matricula.nome=?, matricula.numeroDaMatricula=?, matricula.cidade_idCidade=?, matricula.rua=?, matricula.bairro=?, matricula.cpf=?, matricula.rg=?, matricula.Turma_idTurma=? where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, matricula.getNome());
			stmt.setDouble(2, matricula.getNumDaMatricula());
			stmt.setInt(3, matricula.getCidade());
			stmt.setString(4, matricula.getRua());
			stmt.setString(5, matricula.getBairro());
			stmt.setDouble(6, matricula.getCpf());
			stmt.setDouble(7, matricula.getRg());
			stmt.setDouble(8, matricula.getIdTurmaTurma());
			stmt.setInt(9,matricula.getIdMatricula());
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Alterar!");
			throw new RuntimeException(e);
		}
	}
	public void remove(Matricula matricula) {
	     try {
	         PreparedStatement stmt = connection.prepareStatement("delete " +
	                 "from matricula where id=?");
	         stmt.setInt(1, matricula.getIdMatricula());
	         stmt.execute();
	         JOptionPane.showMessageDialog(null, "Removido com sucesso!");
	         stmt.close();
	     } catch (SQLException e) {
	    	 JOptionPane.showMessageDialog(null, "Erro ao Remover!");
	         throw new RuntimeException(e);
	     }
	 }
}
