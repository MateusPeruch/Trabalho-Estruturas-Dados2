package graphic;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import database.dao.FaseDAO;
import database.dao.MatriculaDAO;
import database.dao.TesteDAO;
import database.model.FASE;
import database.model.MATRICULA_AUX;

public class ConsultaFase extends JInternalFrame
{
	private Connection conexao;
	private JComboBox<String> cbxFase;
	private DefaultTableModel model;
	private JTable tabelaDados;
	private JScrollPane scroll;
	private JButton btnBuscar;
	
	public ConsultaFase(final Connection conexao){
		this.conexao = conexao;
		
		setTitle("Pesquisar por fase");
		setSize(700, 500);
		setLayout(null);
		setClosable(true);
		setIconifiable(true);
		componentesCriar();
		setVisible(true);
	}
	
	private void componentesCriar(){
		cbxFase = new JComboBox<String>();
		cbxFase.addItem("Selection fase");

		try {
			TesteDAO dao = new TesteDAO(conexao);
			List<Object> list = dao.Select();
			for(int i = 0; i < list.size(); i++){
				FASE fase = (FASE)list.get(i);
				cbxFase.addItem(fase.getNomefase());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cbxFase.setEnabled(true);
		cbxFase.setBounds(250, 50, 200, 25);
		getContentPane().add(cbxFase);
		
		model = new DefaultTableModel();
        model.addColumn("Disciplinas");
        model.addColumn("Dia da semana");
        model.addColumn("Professor");
        model.addColumn("Titulo docente");
        tabelaDados = new JTable(model)
        {
        	public boolean editCellAt(int row, int column, java.util.EventObject e) 
        	{
        		return false;
        	}
        };
        scroll = new JScrollPane(tabelaDados);
        scroll.setBounds(100, 200, 500, 200);
        getContentPane().add(scroll);
        
        btnBuscar = new JButton(new AbstractAction("Buscar") 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				while(tabelaDados.getRowCount() > 0)
		    	{
		    		model.removeRow(0);
		    	}
		    	
		    	try
		    	{
		    		FaseDAO dao = new FaseDAO(conexao, cbxFase.getSelectedIndex());
		    		List<Object> lst = dao.Select();
		    		
		    		for (int i = 0; i < lst.size(); i++) 
					{
						FASE fase = (FASE)lst.get(i);
						model.addRow(new String[] {fase.getDisciplina(), fase.getDiaSemana(), fase.getNomeProfessor(), fase.getTituloProfessor()});
					}
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(300, 90, 100, 25);
		getContentPane().add(btnBuscar);
	}
}
