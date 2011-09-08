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
import javax.persistence.SequenceGenerator;

@Entity(name="tipo_status")
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_tipo_status")
public class TipoStatus extends Domain implements Serializable {

    private static final long serialVersionUID = 1781606124390394160L;
    
	//Constantes de tipos de status de cada requisição
    public static final Long AGUARDANDO	= new Long(1);
    public static final Long BLOQUEADO	= new Long(2);
    public static final Long CANCELADO	= new Long(3);
    public static final Long APROVADO	= new Long(4);
    public static final Long ENTEGUE 	= new Long(5);
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(length=25)
    private String nome;

    public TipoStatus() {
	}
    
    public TipoStatus(Long id) {
    	this.id = id; 
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
    
    @Override
    public String toString() {
    	return "[id: "+ id +" nome: "+ nome +"]"; 
    }	    
    
}
