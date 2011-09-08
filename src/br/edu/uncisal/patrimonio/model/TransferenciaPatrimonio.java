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
import br.edu.uncisal.almoxarifado.model.Usuario;

/**
 * Responsável por realizar uma transferência de patrimônio
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity(name = "transferencia_patrimonio")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_transferencia_patrimonio")
public class TransferenciaPatrimonio extends Domain implements Serializable {

    private static final long serialVersionUID = -4782096976250572547L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "transferencia_bens", joinColumns = {@JoinColumn(name = "transferencia_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "patrimonio_id", referencedColumnName = "id")})
    private List<Patrimonio> bens;

    /** Departamento onde se localizava atualmente o bem */
    @ManyToOne
    private Departamento setorOrigem;
    
    /** Departamento onde se localiza atualmente o bem */
    @ManyToOne
    private Departamento setorDestino;
    
    /** Usuário responsábel pela transferência de bem */    
    @ManyToOne
    private Usuario responsavelEnvio;
    
    /** Usuário responsábel pela transferência de bem */    
    @ManyToOne
    private Usuario responsavelRecebimento;

    @Column(name = "data_saida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSaida;
    
    private String motivo;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TransferenciaPatrimonio)) {
            return false;
        }
        TransferenciaPatrimonio other = (TransferenciaPatrimonio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uncisal.almoxarifado.model.transferenciaPatrimonio[id=" + id + "]";
    }

    public List<Patrimonio> getBens() {
        return bens;
    }

    public void setBens(List<Patrimonio> bens) {
        this.bens = bens;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Usuario getResponsavelEnvio() {
        return responsavelEnvio;
    }

    public void setResponsavelEnvio(Usuario responsavelEnvio) {
        this.responsavelEnvio = responsavelEnvio;
    }

    public Usuario getResponsavelRecebimento() {
        return responsavelRecebimento;
    }

    public void setResponsavelRecebimento(Usuario responsavelRecebimento) {
        this.responsavelRecebimento = responsavelRecebimento;
    }

    public Departamento getSetorOrigem() {
        return setorOrigem;
    }

    public void setSetorOrigem(Departamento setorOrigem) {
        this.setorOrigem = setorOrigem;
    }

    public Departamento getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(Departamento departamento) {
        this.setorDestino = departamento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
}
