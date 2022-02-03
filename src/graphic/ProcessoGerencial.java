package graphic;

import database.dao.MatriculaDAO;
import database.dao.UsuarioDAO;
import database.dao.alunoDAO;
import database.model.ALUNO;
import database.model.MATRICULA_AUX;
import database.model.USUARIO;
import lib.MyTextFieldSearch;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessoGerencial extends JInternalFrame {

    private Connection conexao;
    private JTable tabelaDados1;
    private JScrollPane scroll1;
    private DefaultTableModel model1;
    private JTable tabelaDados2;
    private JScrollPane scroll2;
    private DefaultTableModel model2;
    private JTable tabelaDados3;
    private JScrollPane scroll3;
    private DefaultTableModel model3;
    private JTable tabelaDados4;
    private JScrollPane scroll4;
    private DefaultTableModel model4;
    private JTable tabelaDados5;
    private JScrollPane scroll5;
    private DefaultTableModel model5;
    private MyTextFieldSearch txfcodigo;
    private MyTextFieldSearch txfNome;
    private MyTextFieldSearch txfconsulta;
    private JButton btn1;
    private JButton btn2;


    public ProcessoGerencial(final Connection conexao) throws SQLException
    {
        this.conexao = conexao;
        setTitle("Controle de alunos");
        setSize(700, 550);
        setLayout(null);
        setClosable(false);
        setIconifiable(true);
        componentesCriar();
        setVisible(true);
    }

    private void componentesCriar()
    {
        model1 = new DefaultTableModel();
        tabelaDados1 = new JTable(model1);
        scroll1 = new JScrollPane(tabelaDados1);
        scroll1.setBounds(25, 20, 190, 220);
        getContentPane().add(scroll1);

        model2 = new DefaultTableModel();
        model2.addColumn("Aniversario");
        tabelaDados2 = new JTable(model2);
        scroll2 = new JScrollPane(tabelaDados2);
        scroll2.setBounds(25, 280, 190, 220);
        getContentPane().add(scroll2);

        model3 = new DefaultTableModel();
        model3.addColumn("Curso");
        model3.addColumn("Fase");
        model3.addColumn("Disciplina");
        model3.addColumn("Data inicio");
        model3.addColumn("Data fim");
        tabelaDados3 = new JTable(model3);
        scroll3 = new JScrollPane(tabelaDados3);
        scroll3.setBounds(230, 50, 440, 120);
        getContentPane().add(scroll3);

        txfconsulta = new MyTextFieldSearch();
        txfconsulta.setBackground(Color.white);
        txfconsulta.setBounds(230, 180, 440, 60);
        txfconsulta.setEnabled(false);
        getContentPane().add(txfconsulta);


        model5 = new DefaultTableModel();
        model5.addColumn("Vencimento");
        model5.addColumn("Valor");
        tabelaDados5 = new JTable(model5);
        scroll5 = new JScrollPane(tabelaDados5);
        scroll5.setBounds(230, 290, 440, 210);
        getContentPane().add(scroll5);

        btn1 = new JButton("Acessar dados do aluno");
        btn1.setBounds(230,250,200,30);
        getContentPane().add(btn1);

        btn2 = new JButton(new AbstractAction("Acessar dados do Matricula"){
            @Override
            public void actionPerformed(ActionEvent e){
                new MatricularAlunoWindow(conexao);
            }
        });
        btn2.setBounds(468,250,200,30);
        getContentPane().add(btn2);

        txfcodigo = new MyTextFieldSearch();
        txfcodigo.setBounds(230,20,70,25);
        getContentPane().add(txfcodigo);
        txfcodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() ==  KeyEvent.VK_ENTER){
                    if(!txfcodigo.getText().isEmpty()){
                        while (model2.getRowCount() > 0)
                        {
                            model2.removeRow(0);
                        }
                        teste();
                        try {
                            alunoDAO dao = new alunoDAO(conexao);
                            List<Object> list = dao.Selectwhere(Integer.parseInt(txfcodigo.getText()));
                            if(list != null && list.size() > 0){
                                for(int i = 0; i < list.size(); i++){
                                    ALUNO aluno = (ALUNO)list.get(i);
                                    txfNome.setText(aluno.getNome());
                                    SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
                                    Date data2 = formatoData.parse(aluno.getData_nascimeento());
                                    SimpleDateFormat formatoRetorno = new SimpleDateFormat("dd/MM/yyyy");
                                    model2.addRow(new String[]{formatoRetorno.format(data2)});
                                }
                            }
                        } catch (SQLException | ParseException ex) {
                            ex.printStackTrace();
                        }}}
                super.keyPressed(e);
            }
        });

        txfNome = new MyTextFieldSearch();
        txfNome.setBackground(Color.white);
        txfNome.setEnabled(false);
        txfNome.setBounds(310,20,360,25);
        getContentPane().add(txfNome);

    }

    private void teste(){
        while (model3.getRowCount() > 0 || model5.getRowCount() > 0)
        {
            model3.removeRow(0);
            model5.removeRow(0);
        }
        try {
            MatriculaDAO dao = new MatriculaDAO(conexao);
            List<Object> list = dao.SelectMatriculaCurso(Integer.parseInt(txfcodigo.getText()));
            if(list != null && list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    MATRICULA_AUX matricula_aux = (MATRICULA_AUX) list.get(i);
                        txfNome.setText(matricula_aux.getNome_aluno());
                        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
                        Date data = formatoData.parse(matricula_aux.getDataMatricula());
                        SimpleDateFormat formatoRetorno = new SimpleDateFormat("dd/MM/yyyy");
                        model3.addRow(new String[]{matricula_aux.getNome_curso(), matricula_aux.getNome_fase(), matricula_aux.getCodigo_disciplina(), formatoRetorno.format(data), matricula_aux.getDataFim()});
                        model5.addRow(new String[]{formatoRetorno.format(data), String.valueOf(matricula_aux.getValor())});
                        txfconsulta.setBackground(Color.green);
                }
            }else{
                txfconsulta.setBackground(Color.RED);
                txfNome.setText("");
            }
    } catch (SQLException | ParseException exception) {
            exception.printStackTrace();
        }

    }

}

