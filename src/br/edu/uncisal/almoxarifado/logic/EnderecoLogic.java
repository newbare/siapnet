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

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.UfDao;
import br.edu.uncisal.almoxarifado.model.Municipio;
import br.edu.uncisal.almoxarifado.model.Uf;

@SuppressWarnings("unused")
@Component("endereco")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class })
public class EnderecoLogic {
    
    @Parameter
    private Uf uf = new Uf();
    
    @Parameter
    @Out
    private Municipio municipioAtual = new Municipio();
    
    @Out
    private List<Municipio> municipios;

    private UfDao ufDao;
    
    public EnderecoLogic(DaoFactory daoFactory) {
       ufDao = daoFactory.getUfDao();
    }
    
    public String loadMunicipios() {
    	this.municipios = ufDao.getById(uf.getId()).getMunicipios();
    	return "ok";
    }    

}