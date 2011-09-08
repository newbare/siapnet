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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_departamento")
public class Departamento extends Domain implements Serializable {

    private static final long serialVersionUID = 9092190167175090534L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(length=75)
    private String nome;
    @Column(length=10)
    private String sigla;
    @Column(length=75)
    private String responsavel;
    @Column(length=14)
    private String telefone;
    @ManyToOne
    private Unidade unidade;
    @ManyToMany
    private List<Almoxarifado> almoxarifados;

    public Departamento() {
    	super();
    }
    
    public Departamento(Long id) {
    	this();
    	this.id = id;    	
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Almoxarifado> getAlmoxarifados() {
        return almoxarifados;
    }
    
    public void addAlmoxarifado(Almoxarifado a) {
    	if(almoxarifados == null)
    		almoxarifados = new ArrayList<Almoxarifado>();
    	
    	if(almoxarifados.contains(a))
    		return;
    	
    	almoxarifados.add(a);
    }
    
    public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
    
    public Unidade getUnidade() {
		return unidade;
	}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;

		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}

	@Override
    public String toString() {
    	return "[id: "+ id +" nome: "+ nome +" sigla: "+ sigla + " responsavel: " + responsavel +" telefone: " + telefone +" responsavel: " + responsavel +" almoxarifados: " + almoxarifados +"]";
    }
    
}
