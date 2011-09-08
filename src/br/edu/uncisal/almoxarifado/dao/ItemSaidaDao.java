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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemSaida;

/**
 *
 * @author Augusto Oliveira
 */
public class ItemSaidaDao extends Dao<ItemSaida> {

    ItemSaidaDao(Session session) {
        super(session, ItemSaida.class);
    }
	/***/
	public Double calculaConsumoMedio(Item item, Almoxarifado almox, Integer dias){
	    SQLQuery q = session.createSQLQuery("select sum(quantidade) as total from almoxarifado.item_saida itsa " +
	            " inner join almoxarifado.requisicao req on req.id=itsa.requisicao_id" + 
	            " where almoxarifado_id = "+almox.getId()+ " and item_id="+item.getId());
	    //TODO falta terminar os calculs e busca
	    List<Double> total = q.list();
	    Double consumo = total.get(0) / dias;
	    return consumo;
	}
	
	public List<ItemSaida> loadCurvaABC(Date dataInicio, Date dataFim, Long almoxarifado_id) {
		StringBuilder sql = new StringBuilder("SELECT"
				+ " i.id AS item_id,"
				+ " i.nome AS item_nome,"
				+ " COALESCE(SUM(isaida.quantidade), 0) AS quant_saida,"
				+ " COALESCE(SUM(isaida.valor_unitario), 0) AS valor_consumo"
				+ " FROM almoxarifado.item_saida isaida"
				+ " INNER JOIN almoxarifado.requisicao r ON (r.id = isaida.requisicao_id)"
				+ " INNER JOIN almoxarifado.status s ON (s.requisicao_id = r.id)"
				+ " INNER JOIN almoxarifado.almoxarifado a ON (a.id = r.almoxarifado_id)"
				+ " INNER JOIN almoxarifado.item i ON (isaida.item_id = i.id)"
				+ " WHERE a.id = :almoxarifado AND s.tipo_status_id = 4"
				+ " AND isaida.quantidade != 0"
				+ " AND (s.data_cadastro BETWEEN :dataInicio AND :dataFim)"
				+ " GROUP BY a.id, a.nome, i.id, i.nome"
				+ " ORDER BY"
				+ " COALESCE((SELECT SUM(sub3_ie.valor_unitario*sub3_ie.quantidade)/SUM(sub3_ie.quantidade) FROM almoxarifado.item_entrada sub3_ie"
				+ " INNER JOIN almoxarifado.nota_entrada sub3_ne ON(sub3_ne.id = sub3_ie.nota_entrada_id)"
				+ "	WHERE sub3_ie.valor_unitario != 0 AND sub3_ne.almoxarifado_id = a.id AND sub3_ie.item_id = i.id"
				+ " ), 0) * COALESCE(SUM(isaida.quantidade), 0)"
				+ " DESC");
		
		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addScalar("item_id", Hibernate.LONG);
		query.addScalar("item_nome", Hibernate.STRING);
		query.addScalar("quant_saida", Hibernate.BIG_DECIMAL);
		query.addScalar("valor_consumo", Hibernate.BIG_DECIMAL);
		query.setLong("almoxarifado", almoxarifado_id);
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		List<Object[]> list =  query.list();
		
		List<ItemSaida> isaidas = new ArrayList<ItemSaida>();
		for (Object[] objects : list) {
			ItemSaida is = new ItemSaida();
			
			Item i = new Item();
			i.setId((Long) objects[0]);
			i.setNome(objects[1].toString());
			is.setItem(i);
			
			is.setQuantidade((BigDecimal) objects[2]);
			is.setValorConsumo((BigDecimal) objects[3]);			
			
			isaidas.add(is);			
		}
		
		return isaidas;
	}
}
