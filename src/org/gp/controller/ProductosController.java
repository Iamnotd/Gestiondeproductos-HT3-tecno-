package org.gp.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.gp.dao.ProductoDAO;
import org.gp.dao.impl.ProductoDAOImpl;
import org.gp.model.Producto;

public class ProductosController implements Initializable {



    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;


    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colDescripcion;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;



    private ProductoDAO productoDAO;

    private ObservableList<Producto> listaProductos;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productoDAO = new ProductoDAOImpl();


        colId.setCellValueFactory(
                new PropertyValueFactory<>("idProducto")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        colStock.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );


        txtId.setEditable(true);


        tablaProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        cargarProductoSeleccionado(seleccionado);
                    }
                });


        listarProductos();
    }



    @FXML
    private void guardarProducto() {

        try {

            if (!validarCampos()) {
                return;
            }

            String nombre = txtNombre.getText().trim();

            String descripcion =
                    txtDescripcion.getText().trim();

            double precio =
                    Double.parseDouble(
                            txtPrecio.getText().trim()
                    );

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            Producto producto = new Producto(
                    nombre,
                    descripcion,
                    precio,
                    stock
            );

            boolean guardado =
                    productoDAO.insertar(producto);

            if (guardado) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Producto registrado",
                        "El producto fue registrado correctamente."
                );

                limpiarCampos();
                listarProductos();

            } else {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo registrar el producto."
                );
            }

        } catch (SQLException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    e.getMessage()
            );
        }
    }


    @FXML
    private void buscarProducto() {

        String entrada = txtId.getText().trim();

        if (entrada.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "ID requerido",
                    "Ingresa el ID del producto que deseas buscar."
            );

            return;
        }

        try {

            int id = Integer.parseInt(entrada);

            Producto producto =
                    productoDAO.buscarPorId(id);

            if (producto != null) {

                cargarProductoSeleccionado(producto);

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Producto encontrado",
                        "El producto fue encontrado correctamente."
                );

            } else {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Producto no encontrado",
                        "No existe un producto con el ID indicado."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "ID inválido",
                    "El ID debe ser un número entero."
            );

        } catch (SQLException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    e.getMessage()
            );
        }
    }



    @FXML
    private void actualizarProducto() {

        String idTexto = txtId.getText().trim();

        if (idTexto.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Producto requerido",
                    "Selecciona o busca un producto antes de actualizar."
            );

            return;
        }

        try {

            if (!validarCampos()) {
                return;
            }

            int id =
                    Integer.parseInt(idTexto);

            String nombre =
                    txtNombre.getText().trim();

            String descripcion =
                    txtDescripcion.getText().trim();

            double precio =
                    Double.parseDouble(
                            txtPrecio.getText().trim()
                    );

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            Producto producto = new Producto(
                    id,
                    nombre,
                    descripcion,
                    precio,
                    stock
            );

            boolean actualizado =
                    productoDAO.actualizar(producto);

            if (actualizado) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Producto actualizado",
                        "El producto fue actualizado correctamente."
                );

                limpiarCampos();
                listarProductos();

            } else {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Producto no encontrado",
                        "No se encontró el producto que deseas actualizar."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Datos inválidos",
                    "Verifica que el ID, precio y stock sean correctos."
            );

        } catch (SQLException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    e.getMessage()
            );
        }
    }



    @FXML
    private void eliminarProducto() {

        String entrada = txtId.getText().trim();

        if (entrada.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Producto requerido",
                    "Selecciona o busca un producto antes de eliminar."
            );

            return;
        }

        try {

            int id =
                    Integer.parseInt(entrada);

            boolean eliminado =
                    productoDAO.eliminar(id);

            if (eliminado) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Producto eliminado",
                        "El producto fue eliminado correctamente."
                );

                limpiarCampos();
                listarProductos();

            } else {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Producto no encontrado",
                        "No existe el producto que deseas eliminar."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "ID inválido",
                    "El ID debe ser un número entero."
            );

        } catch (SQLException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    e.getMessage()
            );
        }
    }



    @FXML
    private void listarProductos() {

        try {

            List<Producto> productos =
                    productoDAO.listar();

            listaProductos =
                    FXCollections.observableArrayList(
                            productos
                    );

            tablaProductos.setItems(
                    listaProductos
            );

        } catch (SQLException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error al listar",
                    "No se pudieron cargar los productos.\n"
                            + e.getMessage()
            );
        }
    }


    @FXML
    private void limpiarCampos() {

        txtId.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();

        tablaProductos
                .getSelectionModel()
                .clearSelection();

        txtNombre.requestFocus();
    }



    private void cargarProductoSeleccionado(
            Producto producto) {

        txtId.setText(
                String.valueOf(
                        producto.getIdProducto()
                )
        );

        txtNombre.setText(
                producto.getNombre()
        );

        txtDescripcion.setText(
                producto.getDescripcion()
        );

        txtPrecio.setText(
                String.valueOf(
                        producto.getPrecio()
                )
        );

        txtStock.setText(
                String.valueOf(
                        producto.getStock()
                )
        );
    }


    private boolean validarCampos() {

        String nombre =
                txtNombre.getText().trim();

        String precioTexto =
                txtPrecio.getText().trim();

        String stockTexto =
                txtStock.getText().trim();


        if (nombre.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Nombre requerido",
                    "El nombre del producto es obligatorio."
            );

            return false;
        }


        if (precioTexto.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Precio requerido",
                    "Debes ingresar el precio del producto."
            );

            return false;
        }

        double precio;


        try {

            precio =
                    Double.parseDouble(
                            precioTexto
                    );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Precio inválido",
                    "El precio debe ser un valor numérico."
            );

            return false;
        }


        if (precio < 0) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Precio inválido",
                    "El precio no puede ser negativo."
            );

            return false;
        }


        if (stockTexto.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Stock requerido",
                    "Debes ingresar el stock del producto."
            );

            return false;
        }

        int stock;


        try {

            stock =
                    Integer.parseInt(
                            stockTexto
                    );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Stock inválido",
                    "El stock debe ser un número entero."
            );

            return false;
        }


        if (stock < 0) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Stock inválido",
                    "El stock no puede ser negativo."
            );

            return false;
        }

        return true;
    }



    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}