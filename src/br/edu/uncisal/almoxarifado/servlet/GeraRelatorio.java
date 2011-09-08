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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import br.edu.uncisal.almoxarifado.constants.GeneralConstants;
import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.ParametroRelatorioDao;
import br.edu.uncisal.almoxarifado.model.ParametroRelatorio;
import br.edu.uncisal.almoxarifado.model.Relatorio;
import br.edu.uncisal.almoxarifado.util.DateTimeUtil;

/**
 *
 * @author Augusto Oliveira
 */
public class GeraRelatorio extends HttpServlet {

    Map parametros = new HashMap();
    String tipo;
    String pathDir = "";
    //Caso exporte para XLS (Excel) ou RTF (Word)
    ByteArrayOutputStream exportReport;
    final static String RELATORIO = "relatorio";
    final static String FORMATO = "formato";
    final static String SUBREPORT_DIR = "SUBREPORT_DIR";
    final static String PDF = "application/pdf";
    final static String HTML = "text/html";
    final static String EXCEL = "application/vnd.ms-excel";
    final static String DOC = "application/rtf";
    /* caminho do arquivo HTML/TXT*/
    final static String HTML_FILE = "caminho";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        ServletContext context = this.getServletConfig().getServletContext();
        parametros = new HashMap();
        // obtém o arquivo compilado .jasper
        //passar por parametro o nome do relatorio.
        String relatorio = request.getParameter(RELATORIO);
        // tipo do formato que será exportado o relatório (Ex.: pdf, xls, html, rtf)
        String formato = (request.getParameter(FORMATO) != null ? request.getParameter(FORMATO) : PDF);


        //relatorio = "ItensPorGrupo";
        pathDir = context.getRealPath("/relatorios/") + "/";
        String pathJasper = context.getRealPath("/relatorios/" + relatorio + ".jasper");
        File relatorioJasper = new File(pathJasper);

        // parâmetros, se houver

        String pName;
        Relatorio rel = new Relatorio();
        rel.setArquivoJasper(relatorio);


        for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
            pName = (String) e.nextElement();
            if (!(RELATORIO.equals(pName)) && (!FORMATO.equals(pName)) && (!SUBREPORT_DIR.equals(pName))) {
                colocarParametro(new ParametroRelatorio(pName, rel), request);
            }
            if (SUBREPORT_DIR.equals(pName)) {
                parametros.put(SUBREPORT_DIR, pathDir);
            }
        }

        byte[] bytes = null;
        String msgError = null;
        Connection jdbcConnection = null;
        try {
            Context initialContext = new InitialContext(); 
            Context envContext = (Context) initialContext.lookup("java:comp/env"); 
            DataSource ds = (DataSource)envContext.lookup("jdbc/postgresUncisal"); 
            jdbcConnection = ds.getConnection();

            try {

                bytes = geraRelatorio(relatorioJasper.getPath(), parametros, jdbcConnection, formato);
            } catch (JRException ex) {
                Logger.getLogger(GeraRelatorio.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeraRelatorio.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(GeraRelatorio.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
		} finally {
			if(jdbcConnection != null) {
				try {
					jdbcConnection.close();
				} catch (SQLException ex) {
		            Logger.getLogger(GeraRelatorio.class.getName()).log(Level.SEVERE, null, ex);
		            ex.printStackTrace();
				}
			}
		}
        
        if (bytes != null && bytes.length > 0) {
            //Envia o relatório em formato escolhido para o browser
            if (msgError != null) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.setContentLength(bytes.length);
                response.getWriter().print(relatorioErro(msgError));
            } else if ((bytes.length <= 900 && formato.equals(PDF)) || (bytes.length <= 3600 && (formato.equals(EXCEL))) || (bytes.length <= 1024 && (formato.equals(DOC)))) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.setContentLength(bytes.length);
                response.getWriter().print(relatorioVazio());
            } else {
                response.setContentType(formato);
                response.setContentLength(bytes.length);

                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
            if (exportReport != null) {
                exportReport.close();
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
        return "Short description";
    }// </editor-fold>

    private void colocarParametro(ParametroRelatorio pr, HttpServletRequest request) {

        ParametroRelatorioDao parametroRelatorioDao = new DaoFactory().getParametroRelatorioDao();

        tipo = (parametroRelatorioDao.getByNome(pr) != null ? parametroRelatorioDao.getByNome(pr).getTipo() : null);
        if (GeneralConstants.DATE.equals(tipo)) {
            // ao converter a string para data adiciona a hora 00:00 para data inicial e 23:59 para data final para comparar data do mesmo dia (between)
            parametros.put(pr.getNome(), DateTimeUtil.convertDateTime((String) request.getParameter(pr.getNome()) + ("DT_INI".equals(pr.getNome())?" 00:00":" 23:59")));
        } else if (GeneralConstants.LONG.equals(tipo)) {
            parametros.put(pr.getNome(), new Long(request.getParameter(pr.getNome())));
        } else if (GeneralConstants.INTERGER.equals(tipo)) {
            parametros.put(pr.getNome(), new Integer(request.getParameter(pr.getNome())));
        } else if (GeneralConstants.FLOAT.equals(tipo)) {
            parametros.put(pr.getNome(), new Float(request.getParameter(pr.getNome())));
        } else if (GeneralConstants.DOUBLE.equals(tipo)) {
            parametros.put(pr.getNome(), new Double(request.getParameter(pr.getNome())));
        } else if (GeneralConstants.STRING.equals(tipo)) {
            parametros.put(pr.getNome(), (String) request.getParameter(pr.getNome()));
        }
    }

    private byte[] geraRelatorio(String pathJasper, Map params, Connection conn, String formato) throws JRException, FileNotFoundException, IOException {

    	params.put("REPORT_LOCALE", new Locale("pt", "BR"));
    	JasperPrint jasperPrint = JasperFillManager.fillReport(pathJasper, params, conn);
        jasperPrint.setLocaleCode(new Locale("pt", "BR").toString());
        
        exportReport = new ByteArrayOutputStream();
        if (EXCEL.equals(formato)) {

            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                    jasperPrint);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                    exportReport);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE, pathDir);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pathDir + "/relatorio.xls");
            exporterXLS.exportReport();

            return exportReport.toByteArray();

        } else if (DOC.equals(formato)) {
            JRRtfExporter exporter = new JRRtfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exportReport);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pathDir);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pathDir + "/relatorio.doc");
            exporter.exportReport();
            return exportReport.toByteArray();
        } else if (HTML.equals(formato)) {

            return (JasperRunManager.runReportToHtmlFile(pathJasper, params, conn)).getBytes();
        } else { //padrão PDF
            return JasperRunManager.runReportToPdf(pathJasper, params, conn);
        }
    }

    private String relatorioVazio() {
        return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>SIAPNET - Almoxarifado e Patrimônio</title></head><body><script>alert('Não há registros para os critérios informados.');window.close();</script></body></html>";
    }

    private String relatorioErro(String msgErro) {
        return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>SIAPNET - Almoxarifado e Patrimônio</title></head><body><script>alert('" + msgErro + "');window.close();</script></body></html>";
    }
}
