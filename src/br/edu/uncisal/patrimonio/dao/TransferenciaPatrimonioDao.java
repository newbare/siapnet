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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.dao.Dao;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.patrimonio.model.Patrimonio;
import br.edu.uncisal.patrimonio.model.TransferenciaPatrimonio;

/**
 *
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@SuppressWarnings("unchecked")
public class TransferenciaPatrimonioDao extends Dao<TransferenciaPatrimonio> {

	public TransferenciaPatrimonioDao(Session session) {
		super(session, TransferenciaPatrimonio.class);
	}

	public List<TransferenciaPatrimonio> listAll(Patrimonio patrimonio) {
		List<TransferenciaPatrimonio> list = new ArrayList<TransferenciaPatrimonio>();
		Criteria c = null;
		if (patrimonio.getNumero() != null) {
			c = session.createCriteria(TransferenciaPatrimonio.class).add(
					Restrictions.eq("bens.numero", patrimonio.getNumero()));
		} else if (patrimonio.getId() != null) {
			c = session.createCriteria(TransferenciaPatrimonio.class).add(
					Restrictions.eq("bens.numero", patrimonio.getNumero()));
		}
		if (c != null) {
			list = c.list();
		}
		return list;
	}

	public List<TransferenciaPatrimonio> listRecebimentosPendentes(
			Departamento departamento) {
		List<TransferenciaPatrimonio> list = new ArrayList<TransferenciaPatrimonio>();
		Criteria c = null;
		if (departamento != null) {
			c = session.createCriteria(TransferenciaPatrimonio.class).add(
					Restrictions.eq("setorDestino.id", departamento.getId()));
			c.add(Restrictions.isNull("responsavelRecebimento"));
			c.add(Restrictions.isNull("dataRecebimento"));
		}

		if (c != null) {
			list = c.list();
		}
		return list;
	}
	
	public List<TransferenciaPatrimonio> listByDestino(Long departamentoId) {
		Criteria c = session.createCriteria(TransferenciaPatrimonio.class).add(
				Restrictions.eq("setorDestino.id", departamentoId));
		return c.list();
	}
	
}