package itson.edu.mx.Servlets;

import com.google.gson.Gson;
import entidades.Producto;
import excepciones.NegocioException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import eliminarProducto.IEliminarProductoBO;
import eliminarProducto.EliminarProductoBO;

public class EliminarProducto extends HttpServlet {

    private final IEliminarProductoBO eliminarProductoBO = new EliminarProductoBO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();
        String accion = request.getParameter("accion");

        try {
            if ("buscar".equals(accion)) {
                // Reutilizando la misma lógica de búsqueda que en BuscarProducto
                String nombre = request.getParameter("nombre");
                Producto producto = eliminarProductoBO.buscarProducto(nombre);
                if (producto != null) {
                    out.print(gson.toJson(producto));
                } else {
                    out.print("{\"error\":\"Producto no encontrado\"}");
                }
            } else if ("eliminar".equals(accion)) {
                long id = Long.parseLong(request.getParameter("id"));
                eliminarProductoBO.eliminarProducto(id);
                out.print("{\"exito\":true, \"mensaje\":\"Producto eliminado con éxito\"}");
            } else {
                out.print("{\"error\":\"Acción no válida\"}");
            }
        } catch (NegocioException ne) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + ne.getMessage() + "\"}");
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"ID de producto no válido\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error al procesar la solicitud\"}");
        }
    }
}