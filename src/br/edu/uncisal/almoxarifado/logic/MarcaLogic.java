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
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.patrimonio.dao.MarcaDao;
import br.edu.uncisal.patrimonio.model.Marca;

@SuppressWarnings("unused")
@Component("marca")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class MarcaLogic {
    @Parameter
    @Out
    private Marca marca = new Marca();
    @Out
    private List<Marca> marcas;
    @Out
    private String message;
    @Out
    @Parameter
    private String criteria;
    
    private MarcaDao marcaDao;
    
    public MarcaLogic(DaoFactory daoFactory, Usuario authUser) {
        marcaDao = daoFactory.getMarcaDao();
    }

    public String formulario() {
        return "ok";
    }

    /** Método que inicializa os objetos para o formulário, carregado no formModal */
    public String formModal() {
        return formulario();
    }

    public String gravar() {
        marcaDao.save(marca);
        if (marca.getId() == null) {
        	message = "A Marca '<strong>"+ marca.getNome() + "</strong>' foi salva com sucesso!";
        } else {
        	message = "A Marca '<strong>"+ marca.getNome() + "</strong>' foi atualizada com sucesso!";
        }
        return "ok";
    }

     /** Método que grava o objeto do formulário, carregado no formModal */
    public String gravarModal() {
        return gravar();
    }

    //FIXME implementar método exist para não gravar marcas repetidas.
    public void validateGravar(ValidationErrors errors) {
        if (marca.getNome() == null || marca.getNome().equals("")) {
            errors.add(new Message("aviso", "Um nome para a marca não foi definido."));
        }

        /*if (manager.exist(marca) && marca.getId() == null) {
            errors.add(new Message("aviso", "Já existe uma marca cadastrada com este nome."));
        }*/
    }

    public String listAll() {
        marcas = marcaDao.listAll();
        return "ok";
    }
    
    public String loadMarcas() {
        marcas = marcaDao.listAll();
        return "ok";
    }

    public String remove() {
        marcaDao.remove(marca);
        return "ok";
    }

    public String get() {
        this.marca = marcaDao.getById(marca.getId());
        return "ok";
    }

    /** Retorna a lista de Marcas conforme parâmetro passado da busca */
    public String buscar() {
    	marcas = marcaDao.buscar(criteria);
        return "ok";
    }

}