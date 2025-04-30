package registrarVenta;

import daos.ProductoDAO;
import daos.VentaDAO;
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Venta;
import excepciones.NegocioException;
import interfacesDAO.IProductoDAO;
import interfacesDAO.IVentaDAO;
import java.time.LocalDate;
import java.util.List;

public class RegistrarVentaBO implements IRegistrarVentaBO {

    private IProductoDAO prodDAO;
    private IVentaDAO ventaDAO;

    public RegistrarVentaBO() {
        prodDAO = new ProductoDAO();
        ventaDAO = new VentaDAO();
    }

    @Override
    public Producto obtenerProducto(String nombre) throws NegocioException {
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new NegocioException("El nombre del producto no puede estar vacío");
            }
            // Este método puedes conservarlo por si lo usas desde el buscador
            return prodDAO.obtenerProductoPorNombre(nombre);
        } catch (Exception e) {
            throw new NegocioException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    private boolean verificarStock(long productoId, int cantidad) throws NegocioException {
        try {
            if (cantidad <= 0) {
                throw new NegocioException("La cantidad debe ser mayor que cero");
            }

            Producto productoBD = prodDAO.obtenerProductoPorId(productoId);

            if (productoBD == null) {
                throw new NegocioException("Producto no encontrado");
            }

            return productoBD.getStock() >= cantidad;
        } catch (Exception e) {
            throw new NegocioException("Error al verificar stock: " + e.getMessage(), e);
        }
    }

    @Override
    public void registrarVenta(Venta venta) throws NegocioException {
        try {
            if (venta == null) {
                throw new NegocioException("La venta no puede ser nula");
            }

            if (venta.getProductos() == null || venta.getProductos().isEmpty()) {
                throw new NegocioException("La venta debe contener al menos un producto");
            }

            if (venta.getVendedor() == null) {
                throw new NegocioException("La venta debe tener un vendedor asociado");
            }

            for (DetalleVenta detalle : venta.getProductos()) {
                if (!verificarStock(detalle.getProducto().getId(), detalle.getCantidad())) {
                    throw new NegocioException("Stock insuficiente para el producto: "
                            + detalle.getProducto().getNombre());
                }
            }

            if (venta.getFecha() == null) {
                venta.setFecha(LocalDate.now());
            }

            double total = venta.getProductos().stream()
                    .mapToDouble(detalle -> detalle.getPrecioVendido() * detalle.getCantidad())
                    .sum();
            venta.setTotal(total);

            for (DetalleVenta detalle : venta.getProductos()) {
                detalle.setVenta(venta);
                detalle.setFecha(venta.getFecha());
            }

            ventaDAO.registrarVenta(venta);

            for (DetalleVenta detalle : venta.getProductos()) {
                long productoId = detalle.getProducto().getId();
                Producto productoBD = prodDAO.obtenerProductoPorId(productoId);
                productoBD.setStock(productoBD.getStock() - detalle.getCantidad());
                prodDAO.actualizarProducto(productoBD);
            }

        } catch (Exception e) {
            throw new NegocioException("Error al registrar venta: " + e.getMessage(), e);
        }
    }
}
