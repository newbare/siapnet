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

import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.RecursoDao;
import br.edu.uncisal.almoxarifado.model.Recurso;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("recurso")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class })
public class RecursoLogic {

    @Parameter(create=true)
    @Out
    private Recurso recurso;	
	@Out
    private Collection<Recurso> recursos;
	@Out
    private List<Recurso> raizes;

	private RecursoDao recursoDao;
	
    public RecursoLogic(DaoFactory daoFactory, Usuario authUser) {
        recursoDao = daoFactory.getRecursoDao();
    }
    
    public String formulario() {    		
    	raizes		= recursoDao.listarRaizes();
    	recursos	= recursoDao.listAll();
        return "ok";
    }

    public String gravar() {
    	if(recurso.getPai().getId() == null)
    		recurso.setPai(null);
    	
    	recursoDao.save(recurso); 
    	
    	recurso = null;
        return "ok";
    }

    public String listAll() {
        recursos = recursoDao.listarRaizes();
        return "ok";
    }

    public String remove() {
        recursoDao.remove(recurso);
        return "ok";
    }

    public String get() {
    	raizes		= recursoDao.listarRaizes();
    	recursos	= recursoDao.listAll();
    	recurso		= recursoDao.getById(recurso.getId());
        return "ok";
    }
    
    public String loadRecursos() {
    	raizes = recursoDao.listarRaizes();
    	return "ok";
    }
    
}