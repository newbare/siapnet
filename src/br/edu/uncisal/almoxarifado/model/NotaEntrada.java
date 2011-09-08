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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

/**
 * @author Igor Cavalcante
 * @author Augusto Oliveira
 * Está classe implementa a interface datable, para que ela possa ser ordenada por data em conjunto com outras classes.
 */
@Entity
@Table(name = "nota_entrada")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_nota_entrada")
public class NotaEntrada extends Domain implements Serializable, Datable {

	private static final long serialVersionUID = -2213922112709457289L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
	private Long id;
	/**
	 * Número da nota fiscal
	 */
	@Column(length = 100)
	private String numero;
	/**
	 * Observação da nota de entrada
	 */
	@Column(length = 255)
	private String observacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@ManyToOne
	private Almoxarifado almoxarifado;
	@ManyToOne
	private Fornecedor fornecedor;

	/**
	 * Ex.: Doação, Compra, Emprestimo etc.
	 */
	@ManyToOne
	@JoinColumn(name = "tipo_entrada_id")
	private TipoEntrada tipoEntrada = new TipoEntrada();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "nota_entrada_id")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@OrderBy("id")
	private List<ItemEntrada> itensEntrada;

	public NotaEntrada() {
		super();
		setData(new Date());
		setTipoEntrada(new TipoEntrada(1L));
		setItensEntrada(new ArrayList<ItemEntrada>());
	}

	public NotaEntrada(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public Date getData() {
		return data;
	}

	@Override
	public void setData(Date data) {
		this.data = data;
	}

	public Almoxarifado getAlmoxarifado() {
		return almoxarifado;
	}

	public void setAlmoxarifado(Almoxarifado almoxarifado) {
		this.almoxarifado = almoxarifado;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public List<ItemEntrada> getItensEntrada() {
		return itensEntrada;
	}

	public void setItensEntrada(List<ItemEntrada> itemsEntrada) {
		this.itensEntrada = itemsEntrada;
	}

	public void addItemEntrada(ItemEntrada itemEntrada) {
		if (itensEntrada == null) {
			itensEntrada = new ArrayList<ItemEntrada>();
		}
		itensEntrada.add(itemEntrada);
	}

	public void remItemEntrada(ItemEntrada ie) {
		itensEntrada.remove(ie);
		ie = null;
	}

	/**
	 * Efetua as entradas dos itens e diminui do estoque.
	 */
	public Collection<Item> efetuarEntrada() {
		// Itens a serem, atualizados.
		Collection<Item> itens = new HashSet<Item>();

		for (ItemEntrada ie : getItensEntrada()) {
			itens.add(ie.getItem());
		}
		return itens;
	}

	public Collection<Item> remove() {
		// Itens a serem, atualizados.
		HashSet<Item> itens = new HashSet<Item>();

		for (ItemEntrada ie : getItensEntrada()) {
			itens.add(ie.getItem());
		}

		return itens;
	}

	public String toString() {
		return "[id: " + id + " número: " + numero + " data: " + data
				+ " almoxarifado: " + almoxarifado + " fornecedor: "
				+ fornecedor + " tipo de entrada: " + tipoEntrada
				+ " itens de entrada: " + itensEntrada + "]";
	}

}
