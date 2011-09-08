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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.ItemEntrada;

/**
 *
 * @author Augusto Oliveira
 */
public class ItemEntradaDao extends Dao<ItemEntrada> {

    ItemEntradaDao(Session session) {
        super(session, ItemEntrada.class);
    }

    /**
     * Retorna os itens com validade vendida
     */
    public List<ItemEntrada> itensVencidos() {
        Criteria c = session.createCriteria(ItemEntrada.class);
        c.add(Restrictions.lt("validade", new Date()));
        return c.list();
    }

    /**
     * Itens que irao vencer dentro da quantidade de dias informado como parâmetro.
     */
    public List<ItemEntrada> itensParaVencer(Integer quantidadeDias) {
        long dias = Calendar.getInstance().getTimeInMillis() / 1000 * 60 * 60 * 24; //Hoje
        dias += quantidadeDias.intValue();
        Criteria c = session.createCriteria(ItemEntrada.class);

        c.add(Restrictions.lt("validade", new Date(dias)));
        return c.list();
    }

    /** Lista itens Permanentes tombados  ou não pelo patrimonio de acordo com parâmetro passado. De todos os almoxarifados */
    public List<ItemEntrada> itensPermanentesTombados(Boolean tombado) {
        Query q = session.createQuery(" from ItemEntrada ie where ie.item.materialPermanente=:mp and ie.tombado<>:t").setParameter("mp", new Boolean(true)).setParameter("t", !tombado);
        return q.list();
    }

    /** Lista itens Permanentes tombados  ou não pelo patrimonio de acordo com parâmetro passado. De um almoxarifado específico */
    public List<ItemEntrada> itensPermanentesTombados(Boolean tombado, Almoxarifado almoxarifado) {
        Query q = session.createQuery(" from ItemEntrada ie where ie.item.materialPermanente=:mp and ie.tombado<>:t and ie.notaEntrada.almoxarifado.id=:a").setParameter("mp", new Boolean(true)).setParameter("t", !tombado).setParameter("a", almoxarifado.getId());
        return q.list();
    }
    /** Lista itens Permanentes tombados pelo patrimonio pelo nome passado (Autocomplete com AJAX) */
    public List<ItemEntrada> itensPermanentesTombados(String nome) {
        Query q = session.createQuery(" from ItemEntrada ie where ie.item.nome like :nome and ie.tombado=:t").setParameter("nome","%"+ nome+ "%").setParameter("t", new Boolean(true));
        return q.list();
    }

}
