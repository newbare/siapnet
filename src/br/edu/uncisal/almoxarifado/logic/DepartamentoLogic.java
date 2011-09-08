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

import java.util.ArrayList;
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
import br.edu.uncisal.almoxarifado.dao.DepartamentoDao;
import br.edu.uncisal.almoxarifado.dao.OrgaoDao;
import br.edu.uncisal.almoxarifado.dao.UnidadeDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Municipio;
import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Uf;
import br.edu.uncisal.almoxarifado.model.Unidade;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("departamento")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class DepartamentoLogic {

	@Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private Departamento departamento = new Departamento();
    
    @Out
    private List<Departamento> departamentos;
    
    @Out
    private List<Uf> ufs;
    
    @Out
    private List<Municipio> municipios;
    
    @Out
    @Parameter
    private Unidade unidade;
    
    @Out
    private List<Almoxarifado> almoxarifados;    
    
    @Out
    private List<Unidade> unidades;
    
    @Out
    private String message;
    
    @Out
    @Parameter
    private String criteria;
    
    @Out
    private List<Orgao> orgaos;
    
    private Usuario authUser;    
    private Perfil perfil;  
    
    private DepartamentoDao dao;
    private AlmoxarifadoDao almoxarifadoDao;
    private UnidadeDao unidadeDao;
    private OrgaoDao orgaoDao;

    public DepartamentoLogic(DaoFactory daoFactory, Usuario authUser) {
    	this.authUser = authUser;   	
       
        dao = daoFactory.getDepartamentoDao();
        almoxarifadoDao = daoFactory.getAlmoxarifadoDao();
        unidadeDao = daoFactory.getUnidadeDao();
        orgaoDao = daoFactory.getOrgaoDao();
        
        Perfil p = authUser.getUsuarioDepartamentoAtivo().getPerfil(); //Perfil do usuário.
        
        if(p.getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
        	orgaos = orgaoDao.listAtivados(); 
        } else {
        	orgaos = new ArrayList<Orgao>();
        	orgaos.add(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade().getOrgao());
        }
        
        if(p.equals(Perfil.ADMINISTRADOR_LOCAL)) {
        	unidades = unidadeDao.listByOrgao(orgaos.get(0));
        }
        
        if(!p.getId().equals(Perfil.ADMINISTRADOR_GERAL) && !p.getId().equals(Perfil.ADMINISTRADOR_LOCAL)) {
        	unidades = new ArrayList<Unidade>();
        	unidades.add(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        	almoxarifados = almoxarifadoDao.listByUnidade(unidades.get(0));
        }        
    }

    public String formulario() {
    	departamento = new Departamento();       
        return "ok";
    }

     public String gravar() {    	
    	if(departamento.getId() == null)
    		dao.add(departamento);
    	else
    		dao.update(departamento);
    	
        message = "Departamento salvo com sucesso!";
        departamento = null;
        return "ok";
    }

    public void validateGravar(ValidationErrors errors) {   	
        if (departamento.getNome() == null || departamento.getNome().equals("")) {
            errors.add(new Message("aviso", "Um nome para o o departamento não foi definido."));
        }

        if (departamento.getResponsavel() == null || departamento.getResponsavel().equals("")) {
            errors.add(new Message("aviso", "Um responsável não foi definido."));
        }

        if (departamento.getAlmoxarifados() == null || departamento.getAlmoxarifados().size() == 0) {
            errors.add(new Message("aviso", "Nenhum almoxarifado foi definido."));
        }

        if (departamento.exist(dao.listAll())) {
            errors.add(new Message("aviso", "O departamento já existe."));
        }

        if (errors.size() > 0) {
            Unidade u = unidadeDao.getById(departamento.getUnidade().getId());
            almoxarifados = almoxarifadoDao.listByUnidade(u);
            unidades = unidadeDao.listByOrgao(u.getOrgao());
            departamento.setUnidade(u);           
        }
    }

    public String listAll() {
        if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
            departamentos = dao.listAll();
        } else {
            departamentos = dao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        }
        return "ok";
    }

    public String buscar() {
    	if(authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL))
    		departamentos = dao.buscar(criteria, null);    			
    	else 
    		departamentos = dao.buscar(criteria, authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        return "ok";
    }

    public String remove() {
        dao.remove(departamento);
        departamento = null;
        return "ok";
    }

    public String get() {
        departamento = dao.getById(departamento.getId());
        almoxarifados = almoxarifadoDao.listByUnidade(departamento.getUnidade());
        unidades = unidadeDao.listByOrgao(departamento.getUnidade().getOrgao());
        return "ok";
    }

    public String loadAlmoxarifados(Unidade unidade) {
    	if(!unidade.getId().equals(0L))
    		almoxarifados = almoxarifadoDao.listByUnidade(unidade);
    	else
    		almoxarifados = null;
    	
        return "ok";
    }
    
    public String loadUnidades(Orgao orgao) {
    	if(!orgao.getId().equals(0L)) 
    		unidades = unidadeDao.listByOrgao(orgao);
    	else {
    		unidades = null;
    	}
    	
    	return "ok";
    }
    
    public String loadUnidadesAlmoxarifado(Orgao orgao) {
    	return loadUnidades(orgao);
    }
    
    public String addAlmoxarifado(Almoxarifado almoxarifado) {
    	almoxarifado = almoxarifadoDao.getById(almoxarifado.getId());
    	departamento.addAlmoxarifado(almoxarifado);
    	return "ok";    
    }
    
    public String remAlmoxarifado(Almoxarifado almoxarifado) {
    	departamento.getAlmoxarifados().remove(almoxarifado);
    	return "ok";
    }
    
}