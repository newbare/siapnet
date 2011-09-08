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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.edu.uncisal.almoxarifado.taglib.Treeable;

/**
 * Recurso a ser acessado por um usuário em um perfil
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_recurso")
public class Recurso extends Domain implements Serializable, Treeable {

    private static final long serialVersionUID = -5049948424198784301L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(length = 50)
    private String nome;
    @Column(length = 75)
    private String uri;
    @ManyToOne
    @JoinColumn(name = "pai_id")
    private Recurso pai;
    @OneToMany()
    @JoinColumn(name = "pai_id")
    private List<Recurso> filhos;

    public Recurso() {
    }

    public Recurso(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPai(Recurso pai) {
        this.pai = pai;
    }

    @Override
    public Recurso getPai() {
        return pai;
    }

    public void setFilhos(List<Recurso> filhos) {
        this.filhos = filhos;
    }

    public List<Recurso> getFilhos() {
        return filhos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getFolhas() {
        List folhas = filhos;
        return folhas;
    }

    @Override
    public String toString() {
        if (nome == null || nome.equals("")) {
            return uri;
        } else {
            return nome;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.hashCode() == this.hashCode()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id.toString());
    }
}
