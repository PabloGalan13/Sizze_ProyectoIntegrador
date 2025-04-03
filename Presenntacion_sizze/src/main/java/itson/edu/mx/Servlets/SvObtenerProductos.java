package itson.edu.mx.Servlets;


import com.google.gson.Gson;
import daos.ProductoDAO;
import entidades.Producto;
import excepciones.ExcepcionAT;
import interfacesDAO.IProductoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SvObtenerProductos")
public class SvObtenerProductos extends HttpServlet {

    private final IProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            List<Producto> productos = productoDAO.obtenerProductos();
            String json = new Gson().toJson(productos);
            out.print(json);
            out.flush();
        } catch (ExcepcionAT e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los productos");
        }
    }
}
