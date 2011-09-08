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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.edu.uncisal.almoxarifado.util.EstoqueException;

/**
 * Cuida da quantidade de intens estocavel em um determinado almoxarifado.
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 * @see Item
 * @see Almoxarifado
 */
@Entity
@Table(name = "item_estoque")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_item_estoque")
public class ItemEstoque extends Domain implements Serializable {

	private static final long serialVersionUID = 6005194281392653819L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
	
    @ManyToOne
    private Item item;
    
    @ManyToOne
    private Almoxarifado almoxarifado;
    
    private BigDecimal estoque;
    
    @Column(name="estoque_minimo")
    private Integer estoqueMinimo;
    
    @Column(name="estoque_maximo")
    private Integer estoqueMaximo;
    
    @Column(name="consumo_medio")
    private Integer consumoMedio;
    
    @Column(name="custo_medio")
    private BigDecimal custoMedio;
    
    @Column(name = "periodo_dias_consumo_medio")
    private Integer periodoEmDiasConsumoMedio;
    
    @Column(length=50)
    private String localizacao;
    
    @Column(length=10)
    private String patrimonio;

    public ItemEstoque() {
    	setEstoqueMaximo(0);
    	setEstoqueMinimo(0);
    }

    public ItemEstoque(Item item, Almoxarifado almoxarifado) {
    	this();
        this.item = item;
        this.almoxarifado = almoxarifado;
    }
    
    public ItemEstoque(Item item, Almoxarifado almoxarifado, BigDecimal quantidade) {
    	this(item, almoxarifado);
    	this.estoque = quantidade;
    }

    public Almoxarifado getAlmoxarifado() {
        return almoxarifado;
    }

    public void setAlmoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
    }

    public Integer getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(Integer consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public BigDecimal getEstoque() {
        return estoque;
    }

    public void setEstoque(BigDecimal estoque) {
        this.estoque = estoque;
    }

    public Integer getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void setEstoqueMaximo(Integer estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
    public BigDecimal getCustoMedio() {
        return custoMedio;
    }

    public void setCustoMedio(BigDecimal custoMedio) {
        this.custoMedio = custoMedio;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPeriodoEmDiasConsumoMedio(Integer periodoEmDiasConsumoMedio) {
        this.periodoEmDiasConsumoMedio = periodoEmDiasConsumoMedio;
    }

    public Integer getPeriodoEmDiasConsumoMedio() {
        return periodoEmDiasConsumoMedio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemEstoque)) {
            return false;
        }
        ItemEstoque other = (ItemEstoque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return
    		"[id: "+ id +" item: "+ item +" almoxarifado: "+ almoxarifado + " estoque: " + estoque 
    		+" estoque máximo: " + estoqueMaximo +" estoque mínimo: " + estoqueMinimo +" consumo médio: " + consumoMedio 
    		+" intervalo de consumo: " + periodoEmDiasConsumoMedio +" localizacao: " + localizacao +" patrimônio: " + patrimonio +"]";
    }
    
    /**
     * Remove itens do estoque
     * @param q
     * @throws InvalidParameterException caso a quantidade final seja menor que zero.
     */
    public void remEstoque(BigDecimal q) throws EstoqueException {
    	if(q.compareTo(new BigDecimal("0")) < 0 || estoque.subtract(q).compareTo(new BigDecimal("0")) < 0) {
    		throw new EstoqueException("Estoque insuficiente para o item: " + getItem().getNome());
    	}
    	
    	estoque = estoque.subtract(q);
    }
    
    /**
     * Adiciona itens ao estoque
     * @throws InvalidParameterException Caso a quantidade informada seja menor ou igual a zero
     */
    public void addEstoque(BigDecimal q) throws EstoqueException {
    	if(q.compareTo(new BigDecimal("0")) <= 0) {
    		throw new EstoqueException("Não é possível inserir uma quantidade menor ou igual a zero para o item: "
    				+ getItem().getNome()
    		);
    	}
    	
    	estoque = estoque.add(q);
    }
    
    /**
     * @deprecated
     * @param itensEstoque
     * @param i
     * @param a
     * @return
     */
    public ItemEstoque getByItemEAlmoxarifado(List<ItemEstoque> itensEstoque, Item i, Almoxarifado a) {
    	for (ItemEstoque itemEstoque : itensEstoque) {
			if(itemEstoque.getItem().equals(i) && itemEstoque.getAlmoxarifado().equals(a))
				return itemEstoque;
		}
    	return new ItemEstoque(i, a, new BigDecimal("0"));
    }
    
    /**
     * Faz atualização dos valores de estoque
     * @param saidas
     * @param entradas
     * @param ie
     */
    public void atualizaEstoque(Collection<Requisicao> saidas, Collection<NotaEntrada> entradas) {
    	List<Datable> movimentos = new ArrayList<Datable>();		
		if(saidas != null)			
			movimentos.addAll(saidas);
		if(entradas != null)
			movimentos.addAll(entradas);
		
		Collections.sort(movimentos, new MovimentoComparator());
		
		BigDecimal estoqueAtual = new BigDecimal("0");
		BigDecimal cmAtual = new BigDecimal("0");
		for (Datable movimento : movimentos) {
			if(movimento instanceof Requisicao) {
				Requisicao rMov = (Requisicao) movimento;
				for (ItemSaida isMov: rMov.getItensEnviados()) {
					if(isMov.getItem().getId().equals(getItem().getId())) {
						isMov.setValorUnitario(cmAtual);
						estoqueAtual = estoqueAtual.subtract(isMov.getQuantidade());
					}
				}
			} else {
				NotaEntrada neMov = (NotaEntrada) movimento;
				for (ItemEntrada ieMov : neMov.getItensEntrada()) {
					if(ieMov.getItem().equals(getItem())) {
						BigDecimal op1 = estoqueAtual.multiply(cmAtual);
						BigDecimal op2 = ieMov.getQuantidade().multiply(ieMov.getValorUnitario());
						BigDecimal div = ieMov.getQuantidade().add(estoqueAtual);								
						cmAtual = op1.add(op2);
						if(div.equals(new BigDecimal("0")))
							cmAtual = new BigDecimal("0");
						else
							cmAtual = cmAtual.divide(div, MathContext.DECIMAL32);
						
						estoqueAtual = estoqueAtual.add(ieMov.getQuantidade());
					}
				}        				
			}        				
		}
		setEstoque(estoqueAtual);
		setCustoMedio(cmAtual);
    }
    
}