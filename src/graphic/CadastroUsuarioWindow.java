package graphic;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.dao.UsuarioDAO;
import database.model.USUARIO;

public class CadastroUsuarioWindow extends JInternalFrame
{
	
	private JButton btnBuscar, btnAdicionar, btnRemover, btnSalvar;
	private JLabel lblUsuario, lblPerfil, lblSenha, lblConfirmaSenha;
	private JTextField txfUsuario;
	private JComboBox<String> cbxPerfil;
	private JPasswordField txfSenha, txfConfirmaSenha;
	private JTable tblUsuarios;
	private DefaultTableModel model;
	private JScrollPane spnUsuarios;
	private Connection conexao;
	
	private boolean ibInsert = false;
	
	private UsuarioDAO dao;
	
	public CadastroUsuarioWindow(final Connection conexao) throws SQLException 
	{
		this.conexao = conexao;		
		dao = new UsuarioDAO(this.conexao);
		
		setTitle("Cadastro de Usuário");
		setSize(700, 500);
		setLayout(null);
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		componentesCriar();
		
		btnRemover.setEnabled(false);
		btnSalvar.setEnabled(false);
		setVisible(true);
	}
	
	private void componentesCriar() 
	{
		
		btnBuscar = new JButton(new AbstractAction("Buscar") 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				btnRemover.setEnabled(true);
				
				while (tblUsuarios.getRowCount() > 0) 
				{
					model.removeRow(0);
				}
				
				try 
				{
					UsuarioDAO dao = new UsuarioDAO(conexao);
					List<Object> lst = dao.Select();
					for (int i = 0; i < lst.size(); i++) 
					{
						USUARIO usuario = (USUARIO)lst.get(i);
						model.addRow(new String[] {String.valueOf(usuario.getId()), usuario.getUsuario(), usuario.getPerfil()});
					}
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(10, 10, 100, 25);
		getContentPane().add(btnBuscar);
		
		btnAdicionar = new JButton(eventoAdicionar);
		btnAdicionar.setBounds(120, 10, 100, 25);
		getContentPane().add(btnAdicionar);
		
		btnRemover = new JButton(eventoRemover);
		btnRemover.setBounds(230, 10, 100, 25);
		getContentPane().add(btnRemover);
		
		btnSalvar = new JButton(eventoSalvar);
		btnSalvar.setBounds(340, 10, 100, 25);
		getContentPane().add(btnSalvar);
		
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(10, 70, 70, 25);
		getContentPane().add(lblUsuario);
		
		txfUsuario = new JTextField();
		txfUsuario.setEnabled(false);
		txfUsuario.setBounds(80, 70, 200, 25);
		getContentPane().add(txfUsuario);
		
		lblUsuario = new JLabel("Senha:");
		lblUsuario.setBounds(10, 100, 70, 25);
		getContentPane().add(lblUsuario);
		
		txfSenha = new JPasswordField();
		txfSenha.setEnabled(false);
		txfSenha.setBounds(80, 100, 200, 25);
		getContentPane().add(txfSenha);
		
		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setBounds(300, 70, 70, 25);
		getContentPane().add(lblPerfil);
		
		cbxPerfil = new JComboBox<String>();
		cbxPerfil.addItem("- Selecione - ");
		cbxPerfil.addItem("Administrador");
		cbxPerfil.addItem("Cadastral");
		cbxPerfil.addItem("Financeiro");
		cbxPerfil.addItem("Suporte");
		cbxPerfil.setEnabled(false);
		cbxPerfil.setBounds(420, 70, 200, 25);
		getContentPane().add(cbxPerfil);
		
		lblConfirmaSenha = new JLabel("Confirmar Senha:");
		lblConfirmaSenha.setBounds(300, 100, 130, 25);
		getContentPane().add(lblConfirmaSenha);
		
		txfConfirmaSenha = new JPasswordField();
		txfConfirmaSenha.setEnabled(false);
		txfConfirmaSenha.setBounds(420, 100, 200, 25);
		getContentPane().add(txfConfirmaSenha);
		
		model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Usuário");
		model.addColumn("Perfil");
		
		tblUsuarios = new JTable(model) 
		{
	         public boolean editCellAt(int row, int column, java.util.EventObject e) 
	         {
	             return false;
	         }
        };
		tblUsuarios.addMouseListener(new MouseAdapter() 
		{
			
			@Override
			public void mousePressed(MouseEvent e) 
			{
				Point point = e.getPoint();
		        int row = tblUsuarios.rowAtPoint(point);
		        if (e.getClickCount() == 2 && tblUsuarios.getSelectedRow() != -1) 
		        {
		        	
		        	btnBuscar.setEnabled(false);
		        	btnAdicionar.setEnabled(false);
		        	btnRemover.setEnabled(false);
		        	btnSalvar.setEnabled(true);
		        	ibInsert = false;
		        	
		        	int codigo = Integer.parseInt(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString());
		        	String usuario = tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 1).toString();
		        	String perfil = tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 2).toString();
		        	
		        	txfUsuario.setText(usuario);
		        	cbxPerfil.setSelectedItem(perfil);
		        	
		        	txfUsuario.setEnabled(false);
		        	cbxPerfil.setEnabled(false);
		        	
		        	txfSenha.setEnabled(true);
		        	txfConfirmaSenha.setEnabled(true);
		        	cbxPerfil.setEnabled(true);
		        }
			}
			
		});
		spnUsuarios = new JScrollPane(tblUsuarios);
		spnUsuarios.setBounds(10, 140, 615, 270);
		getContentPane().add(spnUsuarios);
		
	}
	
	Action eventoRemover = new AbstractAction("Remover") 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			String codigo = tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString();
			
			try 
			{
				dao.Delete(Integer.parseInt(codigo));
				JOptionPane.showMessageDialog(null, "Usuário removido");				
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Falha ao remover o usuário");
			}
			
			btnBuscar.doClick();
		}
	};
	
	Action eventoAdicionar = new AbstractAction("Adicionar") 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			txfUsuario.setEnabled(true);
			txfSenha.setEnabled(true);
			txfConfirmaSenha.setEnabled(true);
			cbxPerfil.setEnabled(true);
			btnSalvar.setEnabled(true);
			ibInsert = true;			
			btnAdicionar.setEnabled(false);
			btnBuscar.setEnabled(false);
		}
	};
	
	Action eventoSalvar = new AbstractAction("Salvar") 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			if (txfUsuario.getText().isEmpty()) 
			{
				JOptionPane.showMessageDialog(null, "Campo usuário obrigatório");
			}
			else if (txfSenha.getText().toString().isEmpty()) 
			{
				JOptionPane.showMessageDialog(null, "Campo senha obrigatório");
			}
			else if (txfConfirmaSenha.getText().toString().isEmpty()) 
			{
				JOptionPane.showMessageDialog(null, "Campo de confirmação de senha obrigatório");
			}
			else if (cbxPerfil.getSelectedIndex() == 0) 
			{
				JOptionPane.showMessageDialog(null, "Campo perfil obrigatório");
			}
			else if (!txfSenha.getText().toString().equals(txfConfirmaSenha.getText().toString())) 
			{
				JOptionPane.showMessageDialog(null, "Senhas diferentes!");
			}
			else 
			{
				
				if (ibInsert) 
				{
					try 
					{
						dao.Insert(new USUARIO(txfUsuario.getText(), txfSenha.getText().toString(), cbxPerfil.getSelectedItem().toString()));
						JOptionPane.showMessageDialog(null, "Usuário inserido!");
						ibInsert = false;
					} 
					catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(null, "Falha ao inserir o usuário: "+e1.getMessage());
					}
				}
				else 
				{
					// FAZ UPDATE.
					try 
					{
						dao.Update(new USUARIO(txfUsuario.getText(), txfSenha.getText().toString(), cbxPerfil.getSelectedItem().toString()));
						JOptionPane.showMessageDialog(null, "Usuário Atualizado!");
						btnBuscar.doClick();
					} 
					catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(null, "Falha ao atualizar o usuário: "+e1.getMessage());
					}					
				}
				
				txfUsuario.setText(null);
				txfSenha.setText(null);
				txfConfirmaSenha.setText(null);
				cbxPerfil.setSelectedIndex(0);
				
				txfUsuario.setEnabled(false);
				txfSenha.setEnabled(false);
				txfConfirmaSenha.setEnabled(false);
				cbxPerfil.setEnabled(false);
				
				btnSalvar.setEnabled(false);
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(true);
			}
		}
	};

}








