/**
 * Copyright 2010 UNCISAL Universidade de Ciências em Saúde do Estado de Alagoas.
 * 
 * This file is part of SIAPNET.
 *
 * SIAPNET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * SIAPNET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIAPNET.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.edu.uncisal.almoxarifado.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import br.edu.uncisal.almoxarifado.util.DateTimeUtil;

/**
 * Servlet que gera relatório em pdf a partir de arquivo jasper com conexão JDBC
 *  
 * Uso chama o servlet por exemplo:
 * aplicacao/GeraRelatorioJDBC?relatorio=NomeArquivoJasper&DT_INI=12/12/2001&DT_FIM=12/12/2008
 *
 * No arquivo .jrxml, que é complicado em .jasper
 * Deve ter a query SQL com os parâmetros: $P{DT_INI} e $P{DT_FIM}
 * 
 * @author Augusto Oliveira
 */
public class GeraRelatorioJDBC extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext context = this.getServletConfig().getServletContext();


        //recupera por parametro o nome do relatorio.
        String relatorio = request.getParameter("relatorio");

        // obtém o arquivo compilado .jasper
        File relatorioJasper = new File(context.getRealPath("/relatorios/" + relatorio + ".jasper"));

        // parâmetros, se houver
        Map parametros = new HashMap();
        parametros.put("DT_INI", DateTimeUtil.convertDate((String) request.getParameter("DT_INI")));
        parametros.put("DT_FIM", DateTimeUtil.convertDate((String) request.getParameter("DT_FIM")));


        // Busca dados usando JDBC e dereciona a saída do relatório para um stream
        byte[] bytes = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeraRelatorioJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection jdbcConnection;
        try {
            String url = "jdbc:postgresql://172.16.0.173:5432/uncisal";
            String user = "postgres";
            String password = "postgres";
            jdbcConnection = DriverManager.getConnection(url, user, password);
            try {
                bytes = JasperRunManager.runReportToPdf(relatorioJasper.getPath(), parametros, jdbcConnection);
            } catch (JRException ex) {
                Logger.getLogger(GeraRelatorioJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeraRelatorioJDBC.class.getName()).log(Level.SEVERE, null, ex);

            ex.printStackTrace();

            if (bytes != null && bytes.length > 0) {
                //Envia o relatório em formato PDF para o browser
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Servlet que gera relatório em pdf a partir de arquivo jasper";
    }// </editor-fold>
}
