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

package br.edu.uncisal.patrimonio.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import br.edu.uncisal.almoxarifado.model.Domain;
import br.edu.uncisal.almoxarifado.model.Grupo;

/**
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_marca")
public class Marca extends Domain implements Serializable {

	private static final long serialVersionUID = 5752460019876603861L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;

    /** Nome da marca*/
	@Column(length=25)
    private String nome;
    
    /** Grupos de itens que a Marca fornece*/
    @JoinTable(name = "grupos_marca", joinColumns = {@JoinColumn(name = "marca_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "grupo_id", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Grupo> grupos;

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String toString() {
    	return "[id: "+ id +" nome: "+ nome +"]"; 
    }    
   
}
