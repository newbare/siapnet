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
import br.edu.uncisal.almoxarifado.dao.TipoMedidaDao;
import br.edu.uncisal.almoxarifado.model.TipoMedida;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("tipoMedida")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class TipoMedidaLogic {

    @Parameter
    @Out
    private TipoMedida tipoMedida = new TipoMedida();
    @Out
    private List<TipoMedida> tipoMedidas;
    @Out
    private String message;
    @Out
    @Parameter
    private String criteria;
    
    private TipoMedidaDao tipoMedidaDao;

    public TipoMedidaLogic(DaoFactory daoFactory, Usuario authUser) {
    	tipoMedidaDao = daoFactory.getTipoMedidaDao();
    }

    public String formulario() {
        return "ok";
    }

    public String gravar() {
        tipoMedidaDao.save(tipoMedida);
        if (tipoMedida.getId() == null) {
        	message = "Unidade de Medida salva com sucesso!";
        } else {
        	message = "Unidade de Medida <strong>"+ tipoMedida.getNome() + "</strong> foi salvo(a) com sucesso!";
        }
        return "ok";
    }

    //Implementar método exists na validação.
    public void validateGravar(ValidationErrors errors) {
        if (tipoMedida.getNome() == null || tipoMedida.getNome().equals("")) {
            errors.add(new Message("aviso", "Um nome para a unidade de medida não foi definida."));
        }
        
        if (tipoMedida.getNome().length() > 25) {
        	errors.add(new Message("aviso", "O nome da Unidade de Medida não pode ultrapassar 25 caracteres!"));
        }
        
    }

    public String listAll() {
        tipoMedidas = tipoMedidaDao.listAll();
        return "ok";
    }

    public String remove() {
        tipoMedidaDao.remove(tipoMedida);
        return "ok";
    }

    public String get() {
        this.tipoMedida = tipoMedidaDao.getById(tipoMedida.getId());
        return "ok";
    }
    
    /** Retorna a lista de Unidades de Medidas conforme parâmetro passado da busca */
    public String buscar() {
    	tipoMedidas = tipoMedidaDao.buscar(criteria);
        return "ok";
    }
}
