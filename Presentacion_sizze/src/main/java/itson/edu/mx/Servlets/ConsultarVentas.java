/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package itson.edu.mx.Servlets;

import com.google.gson.Gson;
import consultaVenta.ConsultaVentaBO;
import entidades.Venta;
import itson.edu.mx.utils.GsonUtils;
import itson.edu.mx.utils.VentaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "ConsultarVentas", urlPatterns = {"/ConsultarVentas"})
public class ConsultarVentas extends HttpServlet {

    private final Gson gson = GsonUtils.createGson();
    private final ConsultaVentaBO consultaVentaBO = new ConsultaVentaBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            List<Venta> ventas = consultaVentaBO.obtenerTodasLasVentas();
            List<VentaDTO> dto = ventas.stream().map(VentaDTO::new).collect(Collectors.toList());

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(dto));
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace(); // ← Agrega esta línea temporalmente
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\":\"" + ex.getMessage() + "\"}");
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer JSON del cuerpo de la solicitud
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                sb.append(linea);
            }
        }

        Map<String, String> fechas = gson.fromJson(sb.toString(), Map.class);
        String fechaInicioStr = fechas.get("fechaInicio");
        String fechaFinStr = fechas.get("fechaFin");

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        try {
            List<Venta> ventasFiltradas = consultaVentaBO.obtenerVentasPorRangoFechas(fechaInicio, fechaFin);
            List<VentaDTO> dto = ventasFiltradas.stream().map(VentaDTO::new).collect(Collectors.toList());

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(dto));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace(); // ← Imprime en consola
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }
}
