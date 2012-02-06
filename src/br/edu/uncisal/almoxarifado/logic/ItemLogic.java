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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.i18n.Message;
import org.vraptor.validator.ValidationErrors;

import br.edu.uncisal.almoxarifado.dao.AlmoxarifadoDao;
import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.GrupoDao;
import br.edu.uncisal.almoxarifado.dao.ItemDao;
import br.edu.uncisal.almoxarifado.dao.ItemEstoqueDao;
import br.edu.uncisal.almoxarifado.dao.TipoMedidaDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Grupo;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEstoque;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.TipoMedida;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("item")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class ItemLogic {

    @Parameter
    private Long codItemPrevalecente;
    
    @Parameter
    private Long codItemCondenado;
    
	@Parameter(create = true)
    @Out
    private Item item = new Item();
    @Out
    private List<Item> itens;
    @Out
    private List<ItemEstoque> listaEstoque;
    @Parameter
    @Out
    private ItemEstoque itemEstoque = new ItemEstoque();
    @Out
    private List<TipoMedida> tiposMedida;
    @Out
    private List<Grupo> grupos;
    @Out
    private boolean alteravel = false;    
    @Out
    private String message;
    @Out
    private String msgErro;
    
    //Informa o elemento para trabalhar com o autocomplete
    @Out
    @Parameter
    private String elemento;
    
    //Informar se a pesquisa de autocomplete vai ser feita no estoque ou no catálogo.
    @Out
    @Parameter
    private Boolean estoque;
    
    @Out
    @Parameter
    private String criteria;
    
    @Parameter
    private String term;
    
    @Out
    private String json;
    
    @Out
    private Boolean editavel = false;
    
    private Usuario authUser;    
    
    private ItemDao itemDao;
    private TipoMedidaDao tipoMedidaDao;
    private GrupoDao grupoDao;
    private ItemEstoqueDao itemEstoqueDao;
    private AlmoxarifadoDao almoxarifadoDao;

    public ItemLogic(DaoFactory daoFactory, Usuario authUser) {
    	this.authUser = authUser;
        
        itemDao = daoFactory.getItemDao();
        tipoMedidaDao = daoFactory.getTipoMedidaDao();
        grupoDao = daoFactory.getGrupoDao();
        itemEstoqueDao = daoFactory.getItemEstoqueDao();
        almoxarifadoDao = daoFactory.getAlmoxarifadoDao();
    }

    public String formulario() {
        tiposMedida = tipoMedidaDao.listAll();
        grupos = grupoDao.listAll();

        return "ok";
    }

    public String gravar() throws java.text.ParseException {
    	if(item.getId() == null)
    		itemDao.add(item);
    	else
    		itemDao.update(item);
        message = "Dados gravados com sucesso!";
        return "ok";
    }

    //TODO implementar método exist para não deixar criar itens com o mesmo nome.
    public void validateGravar(ValidationErrors errors) {
        if (item.getNome() == null || item.getNome() == "") {
            errors.add(new Message("aviso", "Um nome para o item não foi definido."));
        }

        if (item.getTipoMedidaEntrada() == null) {
            errors.add(new Message("aviso", "Um tipo de medida não foi definido."));
        }

        if (errors.size() > 0) {
            tiposMedida = tipoMedidaDao.listAll();
            grupos = grupoDao.listAll();
        }
    }

    public String listAll() {
    	if(!authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ALMOXARIFE))
    		editavel = true;
        itens = itemDao.listAll();
        return "ok";
    }

    public String listAllEstoqueBaixo() {
        listaEstoque = itemEstoqueDao.listAllEstoqueBaixo();
        itens = new ArrayList<Item>();
        for (ItemEstoque ie : listaEstoque) {
            itens.add(itemDao.getById(ie.getItem().getId()));
        }
        return "ok";
    }

    public String remove() {
        itemDao.remove(item);
        return "ok";
    }

    public String filtro() {
        tiposMedida = tipoMedidaDao.listAll();
        grupos = grupoDao.listAll();

        return "ok";
    }

    public String buscar() {
    	//Usuario u = uManager.get(authUser);
    	Perfil pAtual = authUser.getUsuarioDepartamentoAtivo().getPerfil();
    	if(pAtual.getId().equals(Perfil.ADMINISTRADOR_GERAL) || pAtual.getId().equals(Perfil.CATALOGADOR))
    		alteravel = true;   	
    	
        if (item.getMaterialPermanente() == null) {
            item.setMaterialPermanente(new Boolean(false));
        }
        itens = itemDao.buscar(item);
        return filtro();
    }

    public String get() throws ParseException {
        tiposMedida = tipoMedidaDao.listAll();
        grupos = grupoDao.listAll();
        item = itemDao.getById(item.getId());
        return "ok";
    }

    public String ver() throws ParseException {
        return get();
    }

    /** Retorna o item, usado para retornar o nome e id do item, na busca pelo id */
    public String verItem() {
        item = itemDao.getById(item.getId());
        return "ok";
    }
    
    /** Retorna o item, usado para retornar o nome e id do item, na busca pelo id */
    public String verItemEstoque() {
    	item = itemDao.getById(item.getId());
    	return "ok";
    }
    
    /** Retorna a lista de Itens conforme parâmetro passado da busca */
    public String buscarItens() {
    	if(!authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ALMOXARIFE))
    		editavel = true;
    	itens = itemDao.buscar(criteria);
        return "ok";
    }
    
    public String consolidarForm() {
    	return "ok";
    }
    
    //TODO Depois da consolidação atualizar o estoque do item em cada almoxarifado.
    public String consolidar() {
    	//Pegar todos os itens estoques separados por almoxarifados.
    	Collection<Almoxarifado> almoxarifados = almoxarifadoDao.listAll();
    	for (Almoxarifado a : almoxarifados) {
    		ItemEstoque ieCondenado = null;
    		ItemEstoque iePrevalecente = null;
    		 ieCondenado = itemEstoqueDao.findByExample(new Item(codItemCondenado), a);  		
       		 iePrevalecente = itemEstoqueDao.findByExample(new Item(codItemPrevalecente), a);
       		
       		if(ieCondenado != null) {
       			if(iePrevalecente == null) {
       				ieCondenado.setItem(new Item(codItemPrevalecente));
       				itemEstoqueDao.save(ieCondenado);
       			} else {
       				iePrevalecente.setEstoque(iePrevalecente.getEstoque().add(ieCondenado.getEstoque()));
       				itemEstoqueDao.save(iePrevalecente);
       				itemEstoqueDao.remove(ieCondenado);
       			}       			
       		}    		
		}  	    	
    	itemDao.consolidar(codItemPrevalecente, codItemCondenado);
    	message = "Item consolidado com sucesso!";
    	return "ok";
    }
    
    public String loadItens(Almoxarifado a) {    	
    	loadItensJson(itemDao.loadItensAutoComplete(term, a));    	
    	return "ok";
    }    
   
    //Gera o json dos itens para o autocomplete.
    private void loadItensJson(List<Item> itens) {
    	json = "[";
    	for (int i = 0; i < itens.size(); i++) {
    		if(i == 0)
    			json += "{\"id\" : \"" + itens.get(i).getId() + "\", \"value\" : \"" + itens.get(i).getNome() + "\"}";
    		else {
    			json += ",{\"id\" : \"" + itens.get(i).getId() + "\", \"value\" : \"" + itens.get(i).getNome() + "\"}";
    		}
		}
    	json += "]";    	
    }
    
    
}