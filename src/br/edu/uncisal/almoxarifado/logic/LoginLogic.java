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

import org.hibernate.exception.GenericJDBCException;
import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.scope.ScopeType;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.ItemEstoqueDao;
import br.edu.uncisal.almoxarifado.dao.OrgaoDao;
import br.edu.uncisal.almoxarifado.dao.RequisicaoDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDepartamentoDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.ItemEstoque;
import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Requisicao;
import br.edu.uncisal.almoxarifado.model.TipoStatus;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.almoxarifado.util.HashUtil;

@Component("login")
@InterceptedBy({DaoInterceptor.class})
public class LoginLogic {

    @Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private Usuario authUser;
    @Parameter
    @In(scope = ScopeType.SESSION, required = false)
    private String ip;
    @Parameter
    private String msie;
    @Out
    private List<ItemEstoque> itensEstoqueBaixo;
    @Out
    private List<Requisicao> requisicoesPendentes;
    @Out
    private List<Requisicao> requisicoesNaoEntregues;
    @Out
    private String msgErro;
    @Out
    private List<Orgao> orgaos;
    @Out
    private String message;
    @Out
    private String alert;
    
    private UsuarioDao dao;
    private RequisicaoDao rDao;
    private ItemEstoqueDao itemEstoqueDao;
    private OrgaoDao orgaoDao;
    private UsuarioDepartamentoDao usuarioDepartamentoDao;
    private UsuarioDao usuarioDao;

    public LoginLogic(DaoFactory daoFactory) {
    	dao = daoFactory.getUsuarioDao();
    	rDao = daoFactory.getRequisicaoDao();
    	itemEstoqueDao = daoFactory.getItemEstoqueDao();
    	orgaoDao = daoFactory.getOrgaoDao();
    	usuarioDepartamentoDao = daoFactory.getUsuarioDepartamentoDao();
    	usuarioDao = daoFactory.getUsuarioDao();
    }

    public String form() {
        alert = "Em caso de dúvidas ligar para 33155739 ou mandar e-mail para almoxarifado@itec.al.gov.br.</a>";        		
        return "ok";
    }

    public String login() {
        try {
        	authUser.setSenha(HashUtil.SHA(authUser.getSenha()));
        	List<Usuario> usuarios = dao.login(authUser);
        	if(usuarios.size() > 0)
        		authUser = usuarios.get(0);
        	else
        		authUser = null;
     
            if (authUser != null) {
                authUser.setIp(ip);

                /* caso o usuário trabalhe em mais de um departamento do mesmo orgão,
                é enviada a tela para selecionar o departamento para o login */
                if (authUser.getUsuarioDepartamentos().size() > 1) {
                    return "departamentos";
                } else {
                    authUser.setUsuarioDepartamentoAtivo(authUser.getUsuarioDepartamentos().get(0));
                    return "ok";
                }
            } else {
                msgErro = "Os campos CPF, ou senha não foram preenchidos corretamente. Favor verificar seus dados de entrada.";
                return "error";
            }
        } catch (GenericJDBCException e) {
            msgErro = "Erro ao tentar conectar com o banco de dados. O sistema está fora do ar temporariamente. Tente novamente.";
            return "error";
        }
    }

    public String loginDepartamento() {
        return "ok";
    }

    public String logout() {
        orgaos = orgaoDao.listAtivados();
        if (authUser != null) {
            authUser = null;
        }
        return "ok";
    }

    public String principal() {
        if (authUser != null) {
            authUser = usuarioDao.get(authUser);
            authUser.setUsuarioDepartamentoAtivo(
            	usuarioDepartamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getId()));
            if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ALMOXARIFE)) {
                return "almoxarifado";
            }
            return "ok";
        } else {
            return "form";
        }
    }

    public String almoxarifado() {
        authUser = dao.getById(authUser.getId());
        Almoxarifado a = authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado();        
        itensEstoqueBaixo = itemEstoqueDao.listAllEstoqueBaixo(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        requisicoesPendentes = rDao.findByStatus(TipoStatus.AGUARDANDO, a);        
        requisicoesNaoEntregues = rDao.findByStatus(TipoStatus.APROVADO, a);        
        return "ok";
    }

    public String incompativel() {
    	return "ok";
    }
    
    public String novidades() {
    	return "ok";
    }
    
}
