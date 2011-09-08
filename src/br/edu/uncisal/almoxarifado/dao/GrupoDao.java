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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.edu.uncisal.almoxarifado.model.Grupo;

public class GrupoDao extends Dao<Grupo> {
	public GrupoDao(Session session) {
		super(session, Grupo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> listAll() {
		Criteria c = session.createCriteria(Grupo.class);
		c.addOrder(Order.asc("nome"));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> buscar(String criteria) {
		StringBuffer sql = new StringBuffer(
			" SELECT g.* "
			+ " FROM almoxarifado.grupo g "
			+ " WHERE sem_acentos(g.nome) ilike sem_acentos('%" + criteria + "%') "
			+ " ORDER BY g.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Grupo.class);
		return sqlQuery.list();
	}
	
}
