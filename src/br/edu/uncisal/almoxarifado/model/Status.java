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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_status")
public class Status  implements Serializable {

	private static final long serialVersionUID = 4482352142737498105L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	private String comentario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cadastro")
	private Date dataCadastro;
	
	@ManyToOne
	@JoinColumn(name="tipo_status_id")
	private TipoStatus tipoStatus;

	public Status() {}
	
	/**
	 * Recebe um comentário e um tipo de status, gera automaticamente uma data para o novo status.
	 * @param comentario
	 * @param tipoStatus
	 */
	public Status(String comentario, Long tipoStatus) {
		this.comentario		= comentario;
		this.dataCadastro	= new Date();
		this.tipoStatus		= new TipoStatus(tipoStatus);
	}
	
	public Status(String comentario, Long tipoStatus, Date data) {
		this.comentario		= comentario;
		this.dataCadastro	= data;
		this.tipoStatus		= new TipoStatus(tipoStatus);
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoStatus getTipoStatus() {
		return tipoStatus;
	}

	public void setTipoStatus(TipoStatus tipoStatus) {
		this.tipoStatus = tipoStatus;
	}
	
    @Override
    public String toString() {
    	return "[id: "+ id +" comentário: "+ comentario +" tipo de status: "+ tipoStatus +"]"; 
    }		
	

}
