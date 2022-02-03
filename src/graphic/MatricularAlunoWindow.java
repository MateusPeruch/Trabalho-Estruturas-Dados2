package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.dao.MatriculaDAO;
import database.dao.UsuarioDAO;
import database.model.MATRICULA_AUX;
import database.model.USUARIO;
import lib.DataTextField;
import lib.MyTextFieldSearch;

import javax.swing.border.EmptyBorder;

public class MatricularAlunoWindow extends JFrame {

    private JButton btnBuscar, btnAdicionar, btnRemover, btnSalvar;
    private Connection io_connection;
    private JPanel pn_principal, io_bar, pn_bar, pn_dados;
    private JLabel io_lb_matricula, io_lb_aluno, io_lb_data_matricula, io_lb_vencimento, io_lb_data_encerramento;
    private JTextField io_tf_matricula, io_tf_nm_aluno, io_tf_vencimento;
    private MyTextFieldSearch io_tf_cd_aluno;
    private DataTextField io_tf_data_matricula;
    private JTable io_tb_curso;
    private DefaultTableModel modelo_tabela;
    private JScrollPane io_sp_curso;
    private JButton io_bt_add_curso;
    private boolean ib_inserir = false;

    /**
     * Construtor da Classe.
     *
     * @param ao_connection
     * @wbp.parser.constructor
     */
    public MatricularAlunoWindow(Connection ao_connection) {
        //
        // Armazena a conexao com o banco de dados
        //
        io_connection = ao_connection;

        //
        // Chama a criação da janela
        //
        Window();

        //
        // Seta os parâmetros da tela
        //
        setTitle("Matricular Aluno");
        //setIconifiable(true);
        //setClosable(true);
        setVisible(true);
        pack();

        //
        // Inicializa estado componentes da janela
        //
        TelaHabilitar(false);
    }

    /**
     * Cria a janela.
     */
    private void Window() {

        io_bar = new JPanel();
        io_bar.setBounds(0,0,360,35);



        btnBuscar = new JButton(new AbstractAction("Buscar"){
            @Override
            public void actionPerformed(ActionEvent e) {
                io_tf_cd_aluno.setEnabled(true);
            }
        });
        btnBuscar.setBounds(10, 10, 100, 25);
        io_bar.add(btnBuscar);

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(120, 10, 100, 25);
        io_bar.add(btnAdicionar);

        btnRemover = new JButton("Remover");
        btnRemover.setBounds(230, 10, 100, 25);
        io_bar.add(btnRemover);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(340, 10, 100, 25);
        io_bar.add(btnSalvar);

        pn_bar			=	new JPanel(null);
        pn_bar.setPreferredSize(new Dimension(360, 40));
        pn_bar.add(io_bar);

        //
        // Cria os componentes do painel
        //
        io_lb_matricula		=	new JLabel("Matrícula:");
        io_tf_matricula		=	new JTextField();
        io_tf_matricula.setHorizontalAlignment(SwingConstants.RIGHT);
        io_tf_matricula.setEditable(false);
        io_tf_matricula.setFocusable(false);

        io_lb_aluno		=	new JLabel("Aluno:");

        io_tf_cd_aluno		=	new MyTextFieldSearch();
        io_tf_cd_aluno.setHorizontalAlignment(SwingConstants.RIGHT);
        io_tf_cd_aluno.setBackground(Color.YELLOW);
        io_tf_cd_aluno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if(e.getKeyCode() ==  KeyEvent.VK_ENTER){
                    if(!io_tf_cd_aluno.getText().isEmpty()){
                        try {
                            MatriculaDAO dao = new MatriculaDAO(io_connection);
                            List<Object> list = dao.SelectMatriculaCurso(Integer.parseInt(io_tf_cd_aluno.getText()));
                            if(list != null && list.size() > 0){
                                for(int i = 0; i < list.size(); i++){
                                    MATRICULA_AUX matricula_aux = (MATRICULA_AUX)list.get(i);
                                    io_tf_nm_aluno.setText(matricula_aux.getNome_aluno());
                                    io_tf_data_matricula.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(matricula_aux.getDataMatricula()));
                                    io_tf_vencimento.setText(String.valueOf(matricula_aux.getDiaVencimento()));
                                    modelo_tabela.addRow(new String[]{matricula_aux.getNome_curso(), matricula_aux.getNome_fase(), matricula_aux.getCodigo_disciplina(), String.valueOf(matricula_aux.getDataInicio()), matricula_aux.getDataFim()});
                                }
                            }
                        } catch (SQLException | ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

        });


        io_tf_nm_aluno		=	new JTextField();
        io_tf_nm_aluno.setEditable(false);
        io_tf_nm_aluno.setFocusable(false);
        io_lb_data_matricula	=	new JLabel("Data Matrícula:");
        io_tf_data_matricula	=	new DataTextField();
        io_lb_vencimento	=	new JLabel("Dia do vencimento da fatura:");
        io_tf_vencimento	=	new JTextField();
        io_lb_data_encerramento	=	new JLabel("");
        io_lb_data_encerramento.setForeground(Color.RED);
        io_lb_data_encerramento.setFont(new Font("Arial", Font.BOLD, 13));
        io_bt_add_curso	=	new JButton(new AbstractAction("Adicionar Modalidade") {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new WindowCurso(io_connection, false);
            }
        });

        //
        // Criação do modelo para a tabela
        //
        modelo_tabela = new DefaultTableModel();
        modelo_tabela.addColumn("Curso");
        modelo_tabela.addColumn("Fase");
        modelo_tabela.addColumn("Disciplina");
        modelo_tabela.addColumn("Dt.Inicio");
        modelo_tabela.addColumn("Dt.Fim");

        //
        // Cria a JTable que armazena as modalidades da matricula
        //
        io_tb_curso	=	new JTable(modelo_tabela) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        io_tb_curso.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new WindowCurso(io_connection, true);
                }
            }

        });

        //
        // Atributos padrões de seleção da tabela
        //
        io_tb_curso.setAutoCreateColumnsFromModel(false);
        io_tb_curso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //
        // Define as larguras das colunas da tabela
        //
        io_tb_curso.getColumnModel().getColumn(0).setPreferredWidth(100);
        io_tb_curso.getColumnModel().getColumn(1).setPreferredWidth(100);
        io_tb_curso.getColumnModel().getColumn(2).setPreferredWidth(130);
        io_tb_curso.getColumnModel().getColumn(3).setPreferredWidth(75);
        io_tb_curso.getColumnModel().getColumn(4).setPreferredWidth(75);

        //
        // Cria o jscrollpane com a tabela acima
        //
        io_sp_curso	=	new JScrollPane(io_tb_curso);

        io_lb_matricula.setBounds(10, 15, 80, 25);
        io_tf_matricula.setBounds(100, 15, 75, 25);
        io_lb_data_encerramento.setBounds(180, 15, 250, 30);
        io_lb_aluno.setBounds(10, 50, 80, 25);
        io_tf_cd_aluno.setBounds(100, 50, 75, 25);
        io_tf_nm_aluno.setBounds(180, 50, 310, 25);
        io_lb_data_matricula.setBounds(10, 85, 90, 25);
        io_tf_data_matricula.setBounds(100, 85, 75, 25);
        io_lb_vencimento.setBounds(270, 85, 180, 25);
        io_tf_vencimento.setBounds(440, 85, 50, 25);
        io_bt_add_curso.setBounds(10, 130, 180, 25);
        io_sp_curso.setBounds(10, 160, 480, 130);

        //
        // Cria o painel e adiciona os componentes
        //
        pn_dados		=	new JPanel(null);
        pn_dados.setPreferredSize(new Dimension(500,300));
        pn_dados.add(io_lb_matricula);
        pn_dados.add(io_tf_matricula);
        pn_dados.add(io_lb_data_encerramento);
        pn_dados.add(io_lb_aluno);
        pn_dados.add(io_tf_cd_aluno);
        pn_dados.add(io_tf_nm_aluno);
        pn_dados.add(io_lb_data_matricula);
        pn_dados.add(io_tf_data_matricula);
        pn_dados.add(io_lb_vencimento);
        pn_dados.add(io_tf_vencimento);
        pn_dados.add(io_bt_add_curso);
        pn_dados.add(io_sp_curso);
        io_bt_add_curso.addActionListener(null);

        //
        // Cria o painel principal para ajuste da tela
        //
        pn_principal = new JPanel(new BorderLayout());
        pn_principal.add(pn_bar, BorderLayout.NORTH);
        pn_principal.add(pn_dados, BorderLayout.SOUTH);

        getContentPane().add(pn_principal);
    }

    private void TelaHabilitar(boolean ab_enable) {

        io_lb_matricula.setEnabled(ab_enable);
        io_tf_matricula.setEnabled(ab_enable);

        io_lb_aluno.setEnabled(ab_enable);
        io_tf_cd_aluno.setEnabled(ab_enable);
        io_tf_nm_aluno.setEnabled(ab_enable);

        io_lb_data_matricula.setEnabled(ab_enable);
        io_tf_data_matricula.setEnabled(ab_enable);

        io_lb_vencimento.setEnabled(ab_enable);
        io_tf_vencimento.setEnabled(ab_enable);

        io_bt_add_curso.setEnabled(ab_enable);

        io_tb_curso.setEnabled(true);
        io_sp_curso.setEnabled(ab_enable);
    }

    private void TelaAtualizar() {

    }

    private void MatriculaAdicionar() {

    }

    private void MatriculaBuscar() {

    }

    private void MatriculaSalvar() {

    }

    private void ModalidadeSalvar() {

    }

    private void AlunoBuscar() {

    }

    /*============================================================*/

    /**
     * Classe da janela de manipulação dos dados da Modalidade.
     */
    private final class WindowCurso extends JDialog {
        private Connection io_connection;

        private JLabel io_lb_curso, io_lb_fase, io_lb_disciplina, io_lb_data_inicio, io_lb_data_fim;
        private JComboBox io_cb_curso, io_cb_fase, io_cb_disciplina;
        private DataTextField io_dtf_inicio, io_dtf_fim;
        private JButton io_bt_add;
        private JPanel contentPane;
        private boolean ib_atualizar = false;

        /**
         * Construtor da Classe.
         *
         * @param ao_connection
         */
        public WindowCurso(Connection ao_connection, boolean ab_atualizar) {
            //
            // Armazena a conexao com o banco de dados
            //
            io_connection		=	ao_connection;
            ib_atualizar		=	ab_atualizar;

            //
            // Método que desenha a tela
            //
            Window();

            ModalidadeListar();

            if (ib_atualizar) {

                TelaPreencher();

                io_lb_curso.setEnabled(false);
                io_cb_curso.setEnabled(false);

                io_lb_fase.setEnabled(true);
                io_cb_fase.setEnabled(true);

                io_lb_disciplina.setEnabled(true);
                io_cb_disciplina.setEnabled(true);

                io_lb_data_inicio.setEnabled(false);
                io_dtf_inicio.setEnabled(false);

                io_lb_data_fim.setEnabled(true);
                io_dtf_fim.setEnabled(true);

            }
            else{
                io_lb_curso.setEnabled(true);
                io_cb_curso.setEnabled(true);

                io_lb_fase.setEnabled(false);
                io_cb_fase.setEnabled(false);

                io_lb_disciplina.setEnabled(false);
                io_cb_disciplina.setEnabled(false);

                io_lb_data_inicio.setEnabled(false);
                io_dtf_inicio.setEnabled(false);

                io_lb_data_fim.setEnabled(false);
                io_dtf_fim.setEnabled(false);
            }

            //
            // Propriedades da tela
            //
            setTitle("Adicionar Curso");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(true);
            setModal(true);
            setVisible(true);
        }

        /**
         * Cria a janela.
         */
        private void Window() {
            setSize(300, 240);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            GridBagLayout gbl_contentPane = new GridBagLayout();
            gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
            gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
            gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
            gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
            contentPane.setLayout(gbl_contentPane);

            io_lb_curso = new JLabel("Curso:");
            GridBagConstraints gbc_lblModalidade = new GridBagConstraints();
            gbc_lblModalidade.insets = new Insets(5, 5, 5, 5);
            gbc_lblModalidade.anchor = GridBagConstraints.WEST;
            gbc_lblModalidade.gridx = 0;
            gbc_lblModalidade.gridy = 0;
            contentPane.add(io_lb_curso, gbc_lblModalidade);

            io_cb_curso = new JComboBox();
            io_cb_curso.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    io_lb_fase.setEnabled(true);
                    io_cb_fase.setEnabled(true);

                    io_lb_disciplina.setEnabled(true);
                    io_cb_disciplina.setEnabled(true);

                    io_lb_data_inicio.setEnabled(true);
                    io_dtf_inicio.setEnabled(true);

                    io_lb_data_fim.setEnabled(true);
                    io_dtf_fim.setEnabled(true);

                    GraduacaoListar();
                    PlanoListar();
                }
            });
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.fill = GridBagConstraints.HORIZONTAL;
            gbc_textField.insets = new Insets(5, 5, 5, 5);
            gbc_textField.gridx = 1;
            gbc_textField.gridy = 0;
            contentPane.add(io_cb_curso, gbc_textField);

            io_lb_fase = new JLabel("Fase:");
            GridBagConstraints gbc_lblGraduao = new GridBagConstraints();
            gbc_lblGraduao.anchor = GridBagConstraints.WEST;
            gbc_lblGraduao.insets = new Insets(5, 5, 5, 5);
            gbc_lblGraduao.gridx = 0;
            gbc_lblGraduao.gridy = 1;
            contentPane.add(io_lb_fase, gbc_lblGraduao);

            io_cb_fase = new JComboBox();
            GridBagConstraints gbc_textField_1 = new GridBagConstraints();
            gbc_textField_1.insets = new Insets(5, 5, 5, 5);
            gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
            gbc_textField_1.gridx = 1;
            gbc_textField_1.gridy = 1;
            contentPane.add(io_cb_fase, gbc_textField_1);

            io_lb_disciplina = new JLabel("Disciplina:");
            GridBagConstraints gbc_lblAviso = new GridBagConstraints();
            gbc_lblAviso.anchor = GridBagConstraints.WEST;
            gbc_lblAviso.insets = new Insets(5, 5, 5, 5);
            gbc_lblAviso.gridx = 0;
            gbc_lblAviso.gridy = 2;
            contentPane.add(io_lb_disciplina, gbc_lblAviso);

            io_cb_disciplina = new JComboBox();
            GridBagConstraints gbc_cb_plano = new GridBagConstraints();
            gbc_cb_plano.insets = new Insets(5, 5, 5, 5);
            gbc_cb_plano.fill = GridBagConstraints.HORIZONTAL;
            gbc_cb_plano.gridx = 1;
            gbc_cb_plano.gridy = 2;
            contentPane.add(io_cb_disciplina, gbc_cb_plano);

            io_lb_data_inicio = new JLabel("Data Início:");
            GridBagConstraints gbc_lb_data_inicio = new GridBagConstraints();
            gbc_lb_data_inicio.insets = new Insets(5, 5, 5, 5);
            gbc_lb_data_inicio.fill = GridBagConstraints.HORIZONTAL;
            gbc_lb_data_inicio.gridx = 0;
            gbc_lb_data_inicio.gridy = 3;
            contentPane.add(io_lb_data_inicio, gbc_lb_data_inicio);

            io_dtf_inicio = new DataTextField();
            GridBagConstraints gbc_dtf_inicio = new GridBagConstraints();
            gbc_dtf_inicio.insets = new Insets(5, 5, 5, 5);
            gbc_dtf_inicio.fill = GridBagConstraints.HORIZONTAL;
            gbc_dtf_inicio.gridx = 1;
            gbc_dtf_inicio.gridy = 3;
            contentPane.add(io_dtf_inicio, gbc_dtf_inicio);

            io_lb_data_fim = new JLabel("Data Fim:");
            GridBagConstraints gbc_lb_data_fim = new GridBagConstraints();
            gbc_lb_data_fim.insets = new Insets(5, 5, 5, 5);
            gbc_lb_data_fim.fill = GridBagConstraints.HORIZONTAL;
            gbc_lb_data_fim.gridx = 0;
            gbc_lb_data_fim.gridy = 4;
            contentPane.add(io_lb_data_fim, gbc_lb_data_fim);

            io_dtf_fim = new DataTextField();
            GridBagConstraints gbc_dtf_fim = new GridBagConstraints();
            gbc_dtf_fim.insets = new Insets(5, 5, 5, 5);
            gbc_dtf_fim.fill = GridBagConstraints.HORIZONTAL;
            gbc_dtf_fim.gridx = 1;
            gbc_dtf_fim.gridy = 4;
            contentPane.add(io_dtf_fim, gbc_dtf_fim);

            io_bt_add = new JButton("OK");
            GridBagConstraints gbc_btnOk = new GridBagConstraints();
            gbc_btnOk.anchor = GridBagConstraints.EAST;
            gbc_btnOk.insets = new Insets(0, 0, 0, 5);
            gbc_btnOk.gridx = 1;
            gbc_btnOk.gridy = 5;
            contentPane.add(io_bt_add, gbc_btnOk);
            io_bt_add.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (io_cb_curso.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Campo curso obrigatório!");
                    }
                    else if (io_cb_fase.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Campo fase obrigatório!");
                    }
                    else if (io_cb_disciplina.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Campo disciplina obrigatório!");
                    }
                    else if (io_dtf_inicio.getDate() == null) {
                        JOptionPane.showMessageDialog(null, "Você deve inserir a data de inicio !");
                    }
                    else {

                        if (ib_atualizar) {

                            int linha = io_tb_curso.getSelectedRow();
                            io_tb_curso.setValueAt(io_cb_curso.getSelectedItem().toString(), linha, 0);
                            io_tb_curso.setValueAt(io_cb_fase.getSelectedItem().toString(), linha, 1);
                            io_tb_curso.setValueAt(io_cb_disciplina.getSelectedItem().toString(), linha, 2);
                            io_tb_curso.setValueAt(io_dtf_inicio.getText(), linha, 3);
                            io_tb_curso.setValueAt(io_dtf_fim.getText(), linha, 4);

                            dispose();
                        }
                        else {

                            if (isModalidadeDuplicada()) {
                                JOptionPane.showMessageDialog(null, "Aluno já matriculado nesta modalidade!!!");
                            }
                            else {
                                modelo_tabela.addRow
                                        (
                                                new Object[]
                                                        {
                                                                io_cb_curso.getSelectedItem().toString(),
                                                                io_cb_fase.getSelectedItem().toString(),
                                                                io_cb_disciplina.getSelectedItem().toString(),
                                                                new SimpleDateFormat("dd/MM/yyyy").format(io_dtf_inicio.getDate()),
                                                                null
                                                        }
                                        );

                                dispose();
                            }
                        }
                    }
                }
            });
        }

        private boolean isModalidadeDuplicada() {

            boolean duplicado = false;
            if (io_tb_curso.getRowCount() > 0) {
                for (int i = 0; i < io_tb_curso.getRowCount(); i++) {
                    if (io_cb_curso.getSelectedItem().toString().equals(io_tb_curso.getValueAt(i, 0))) {
                        duplicado = true;
                        break;
                    }
                }
            }

            return duplicado;
        }

        private void TelaPreencher() {

            int ln_linha = io_tb_curso.getSelectedRow();

            io_cb_curso.setSelectedItem(io_tb_curso.getValueAt(ln_linha, 0).toString());
            io_cb_fase.setSelectedItem(io_tb_curso.getValueAt(ln_linha, 1).toString());
            io_cb_disciplina.setSelectedItem(io_tb_curso.getValueAt(ln_linha, 2).toString());

            io_dtf_inicio.setText(io_tb_curso.getValueAt(ln_linha, 3).toString());

        }

        private void ModalidadeListar() {
            io_cb_curso.addItem("-- Selecione --");
        }

        private void GraduacaoListar() {

            while (io_cb_fase.getItemCount() > 0) {
                io_cb_fase.removeItemAt(0);
            }

            io_cb_fase.addItem("-- Selecione --");
        }

        private void PlanoListar() {

            while (io_cb_disciplina.getItemCount() > 0) {
                io_cb_disciplina.removeItemAt(0);
            }

            io_cb_disciplina.addItem("-- Selecione --");
        }

    }
}










