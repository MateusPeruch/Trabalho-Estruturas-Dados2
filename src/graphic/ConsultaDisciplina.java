package graphic;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import database.dao.DisciplinaDAO;
import database.model.DISCIPLINA;

public class ConsultaDisciplina extends JInternalFrame
{
	
	private Connection conexao;
	private DefaultTableModel model;
	private JTable tabelaDados;
	private JScrollPane scroll;
	private JButton btnBuscar;
	
	public ConsultaDisciplina(final Connection conexao)
	{
		this.conexao = conexao;
		
		setTitle("Consulta Disciplinas");
		setSize(700, 500);
		setLayout(null);
		setClosable(true);
		setIconifiable(true);
		componentesCriar();
		setVisible(true);
	}
	
	private void componentesCriar()
	{
		model = new DefaultTableModel();
        model.addColumn("Disciplinas");
        model.addColumn("Dia da semana");
        model.addColumn("Professor");
        tabelaDados = new JTable(model)
        {
        	public boolean editCellAt(int row, int column, java.util.EventObject e) 
        	{
        		return false;
        	}
        };
        scroll = new JScrollPane(tabelaDados);
        scroll.setBounds(100, 100, 500, 320);
        getContentPane().add(scroll);
        
        btnBuscar = new JButton(new AbstractAction("Buscar") 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				while(tabelaDados.getRowCount() > 0)
		    	{
		    		model.removeRow(0);
		    		System.out.println("Teste");
		    	}
		    	
		    	try
		    	{
		    		DisciplinaDAO dao = new DisciplinaDAO(conexao);
		    		List<Object> lst = dao.Select();
		    		
		    		for (int i = 0; i < lst.size(); i++) 
					{
						DISCIPLINA disciplina = (DISCIPLINA)lst.get(i);
						model.addRow(new String[] {disciplina.getDisciplina(), disciplina.getDiaAtual(), disciplina.getNomeProfessor()});
					}
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(300, 40, 100, 25);
		getContentPane().add(btnBuscar);
	}
	
}
