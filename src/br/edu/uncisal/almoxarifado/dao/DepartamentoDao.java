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

package br.edu.uncisal.almoxarifado.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Unidade;

public class DepartamentoDao extends Dao<Departamento> {

    DepartamentoDao(Session session) {
        super(session, Departamento.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departamento> listAll() {
        return session.createCriteria(Departamento.class).addOrder(Order.asc("nome")).list();
    }

    @SuppressWarnings("unchecked")
    public List<Departamento> listByUnidade(Unidade u) {
        Criteria c = session.createCriteria(Departamento.class);
        if (u != null) {
            c.add(Restrictions.eq("unidade.id", u.getId()));
            c.add(Restrictions.naturalId());
        }
        c.addOrder(Order.asc("nome"));
        return c.list();
    }

    @SuppressWarnings("unchecked")
	public List<Departamento> buscar(String criteria, Unidade unidade) {    	  		
    	Criteria c = session.createCriteria(Departamento.class);
    	c.createCriteria("unidade", "u").createCriteria("orgao", "o");
    	if(unidade == null)
    		c.add(Restrictions.or(Restrictions.ilike("o.sigla", "%" + criteria + "%"), Restrictions.or(
    				Restrictions.ilike("o.nome", "%" + criteria + "%"),
    				Restrictions.or(
    						Restrictions.ilike("nome", "%" + criteria + "%"),
    						Restrictions.ilike("sigla", "%" + criteria + "%")    				
    				)
    		)));
    	else {
    		c.add(Restrictions.or(Restrictions.ilike("nome", "%" + criteria + "%"), Restrictions.ilike("sigla", "%" + criteria + "%")));
    		c.add(Restrictions.eq("unidade.id", unidade.getId()));
    	}
    	
    	return c.list();
    }
    
	@SuppressWarnings("unchecked")
	public Collection<Departamento> listByAlmoxarifado(Almoxarifado a) {
    	Criteria c = session.createCriteria(Departamento.class);
    	c.createCriteria("almoxarifados")
    		.add(Restrictions.eq("id", a.getId()));    	
    	
    	return c.list();
    }
     
}
