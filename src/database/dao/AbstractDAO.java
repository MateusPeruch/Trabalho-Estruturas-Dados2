package database.dao;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO 
{
	
	public abstract List<Object> Select() throws SQLException;
	
	public abstract int Insert(final Object parametros) throws SQLException;
	
	public abstract void Update(final Object parametros) throws SQLException;
	
	public abstract void Delete(final Object parametros) throws SQLException;

}
