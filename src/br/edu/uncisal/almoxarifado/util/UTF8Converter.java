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

package br.edu.uncisal.almoxarifado.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.mail.EmailException;

public class UTF8Converter implements Filter {

    public void destroy() {
    }

    /** Preform the filtering. */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        try {
            chain.doFilter(request, response);

        } catch (IOException ioe) {
            enviarEmail(ioe, request);
        }
    }

    private void enviarEmail(Exception e, ServletRequest request)  {
        try {
            String dadosServidor = "Nome: " + request.getLocalName() + "\n" + "IP: " + request.getLocalAddr() + "\n" + "Porta: " + request.getLocalPort() + "\n";
            String erro="Erro -> ";
            for (StackTraceElement ste : e.getStackTrace()) {
                erro += " na classe: " + ste.getClassName();
                erro += " do arquivo: " + ste.getFileName();
                erro += " no método: " + ste.getMethodName();
                erro += " na linha: " + ste.getLineNumber() + "\n\n";
            }
            EMailUtil.enviarEmail(Configuracoes.EMAIL.MAIL_SERVER_NAME,
                    "Sistema do SIAPNet (Almoxarifado & Patrimonio)", //FROM nome
                    "cetis.dev@uncisal.edu.br", //FROM email
                    "Desenvolvimento do SIAPNet (Almoxarifado & Patrimonio)", // to nome
                    "augusto.oliveira@uncisal.edu.br", // to email
                    //Conteudo
                    "Orreceu um erro interno no sistema SIAPnet rodando no seguinte ambiente:\n" +
                    dadosServidor + "\n\nCom a seguinte mensagem de erro:\n" +
                    e.getMessage() +
                    "\nCausa:\n" + e.getCause() + "\n" +
                    erro,
                    "SIAPNET - Almoxarifado e Patrimonio - ERRO INTERNO NO SISTEMA 500" // assunto
                    );
        } catch (EmailException ex) {
            Logger.getLogger(UTF8Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
    
    /**
     * Converte de utf-8 para iso
     * @param s
     * @return
     */
    public static String converteUtf8toIso(String s) {
    	Charset iso88591charset = Charset.forName("ISO-8859-1");
    	  
    	   ByteBuffer bb = iso88591charset.encode(s);  
    	   return new String(bb.array());  
    }

}
