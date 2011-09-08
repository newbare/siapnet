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
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Unidade;

/**
 *
 * @author Augusto Oliveira
 */
public class AlmoxarifadoDao extends Dao<Almoxarifado> {

    AlmoxarifadoDao(Session session) {
        super(session, Almoxarifado.class);
    }
    
    @SuppressWarnings("unchecked")
	public List<Almoxarifado> listByUnidade(Unidade u) {
	    Criteria c = session.createCriteria(Almoxarifado.class);
	    c.add(Restrictions.eq("unidade.id", u.getId()));
	    return c.list();
    }

    @SuppressWarnings("unchecked")
	public List<Almoxarifado> listByOrgao(Orgao o) {
            String sql = "from Almoxarifado a where a.unidade.orgao.id="+o.getId();
	    Query q = session.createQuery(sql);
	    
	    return q.list();
    }
	
	@SuppressWarnings("unchecked")
	public List<Almoxarifado> buscarAlmoxarifado(String criteria) {
		StringBuffer sql = new StringBuffer(
			" SELECT a.* "
			+ " FROM almoxarifado.almoxarifado a "
			+ " WHERE sem_acentos(a.nome) ilike sem_acentos('%" + criteria + "%') "
			+ " ORDER BY a.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Almoxarifado.class);
		return sqlQuery.list();
	}

}
