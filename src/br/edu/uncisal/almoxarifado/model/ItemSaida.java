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
import java.math.MathContext;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Igor Cavalcante
 */
@Entity(name = "item_saida")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_item_saida")
public class ItemSaida extends Domain implements Serializable {

    private static final long serialVersionUID = -7770300923431094786L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    
    private BigDecimal quantidade;
    
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;
    
    @ManyToOne
    private Item item;
    
    @Transient
    private BigDecimal valorConsumo;
    
    /**
     * Percentual daquele item. Serve para o relatório de curva ABC.
     */
    @Transient
    private BigDecimal percentualAcumulado;

    /**
     * Percentual acumulado até este item. Serve para o relatório de curva ABC.
     */
    @Transient
    private BigDecimal valorAcumulado;
    
    public ItemSaida() {
    }
  
    public ItemSaida(Long itemId, BigDecimal qtd) {
    	this();
    	this.item = new Item(itemId);
    	this.quantidade = qtd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public BigDecimal getValorConsumo() {
		return valorConsumo;
	}
    
    public void setValorConsumo(BigDecimal valorConsumo) {
		this.valorConsumo = valorConsumo;
	}
    
    public void setPercentualAcumulado(BigDecimal percentualAcumulado) {
		this.percentualAcumulado = percentualAcumulado;
	}
    
    public BigDecimal getPercentualAcumulado() {
		return percentualAcumulado;
	}
    
    public void setValorAcumulado(BigDecimal valorAcumulado) {
		this.valorAcumulado = valorAcumulado;
	}
    
    public BigDecimal getValorAcumulado() {
		return valorAcumulado;
	}

    @Override
    public String toString() {
        return "[id: " + id + " quantidade: " + quantidade + " item: " + item + "]";
    }
    
    public List<ItemSaida> loadCurvaABC(Date dataInicio, Date dataFim, List<ItemSaida> itensSaida) {
    	BigDecimal montante = new BigDecimal("0");
    	
    	for (ItemSaida i : itensSaida) {
			montante = montante.add(i.getValorConsumo());
			i.setValorAcumulado(new BigDecimal(montante.toString()));
		}
    	
    	for (ItemSaida i : itensSaida) {    		
			i.setPercentualAcumulado(i.getValorAcumulado().divide(montante, MathContext.DECIMAL32).multiply(new BigDecimal("100.00")));
		}
    	
    	return itensSaida;
    }
    
}
