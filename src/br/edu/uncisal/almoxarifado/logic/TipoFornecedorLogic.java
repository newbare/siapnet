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

import java.util.Collection;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.TipoFornecedorDao;
import br.edu.uncisal.almoxarifado.model.TipoFornecedor;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("tipoFornecedor")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class })
public class TipoFornecedorLogic {
	
	@Parameter
	@Out
	private TipoFornecedor tipoFornecedor = new TipoFornecedor();
	@Out
	private Collection<TipoFornecedor> tipoFornecedors;
	
	private TipoFornecedorDao tipoFornecedorDao;

	public TipoFornecedorLogic(DaoFactory daoFactory, Usuario authUser) {
		tipoFornecedorDao = daoFactory.getTipoFornecedorDao();
	}

	public String formulario() {
		return "ok";
	}

	public String gravar() {
		tipoFornecedorDao.save(tipoFornecedor);
		return "ok";
	}

	public String listAll() {
		tipoFornecedors = tipoFornecedorDao.listAll();
		return "ok";
	}

	public String remove() {
		tipoFornecedorDao.remove(tipoFornecedor);
		return "ok";
	}

	public String get() {
		this.tipoFornecedor = tipoFornecedorDao.getById(tipoFornecedor.getId());
		return "ok";
	}

}
