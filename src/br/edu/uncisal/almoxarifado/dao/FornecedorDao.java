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
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Fornecedor;
import br.edu.uncisal.almoxarifado.model.TipoFornecedor;

/**
 * @author Augusto Oliveira
 */
public class FornecedorDao extends Dao<Fornecedor> {

    FornecedorDao(Session session) {
        super(session, Fornecedor.class);
    }

    @SuppressWarnings("unchecked")
    public List<Fornecedor> listAll() {
       Criteria c =  this.session.createCriteria(Fornecedor.class).addOrder(Order.asc("nome"));
        return c.list();
    }
    
    public List<Fornecedor> listAllbyTipoFornecedor(TipoFornecedor tipoFornecedor) {
        Criteria c = session.createCriteria(Fornecedor.class);
        c.add(Restrictions.eq("tipoFornecedor.id", tipoFornecedor.getId()));
        return c.list();
    }

	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscar(String criteria) {
		StringBuffer sql = new StringBuffer(
			" SELECT f.*, (select t.nome from almoxarifado.tipo_fornecedor t where t.id = f.tipo_fornecedor_id) "
			+ " FROM almoxarifado.fornecedor f "
			+ " WHERE sem_acentos(f.nome) ilike sem_acentos('%" + criteria + "%') "
			+ " ORDER BY f.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Fornecedor.class);
		return sqlQuery.list();
	}


}
