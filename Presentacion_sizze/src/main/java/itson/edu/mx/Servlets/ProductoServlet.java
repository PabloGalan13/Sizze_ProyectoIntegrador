/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package itson.edu.mx.Servlets;

import daos.CategoriaDAO;
import daos.ProductoDAO;
import entidades.Categoria;
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
@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB antes de escribir en disco
        maxFileSize = 1024 * 1024 * 10, // Tamaño máximo de archivo 10MB
        maxRequestSize = 1024 * 1024 * 50 // Tamaño máximo de la petición 50MB
)
public class ProductoServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductoServlet at " + request.getContextPath() + "</h1>");
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
            String nombre = request.getParameter("nombre");
            String tipo = request.getParameter("tipo");
            String descripcion = request.getParameter("talla");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String categoriaNombre = request.getParameter("categoria");

            Categoria categoria = new CategoriaDAO().obtenerCategoriaPorNombre(categoriaNombre);
            
            Part filePart = request.getPart("imgPortada");
            String fileName = filePart.getSubmittedFileName();

            // Ruta para guardar la imagen
            String uploadPath = getServletContext().getRealPath("") + File.separator + "postImgs";

            // Crear la carpeta si no existe
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                if (uploadDir.mkdirs()) {
                    System.out.println("Directorio creado: " + uploadPath);
                } else {
                    System.out.println("No se pudo crear el directorio: " + uploadPath);
                }
            }

            // Generar un nombre único para la imagen
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

            // Guardar la imagen en la carpeta
            String filePath = uploadPath + File.separator + uniqueFileName;
            try {
                filePart.write(filePath);
                System.out.println("Archivo guardado en: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al guardar el archivo: " + e.getMessage());
            }
            // Guardar la ruta relativa para la base de datos
            String imagen = "postImgs/" + uniqueFileName;
            
            Producto producto = new Producto(nombre, descripcion,descripcion , precio, stock, categoria, tipo, imagen);
            ProductoDAO productoDAO = new ProductoDAO();

            boolean guardado = productoDAO.registrarProducto(producto);

            if (guardado) {
                response.sendRedirect("html/RegistroProducto.html?mensaje=exito");
            } else {
                response.sendRedirect("html/RegistroProducto.html?mensaje=error");
            }
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
