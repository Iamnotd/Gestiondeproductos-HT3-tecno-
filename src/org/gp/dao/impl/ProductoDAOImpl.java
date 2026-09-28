package org.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gp.model.Producto;
import org.gp.util.Conexion;

public class ProductoDAOImpl implements ProductoDAO {

    private final Connection conexion;

    public ProductoDAOImpl() {
        conexion = Conexion.getInstancia().getConexion();
    }


    @Override
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT id_producto, nombre, descripcion, precio, stock "
                   + "FROM productos ORDER BY id_producto";

        try (Statement statement = conexion.createStatement();
             ResultSet resultado = statement.executeQuery(sql)) {

            while (resultado.next()) {

                Producto producto = new Producto(
                        resultado.getInt("id_producto"),
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("precio"),
                        resultado.getInt("stock")
                );

                productos.add(producto);
            }
        }

        return productos;
    }


    public Producto buscarPorId(int idProducto) throws SQLException {

        String sql = "{CALL buscar_producto_por_id(?)}";

        try (CallableStatement callable =
                conexion.prepareCall(sql)) {

            callable.setInt(1, idProducto);

            try (ResultSet resultado = callable.executeQuery()) {

                if (resultado.next()) {

                    return new Producto(
                            resultado.getInt("id_producto"),
                            resultado.getString("nombre"),
                            resultado.getString("descripcion"),
                            resultado.getDouble("precio"),
                            resultado.getInt("stock")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public boolean insertar(Producto producto) throws SQLException {

        String sql = "INSERT INTO productos "
                   + "(nombre, descripcion, precio, stock) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement prepared =
                conexion.prepareStatement(sql)) {

            prepared.setString(1, producto.getNombre());
            prepared.setString(2, producto.getDescripcion());
            prepared.setDouble(3, producto.getPrecio());
            prepared.setInt(4, producto.getStock());

            int filasAfectadas = prepared.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean actualizar(Producto producto) throws SQLException {

        String sql = "UPDATE productos "
                   + "SET nombre = ?, descripcion = ?, "
                   + "precio = ?, stock = ? "
                   + "WHERE id_producto = ?";

        try (PreparedStatement prepared =
                conexion.prepareStatement(sql)) {

            prepared.setString(1, producto.getNombre());
            prepared.setString(2, producto.getDescripcion());
            prepared.setDouble(3, producto.getPrecio());
            prepared.setInt(4, producto.getStock());
            prepared.setInt(5, producto.getIdProducto());

            int filasAfectadas = prepared.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean eliminar(int idProducto) throws SQLException {

        String sql = "DELETE FROM productos "
                   + "WHERE id_producto = ?";

        try (PreparedStatement prepared =
                conexion.prepareStatement(sql)) {

            prepared.setInt(1, idProducto);

            int filasAfectadas = prepared.executeUpdate();

            return filasAfectadas > 0;
        }
    }
}