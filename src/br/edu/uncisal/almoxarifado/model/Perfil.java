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

package br.edu.uncisal.almoxarifado.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

/**
 * Perfil dos usuários cadastrado no sistema. Cada perfil vai ter uma série de permissões.
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_perfil")
public class Perfil extends Domain implements Serializable, Comparable<Perfil> {

    private static final long serialVersionUID = 6469537842824329443L;
    public static final long ADMINISTRADOR_GERAL = 16L;
    public static final long ADMINISTRADOR_LOCAL = 1L;
    public static final long ALMOXARIFE = 12L;
    public static final long CONSUMIDOR = 13L;
    public static final long PATRIMONIO = 11L;
    public static final long ALMOXARIFE_PATRIMONIO = 14L;
    public static final long CATALOGADOR = 18L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(length = 25)
    private String nome;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Recurso> recursos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o == this)
    		return true;
    	
    	if(o.getClass() == this.getClass()) {
    		Perfil p = (Perfil) o;
    		if(this.getId().equals(p.getId()))
    			return true;
    	}
    	
    	return false;
    }

    @Override
    public String toString() {
        return "[id: " + id + " nome: " + nome + " recursos: " + recursos + "]";
    }

	@Override
	public int compareTo(Perfil o) {
		return this.getNome().compareTo(o.getNome());
	}
}
