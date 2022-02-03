package graphic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import database.dao.CursoDAO;
import database.dao.DisciplinaDAO;
import database.dao.FaseDAO;

public class Importa extends JInternalFrame
{
    private JButton btbimporta;
    private JButton btbConsulta;
    private JTable tabelaDados1;
    private JTable tabelaDados2;
    private JTable tabelaDados3;
    private JTextField txfCaminho;
    private JLabel lblcurso;
    private JLabel Curso;
    private JLabel ano;
    private JLabel lblano;
    private JLabel faseInicial;
    private JLabel lblfaseInicial;
    private JLabel faseFinal;
    private JLabel lblfaseFinal;
    private JLabel versao;
    private JLabel lblversao;
    private JLabel lblRegistro;
    private JLabel registro;
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    private JScrollPane scroll3;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    private Connection conexao;
    private CursoDAO cursoDAO;
    private FaseDAO faseDAO;
    private DisciplinaDAO disciplinaDAO;
    private Metodos metados;
    private String faseAtual;
    private String disciplinaAtual;
    private String professoratual;
    private String diaatual;
    private String formacaoProfessor;
    private int verifica;
    private static final String SEPARATOR = ";";

    private HashMap<String, String> hmpDisciplina = new HashMap<String, String>();
    {
        hmpDisciplina.put("23215","Algoritmos e Programação");
        hmpDisciplina.put("23217", "Cenários da Tecnologia");
        hmpDisciplina.put("23216", "Fundamentos Matemáticos");
        hmpDisciplina.put("23219", "Lógica para Computação");
        hmpDisciplina.put("23218", "Sistemas Digitais I");
        hmpDisciplina.put("23221", "Cálculo I");
        hmpDisciplina.put("23222", "Design de Interação");
        hmpDisciplina.put("23220", "Laboratório de Programação");
        hmpDisciplina.put("23224", "Metodologia Cientifica e da Pesquisa");
        hmpDisciplina.put("23272", "Sistemas Digitais II");
        hmpDisciplina.put("23227", "Arquitetura de Computadores");
        hmpDisciplina.put("23273", "Cálculo II");
        hmpDisciplina.put("23226", "Estrutura de Dados I");
        hmpDisciplina.put("23228", "Programação Orientada a Objetos");
        hmpDisciplina.put("23225", "Programação Web I");
        hmpDisciplina.put("23223", "Sociologia");
        hmpDisciplina.put("23229", "Banco de Dados I");
        hmpDisciplina.put("23275", "Estrutura de Dados II");
        hmpDisciplina.put("23230", "Projeto e Otimização de Algoritmos");
        hmpDisciplina.put("23231", "Projeto Integrador I");
        hmpDisciplina.put("23232", "Sistemas Operacionais");
        hmpDisciplina.put("23276", "Banco de Dados II");
        hmpDisciplina.put("23235", "Comunicação de Dados");
        hmpDisciplina.put("23233", "Engenharia de Software I");
        hmpDisciplina.put("23236", "Produção e Interpretação de Textos");
        hmpDisciplina.put("23274", "Programação Web II");
        hmpDisciplina.put("23234", "Teoria de Grafos");
        hmpDisciplina.put("23278", "Engenharia de Software II");
        hmpDisciplina.put("23237", "Linguagens Formais");
        hmpDisciplina.put("23238", "Redes de Computadores");
        hmpDisciplina.put("23239", "Programação para Dispositivos Móveis");
        hmpDisciplina.put("23277", "Projeto Integrador II");
        hmpDisciplina.put("10886", "Administração Empresarial");
        hmpDisciplina.put("10887", "TCC I");
        hmpDisciplina.put("10883", "Computação Paralela e Distribuída");
        hmpDisciplina.put("10885", "Inteligência Artificial");
        hmpDisciplina.put("10884", "Computação Gráfica");
        hmpDisciplina.put("10889", "TCC II");
        hmpDisciplina.put("10888", "Tópicos Especiais II");
        hmpDisciplina.put("10890", "Gestão de Sistemas de Informação");
        hmpDisciplina.put("10891", "Empreendedorismo");
        hmpDisciplina.put("10894", "TCC III");
        hmpDisciplina.put("10901", "Java para Web");
        hmpDisciplina.put("10893", "Tópicos Especiais III");
    }
    
    private HashMap<String,String> hmpDias = new HashMap<String,String>();
    {
        hmpDias.put("02", "Segunda-feira");
        hmpDias.put("03", "Terca-feira");
        hmpDias.put("04", "Quarta-feira");
        hmpDias.put("05", "Quinta-feira");
        hmpDias.put("06", "Sexta-feira");
        hmpDias.put("07", "Sabado");
    }
    
    private HashMap<String, String> hmpFormacao = new HashMap<String,String>();
    {
        hmpFormacao.put("01", "Pos-Graduacao");
        hmpFormacao.put("02", "Mestrado");
        hmpFormacao.put("03", "Doutorado");
        hmpFormacao.put("04", "Pos-Doutorado");
    }

    public Importa(final Connection conexao) throws SQLException
    {
    	this.conexao = conexao;
    	cursoDAO = new CursoDAO(this.conexao);
    	faseDAO = new FaseDAO(this.conexao, 0);
        disciplinaDAO = new DisciplinaDAO(this.conexao);
        metados = new Metodos(this.conexao);
    	
        setTitle("Ficha de Cadastro");
        setSize(1300, 500);
        setClosable(true);
        setIconifiable(true);
        setLayout(null);
        Componentes();
        setVisible(true);
    }
    private void Componentes()
    {

        btbimporta = new JButton(new AbstractAction("Importar")
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione apenas arquivos.txt", "txt");
                chooser.setFileFilter(filter);
                int retorno = chooser.showOpenDialog(null);
                if(retorno == JFileChooser.APPROVE_OPTION)
                {
                    txfCaminho.setText(chooser.getSelectedFile().getAbsolutePath());
                    Lerflags();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Arquivo não importado");
                }
            }
        });
        btbimporta.setBounds(370, 30, 100, 25);
        getContentPane().add(btbimporta);

        txfCaminho = new JTextField();
        txfCaminho.setBounds(50, 30, 300, 25);
        getContentPane().add(txfCaminho);
        txfCaminho.setEnabled(false);

        Curso = new JLabel("Curso: ");
        Curso.setBounds(1100, 30, 150, 25);
        getContentPane().add(Curso);
        lblcurso = new JLabel();
        lblcurso.setBounds(1140, 30, 150, 25);
        getContentPane().add(lblcurso);

        ano = new JLabel("Ano: ");
        ano.setBounds(1100, 50, 150, 25);
        getContentPane().add(ano);
        lblano = new JLabel();
        lblano.setBounds(1130, 50, 100, 25);
        getContentPane().add(lblano);

        faseInicial = new JLabel("Fase incial: ");
        faseInicial.setBounds(1100, 70, 150, 25);
        getContentPane().add(faseInicial);
        lblfaseInicial = new JLabel();
        lblfaseInicial.setBounds(1165, 70, 100, 25);
        getContentPane().add(lblfaseInicial);

        faseFinal = new JLabel("Fase final: ");
        faseFinal.setBounds(1100, 90, 150, 25);
        getContentPane().add(faseFinal);
        lblfaseFinal = new JLabel();
        lblfaseFinal.setBounds(1160, 90, 100, 25);
        getContentPane().add(lblfaseFinal);

        versao = new JLabel("Versao: ");
        versao.setBounds(1100, 110, 150, 25);
        getContentPane().add(versao);
        lblversao = new JLabel();
        lblversao.setBounds(1145,110,60,25);
        getContentPane().add(lblversao);


        model1 = new DefaultTableModel();
        model1.addColumn("Fase");
        model1.addColumn("Quantidade Disciplina");
        model1.addColumn("Quantidade professor");

        tabelaDados1 = new JTable(model1);
        scroll1 = new JScrollPane(tabelaDados1);
        scroll1.setBounds(20, 150, 400, 200);
        getContentPane().add(scroll1);

        model2 = new DefaultTableModel();
        model2.addColumn("Disciplinas");
        model2.addColumn("Dia semana");
        model2.addColumn("Quantidade professor");

        tabelaDados2 = new JTable(model2);
        scroll2 = new JScrollPane(tabelaDados2);
        scroll2.setBounds(440, 150, 400, 200);
        getContentPane().add(scroll2);

        model3 = new DefaultTableModel();
        model3.addColumn("Professor");
        model3.addColumn("Formacao do professor");

        tabelaDados3 = new JTable(model3);
        scroll3 = new JScrollPane(tabelaDados3);

        scroll3.setBounds(870, 150, 400, 200);
        getContentPane().add(scroll3);

        btbConsulta = new JButton(new AbstractAction("Consulta"){
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaWindow(conexao);
            }
        });
        btbConsulta.setBounds(1145,400,100,25);
        getContentPane().add(btbConsulta);
    }

    private void Lerflags()
    {
        try
        {
            InputStream stream = new FileInputStream(txfCaminho.getText());
            InputStreamReader sreader = new InputStreamReader(stream);
            BufferedReader Buffer = new BufferedReader(sreader);

            String linha = Buffer.readLine();
            while (linha != null)
            {
                final String partes [] = linha.split(SEPARATOR);

                if(partes[0].equalsIgnoreCase("0"))//curso
                {
                    lblcurso.setText(partes[1]);
                    lblano.setText(partes[2]);
                    lblfaseInicial.setText(partes[3].replaceAll("0", ""));
                    lblfaseFinal.setText(partes[4].replaceAll("0", ""));
                    lblversao.setText(partes[6]);
                    metados.inserirCurso(lblcurso.getText());
                }
                else if(partes[0].equalsIgnoreCase("1"))//fase
                {
                    model1.addRow(new String[]{partes[1], partes[2], partes[3]});
                    faseAtual = partes[1];
                    metados.inserirFase(faseAtual, metados.getIdcurso());
                }
                else if(partes[0].equalsIgnoreCase("2"))//disciplina
                {
                    model2.addRow(new String []{hmpDisciplina.get(partes[1]), hmpDias.get(partes[2]), partes[3]});
                    disciplinaAtual = hmpDisciplina.get(partes[1]);
                    diaatual = hmpDias.get(partes[2]);
                    metados.inserirDisciplina(disciplinaAtual, diaatual, metados.getIdfase());
                }
                else if(partes[0].equalsIgnoreCase("3"))//professor
                {
                    model3.addRow(new String[]{partes[1].replaceAll("0", ""), hmpFormacao.get(partes[2])});
                    professoratual = partes[1].replaceAll("0", "");
                    formacaoProfessor = hmpFormacao.get(partes[2]);
                    metados.inserirProfessor(professoratual, formacaoProfessor, metados.getIdDisciplina());
                }
                linha = Buffer.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Arquivo nao encontrado");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Falha ao ler o arquivo");
            e.printStackTrace();
        }
    }
}





