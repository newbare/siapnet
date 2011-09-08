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

package br.edu.uncisal.almoxarifado.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TreePrintTag extends TagSupport {

    private static final long serialVersionUID = -3612723662835886849L;
    private JspWriter out;
    /**
     * Contador para definir as posições das listas.
     */
    private Integer i;
    /**
     * Define se o tipo de lista vai ser normal ou vai ser uma lista com checkbox.
     */
    private Boolean checkeable = false;
    /**
     * Raizes das árvores. São as entradas principais do menu.
     */
    private List<Treeable> treeables;
    /**
     * Entradas de menu pré-existentes para fazer comparações com o as entradas de menu cadastradas no banco.
     */
    private List<Treeable> existentes;

    private void montaListaLinkavel() throws IOException {
        out.print("<ul>\n");
        for (Treeable treeable : treeables) {
            percorreNo(treeable);
        }
        out.print("</ul>\n");
    }

    public void percorreNo(Treeable no) throws IOException {
        out.println("<li><a href=\"recurso.get.logic?recurso.id=" + no.getId() + "\">" + no + "</a>");
        if (no.getFolhas() != null && no.getFolhas().size() != 0) {
            out.println("<ul>");
            for (Treeable vo : no.getFolhas()) {
                percorreNo(vo);
            }
            out.println("</ul>");
        }
        out.println("</li>");
    }

    private void montaLista() throws IOException {
        i = 0;
        out.println("<ul class=\"recursos\">\n");
        for (Treeable treeable : treeables) {
            percorreNoCheckeable(treeable);
        }
        out.println("</ul>\n");
    }

    private void percorreNoCheckeable(Treeable no) throws IOException {
        Boolean esta = false;
        if (existentes != null) {
            for (Treeable n : existentes) {
                if (n.equals(no)) {
                    esta = true;
                    break;
                }
            }
        }
        if (esta) {
            out.print("<li><input type=\"checkbox\" class=\"check\" name=\"recursosEscolhidos\" value=\"" + no.getId() + "\" checked=\"checked\" /> " + no + "\n");
        } else {
            out.print("<li><input type=\"checkbox\" class=\"check\" name=\"recursosEscolhidos\" value=\"" + no.getId() + "\" /> " + no + "\n");
        }

        i++;

        if (no.getFolhas() != null && no.getFolhas().size() != 0) {
            out.print("<ul>");
            for (Treeable vo : no.getFolhas()) {
                percorreNoCheckeable(vo);
            }
            out.print("</ul>");
        }
        out.print("</li>\n");
    }

    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        try {
            if (checkeable) {
                montaLista();
            } else {
                montaListaLinkavel();
            }
        } catch (IOException e) {
            new JspException(e);
        }
        return SKIP_BODY;
    }

    public void setCheckeable(Boolean checkeable) {
        this.checkeable = checkeable;
    }

    public Boolean getCheckeable() {
        return checkeable;
    }

    public void setTreeables(List<Treeable> treeables) {
        this.treeables = treeables;
    }

    public List<Treeable> getTreeables() {
        return treeables;
    }

    public void setExistentes(List<Treeable> existentes) {
        this.existentes = existentes;
    }

    public List<Treeable> getExistentes() {
        return existentes;
    }
}
