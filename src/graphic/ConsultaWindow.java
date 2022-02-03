package graphic;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class ConsultaWindow
{

    private Connection conexao;
    private JMenuBar menuBar;
    private JMenu fase;
    private JMenuItem consultaFase;
    private JMenu curso;
    private JMenuItem computacao;
    private JMenu disciplina;
    private JMenuItem consultaDisciplina;
    private int Limpatabela = 0;
    private JScrollPane scroll;
    private JTable tabelaDados;
    private DefaultTableModel model;
    private JFrame frame;
    private JDesktopPane desktop;


    public ConsultaWindow(final Connection conexao)
    {
        this.conexao = conexao;
        frame = new JFrame();
        frame.setSize(1600, 950);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        Menucriar();
    }

    private final void Menucriar()
    {

        menuBar = new JMenuBar();
        
        curso = new JMenu("Curso");
        computacao = new JMenuItem(new AbstractAction("Computacao")
        {
            @Override
            public void actionPerformed(ActionEvent e){
            fase.setEnabled(true);
            disciplina.setEnabled(true);
            }
        });
        curso.add(computacao);
        
        fase = new JMenu("Fase");
        fase.setEnabled(false);
        	consultaFase = new JMenuItem(new AbstractAction("Consulta por fase")
        	{
        		public void actionPerformed(ActionEvent e)
        		{
                    desktop.add(new ConsultaFase(conexao));
        		}
        	}
        	);
        fase.add(consultaFase);
        
        disciplina = new JMenu("Disciplina");
        disciplina.setEnabled(false);
        	consultaDisciplina = new JMenuItem(new AbstractAction("Consultar disciplinas")
        		{
        			public void actionPerformed(ActionEvent e)
        			{
                        desktop.add(new ConsultaDisciplina(conexao));
        			}
        		});
        disciplina.add(consultaDisciplina);
        	
        menuBar.add(curso);
        menuBar.add(fase);
        menuBar.add(disciplina);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(criarcontainer());
    }

    private Container criarcontainer(){
        JPanel panel = new JPanel(new BorderLayout());
        desktop = new JDesktopPane();
        panel.setOpaque(true);
        panel.add(desktop);
        return panel;
    }
}
