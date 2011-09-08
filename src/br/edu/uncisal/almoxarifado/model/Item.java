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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Unidade elementar do controle de estoque.
 */
@Entity
@Table(name = "item")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_item")
public class Item extends Domain implements Serializable {

	private static final long serialVersionUID = -8957458768879594420L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	@Column(length = 100)
	private String nome;
	/** Aplicação do material */
	@Column(length = 50)
	private String aplicacao;

	@Column(length = 100, name = "nome_modificador")
	private String nomeModificador;
	@Column(length = 100, name = "nome_caracteristicas_fisicas")
	private String nomeCaracteristicasFisicas;
	@Column(length = 100, name = "nome_identificacao_auxiliar")
	private String nomeIdentificacaoAuxiliar;
	@Column(length = 100, name = "nome_referencia_comercial")
	private String nomeReferenciaComercial;
	@Column(length = 500, name = "nome_descritivo")
	private String nomeDescritivo;
	/**
	 * Tipo de medida em que se recebe o material. Ex.: Um material é recebido
	 * em caixas.
	 */
	@ManyToOne
	@JoinColumn(name = "tipo_medida_id")
	private TipoMedida tipoMedidaEntrada;
	
	/**
	 * Tipo de estoque. Ex.: Remédio, Alimento etc.
	 */
	@ManyToOne
	private Grupo grupo;
	/**
	 * Verifica se o item é um bem de consumo ou um material permanente
	 */
	@Column(name = "material_permanente")
	private Boolean materialPermanente = new Boolean(false);

	public Item() {
		super();
	}

	public Item(Long id) {
		super();
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
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

	public String getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(String aplicacao) {
		this.aplicacao = aplicacao;
	}

	public Boolean getMaterialPermanente() {
		return materialPermanente;
	}

	public void setMaterialPermanente(Boolean materialPermanente) {
		this.materialPermanente = materialPermanente;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setTipoMedidaEntrada(TipoMedida tipoMedidaEntrada) {
		this.tipoMedidaEntrada = tipoMedidaEntrada;
	}

	public TipoMedida getTipoMedidaEntrada() {
		return tipoMedidaEntrada;
	}

	public String getNomeCaracteristicasFisicas() {
		return nomeCaracteristicasFisicas;
	}

	public void setNomeCaracteristicasFisicas(String nomeCaracteristicasFisicas) {
		this.nomeCaracteristicasFisicas = nomeCaracteristicasFisicas;
	}

	public String getNomeIdentificacaoAuxiliar() {
		return nomeIdentificacaoAuxiliar;
	}

	public void setNomeIdentificacaoAuxiliar(String nomeIdentificacaoAuxiliar) {
		this.nomeIdentificacaoAuxiliar = nomeIdentificacaoAuxiliar;
	}

	public String getNomeModificador() {
		return nomeModificador;
	}

	public void setNomeModificador(String nomeModificador) {
		this.nomeModificador = nomeModificador;
	}

	public String getNomeReferenciaComercial() {
		return nomeReferenciaComercial;
	}

	public void setNomeReferenciaComercial(String nomeReferenciaComercial) {
		this.nomeReferenciaComercial = nomeReferenciaComercial;
	}

	public void setNomeDescritivo(String nomeDescritivo) {
		this.nomeDescritivo = nomeDescritivo;
	}

	public String getNomeDescritivo() {
		return nomeDescritivo;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}

	public String toString() {
		return "[id: " + id + " nome: " + nome + ", "
				+ "nome_descritivo(modificador: " + nomeModificador
				+ ", caracteristicas físicas: " + nomeCaracteristicasFisicas
				+ ", idenficacao auxiliar: " + nomeIdentificacaoAuxiliar
				+ ", referencia comercial: " + nomeReferenciaComercial
				+ "), aplicacao: " + aplicacao + " grupo: " + grupo + "]";
	}
}
