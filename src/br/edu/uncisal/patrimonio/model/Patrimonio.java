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

package br.edu.uncisal.patrimonio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Domain;
import br.edu.uncisal.almoxarifado.model.Fornecedor;
import br.edu.uncisal.almoxarifado.model.ItemEntrada;

/**
 * Representa um bem permanente.
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_patrimonio")
public class Patrimonio extends Domain implements Serializable {

    private static final long serialVersionUID = -4782096976250572547L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;

    /** Número de Tombamento do Patrimônio */
    private Integer numero;

    /** Item Permanente (bem tombado pelo patrimônio) */
    @ManyToOne
    @JoinColumn(name="item_entrada_id")
    private ItemEntrada itemEntrada;

    /** Departamento onde se localiza atualmente o bem */
    @ManyToOne
    private Departamento departamento;

    /** Estado de Depreciação do bem */
    @ManyToOne
    @JoinColumn(name = "estado_depreciacao_id")
    private EstadoDepreciacao estadoDepreciacao;

    /** localização específica do bem dentro do setor(departamento) */
    @Column(length=50)
    private String localizacao;

    /** Modelo do bem */
    @Column(length=50)
    private String modelo;

    /** Número de série do bem */
    @Column(name="numero_serie", length=100)
    private String numeroSerie;

    /** Marca do bem */
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    /** Observação adicional sobre o bem */
    private String observacao;

    /** Data de tombamento do bem */
    @Column(name = "data_tombamento")
    @Temporal(TemporalType.DATE)
    private Date dataTombamento;

    /** Data final da garantia */
    @Temporal(TemporalType.DATE)
    private Date garantia;

    /** Lista de assistencia Tecnica para determinado patrimonio */
    @JoinTable(name = "assistencia_tecnica_patrimonio", joinColumns = {@JoinColumn(name = "patrimonio_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "fornecedor_id", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Fornecedor> assistenciasTecnica;

    /** Lista a movimentação de transferência de setores para determinado patrimonio */
    @JoinTable(name = "transferencia_bens", joinColumns = {@JoinColumn(name = "patrimonio_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "transferencia_id", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<TransferenciaPatrimonio> movimentacaoBem;

    public Patrimonio() {
    	super();
    }

    public Patrimonio(Long id) {
    	this();
    	setId(id);
    }
    
    public List<TransferenciaPatrimonio> getMovimentacaoBem() {
		return movimentacaoBem;
	}

	public void setMovimentacaoBem(List<TransferenciaPatrimonio> movimentacaoBem) {
		this.movimentacaoBem = movimentacaoBem;
	}

	public Date getDataTombamento() {
        return dataTombamento;
    }

    public void setDataTombamento(Date dataTombamento) {
        this.dataTombamento = dataTombamento;
    }

    public EstadoDepreciacao getEstadoDepreciacao() {
        return estadoDepreciacao;
    }

    public void setEstadoDepreciacao(EstadoDepreciacao estadoDepreciacao) {
        this.estadoDepreciacao = estadoDepreciacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Fornecedor> getAssistenciasTecnica() {
        return assistenciasTecnica;
    }

    public void setAssistenciasTecnica(List<Fornecedor> assistenciasTecnica) {
        this.assistenciasTecnica = assistenciasTecnica;
    }

    public Date getGarantia() {
        return garantia;
    }

    public void setGarantia(Date garantia) {
        this.garantia = garantia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public ItemEntrada getItemEntrada() {
        return itemEntrada;
    }

    public void setItemEntrada(ItemEntrada itemEntrada) {
        this.itemEntrada = itemEntrada;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

   @Override
    public boolean equals(Object object) {
        if (!(object instanceof Patrimonio)) {
            return false;
        }
        Patrimonio other = (Patrimonio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

   @Override
   public String toString() {
	   return
	   	"[id: "+ id +" número: "+ numero +" item de entrada: "+ itemEntrada + " departamento: " + departamento
	   	+" localização: "+ localizacao +" estado de depreciação: " + estadoDepreciacao +" modelo: " + modelo
	   	+" marca: " + marca +" número de série: " + numeroSerie +" data de tombamento: " + dataTombamento
	   	+" garantia: " + garantia +" obervacao: " + observacao +" assistência técnica: " + assistenciasTecnica
	   	+" movimentação: " + movimentacaoBem +"]";
   }
   
}
