package graphic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

import database.model.USUARIO;

public class MenuWindow
{
	
	private JMenuBar menuBar;	
	private JMenu menuSistema;
	private JMenu menuCadastro;
	private JMenu menuProcessos;
	private JMenu menuGerenciador;
	private JMenuItem menuItemSistemaUsuario;
	private JMenuItem menuItemSistemaSair;	
	private JMenuItem menuItemCadastroAluno;	
	private JMenuItem menuItemProcessosMatricula;
	private JMenuItem menuItemGerenciador;
	private USUARIO user;
	private Connection conexao;
	private JMenu menuImports;
	private JMenuItem menuImportsitem;
	private JFrame frame;
	private JDesktopPane desktop;


	
	public MenuWindow(final USUARIO user, final Connection conexao) throws SQLException {
		this.user = user;
		this.conexao = conexao;
		frame = new JFrame("Sistema de Gerenciamento - v21.08.17");
		frame.setSize(1600, 950);
		frame.setLocationRelativeTo(null);
		menuCriar();
		menuHabilitar();
		frame.setVisible(true);
		desktop.add(new ProcessoGerencial(conexao));
	}
	
	private final void menuCriar() 
	{
		menuBar = new JMenuBar();		
			menuSistema = new JMenu("Sistema");
				menuItemSistemaUsuario = new JMenuItem(new AbstractAction("Usuário") 
				{
					public void actionPerformed(ActionEvent e) 
					{
						try 
						{
							desktop.add(new CadastroUsuarioWindow(conexao));
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(null, "Falha de conexão com o banco!");
						}
					}
				});
				
				menuItemSistemaSair = new JMenuItem("Sair");
				menuSistema.add(menuItemSistemaUsuario);
				menuSistema.add(menuItemSistemaSair);
				
			menuCadastro = new JMenu("Cadastro");
				menuItemCadastroAluno = new JMenuItem("Aluno");
				menuCadastro.add(menuItemCadastroAluno);
				
			menuProcessos = new JMenu("Processo");
				menuItemProcessosMatricula = new JMenuItem(new AbstractAction("Matricula"){
					@Override
					public void actionPerformed(ActionEvent e) {
						new MatricularAlunoWindow(conexao).setEnabled(true);
					}
				});
				menuProcessos.add(menuItemProcessosMatricula);

			menuImports = new JMenu("Utilitários - Importador");
				menuImportsitem = new JMenuItem(new AbstractAction("Importar")
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						try 
						{
							desktop.add(new Importa(conexao));
						} catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(null, "Falha de conexão com o banco!");
						}
					}
				});
				menuImports.add(menuImportsitem);

				menuGerenciador = new JMenu("Gerenciador de processos");
				menuItemGerenciador = new JMenuItem(new AbstractAction("Gerenciador de processos"){
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							desktop.add(new ProcessoGerencial(conexao));
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				});
				menuGerenciador.add(menuItemGerenciador);


		menuBar.add(menuSistema);
		menuBar.add(menuCadastro);
		menuBar.add(menuProcessos);
		menuBar.add(menuImports);
		menuBar.add(menuGerenciador);
		
		frame.setJMenuBar(menuBar);
		frame.setContentPane(criaContainer());
	}
	
	private void menuHabilitar() 
	{
		if (user.getPerfil().equalsIgnoreCase("Administrador")) 
		{
			menuSistema.setEnabled(true);
			menuCadastro.setEnabled(true);
			menuProcessos.setEnabled(true);
		}
		else if (user.getPerfil().equalsIgnoreCase("Cadastral")) 
		{
			menuSistema.setEnabled(false);
			menuCadastro.setEnabled(true);
			menuProcessos.setEnabled(false);
		}
		else if (user.getPerfil().equalsIgnoreCase("Financeiro")) 
		{
			menuSistema.setEnabled(false);
			menuCadastro.setEnabled(false);
			menuProcessos.setEnabled(true);
		}
		else if (user.getPerfil().equalsIgnoreCase("Suporte")) 
		{
			menuSistema.setEnabled(true);
			menuCadastro.setEnabled(true);
			menuProcessos.setEnabled(false);
		}
		else 
		{
			System.out.println("Tipo de Perfil inválido!");
		}
		
	}

	private Container criaContainer(){
		JPanel panel = new JPanel(new BorderLayout());
		desktop = new JDesktopPane();
		panel.setOpaque(true);
		panel.add(desktop);
		return panel;
	}

}
