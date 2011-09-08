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
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEstoque;

@SuppressWarnings("unchecked")
public class ItemEstoqueDao extends Dao<ItemEstoque> {

    ItemEstoqueDao(Session session) {
        super(session, ItemEstoque.class);
    }

    public List<ItemEstoque> itensPermanentes() {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.eq("item.materialPermanente", new Boolean(true)));
        return c.list();
    }

    public List<ItemEstoque> itensPermanentes(ItemEstoque ie) {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.eq("itemEstoque.item.materialPermanente", new Boolean(true)));
        if (ie != null) {
            if (ie.getAlmoxarifado() != null) {
                c.add(Restrictions.eq("itemEstoque.almoxarifado.id", ie.getAlmoxarifado().getId()));
            }
            if (ie.getItem().getNome() != null) {
                c.add(Restrictions.ilike("itemEstoque.item.nome", ie.getItem().getNome()));
            }

        }

        return c.list();
    }

    public List<ItemEstoque> itensPermanentesTombados(Boolean tombado) {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.eq("item.materialPermanente", new Boolean(true)));
        if (tombado) {
            c.add(Restrictions.isNotEmpty("patrimonio"));
        } else {
            c.add(Restrictions.isEmpty("patrimonio"));
        }
        return c.list();
    }

    public ItemEstoque findByExample(Item item, Almoxarifado almoxarifado) {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.eq("almoxarifado.id", almoxarifado.getId()));
        c.add(Restrictions.eq("item.id", item.getId()));

        List<ItemEstoque> resultados = c.list();
        
        if(resultados != null && resultados.size() > 0)
        	return resultados.get(0);        	
        
        return null;
    }

    public List<ItemEstoque> listAllEstoqueBaixo() {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.leProperty("estoque", "estoqueMinimo"));
        return c.list();
    }

    public List<ItemEstoque> listAllEstoqueBaixo(Almoxarifado almoxarifado) {
        Criteria c = session.createCriteria(ItemEstoque.class);
        c.add(Restrictions.leProperty("estoque", "estoqueMinimo"));
        c.add(Restrictions.eq("almoxarifado", almoxarifado));
        return c.list();
    }

    public List<ItemEstoque> listByAlmoxarifado(Almoxarifado a) {
        return session.createQuery(" from ItemEstoque ie where ie.almoxarifado.id=" + a.getId() + " order by ie.item.nome").list();
    }

    public List<ItemEstoque> buscar(ItemEstoque ie) {

        StringBuffer hql = new StringBuffer(" from ItemEstoque ie where 1=1 ");
        if (ie != null) {
            if (ie.getAlmoxarifado() != null) {
                hql.append(" and ie.almoxarifado.id=" + ie.getAlmoxarifado().getId());
            }
            if (ie.getItem() != null && ie.getItem().getId() != null) {
                hql.append(" and ie.item.id=" + ie.getItem().getId());
            }
            if (ie.getItem() != null && ie.getItem().getNome() != null && !"".equals(ie.getItem().getNome())) {
                hql.append(" and lower(ie.item.nome) like lower('%" + ie.getItem().getNome() + "%')");
            }
            if (ie.getItem() != null && ie.getItem().getGrupo() != null && ie.getItem().getGrupo().getId() != null) {
                hql.append(" and ie.item.grupo.id =" + ie.getItem().getGrupo().getId());
            }
            if (ie.getItem() != null && ie.getItem().getTipoMedidaEntrada() != null && ie.getItem().getTipoMedidaEntrada().getId() != null) {
                hql.append(" and ie.item.tipoMedidaEntrada.id=" + ie.getItem().getTipoMedidaEntrada().getId());
            }
        }
        Query q = session.createQuery(hql.toString());
        return q.list();
    }

	public List<ItemEstoque> buscarItem(String criteria, Almoxarifado a) {
	   Long l = null;
	   try {
		   l = new Long(criteria);
	   } catch (NumberFormatException e) {
		   l = 0L;
	   }
		
		StringBuffer sql = new StringBuffer(
			" SELECT ie.*, i.nome, a.nome "
			+ " FROM almoxarifado.item_estoque ie "
			+ " join almoxarifado.item i on (i.id = ie.item_id) "
			+ " join almoxarifado.almoxarifado a on (a.id = ie.almoxarifado_id) "
			+ " WHERE ie.almoxarifado_id = " + a.getId() + " AND "
			+ " (sem_acentos(i.nome) ilike sem_acentos('%" + criteria + "%') OR "
			+ " i.id = " + l + ") "
			+ " ORDER BY i.nome"
		);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()).addEntity(ItemEstoque.class);
		return sqlQuery.list();
	}
	
	public void atualizaEstoque(Item i, Almoxarifado a) {
		StringBuilder sql = new StringBuilder();
		sql.append(
			" UPDATE almoxarifado.item_estoque SET estoque = ("
			+ " (SELECT COALESCE(SUM(ie.quantidade), 0) FROM almoxarifado.item_entrada ie"
			+ " INNER JOIN almoxarifado.nota_entrada ne ON (ne.id = ie.nota_entrada_id)"
			+ " WHERE ne.almoxarifado_id = "+ a.getId() +" AND ie.item_id = "+ i.getId() +")"
			+ " -"
			+ " (SELECT COALESCE(SUM(isaida.quantidade), 0) FROM almoxarifado.item_saida isaida"
			+ " INNER JOIN almoxarifado.requisicao r ON (r.id = isaida.requisicao_id)"
			+ " INNER JOIN almoxarifado.status sa ON (r.status_atual_id = sa.id)"
			+ " WHERE r.almoxarifado_id = "+ a.getId() +" AND isaida.item_id = "+ i.getId() +" AND (sa.tipo_status_id = 5 OR sa.tipo_status_id = 4))"
			+ " ) WHERE item_id = "+ i.getId() +" AND almoxarifado_id = "+ a.getId() +""
		);		
		
		session.createSQLQuery(sql.toString()).executeUpdate();
	}
	
}
