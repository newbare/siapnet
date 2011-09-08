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

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Orgao;

public class OrgaoDao extends Dao<Orgao> {

    OrgaoDao(Session session) {
        super(session, Orgao.class);
    }

    @Override
    public List<Orgao> listAll() {
        // Listar apenas orgãos da tabela orgao que tenham o campo id_uncisal.
        return this.session.createCriteria(Orgao.class).add(Restrictions.isNotNull("idUncisal")).list();
    }

    /** Lista orgão que estã ativados para terem almoxarifados */
    @SuppressWarnings("unchecked")
	public List<Orgao> listAtivados() {
        // Listar apenas orgãos da tabela orgao que tenham o campo id_uncisal.
        return this.session.createCriteria(Orgao.class)
        	.add(Restrictions.isNotNull("idUncisal"))
        	.add(Restrictions.eq("temAlmoxarifado", new Boolean(true)))
        	.addOrder(Order.asc("nome")).list();
    }
}
