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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author augusto
 */
public class EMailUtil {

    static public void enviarEmail(String smtpHost, String fromName, String fromEmail,
            String toName, String toEmail, String conteudo, String assunto) throws EmailException {

        SimpleEmail email = new SimpleEmail();

        email.setHostName(smtpHost);  //smtpHost, o servidor SMTP para envio do e-mail

        email.addTo(toEmail, toName); //destinatário

        email.setFrom(fromEmail, fromName); // remetente

        email.setSubject(assunto); // assunto do e-mail

        email.setMsg(conteudo); //conteudo do e-mail

        email.send(); //envia o e-mail
    }
}
