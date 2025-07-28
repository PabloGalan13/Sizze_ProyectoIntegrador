/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package itson.edu.mx.Servlets;

import consultaProducto.ConsultaProductoBO;
import daos.ProductoDAO;
import entidades.Producto;
import excepciones.ExcepcionAT;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "ActualizarStockServlet", urlPatterns = {"/ActualizarStockServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB antes de escribir en disco
        maxFileSize = 1024 * 1024 * 10, // Tamaño máximo de archivo 10MB
        maxRequestSize = 1024 * 1024 * 50 // Tamaño máximo de la petición 50MB
)
public class ActualizarStockServlet extends HttpServlet {

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
            out.println("<title>Servlet ActualizarStock</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActualizarStock at " + request.getContextPath() + "</h1>");
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
       try {
            // Obtener parámetros
            Long idProducto = Long.parseLong(request.getParameter("idProducto"));
            String nuevoNombre = request.getParameter("nombre");
            String nuevaDescripcion = request.getParameter("descripcion");
            int nuevoStock = Integer.parseInt(request.getParameter("stock"));

            ConsultaProductoBO consultaProducto = new ConsultaProductoBO();
            Producto producto = consultaProducto.obtenerProductoPorId(idProducto);

            if (producto == null) {
                response.sendRedirect("html/ActualizarStock.html?mensaje=producto_no_encontrado");
                return;
            }

            // Actualizar datos
            producto.setNombre(nuevoNombre);
            producto.setDescripcion(nuevaDescripcion);
            producto.setStock(nuevoStock);

            // Procesar imagen si se subió una
            Part filePart = request.getPart("imgPortada");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                String uploadPath = getServletContext().getRealPath("") + File.separator + "postImgs";

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = uploadPath + File.separator + uniqueFileName;
                filePart.write(filePath);

                String imagen = "postImgs/" + uniqueFileName;
                producto.setImagen(imagen); // Asegúrate de tener este campo y setter
            }

            // Guardar cambios
            consultaProducto.actualizarProducto(producto);

            response.sendRedirect("html/ActualizarStock.html?mensaje=exito");

        } catch (ExcepcionAT | NumberFormatException | IOException | ServletException ex) {
            Logger.getLogger(ActualizarStockServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("html/ActualizarStock.html?mensaje=error");
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
