package database.dao;

import database.model.DISCIPLINA;
import database.model.FASE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteDAO extends AbstractDAO{

    private final String select = "select nomeFase from fase;";
    private final PreparedStatement psSelect;

    public TesteDAO(final Connection conexao) throws SQLException {
        psSelect = conexao.prepareStatement(select);
    }

    @Override
    public List<Object> Select() throws SQLException {

        List<Object> listaNomeFase = new ArrayList<Object>();
        ResultSet resultado = psSelect.executeQuery();

        if (resultado != null)
        {
            while (resultado.next())
            {
                FASE fase = new FASE();
                fase.setNomefase(resultado.getString("nomeFase"));
                listaNomeFase.add(fase);
            }
            resultado.close();
        }
        return listaNomeFase;
    }

    @Override
    public int Insert(Object parametros) throws SQLException {
        return 0;
    }

    @Override
    public void Update(Object parametros) throws SQLException {

    }

    @Override
    public void Delete(Object parametros) throws SQLException {

    }
}
