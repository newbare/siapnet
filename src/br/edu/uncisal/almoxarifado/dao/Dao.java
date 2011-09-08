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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.edu.uncisal.almoxarifado.model.Domain;

public class Dao<T extends Domain> {

    protected final Session session;
    @SuppressWarnings("rawtypes")
	private final Class classe;

    @SuppressWarnings("rawtypes")
	public Dao(Session session, Class classe) {
        this.session = session;
        this.classe = classe;
    }

    public void add(T obj) {
        this.session.save(obj);
        this.session.flush();
        evict(obj);
    }
    /**
     * Salva ou atualiza um objeto.
     */
    public Serializable save(T obj) {
    	Serializable r = null;
    	
    	if(obj.getId() == null) {
    		r = this.session.save(obj);
    	} else {
    		this.session.update(obj);
    	}
    	
        this.session.flush();
        evict(obj);
        return r;
    }
    
    public Object merge(T obj) {
    	return session.merge(obj);
    }

    public void remove(T obj) {
        this.session.delete(obj);
    }

    public void update(T obj) {
        this.session.merge(obj);
        this.session.flush();
        evict(obj);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> listAll() {
        return this.session.createCriteria(classe).addOrder(Order.asc("id")).list();
    }

    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) session.get(classe, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T obj) {
        Criteria c = this.session.createCriteria(classe);
        c.add(Example.create(obj).enableLike().ignoreCase());
        return c.list();
    }

    @SuppressWarnings("unchecked")
    /**
     * findByExample - Método que monta os critérios e faz a busca usando o objeto passado como exemplo.
     * Mas para atributos que são objetos do dominio. Deve-se adicionar as propriedades no Map
     * o atributo com o id e o valor.
     * Exemplo: O atributo unidade da classe Departamento adiciona no map unidade.id.
     * Código: map.put("unidade.id",departamento.getUnidade().getId());
     */
    public List<T> findByExample(T obj, Map<String,Long> properties) {
        Criteria c = this.session.createCriteria(classe);
        c.add(Example.create(obj).enableLike().ignoreCase());
        if (properties!=null){

        for (String child : properties.keySet())  {
                c.add(Restrictions.eq(child, properties.get(child)));
           }
        }
        return c.list();
    }

    @SuppressWarnings("unchecked")
    public T getByExample(T obj) {
        Criteria c = this.session.createCriteria(classe);
        c.add(Example.create(obj).enableLike());
        return (c.list().size() > 0 ? (T) c.list().toArray()[0] : null);
    }
    
    /**
     * Evita que um objeto seja colocado no cache.
     * Geralmente serve após os objetos serem gravados, para que eles possam
     * ser recarregados de acordo com os dados no banco de dados.
     * @param vo
     */
    public void evict(Object vo) {
        if (this.session.contains(vo)) {
            session.evict(vo);
        }
    }
    
}