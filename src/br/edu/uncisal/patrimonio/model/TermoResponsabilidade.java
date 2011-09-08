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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Domain;

/**
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@Table(name = "termo_responsabilidade")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_termo_responsabilidade")
public class TermoResponsabilidade extends Domain implements Serializable {

	private static final long serialVersionUID = -7494160550749871727L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /** Setor de Origem */
    @ManyToOne
    private Departamento departamentoOrigem;
    /** Setor de Destino */
    @ManyToOne
    private Departamento departamentoDestino;
    /** Data de geração do documento   */
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    /** Numero do termo e ano - Num. 999/9999 */
    private Integer numero;
    private Integer ano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Departamento getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(Departamento departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }

    public Departamento getDepartamentoOrigem() {
        return departamentoOrigem;
    }

    public void setDepartamentoOrigem(Departamento departamentoOrigem) {
        this.departamentoOrigem = departamentoOrigem;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TermoResponsabilidade)) {
            return false;
        }
        TermoResponsabilidade other = (TermoResponsabilidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uncisal.patrimonio.model.TermoResponsabilidade[id=" + id + "]";
    }
}
