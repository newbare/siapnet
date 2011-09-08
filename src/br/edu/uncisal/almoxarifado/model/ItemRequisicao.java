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
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * Itens Requeridos por um usuário que são atrelados a uma determinada requisição
 * @author Igor Cavalcante
 * @see Item
 * @see Requisicao
 */
@Entity(name = "item_requisicao")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_item_requisicao")
public class ItemRequisicao extends Domain implements Serializable {

    private static final long serialVersionUID = -6097561547106174967L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    
    @Column(name = "qtd_requisitada")
    private BigDecimal qtdRequisitada;
    
    @ManyToOne
    private Item item;
    
    @Transient
    private BigDecimal estoqueVirtual;

    public ItemRequisicao() {
    }

    public ItemRequisicao(Item item, BigDecimal qtdRequisitada) {
        this.item = item;
        this.qtdRequisitada = qtdRequisitada;
    }

    public ItemRequisicao(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getQtdRequisitada() {
        return qtdRequisitada;
    }

    public void setQtdRequisitada(BigDecimal qtdRequisitada) {
        this.qtdRequisitada = qtdRequisitada;
    }

    public void setEstoqueVirtual(BigDecimal estoqueVirtual) {
        this.estoqueVirtual = estoqueVirtual;
    }

    public BigDecimal getEstoqueVirtual() {
        return estoqueVirtual;
    }

    /**
     * Devolve a quantidade requisitada no formato de saída.
     * @return
     */
    public BigDecimal getQuantidadeSaida() {
        return qtdRequisitada;
    }

    @Override
    public boolean equals(Object itemRequisicao) {
        ItemRequisicao vo = (ItemRequisicao) itemRequisicao;
        if (item.getId().equals((vo.getItem().getId()))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[id: " + id + " quantidade: " + qtdRequisitada + " item: " + item + "]";
    }
    
}
