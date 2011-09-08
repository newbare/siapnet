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

import java.io.IOException;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.In;
import org.vraptor.annotations.Out;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Recurso;
import br.edu.uncisal.almoxarifado.model.Usuario;

public class AutorizadorInterceptor implements Interceptor {

    @In(scope = ScopeType.SESSION, required=false)
    private Usuario authUser;
    private String URI;

    @Override
    public void intercept(LogicFlow flow) throws LogicException, ViewException {
        try {
            if (authUser != null) {
                URI = flow.getLogicRequest().getRequest().getRequestURI();
                if (!pedePermissao()) {
                    flow.getLogicRequest().getResponse().sendRedirect("login.form.logic");
                } else {
                    flow.execute();
                }
            } else {
                flow.getLogicRequest().getResponse().sendRedirect("login.form.logic");
            }
        } catch (IOException e) {
            throw new LogicException(e);
        }
    }

    private boolean pedePermissao() {
    	if(authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL))
    		return true;
    	
        for (Recurso recurso : authUser.getUsuarioDepartamentoAtivo().getPerfil().getRecursos()) {
            if (URI.equals(recurso.getUri())) {
                return true;
            }
        }
        return false;
    }
    
    @Out(key = "br.edu.uncisal.almoxarifado.model.Usuario")
    public Usuario getAuthUser() {
    	return authUser;
    }
}
