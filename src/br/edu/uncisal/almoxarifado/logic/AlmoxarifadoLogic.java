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
import br.edu.uncisal.almoxarifado.dao.OrgaoDao;
import br.edu.uncisal.almoxarifado.dao.UnidadeDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Municipio;
import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Uf;
import br.edu.uncisal.almoxarifado.model.Unidade;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("almoxarifado")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class AlmoxarifadoLogic {

    @Parameter(create=true)
    @Out
    private Almoxarifado almoxarifado = new Almoxarifado();
    @Parameter
    @Out
    private Unidade unidade;
    
    @Out
    private List<Orgao> orgaos;
    
    @Out
    private Collection<Almoxarifado> almoxarifados;
    @Out
    private List<Uf> ufs;
    @Out
    private List<Municipio> municipios;
    @Out
    private String message;
    @Out
    private String msgErro;
    @Out
    private List<Unidade> unidades;
    
    private Usuario authUser;    
    
    private AlmoxarifadoDao aDao;
    private OrgaoDao orgaoDao;
    private UnidadeDao unidadeDao;
    
    @Out
    @Parameter
    private String criteria;

    public AlmoxarifadoLogic(DaoFactory daoFactory, Usuario authUser) {
    	this.authUser = authUser;
    	
    	aDao = daoFactory.getAlmoxarifadoDao();    	
    	orgaoDao = daoFactory.getOrgaoDao();
        unidadeDao = daoFactory.getUnidadeDao();
        
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
        }

    }

    public String formulario() {       
        return "ok";
    }

    public String filtro() {
        return formulario();
    }

    public String gravar() {
        aDao.save(almoxarifado);        
        message = "Dados salvos com sucesso.";
        return "ok";
    }

    public void validateGravar(ValidationErrors errors) {
        if (almoxarifado.getNome() == null || almoxarifado.getNome().equals("")) {
            errors.add(new Message("aviso", "Um nome para o almoxarifado não foi definido."));
        }

        if (almoxarifado.getResponsavel() == null || almoxarifado.getResponsavel().equals("")) {
            errors.add(new Message("aviso", "Um responsável precisa ser definido."));
        }
        
        if (almoxarifado.getUnidade() == null || almoxarifado.getUnidade().getId().equals(0L)) {
            errors.add(new Message("aviso", "Uma unidade precisa ser definida."));
        }        

        if (almoxarifado.getTelefone() == null || almoxarifado.getTelefone().equals("")) {
            errors.add(new Message("aviso", "Um telefone para o almoxarifado não foi definido."));
        }
        
        if(almoxarifado.getImplantando() == null){
        	almoxarifado.setImplantando(false);
        }

        if (errors.size() > 0) {         
            Unidade u = unidadeDao.getById(almoxarifado.getUnidade().getId());            
            unidades = unidadeDao.listByOrgao(u.getOrgao());
            almoxarifado.setUnidade(u);            
        }
    }

    public String buscar() {
        if (unidade!=null)
        	almoxarifado.setUnidade(unidade);
        almoxarifado.setNome("%"+almoxarifado.getNome()+"%");
        almoxarifado.setResponsavel("%"+almoxarifado.getResponsavel()+"%");
        almoxarifado.setTelefone("%"+almoxarifado.getTelefone()+"%");
        almoxarifados = aDao.findByExample(almoxarifado);
        return "ok";
    }

    public String listAll() {
    	if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
            this.almoxarifados = aDao.listAll();
        } else {        	
            this.almoxarifados = aDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        }

        return "ok";
    }

    public String remove() {
        try {
            aDao.remove(almoxarifado);
            message = "O almoxarifado foi removido com sucesso.";
        } catch (Exception e) {
            msgErro = "Não é possível remover este almoxarifado. Pois este está associado a outros elementos.";
        }

        return "ok";
    }

    public String get() {
        this.almoxarifado = aDao.getById(almoxarifado.getId());        
        unidades = unidadeDao.listByOrgao(almoxarifado.getUnidade().getOrgao());
        
        return "ok";
    }

    public String ver() {
        this.almoxarifado = aDao.getById(almoxarifado.getId());
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
    
    /** Retorna a lista de Almoxarifados conforme parâmetro passado da busca */
    public String buscarAlmoxarifado() {
    	almoxarifados = aDao.buscarAlmoxarifado(criteria);
        return "ok";
    }
    
}
