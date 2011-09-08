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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Define qual a unidade de medida será aplicada no Item
 * @author Igor Cavalcante
 * @see Item
 */
@Entity(name = "tipo_medida")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_tipo_medida")
public class TipoMedida extends Domain implements Serializable {

	private static final long serialVersionUID = 4920441286276319623L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	@Column(length=25)
	private String nome;

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
	
	@Override
	public String toString() {
		return "[id: "+ id +" nome: "+ nome +"]";
	}
}
