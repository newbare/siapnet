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

package br.edu.uncisal.almoxarifado.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.EmailException;
import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.util.EMailUtil;

@Component("ajuda")
@InterceptedBy({DaoInterceptor.class})
public class AjudaLogic {

    @Parameter
    private String fromName;
    @Parameter
    private String fromEmail;
    @Parameter
    private String fromPhone;
    @Parameter
    private String toName;
    @Parameter
    private String toEmail;
    @Parameter
    private String content;
    @Parameter
    private String subject;
    @Parameter
    private String orgName;
    @Parameter
    private String unitName;
    @Parameter
    private String typeMessage;
    /* nome do servidor de email para envio via SMTP */
    private String mailServer;
    /** Mensagens  de interação com o usuário */
    @Out
    private String msgErro;
    @Out
    private String message;

    public AjudaLogic(DaoFactory daoFactory) {
        mailServer = "mail.uncisal.edu.br";
    }

    public String info() {
        return "ok";
    }

    public String suporte() {
        return "ok";
    }

    public String suporteEnviar() {
        String subjectEmail = "Suporte SIAPNet - Almoxarifado & Patrimonio";
        StringBuffer sb = new StringBuffer("\n");
        sb.append("NOME: \t").append(fromName).append("\n");
        sb.append("E-MAIL: \t").append(fromEmail).append("\n");
        sb.append("TELEFONE: \t").append(fromPhone).append("\n");
        sb.append("ORGAO: \t").append(orgName).append("\n");
        sb.append("UNIDADE: \t").append(unitName).append("\n");
        sb.append("TIPO: \t").append(typeMessage).append("\n");
        sb.append("ASSUNTO: \t").append(subject).append("\n");
        sb.append("MENSAGEM: \t").append(content).append("\n");
     
        try {
            EMailUtil.enviarEmail(
                    mailServer,
                    fromName,
                    fromEmail,
                    "Desenvolvimento do SIAPnet(Almoxarifado & Patrimonio)",
                    "cetis.dev@uncisal.edu.br", 
                    sb.toString(),
                    subjectEmail);
        } catch (EmailException ex) {
            Logger.getLogger(AjudaLogic.class.getName()).log(Level.SEVERE, null, ex);
            msgErro = "Houve erro na tentativa de envio da mensagem.";
            return "invalid";
        }
        message = "Sua mensagem foi enviada com sucesso.";
        return "ok";
    }
}