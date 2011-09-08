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

package br.edu.uncisal.almoxarifado.logic;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.i18n.Message;
import org.vraptor.validator.ValidationErrors;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.GrupoDao;
import br.edu.uncisal.almoxarifado.model.Grupo;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("grupo")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class })
public class GrupoLogic {
	@Parameter
	@Out
	private Grupo grupo = new Grupo();
	@Out
	private List<Grupo> grupos;
    @Out
    private String message;
    @Out
    @Parameter
    private String criteria;
    
    private GrupoDao grupoDao;

	public GrupoLogic(DaoFactory daoFactory, Usuario authUser) {
		grupoDao = daoFactory.getGrupoDao();
	}

	public String formulario() {
		return "ok";
	}

	public String gravar() {
		grupoDao.save(grupo);
        if (grupo.getId() == null) {
        	message = "Unidade de Medida salva com sucesso!";
        } else {
        	message = "O Grupo '<strong>"+ grupo.getNome() + "</strong>' foi atualizado com sucesso!";
        }
		return "ok";
	}
        
    //TODO Implementar exist para não deixar grupos repetidos.
	public void validateGravar(ValidationErrors errors) {
        if (grupo.getNome() == null || grupo.getNome().equals("")) {
            errors.add(new Message("aviso", "Um nome para o grupo não foi definido."));
        }
    }
        
	public String listAll() {
		grupos = grupoDao.listAll();
		return "ok";
	}

	public String remove() {
		grupoDao.remove(grupo);
		return "ok";
	}

	public String get() {
		this.grupo = grupoDao.getById(grupo.getId());
		return "ok";
	}

    /** Retorna a lista de Grupos conforme parâmetro passado da busca */
    public String buscar() {
    	grupos = grupoDao.buscar(criteria);
        return "ok";
    }
}
