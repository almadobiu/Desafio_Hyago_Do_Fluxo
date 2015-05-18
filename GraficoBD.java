package br.com.escola;



import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraficoBD {
	JFrame janela;
	JPanel painel;
	Cadastro cadastro;
	
	public void inicio() {
		configuraJanela();
		exibePainel();
		inserir();
		atualizar();
		remover();
		selecionar();
		sair();
		exibeJanela();
		
	}
	public void exibeJanela() {
		JOptionPane.showMessageDialog(null, "BEM-VINDO AO SISTEMA");
		janela.setLocation(new Point(670, 250));
		janela.show();

	}
	
	public void configuraJanela() {
		janela = new JFrame("MATRICULA");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(180,200);
	}
	
	public void exibePainel() {
		painel = new JPanel();
		janela.add(painel);
	}
	
	private void inserir() {
		JButton inserir = new JButton("INSERIR");
		inserir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent insert) {
				Matricula matricula = new Matricula();
				cadastro = new Cadastro();
				cadastro.inserir(matricula);
				cadastro.adiciona(matricula);
			}
		});
		painel.add(inserir);
	}
	
	public void atualizar() {
		JButton atualizar = new JButton("ATUALIZAR");
		atualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Matricula matricula = new Matricula();
				Cadastro c = new Cadastro();
				c.inserir(matricula);
				matricula.setIdMatricula(Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do USUÁRIO que deseja ALTERAR")));
				c.alterar(matricula);
			}
		});
		painel.add(atualizar);
	}
	
	public void remover() {
		JButton remover = new JButton("REMOVER");
		remover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Matricula matricula = new Matricula();
				cadastro = new Cadastro();
				matricula.setIdMatricula(Integer.parseInt(JOptionPane.showInputDialog("INSIRA o ID deseja REMOVER")));
				cadastro.remove(matricula);
			}
		});
		painel.add(remover);
	}
	
	public void selecionar() {
		JButton selecionar = new JButton("SELECIONAR");
		selecionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Cadastro().mostraLista();
			}
		});
		painel.add(selecionar);

	}
	public void sair() {
		JButton sair = new JButton("SAIR");
		
		sair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Bye bye..");
				System.exit(0);
			}
		});
		painel.add(sair);

	}
}


