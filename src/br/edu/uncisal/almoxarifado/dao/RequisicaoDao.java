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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.Requisicao;
import br.edu.uncisal.almoxarifado.model.TipoStatus;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unchecked")
public class RequisicaoDao extends Dao<Requisicao>{

	RequisicaoDao(Session session) {
		super(session, Requisicao.class);
	}
	
	public List<Requisicao> findByStatus(Long tipoStatusId, Almoxarifado a) {
		Criteria crit = session.createCriteria(Requisicao.class);
		crit.add(Restrictions.eq("almoxarifado.id", a.getId()));		
		crit.createCriteria("statusAtual")
			.add(Restrictions.eq("tipoStatus.id", tipoStatusId));
		crit.addOrder(Order.desc("id"));
				
		return crit.list();		
	}
	
	public List<Requisicao> findByDepartamento(Departamento d, Almoxarifado a) {
		Criteria crit = session.createCriteria(Requisicao.class);
		crit.add(Restrictions.eq("almoxarifado.id", a.getId()));
		crit.add(Restrictions.eq("departamento.id", d.getId()));
		crit.createCriteria("statusAtual")
			.add(Restrictions.eq("tipoStatus.id", TipoStatus.ENTEGUE))
			.addOrder(Order.desc("dataCadastro"));
        
        return crit.list();
	}
	
	public List<Requisicao> listarParaAcompanhar(Usuario u) {
		Criteria crit = session.createCriteria(Requisicao.class)
				.add(Restrictions.eq("usuario.id", u.getId()))
				.createCriteria("statusAtual")
					.addOrder(Order.desc("dataCadastro"));
				//.createCriteria("statusAtual")
				//	.add(Restrictions.or(Restrictions.eq("tipoStatus.id", new Long(1)), Restrictions.eq("tipoStatus.id", new Long(4))));			
		return crit.list();
	}
	
	public Set<Requisicao> findAprovadasByItem(Almoxarifado a, Item i) {
		Criteria crit = session.createCriteria(Requisicao.class);
		crit.createCriteria("itensEnviados", "ie")
			.createCriteria("item", "i");
		crit.createCriteria("almoxarifado", "a");
		crit.createCriteria("statusAtual", "sa")
			.createCriteria("tipoStatus", "ts");		
		crit.add(Restrictions.eq("a.id", a.getId()));
		crit.add(Restrictions.eq("i.id", i.getId()));
		crit.add(Restrictions.disjunction()
			.add(Restrictions.eq("ts.id", TipoStatus.APROVADO))
			.add(Restrictions.eq("ts.id", TipoStatus.ENTEGUE)));
		
		return new HashSet<Requisicao>(crit.list()); 
	}
	
}
