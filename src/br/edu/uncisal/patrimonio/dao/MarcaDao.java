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

package br.edu.uncisal.patrimonio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.edu.uncisal.almoxarifado.dao.Dao;
import br.edu.uncisal.patrimonio.model.Marca;

public class MarcaDao extends Dao<Marca> {
	public MarcaDao(Session session) {
		super(session, Marca.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> listAll() {
        Criteria c = session.createCriteria(Marca.class);
        c.addOrder(Order.asc("nome"));
        return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Marca> buscar(String criteria) {
		StringBuffer sql = new StringBuffer(
			" SELECT m.* "
			+ " FROM almoxarifado.marca m "
			+ " WHERE sem_acentos(m.nome) ilike sem_acentos('%" + criteria + "%') "
			+ " ORDER BY m.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Marca.class);
		return sqlQuery.list();
	}
	
}
