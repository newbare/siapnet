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

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.NotaEntrada;

@SuppressWarnings("unchecked")
public class NotaEntradaDao extends Dao<NotaEntrada> {

    NotaEntradaDao(Session session) {
        super(session, NotaEntrada.class);
    }
    
    public List<NotaEntrada> listByExample(String numeroNota, Long idFornecedor) {
    	Criteria c = session.createCriteria(NotaEntrada.class);
    	c.add(Restrictions.eq("numero", numeroNota));
    	c.add(Restrictions.eq("fornecedor.id", idFornecedor));
    	return c.list();
    }
    
    public List<NotaEntrada> listByAlmoxarifado(Almoxarifado a) {
        Criteria c = session.createCriteria(NotaEntrada.class);
        c.add(Restrictions.eq("almoxarifado.id", a.getId()));
        c.addOrder(Order.asc("id"));
        return c.list();
    }

    public List<NotaEntrada> list(NotaEntrada ne) {

        StringBuffer sql = new StringBuffer(" select distinct (ne.id),ne.* from almoxarifado.nota_entrada ne, ");
        sql.append(" almoxarifado.item_entrada ie ");
        sql.append(" where ne.id = ie.nota_entrada_id ");

        if (ne.getData() != null) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            sql.append(" and (ne.data between '" + form.format(ne.getData()) + " 00:00:00' and '" + form.format(ne.getData()) + " 23:59:59')");
        }
        if (ne.getNumero() != null && !"".equals(ne.getNumero())) {
            sql.append(" and ne.numero like :numero");
        }

        if (ne.getObservacao() != null) {
            sql.append(" and ne.observacao like :observ");
        }

        if (ne.getAlmoxarifado() != null) {
            sql.append(" and ne.almoxarifado_id = " + ne.getAlmoxarifado().getId());
        }

        if (ne.getFornecedor() != null && ne.getFornecedor().getId() != null) {
            sql.append(" and ne.fornecedor_id = " + ne.getFornecedor().getId());
        }

        if (ne.getTipoEntrada() != null && ne.getTipoEntrada().getId() != null) {
            sql.append(" and ne.tipo_entrada_id =" + ne.getTipoEntrada().getId());
        }

        if (ne.getItensEntrada()!=null && ne.getItensEntrada().get(0).getItem().getId() != null) {
            sql.append(" and ie.item_id=" + ne.getItensEntrada().get(0).getItem().getId());
        }
        
        SQLQuery query = session.createSQLQuery(sql.toString()).addEntity(NotaEntrada.class);
        if (ne.getNumero() != null && !"".equals(ne.getNumero())) {
            query.setParameter("numero", "%" + ne.getNumero() + "%");
        }

        if (ne.getObservacao() != null) {
            query.setParameter("observ", "%" + ne.getObservacao() + "%");
        }

        return query.list();
    }
    
    public Set<NotaEntrada> list(Almoxarifado a, Item i) {
    	Criteria c = session.createCriteria(NotaEntrada.class)
    		.add(Restrictions.eq("almoxarifado.id", a.getId()));
    	c.createCriteria("itensEntrada", "ie")
    		.add(Restrictions.eq("ie.item.id", i.getId()));
    	return new HashSet<NotaEntrada>(c.list());    	
    }

	public List<NotaEntrada> buscar(String criteria, Almoxarifado a) {
		Long l = null;
    	try {
    		l = new Long(criteria);
    	} catch (NumberFormatException e) {
			l = 0L;
		}
		StringBuffer sql = new StringBuffer(
			" SELECT ne.*, f.nome, a.nome "
			+ " FROM almoxarifado.nota_entrada ne "
			+ " JOIN almoxarifado.fornecedor f ON (f.id = ne.fornecedor_id) "
			+ " JOIN almoxarifado.almoxarifado a ON (a.id = ne.almoxarifado_id) "
			+ " WHERE (ne.almoxarifado_id = " + a.getId() + ") "
			+ " AND (ne.id = " + l + " OR "
			+ " sem_acentos(f.nome) ilike sem_acentos('%" + criteria + "%')"
			+ "	OR ne.numero ilike ('%" + criteria + "%'))"
			+ " ORDER BY ne.data"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(NotaEntrada.class);
		return sqlQuery.list();
	}
	
}