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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Unidade;

public class UnidadeDao extends Dao<Unidade> {

    UnidadeDao(Session session) {
        super(session, Unidade.class);
    }

    @SuppressWarnings("unchecked")
	public List<Unidade> listByOrgao(Orgao o) {
        Criteria crit = session.createCriteria(Unidade.class);
        crit.add(Restrictions.eq("orgao.id", o.getId()));
        crit.addOrder(Order.asc("nome"));
        return crit.list();
    }

  
}
