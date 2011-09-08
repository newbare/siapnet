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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Augusto Oliveira
 */
@Entity(name="parametro_relatorio")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_parametro_relatorio")
public class ParametroRelatorio extends Domain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    /** nome do parâmetro */
    @Column(length=100)
    private String nome;
    /** Tipo(java) do parâmetro
     * Exemplos: java.lang.String, java.lang.Integer
     */
    @Column(length=50)
    private String tipo;
    @Column(length=100)
    private String titulo;

    /* Relatório do parâmetro */
    @ManyToOne
    private Relatorio relatorio;

    public ParametroRelatorio() {
    }

    public ParametroRelatorio(String nome) {
        this.nome = nome;
    }

    public ParametroRelatorio(String nome, Relatorio relatorio) {
        this.nome = nome;
        this.relatorio = relatorio;
    }

    public ParametroRelatorio(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public ParametroRelatorio(String nome, String tipo, Relatorio relatorio) {
        this.nome = nome;
        this.tipo = tipo;
        this.relatorio = relatorio;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParametroRelatorio)) {
            return false;
        }
        ParametroRelatorio other = (ParametroRelatorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "[id: "+ id +" titulo: "+ titulo +" nome: "+ nome +" tipo: "+ tipo +"]";
	}
}
