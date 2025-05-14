/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package itson.edu.mx.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entidades.Venta;
import entidades.DetalleVenta;
import entidades.Empleado;
import entidades.EmpleadoNormal;
import entidades.MetodoPago;
import entidades.Producto;
import excepciones.NegocioException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import registrarVenta.IRegistrarVentaBO;
import registrarVenta.RegistrarVentaBO;

/**
 *
 * @author PERSONAL
 */
public class RegistrarVenta extends HttpServlet {

    private final IRegistrarVentaBO ventaBO = new RegistrarVentaBO();
    private final Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistrarVenta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrarVenta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        try {
            BufferedReader reader = request.getReader();
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // 1. Crear el objeto Venta básico
            Venta venta = new Venta();
            venta.setFecha(LocalDate.now());
            venta.setMetodoPago(MetodoPago.valueOf(jsonObject.get("metodoPago").getAsString()));

            // 2. Obtener el vendedor (simplificado)
            Empleado vendedor = new EmpleadoNormal();
            vendedor.setId(jsonObject.getAsJsonObject("vendedor").get("id").getAsLong());
            venta.setVendedor(vendedor);
            // 3. Procesar los productos
            JsonArray productosArray = jsonObject.getAsJsonArray("productos");
            List<DetalleVenta> detalles = new ArrayList<>();
            double totalVenta = 0;

            for (JsonElement element : productosArray) {
                JsonObject productoJson = element.getAsJsonObject();

                // Crear detalle de venta
                DetalleVenta detalle = new DetalleVenta();
                detalle.setFecha(LocalDate.now());
                detalle.setCantidad(productoJson.get("cantidad").getAsInt());
                JsonElement precioElement = productoJson.get("precioUnitario");
                if (precioElement == null || precioElement.isJsonNull()) {
                    throw new IllegalArgumentException("Falta el campo 'precioUnitario' en uno de los productos.");
                }
                detalle.setPrecioVendido(precioElement.getAsDouble());

                // Crear producto mínimo necesario
                Producto producto = new Producto();
                producto.setId(productoJson.get("productoId").getAsLong());
                detalle.setProducto(producto);

                // Relación bidireccional
                detalle.setVenta(venta);
                detalles.add(detalle);

                // Calcular total
                totalVenta += detalle.getPrecioVendido() * detalle.getCantidad();
            }

            venta.setProductos(detalles);
            venta.setTotal(totalVenta);

            // Registrar la venta
            ventaBO.registrarVenta(venta);

            out.print("{\"exito\":true, \"mensaje\":\"Venta registrada con éxito\"}");
        } catch (NegocioException ne) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"exito\":false, \"mensaje\":\"" + ne.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"exito\":false, \"mensaje\":\"Error al procesar la venta: " + e.getMessage() + "\"}");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
