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

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.util.UTF8Converter;

@SuppressWarnings("unchecked")
public class ItemDao extends Dao<Item> {

	public ItemDao(Session session) {
		super(session, Item.class);
	}
	
	public List<Item> listAll() {
		Criteria c = session.createCriteria(Item.class);
		c.addOrder(Order.asc("nome"));
		return c.list();
	}
	
	public List<Item> buscar(Item item) {

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM almoxarifado.item WHERE 1=1 ");

		if (item.getId() != null) {
			sql.append(" AND id=" + item.getId());

		} else {
			if (item.getNome() != null && !("".equals(item.getNome()))) {
				sql.append(" AND sem_acentos(nome) ilike (sem_acentos('%"
						+ item.getNome() + "'%))");
			}
			if (item.getGrupo() != null && item.getGrupo().getId() != null) {
					sql.append(" AND grupo_id=" + item.getGrupo().getId());
			}
			if (item.getTipoMedidaEntrada() != null
						&& item.getTipoMedidaEntrada().getId() != null) {
					sql.append(" AND tipo_medida_id="
							+ item.getTipoMedidaEntrada().getId());
			}
			if (item.getMaterialPermanente() != null
						&& item.getMaterialPermanente()) {
					sql.append(" AND material_permanente="
							+ item.getMaterialPermanente());
			}
			if (item.getAplicacao() != null
						&& !("".equals(item.getAplicacao()))) {
					sql.append(" AND sem_acentos(aplicacao) ilike (sem_acentos('%"
									+ item.getAplicacao() + "%'))");
			}

		}
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(
				Item.class);
		return sqlQuery.list();
	}

	public List<Item> loadItensAutoComplete(String q, Almoxarifado a) {
		q = UTF8Converter.converteUtf8toIso(q);
		StringBuilder sql;
		if(a == null || a.getId() == null) {
			sql = new StringBuilder(
				" SELECT * FROM almoxarifado.item i"
				+ " WHERE sem_acentos(nome) ilike (sem_acentos('%" + q + "%'))"
			);			
			
		} else {
			sql = new StringBuilder(
				" SELECT * FROM almoxarifado.item i"
				+ " JOIN almoxarifado.item_estoque ie ON(ie.item_id = i.id)"
				+ " WHERE ie.almoxarifado_id=" + a.getId()
				+ " AND sem_acentos(nome) ilike (sem_acentos('%" + q + "%'))"
			);
		}
		
		//sql.append(" AND materialpermanente=" + permanente);		
		sql.append( "ORDER BY nome");
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Item.class);
		return sqlQuery.list();
	}

	public List<Item> itensPermanentes() {
		Criteria c = session.createCriteria(Item.class);
		c.add(Restrictions.eq("materialPermanente", new Boolean(true)));

		return c.list();
	}
	
	public List<Item> buscar(String criteria){
		StringBuffer sql = new StringBuffer(
			" SELECT *"
			+ " FROM almoxarifado.item i "
			+ " JOIN almoxarifado.tipo_medida tm ON (tm.id = i.tipo_medida_id) "
			+ " LEFT JOIN almoxarifado.grupo g ON (g.id = i.grupo_id) "
			+ " WHERE sem_acentos(i.nome) ilike sem_acentos('%" + criteria + "%')"
		);
    	
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(Item.class);
		return sqlQuery.list();
    }
	
	public void consolidar(Long codItemPrevalecente, Long codItemCondenado) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE almoxarifado.item_entrada SET item_id = " + codItemPrevalecente + " WHERE item_id = " + codItemCondenado);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
		sqlQuery.addEntity(Item.class);
		sqlQuery.executeUpdate();
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("UPDATE almoxarifado.item_requisicao SET item_id = " + codItemPrevalecente + " WHERE item_id = " + codItemCondenado);
		SQLQuery sqlQuery2 = session.createSQLQuery(sql2.toString());
		sqlQuery2.addEntity(Item.class);
		sqlQuery2.executeUpdate();
		
		StringBuilder sql3 = new StringBuilder();
		sql3.append("UPDATE almoxarifado.item_saida SET item_id = " + codItemPrevalecente + " WHERE item_id = " + codItemCondenado);
		SQLQuery sqlQuery3 = session.createSQLQuery(sql3.toString());
		sqlQuery3.addEntity(Item.class);
		sqlQuery3.executeUpdate();
		
		StringBuilder sql4 = new StringBuilder();
		sql4.append("DELETE FROM almoxarifado.item WHERE id = " + codItemCondenado);
		SQLQuery sqlQuery4 = session.createSQLQuery(sql4.toString());
		sqlQuery4.addEntity(Item.class);
		sqlQuery4.executeUpdate();		
	}
	
}
