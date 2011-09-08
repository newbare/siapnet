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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.i18n.Message;
import org.vraptor.scope.ScopeType;
import org.vraptor.validator.ValidationErrors;

import br.edu.uncisal.almoxarifado.dao.AlmoxarifadoDao;
import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.GrupoDao;
import br.edu.uncisal.almoxarifado.dao.ItemEstoqueDao;
import br.edu.uncisal.almoxarifado.dao.TipoMedidaDao;
import br.edu.uncisal.almoxarifado.dao.UnidadeDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Grupo;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEstoque;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.TipoMedida;
import br.edu.uncisal.almoxarifado.model.Unidade;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("itemEstoque")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class ItemEstoqueLogic {

	
	/**
	 * Utilizando Float porque o vraptor não tem um conversor para BigDecimal
	 */
	@Parameter
	private Float estoque;
	
    @Parameter(create = true)
    @Out
    private ItemEstoque itemEstoque = new ItemEstoque();
    @Parameter
    private String q;
    @Out
    private List<ItemEstoque> itensEstoque;
    @Out
    private Collection<Almoxarifado> almoxarifados;
    @Out
    private List<Item> itens;
    private Unidade unidade;
    private Perfil perfil;
    private Almoxarifado almoxarifado;

    @Out
    @Parameter
    private String criteria;
    @Parameter
    @In(scope = ScopeType.SESSION)
    @Out(scope = ScopeType.SESSION)
    private Usuario authUser;
    @Out
    private List<TipoMedida> tiposMedida;
    @Out
    private List<Grupo> grupos;
    
    private AlmoxarifadoDao almoxarifadoDao;
    private GrupoDao grupoDao;
    private ItemEstoqueDao itemEstoqueDao;
    private TipoMedidaDao tipoMedidaDao;
    private UnidadeDao unidadeDao;
    
    public ItemEstoqueLogic(DaoFactory daoFactory, Usuario authUser) {
        almoxarifadoDao = daoFactory.getAlmoxarifadoDao();
        grupoDao = daoFactory.getGrupoDao();
        itemEstoqueDao = daoFactory.getItemEstoqueDao();    	
        tipoMedidaDao = daoFactory.getTipoMedidaDao();
        unidadeDao = daoFactory.getUnidadeDao();
        
        unidade = unidadeDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade().getId());
        almoxarifado = authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado();
        perfil = authUser.getUsuarioDepartamentoAtivo().getPerfil();
    }

    public String formulario() {
        if (perfil.getId().equals(new Long(1))) {
            almoxarifados = almoxarifadoDao.listAll();
        } else {
            almoxarifados = almoxarifadoDao.listByUnidade(unidade);
        }
        return "ok";
    }

    public String gravar() {
    	itemEstoque.setEstoque(new BigDecimal(estoque));
        itemEstoqueDao.save(itemEstoque);
        return "ok";
    }

    //TODO implementar método exists pra ver se já existe algum item estoque antes de ser criado.
    public void validateGravar(ValidationErrors errors) {
        if (itemEstoque.getItem().getId() == null || itemEstoque.getItem().getId().equals(new Long("0"))) {
            errors.add(new Message("aviso", "Um item precisa ser definido."));
        }

        if (itemEstoque.getEstoqueMaximo() == null || itemEstoque.getEstoqueMaximo().equals("")) {
            errors.add(new Message("aviso", "Uma quantidade de estoque máxima precisa ser definda."));
        }

        if (itemEstoque.getEstoqueMinimo() == null || itemEstoque.getEstoqueMinimo().equals("")) {
            errors.add(new Message("aviso", "Uma quantidade de estoque mínima precisa ser definda."));
        }

        if (itemEstoque.getEstoqueMinimo() > itemEstoque.getEstoqueMaximo()) {
        	errors.add(new Message("aviso", "A quantidade do Estoque MÍNIMO não pode ser superior ao Estoque MÁXIMO!"));
        }

        if (errors.size() > 0) {
            almoxarifados = almoxarifadoDao.listAll();
        }
    }

    public String listAll() {
        itensEstoque = itemEstoqueDao.listByAlmoxarifado(almoxarifado);
        return "ok";
    }

    /** Método de filtro de lista de itens em estoque */
    public String buscar() {
        itemEstoque.setAlmoxarifado(almoxarifado);
        itensEstoque = itemEstoqueDao.buscar(itemEstoque);
        return "ok";
    }

    /** Método que carrega o formulário de filtro de lista de itens em estoque */
    public String filtro() {
        tiposMedida = tipoMedidaDao.listAll();
        grupos = grupoDao.listAll();
        return "ok";
    }

    public String get() {
        almoxarifados = almoxarifadoDao.listAll();
        itemEstoque = itemEstoqueDao.getById(itemEstoque.getId());
        return "ok";
    }
  
    /** Retorna a lista de Item(ns) em Estoque conforme parâmetro passado da busca */
    public String buscarItem() {
    	itensEstoque = itemEstoqueDao.buscarItem(criteria, authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        return "ok";
    }
}