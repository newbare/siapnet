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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uncisal.patrimonio.model.Patrimonio;

/**
 * Item de entrada no estoque de um almoxarifado
 * @author Igor Cavalcante
 * @author Augusto Oliveira
 * TODO Verificar itens que estão perto de vencer a validade e quais estão fora da validade.
 */
@Entity
@Table(name = "item_entrada")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_item_entrada")
public class ItemEntrada extends Domain implements Serializable {

    private static final long serialVersionUID = 1025063412892715342L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(name="valor_unitario")
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    @Column(length=25)
    private String lote;
    @Temporal(TemporalType.DATE)
    private Date validade;
    @ManyToOne
    private Item item;
    
    /**
     * Caso este item for um bem permanente 
     */
    private Boolean tombado = new Boolean(false);
     
    @OneToMany(mappedBy = "itemEntrada")
    private List<Patrimonio> patrimonios;

    public ItemEntrada() {
    	super();
    }

    public ItemEntrada(Item item) {
    	this();
        this.item = item;
    }
    
    public ItemEntrada(Item item, BigDecimal quantidade) {
        this(item);
        this.quantidade = quantidade;        
    }
    
    public ItemEntrada(Item item, BigDecimal quantidade, BigDecimal valorUnitario) {
    	this(item, quantidade);
    	setValorUnitario(valorUnitario);
    }

    /** Retorna true se a validade estiver vencida. E false caso contrário.
     */
    public Boolean validadeVencida() {
        return validade.after(new Date());
    }

    /** Retorna True se a validade estiver vencida.
     */
    public Integer diasParaVencerValidade() {
        if (this.validadeVencida()) {
            return null;
        }

        Long diasFaltando = null;
        Calendar cal = Calendar.getInstance();
        long hoje = cal.getTimeInMillis();
        long val = validade.getTime();
        long intervalo = hoje - val;
        diasFaltando = new Long((intervalo / 1000) * 60 * 60 * 24);
        return new Integer(diasFaltando.intValue());
    }
    /* Métodos GETs e SETs */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Boolean getTombado() {
        return tombado;
    }

    public void setTombado(Boolean tombado) {
        this.tombado = tombado;
    }

    public void setPatrimonios(List<Patrimonio> patrimonios) {
        this.patrimonios = patrimonios;
    }

    public List<Patrimonio> getPatrimonios() {
        return patrimonios;
    }
   
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
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
		
		ItemEntrada other = (ItemEntrada) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
			return false;
		
		return true;
	}

	public String toString() {
    	return
    		"[id: "+ id +" item: "+ item +" valor unitário: "+ valorUnitario + " quantidade: " + quantidade 
    		+" lote: "+ lote +" validade: "+ validade +" tombado: "+ tombado +" lote: "+ lote +"]";
    }
    
}