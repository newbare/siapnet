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

package br.edu.uncisal.almoxarifado.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.model.Domain;

/**
 * Classe que registra todas as operações ocorridas no sistema para fins de auditoria.
 * @author Igor Cavalcante
 * @author Augusto Oliveira
 */
@SuppressWarnings("unused")
@Entity(name="auditor")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_auditor")
public class Auditante extends Domain implements Serializable {
	
	private static final long serialVersionUID = -7259503551917874082L;
	
	/**
	 * Código da operação
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private String id;	
	/**
	 * Representação textual do objeto antes da operação
	 */
	@Column(name="obj_anterior")
	private String objAnterior;
	/**
	 * Representação textual do objeto após a operação
	 */
	@Column(name="obj_atual")
	private String objAtual;	
	/**
	 * Tipo de operação a que o objeto foi submetido
	 */
	private String operacao;	
	/**
	 * Data da operação
	 */
	private Date data;	
	/**
	 * Usuário que efetuou a operação
	 */
	private String usuario;
	
	@Transient
	private DaoFactory daoFactory;
	
	public Auditante(DaoFactory factory) {
		this.daoFactory = factory;
	}
	
	public void setUp(String operacao, Object usuario) {
		this.operacao		= operacao;
		this.usuario		= usuario.toString();
		this.data               = new Date();
	}
	
	public void setUp(Object objAtual, String operacao, Object usuario) {
		setUp(operacao, usuario);
		this.objAtual	= objAtual.toString();
		this.data		= new Date();
	}
	
	public void setUp(Object objAnterior, Object objAtual, String operacao, Object usuario) {
		setUp(objAtual, operacao, usuario);
		this.objAnterior= objAnterior.toString();
		this.data		= new Date();
	}
	
	public void save() {
        daoFactory.getAuditanteDao().add(this);
	}
	
	public void setId(Long id) {
		this.id = id.toString();
	}
	
	@Override
	public Long getId() {
		return null;
	}
	
}
