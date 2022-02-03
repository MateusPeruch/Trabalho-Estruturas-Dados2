package graphic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.ConnectionFactory;
import database.dao.UsuarioDAO;
import database.model.USUARIO;

public class LoginWindow extends JDialog 
{
	private Connection conexao;
	private JLabel lblNomeSistema;
	private JLabel lblUsuario;
	private JLabel lblSenha;
	private JTextField txfUsuario;
	private JPasswordField txfSenha;
	private JButton btnEntrar;
	private USUARIO user; 
	
	public LoginWindow() 
	{
		setSize(400, 200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Autenticação do Sistema");
		setLocationRelativeTo(null);
		setLayout(null);
		componentesCriar();
	}
	
	private void componentesCriar() 
	{
		
		lblNomeSistema = new JLabel("Sistema");
		lblNomeSistema.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		lblNomeSistema.setBounds(170, 10, 100, 25);
		getContentPane().add(lblNomeSistema);
		
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(50, 40, 100, 25);
		getContentPane().add(lblUsuario);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(50, 70, 100, 25);
		getContentPane().add(lblSenha);
		
		txfUsuario = new JTextField();
		txfUsuario.setBounds(115, 40, 200, 25);
		getContentPane().add(txfUsuario);
		
		txfSenha = new JPasswordField();
		txfSenha.setBounds(115, 70, 200, 25);
		getContentPane().add(txfSenha);
		
		btnEntrar = new JButton(new AbstractAction("ENTRAR") 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				autenticacaoValidar();

			}

		});
		btnEntrar.setBounds(115,100,200,25);
		getContentPane().add(btnEntrar);
	}
	
	private final void autenticacaoValidar() 
	{
		final String usuarioDigitado = txfUsuario.getText();
		final String senhaDigitada = txfSenha.getText();
		
		if (usuarioDigitado.isEmpty()) 
		{
			JOptionPane.showMessageDialog(null, "Campo USUÁRIO obrigatório!");
		}
		else if (senhaDigitada.isEmpty()) 
		{
			JOptionPane.showMessageDialog(null, "Campo SENHA obrigatório!");					
		}
		else 
		{
			
			try 
			{
				conexao = ConnectionFactory.getConnection("localhost", 3306, "sistema", "root", "mateusperuchi");

				if (conexao != null) 
				{
					
					try 
					{
						UsuarioDAO dao = new UsuarioDAO(conexao);					
						USUARIO usuario = dao.SelectUsuario(usuarioDigitado);
						if (usuario != null) 
						{
							if (usuario.getSenha().equalsIgnoreCase(senhaDigitada)) 
							{
								new MenuWindow(usuario, conexao);
								dispose();
							}
							else 
							{
								JOptionPane.showMessageDialog(null, "Senha inválida!");	
							}
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Usuário não encontrado");
						}
					}
					catch (SQLTimeoutException e) 
					{
						JOptionPane.showMessageDialog(null, "Não foi possível buscar o usuário. Tente novamente!");
					}
					catch (SQLException e) 
					{
						JOptionPane.showMessageDialog(null, "Erro de SQL: "+e.getMessage());
					}				
				}				
			} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Falha com o banco de dados: "+e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) 
	{		
		LoginWindow l = new LoginWindow();
		l.setVisible(true);
	}

}
