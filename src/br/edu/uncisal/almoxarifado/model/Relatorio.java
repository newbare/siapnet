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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * @author Augusto Oliveira
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name="SEQ_CLOG", sequenceName="sq_relatorio")
public class Relatorio extends Domain implements Serializable {

	private static final long serialVersionUID = -7699435153556997409L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    /** Título do Relatório */
	@Column(length=100)
    private String titulo;
    /** nome do arquivo jasper */
	@Column(length=100)
    private String arquivoJasper;
    /** lista de parametros do relatório */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "relatorio_id")
    private List<ParametroRelatorio> parametros;

    public String getArquivoJasper() {
        return arquivoJasper;
    }

    public void setArquivoJasper(String arquivoJasper) {
        this.arquivoJasper = arquivoJasper;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<ParametroRelatorio> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroRelatorio> parametros) {
        this.parametros = parametros;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Relatorio)) {
            return false;
        }
        Relatorio other = (Relatorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "[id: "+ id +" titulo: "+ titulo +" arquivo: "+ arquivoJasper +" parâmetros: "+ parametros +"]";
	}
	
}
