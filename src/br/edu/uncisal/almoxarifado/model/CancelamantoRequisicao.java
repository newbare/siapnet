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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Unidade elementar do controle de estoque.
 */
@Entity
@Table(name = "historico_cancelamento_requisicao")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_canc_requisicao")
public class CancelamantoRequisicao extends Domain implements Serializable {

	private static final long serialVersionUID = -8957458768879594420L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	@Column(name = "numero_requisicao")
	private Long numeroRequisicao;
	@Column(name = "justificativa_cancelamento")
	private String justificativa;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
    private Usuario responsavelCancelamento;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cancelamento")
	private Date dataCancelamento;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumeroRequisicao() {
		return numeroRequisicao;
	}
	public void setNumeroRequisicao(Long numeroRequisicao) {
		this.numeroRequisicao = numeroRequisicao;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public Usuario getResponsavelCancelamento() {
		return responsavelCancelamento;
	}
	public void setResponsavelCancelamento(Usuario responsavelCancelamento) {
		this.responsavelCancelamento = responsavelCancelamento;
	}
	public Date getDataCancelamento() {
		return dataCancelamento;
	}
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	
	
}
