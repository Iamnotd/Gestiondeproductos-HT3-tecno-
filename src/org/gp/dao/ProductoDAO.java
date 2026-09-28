package org.gp.dao;

import java.sql.SQLException;
import java.util.List;
import org.gp.model.Producto;

public interface ProductoDAO {

    List<Producto> listar() throws SQLException;

    Producto buscarPorId(int idProducto) throws SQLException;

    boolean insertar(Producto producto) throws SQLException;

    boolean actualizar(Producto producto) throws SQLException;

    boolean eliminar(int idProducto) throws SQLException;
}