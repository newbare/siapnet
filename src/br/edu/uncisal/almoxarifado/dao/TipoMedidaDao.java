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

import br.edu.uncisal.almoxarifado.model.TipoMedida;

public class TipoMedidaDao extends Dao<TipoMedida> {

	public TipoMedidaDao(Session session) {
		super(session, TipoMedida.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoMedida> listAll() {
		Criteria c = session.createCriteria(TipoMedida.class);
		c.addOrder(Order.asc("nome"));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<TipoMedida> buscar(String criteria) {
		StringBuffer sql = new StringBuffer(
			" SELECT t.* "
			+ " FROM almoxarifado.tipo_medida t "
			+ " WHERE sem_acentos(t.nome) ilike sem_acentos('%" + criteria + "%') "
			+ " ORDER BY t.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(TipoMedida.class);
		return sqlQuery.list();
	}
	
}
