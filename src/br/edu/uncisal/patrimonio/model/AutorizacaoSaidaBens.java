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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Domain;
import br.edu.uncisal.almoxarifado.model.Usuario;

/**
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@Table(name = "autorizacao_saida_bens")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_autorizacao_saida")
public class AutorizacaoSaidaBens extends Domain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    /** Servidor autorizado a sair com os bens */
    @ManyToOne
    private Usuario servidorAutorizado;
    /** Setor responsável pela saida */
    @ManyToOne
    private Departamento setorResponsavel;
    /** Bens autorizado a sair */
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "autorizacao_patrimonio", joinColumns = {@JoinColumn(name = "autorizacao_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "patrimonio_id", referencedColumnName = "id")})
    private List<Patrimonio> bens;
    /** Data de geração do documento   */
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    /** Motivo da saída dos bens*/
    @Column(name="motivo_saida", length=100)
    private String motivoSaida;

    public Long getId() {
        return id;
    }

    public List<Patrimonio> getBens() {
        return bens;
    }

    public void setBens(List<Patrimonio> bens) {
        this.bens = bens;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getServidorAutorizado() {
        return servidorAutorizado;
    }

    public void setServidorAutorizado(Usuario servidorAutorizado) {
        this.servidorAutorizado = servidorAutorizado;
    }

    public Departamento getSetorResponsavel() {
        return setorResponsavel;
    }

    public void setSetorResponsavel(Departamento setorResponsavel) {
        this.setorResponsavel = setorResponsavel;
    }

    public String getMotivoSaida() {
        return motivoSaida;
    }

    public void setMotivoSaida(String motivoSaida) {
        this.motivoSaida = motivoSaida;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AutorizacaoSaidaBens)) {
            return false;
        }
        AutorizacaoSaidaBens other = (AutorizacaoSaidaBens) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uncisal.patrimonio.model.AutorizacaoSaidaBens[id=" + id + "]";
    }
}
