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

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.dao.Dao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEntrada;
import br.edu.uncisal.patrimonio.model.Patrimonio;

/**
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@SuppressWarnings("unchecked")
public class PatrimonioDao extends Dao<Patrimonio> {

    public PatrimonioDao(Session session) {
        super(session, Patrimonio.class);
    }

	public List<Patrimonio> listAll(Item item) {
        Query q = session.createQuery(" from Patrimonio p where p.itemEntrada.itemEstoque.item.id=:item").setParameter("item", item.getId());
        return q.list();
    }

    public List<Patrimonio> listAll(ItemEntrada itemEntrada) {
        Query q = session.createQuery(" from Patrimonio p where p.itemEntrada.id=:ie").setParameter("ie", itemEntrada.getId());
        return q.list();
    }

    public List<Patrimonio> listAll(Almoxarifado almoxarifado) {
        Query q = session.createQuery(" from Patrimonio p where p.itemEntrada.notaEntrada.almoxarifado.id=:a").setParameter("a", almoxarifado.getId());
        return q.list();
    }
    
    public Collection<Patrimonio> loadByDepartamento(Departamento d) {
    	Criteria c = session.createCriteria(Patrimonio.class);
    	c.add(Restrictions.eq("departamento.id", d.getId()));
        return c.list();
    }
    
    //Verificar necessidade deste método.
    /*public List<Patrimonio> buscar(Patrimonio p) {

        StringBuffer sql = new StringBuffer("select p.* from almoxarifado.patrimonio p ").append("inner join almoxarifado.item_entrada ie on ie.id = p.item_entrada_id ").append("inner join almoxarifado.item i on i.id = ie.item_id ").append("inner join almoxarifado.nota_entrada ne on ne.id = ie.nota_entrada_id where 1=1 ");
        HashMap parametros = new HashMap();

        if (p.getNumero() != null) {
            sql.append(" and p.numero=:numero ");
            parametros.put("numero", p.getNumero());

        } else {

            if (p.getItemEntrada().getItem().getNome() != null && !("".equals(p.getItemEntrada().getItem().getNome()))) {
                sql.append(" and i.nome ilike :nome ");
                parametros.put("nome", "%" + p.getItemEntrada().getItem().getNome() + "%");
            }

            if (p.getModelo() != null && !("".equals(p.getModelo()))) {
                sql.append(" and p.modelo ilike :modelo");
                parametros.put("modelo", "%" + p.getModelo() + "%");
            }

            if (p.getMarca().getId() != null) {
                sql.append(" and p.marca_id=:marca");
                parametros.put("marca", p.getMarca().getId());
            }

            if (p.getEstadoDepreciacao().getId() != null) {
                sql.append(" and p.estado_depreciacao_id=:depreciacao ");
                parametros.put("depreciacao", p.getEstadoDepreciacao().getId());

            }

            if (p.getItemEntrada().getNotaEntrada() != null && p.getItemEntrada().getNotaEntrada().getAlmoxarifado().getId() != null) {
                sql.append(" and ne.almoxarifado_id=:almox");
                parametros.put("almox", p.getItemEntrada().getNotaEntrada().getAlmoxarifado().getId());
            }

        }

        SQLQuery q = session.createSQLQuery(sql.toString()).addEntity(Patrimonio.class);
        Set<String> chaves = parametros.keySet();
        for (String chave : chaves) {
            q.setParameter(chave, parametros.get(chave));
        }
        return q.list();

    }*/
}