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
 * Agrupamentos para classificação dos itens do almoxarifado.
 * 
 * @see Item
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_grupo")
public class Grupo extends Domain implements Serializable {

	private static final long serialVersionUID = 3092732071550363887L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	
	@Column(length = 50)
	private String nome;

	/**
	 * Código de Espécie de despesa do sistema SIAFEM
	 * */
	@Column(name="codigo_siafem")
	private String codigoSIAFEM;

	/**
	 * Descritivo do grupo, informando os tipo de materiais que podem ser classificados no grupo
	 * */
	@Column(name="descritivo")
	private String descritivo;
		
	/**
	 * Flag se este grupo é de materiais permanentes
	 * */
	@Column(name = "material_permanente")
	private Boolean materialPermanente;

	/**
	 * Taxa de Depreciação em "%" deste grupo
	 * */
	@Column(name = "taxa_depreciacao")
	private Float taxaDepreciacao;

	/** Tempo em Anos de vida útil deste grupo de materiais permanentes */
	@Column(name = "vida_util")
	private Integer vidaUtil;

	public Boolean getMaterialPermanente() {
		return materialPermanente;
	}

	public void setMaterialPermanente(Boolean materialPermanente) {
		this.materialPermanente = materialPermanente;
	}

	public Float getTaxaDepreciacao() {
		return taxaDepreciacao;
	}

	public void setTaxaDepreciacao(Float taxaDepreciacao) {
		this.taxaDepreciacao = taxaDepreciacao;
	}

	public Integer getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Integer vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	@Override
	public Long getId() {
		return this.id;
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
	public String getCodigoSIAFEM() {
		return codigoSIAFEM;
	}

	public void setCodigoSIAFEM(String codigoSIAFEM) {
		this.codigoSIAFEM = codigoSIAFEM;
	}

	public String getDescritivo() {
		return descritivo;
	}

	public void setDescritivo(String descritivo) {
		this.descritivo = descritivo;
	}

	public String toString() {
		return "[id: " + id + " nome: " + nome + " Bem ? "+materialPermanente+ " taxa de depreciação: "+taxaDepreciacao+" vida útil:"+ "]";
	}

}
