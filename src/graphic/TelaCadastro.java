package graphic;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaCadastro extends JFrame
{

	private JLabel lblNome;
	private JLabel lblCpf;
	private JTextField txfNome;
	private JTextField txfCpf;
	private JButton btbBuscar;
	private JButton btbAdicionar;
	private JButton btbRemover;
	private JButton btbSalvar;
	private JTable tabelaDados;
	private DefaultTableModel model;
	private JScrollPane scroll;
	
	
	public TelaCadastro() 
	{
		setTitle("Ficha de Cadastro");
		setSize(600, 600);
		setLocationRelativeTo(null);
		componentesCriarCadastro();
		setLayout(null);
		setVisible(true);
	}
	
	private void componentesCriarCadastro()
	{
		
		lblNome = new JLabel("Aluno:");
		lblNome.setBounds(20, 70, 100, 25);
		getContentPane().add(lblNome);
		
		lblCpf = new JLabel("Codigo:");
		lblCpf.setBounds(20, 100, 100, 25);
		getContentPane().add(lblCpf);
		
		txfNome = new JTextField();
		txfNome.setBounds(75, 70, 200, 25);
		getContentPane().add(txfNome);
		
		txfCpf = new JTextField();
		txfCpf.setBounds(75, 100, 200, 25);
		getContentPane().add(txfCpf);
		
		btbBuscar = new JButton("Buscar");
		btbBuscar.setBounds(20, 20, 100, 25);
		getContentPane().add(btbBuscar);
		
		btbAdicionar = new JButton(new AbstractAction("Adicionar")
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Autenticar();
				
			}
		});
		btbAdicionar.setBounds(130, 20, 100, 25);
		getContentPane().add(btbAdicionar);
		
		btbRemover = new JButton("Remover");
		btbRemover.setBounds(240, 20, 100, 25);
		getContentPane().add(btbRemover);
		
		btbSalvar = new JButton("Salvar");
		btbSalvar.setBounds(350, 20, 100, 25);
		getContentPane().add(btbRemover);
		
		model = new DefaultTableModel();
		model.addColumn("Codigo");
		model.addColumn("Aluno");
		
		tabelaDados = new JTable(model);
		scroll = new JScrollPane(tabelaDados);
		
		scroll.setBounds(20, 150, 550, 400);
		getContentPane().add(scroll);
		
		
	}
	
	public void Autenticar()
	{
		
		final String nomeDigitado = txfNome.getText();
		final String CpfDigitado = txfCpf.getText();
		
		if(nomeDigitado.isEmpty() && CpfDigitado.isEmpty()) 
		{
			JOptionPane.showMessageDialog(null,"Campo obrigatório");
		}
	}
	
}
