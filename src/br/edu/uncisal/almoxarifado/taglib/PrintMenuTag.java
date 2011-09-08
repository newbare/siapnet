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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Recurso;
import br.edu.uncisal.almoxarifado.model.Usuario;

public class PrintMenuTag extends TagSupport {

    private static final long serialVersionUID = -3612723662835886849L;
    private Usuario usuario;
    private String browser;
    private Perfil p;
    private List<Treeable> treeables; //Raizes da árvore
    private JspWriter out;

    public void percorreNo(Treeable no) throws IOException {
        if (no.getNome() != null && !no.getNome().equals("")) {
            if (no.getUri() != null && !no.getUri().equals("")) {
                out.println("<li><a href=\"" + no.getUri() + "\">" + no.getNome() + "</a>");
            } else {
                out.println("<li>" + no.getNome());
            }
        }

        if (no.getFolhas() != null && no.getFolhas().size() != 0) {
            out.println("<ul>");
            for (Treeable vo : no.getFolhas()) {
                boolean possui = false;
                for (Treeable r : p.getRecursos()) {
                    if (r.equals(vo)) {
                        possui = true;
                    }
                }

                if (possui) {
                    percorreNo(vo);
                }
            }
            out.println("</ul>");
        }
        if (no.getNome() != null && !no.getNome().equals("")) {
            out.println("</li>");
        }
    }

    private void percorreNoJavascript(Treeable no) throws IOException {

        if (no.getNome() != null && !no.getNome().equals("")) {
            if (no.getUri() != null && !no.getUri().equals("")) {
                out.print("['', '" + no + "', '" + no.getUri() + "','_self', ''\n");
            } else {
                out.print("['', '" + no + "', '','_self', '',\n");
            }
        }

        if (no.getFolhas() != null && no.getFolhas().size() != 0) {
            for (Treeable vo : no.getFolhas()) {
                boolean possui = false;
                for (Treeable r : p.getRecursos()) {
                    if (r.equals(vo)) {
                        possui = true;
                    }
                }
                if (possui) {
                    percorreNoJavascript(vo);
                }
            }
        }
        if (no.getNome() != null && !no.getNome().equals("")) {
            out.print("],\n");
        }
    }

    @Override
    public int doStartTag() throws JspException {
        loadRaizes();
        out = pageContext.getOut();
        try {
            out.println("<div id=\"menu\">");
            out.println("<ul>");
            if (!"IE".equals(browser)) {
                for (Treeable treeable : treeables) {
                    percorreNo(treeable);
                }
                out.println("</ul>");
                out.println("<span id=\"logout\"><a href=\"login.logout.logic\" >Sair do sistema</a></span>");
                out.println("</div>");
            } else {
                int i = 0;

                out.print("<div id=\"menu\">");
                out.print("<script type=\"text/javascript\">");
                out.print("var myMenu = [");
                for (Treeable treeable : treeables) {
                    percorreNoJavascript(treeable);
                    out.print("_cmSplit,");
                    i++;
                }
                out.print("];\ncmDraw('menu', myMenu, 'hbr', cmThemePanel, 'ThemePanel');\n</script>\n");
                out.println("<span id=\"logout\"><a href=\"login.logout.logic\" >Sair do sistema</a></span>");
                out.println("</div>");
            }
        } catch (IOException e) {
            new JspException(e);
        }
        return SKIP_BODY;
    }

    /**
     * Carrega as raizes dos recursos dos sistemas pra gerar o menu.
     */
    private void loadRaizes() {
        p = new DaoFactory().getPerfilDao().getById(usuario.getUsuarioDepartamentoAtivo().getPerfil().getId());
        treeables = new ArrayList<Treeable>();
        for (Recurso r : p.getRecursos()) {
            if (r.getPai() == null) {
                treeables.add(r);
            }
        }
    }

    public void setTreeables(List<Treeable> treeables) {
        this.treeables = treeables;
    }

    public List<Treeable> getTreeables() {
        return treeables;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
