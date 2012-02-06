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
import java.util.TreeSet;

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
import br.edu.uncisal.almoxarifado.dao.PerfilDao;
import br.edu.uncisal.almoxarifado.dao.UnidadeDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Orgao;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Unidade;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.almoxarifado.model.UsuarioDepartamento;
import br.edu.uncisal.almoxarifado.util.HashUtil;

@SuppressWarnings("unused")
@Component("usuario")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class UsuarioLogic {

    @Parameter
    private String q;	
	@Parameter(create = true)
    @Out
    private Usuario usuario;
    @Out
    @Parameter
    private Usuario consumidor;
    @Out
    private Collection<Usuario> usuarios;
    @Out
    private List<Departamento> departamentos;
    @Out
    private Collection<Perfil> perfis;
    @Out
    private List<Almoxarifado> almoxarifados;
    @Out
    private List<Unidade> unidades;
    @Parameter(create = true)
    @Out
    private Unidade unidade;
    @Parameter(create = true)
    @Out
    private String unidadeId;
    @Out
    private List<Orgao> orgaos;
    @Parameter(create = true)
    @Out
    private Orgao orgao;
    @Out
    private String msgErro;
    @Out
    private String message;
    @Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private Usuario authUser;
    @Parameter
    private String novaSenha;
    @Parameter
    private String confirmacaoNovaSenha;
    @Out
    @Parameter
    private boolean desativado = false;
    
    private Usuario u;
    @Out
    @Parameter(create = true)
    private Departamento departamento;
    @Parameter(create = true)
    private Perfil perfil;
    @Parameter(create = true)
    private Almoxarifado almoxarifado;
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    @Parameter(create = true)
    private List<UsuarioDepartamento> usuarioDepartamentos;
    
    private UsuarioDao usuarioDao;
    private AlmoxarifadoDao almoxarifadoDao;
    private DepartamentoDao departamentoDao;
    private OrgaoDao orgaoDao;
    private PerfilDao perfilDao;
    private UnidadeDao unidadeDao;

    public UsuarioLogic(DaoFactory daoFactory, Usuario authUser) {
        u = authUser;
        consumidor = new Usuario();
        
        usuarioDao = daoFactory.getUsuarioDao();
        almoxarifadoDao = daoFactory.getAlmoxarifadoDao();
        orgaoDao = daoFactory.getOrgaoDao();
        unidadeDao = daoFactory.getUnidadeDao();
        perfilDao = daoFactory.getPerfilDao();
        departamentoDao = daoFactory.getDepartamentoDao();
    }

    public String formulario() {
        usuarioDepartamentos = new ArrayList<UsuarioDepartamento>();

        usuario = usuarioDao.get(authUser);
        if (usuario.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
            orgaos = orgaoDao.listAtivados();
        } else if (usuario.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_LOCAL)) {
            orgao = departamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId()).getUnidade().getOrgao();
            unidades = unidadeDao.listByOrgao(orgao);
        }

        perfis = perfilDao.listAll();
        usuario = new Usuario();
        return "ok";
    }

    public String formularioConsumidor() {
        consumidor = new Usuario();
        unidade = authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade();
        departamentos = departamentoDao.listByUnidade(unidade);
        return formulario();
    }

    public String filtroConsumidor() {
        consumidor = new Usuario();
        return formularioConsumidor();
    }

    public String filtro() {
        unidade = new Unidade();
        usuario = new Usuario();
        return "ok";
    }

    public String formEdit() {
        usuario = usuarioDao.get(authUser);
        departamento = usuario.getUsuarioDepartamentoAtivo().getDepartamento();
        return "ok";
    }

    public String recuperaConsumidor() {
        consumidor = usuarioDao.get(consumidor);
        departamento = usuario.getUsuarioDepartamentoAtivo().getDepartamento();
        return "ok";
    }

    public String salvarSenha() {
        usuario = usuarioDao.getById(usuario.getId());
        usuario.setSenha(HashUtil.SHA(novaSenha));
        message = "Senha alterada com sucesso.";
        return "ok";
    }
    
    public void validateSalvarSenha(ValidationErrors errors) {
    	Usuario u = usuarioDao.get(usuario);
    	String senhaCriptografada = HashUtil.SHA(usuario.getSenha());
    	
        if (novaSenha.length() < 6) {
            errors.add(new Message("aviso", "A senha deve conter no mínimo 6 caracteres."));
        }
    	
        if (!novaSenha.equals(confirmacaoNovaSenha)) {
            errors.add(new Message("aviso", "A nova senha e a confirmação da nova senha possuem valores diferentes."));
        }
        
        if (!senhaCriptografada.equals(u.getSenha())) {
        	errors.add(new Message("aviso", "A senha atual digitada não confere com a senha do usuário."));
        }
        
        if(errors.size() > 0) {
            usuario = usuarioDao.get(authUser);
            departamento = usuario.getUsuarioDepartamentoAtivo().getDepartamento();
        }
    	
    }

    public String gravar() {
    	usuario.setUsuarioDepartamentos(usuarioDepartamentos);
    	
        //Para os usuários que não definiram senha no formulário ele vai usar a senha anterior, para os usuários que já definiram ele gera um hash da senha.
        if(usuario.getSenha() != null && usuario.getSenha().length() < 6)
        	usuario.setSenha(null);
        
        if(usuario.getId() == null || (usuario.getId() != null && usuario.getSenha() != null))
    		usuario.setSenha(HashUtil.SHA(usuario.getSenha()));
    	else {
    		Usuario u = usuarioDao.getById(usuario.getId());
			usuario.setSenha(u.getSenha());
			u = null;
    	}    	

        usuarioDao.merge(usuario);
        
        message = "Dados do usuário foram salvos com sucesso.";
        listAll();
        return "ok";
    }

    public void validateGravar(ValidationErrors errors) {
        if (usuario.getNome() == null || usuario.getNome().equals("")) {
            errors.add(new Message("aviso", "Nome do usuário não preenchido."));
        }
        if (usuario.getCpf() == null || usuario.getCpf().equals("")) {
            errors.add(new Message("aviso", "CPF não preenchido."));
        }
        if (usuario.getFuncao() == null || usuario.getFuncao().equals("")) {
            errors.add(new Message("aviso", "Função não preenchido."));
        }

        if (usuarioDepartamentos.size() == 0) {
            errors.add(new Message("aviso", "Nenhum departamento associado ao usuário."));
        }
        
        if(usuario.getId() == null && usuario.getSenha().length() < 6) {
        	errors.add(new Message("aviso", "A senha deve conter no mínimo 6 caracteres."));
        }

        //Verifica se já existe um usuário com o cpf escolhido.
        if (usuario.getCpf() != null && !usuario.getCpf().equals("")) {
            Usuario uExample = new Usuario();
            uExample.setCpf(usuario.getCpf());
            
            usuarios = usuarioDao.findByExample(uExample);

            if (usuario.getId() != null) {
                if (usuarios.size() > 1) {
                    errors.add(new Message("aviso", "Já existe um usuário com o CPF escolhido."));
                }
            } else if (usuarios.size() > 0) {
                errors.add(new Message("aviso", "Já existe um usuário com o CPF escolhido."));
            }
            
            usuarios = null;
            uExample = null;
        }

        if (errors.size() > 0) {
            Usuario us = usuarioDao.get(authUser);
            if (us.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
                orgaos = orgaoDao.listAtivados();
            } else {
                orgao = departamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId()).getUnidade().getOrgao();
                unidades = unidadeDao.listByOrgao(orgao);
            }

            perfis = perfilDao.listAll();
        }
    }

    public String gravarConsumidor() {
        Perfil perfilConsumidor = new Perfil();
        perfilConsumidor.setId(Perfil.CONSUMIDOR);

        UsuarioDepartamento ud = new UsuarioDepartamento();
        ud.setPerfil(perfilConsumidor);
        ud.setDepartamento(departamento);

        consumidor.setUsuarioDepartamentos(new ArrayList<UsuarioDepartamento>());
        consumidor.getUsuarioDepartamentos().add(ud);

        usuario = consumidor;
        usuarioDao.save(usuario);
        message = "Dados do usuário foram salvos com sucesso.";
        return formularioConsumidor();
    }

    public void validateGravarConsumidor(ValidationErrors errors) {
        if (consumidor.getNome() == null || consumidor.getNome().equals("")) {
            errors.add(new Message("aviso", "Nome do usuário não preenchido."));
        }
        if (consumidor.getCpf() == null || consumidor.getCpf().equals("")) {
            errors.add(new Message("aviso", "CPF não preenchido."));
        }
        if (consumidor.getFuncao() == null || consumidor.getFuncao().equals("")) {
            errors.add(new Message("aviso", "Função não preenchido."));
        }
        if (consumidor.getEmail() == null || consumidor.getEmail().equals("")) {
            errors.add(new Message("aviso", "Email não preenchido."));
        }
        if (departamento.getId().equals(new Long(0L))) {
            errors.add(new Message("aviso", "Nenhum departamento associado ao usuário."));
        }
        if(consumidor.getId() == null && consumidor.getSenha().length() < 6) {
        	errors.add(new Message("aviso", "A senha deve conter no mínimo 6 caracteres."));
        }

        //Verifica se já existe um usuário com o cpf escolhido.
        if (consumidor.getCpf() != null && !consumidor.getCpf().equals("")) {
            Usuario uExample = new Usuario();
            uExample.setCpf(consumidor.getCpf());

            if (consumidor.getId() != null) {
                if (usuarioDao.findByExample(uExample).size() > 1) {
                    errors.add(new Message("aviso", "Já existe um usuário com o CPF escolhido."));
                }
            } else if (usuarioDao.findByExample(uExample).size() > 0) {
                errors.add(new Message("aviso", "Já existe um usuário com o CPF escolhido."));
            }
        }

        if (errors.size() > 0) {
            Usuario us = usuarioDao.get(authUser);

            unidade = us.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade();
            departamentos = departamentoDao.listByUnidade(unidade);
        }
    }
    
    public String listAll() {        
        if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
        	orgaos = orgaoDao.listAtivados();
        } else {
        	orgaos = new ArrayList<Orgao>();
        	orgaos.add(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade().getOrgao());
        	unidades = unidadeDao.listByOrgao(orgaos.get(0));
        	usuarios = usuarioDao.buscar(new Usuario(), unidades.get(0), desativado);
        }
        
        return "ok";
    }

    public String buscar() {
    	unidade = authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade();

    	if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL))
    		usuarios = usuarioDao.buscar(usuario, null, desativado);
    	else if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_LOCAL))
    		usuarios = usuarioDao.buscar(usuario, unidade, desativado);
    	else
    		return "invalid";
    	
        return "ok";
    }

    public String remove() {
    	listAll();
        usuarioDao.remove(usuario);
        return "ok";
    }

    /**
     * @FIXME Falha de segurança, dadas as circunstâncias um usuário não privilegiado,
     * pode interferir no cadastro de outro apenas colocando o código do usuário via GET.
     */
    public String get() {
        Usuario u = usuarioDao.get(authUser);
        if (u.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
            orgaos = orgaoDao.listAtivados();
        } else if (u.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_LOCAL)) {
            orgao = departamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId()).getUnidade().getOrgao();
            unidades = unidadeDao.listByOrgao(orgao);
        }

        perfis = new TreeSet<Perfil>(perfilDao.listAll()); 

        usuario = usuarioDao.get(usuario);
        usuarioDepartamentos = usuario.getUsuarioDepartamentos();

        return "ok";
    }

    public String loadDepartamentos(Unidade unidade) {
        departamentos = departamentoDao.listByUnidade(unidade);
        return "ok";
    }

    public String loadAlmoxarifados(Unidade unidade, Perfil perfil) {
        if (!unidade.getId().equals(0L) && !perfil.getId().equals(0L)) {
            if (!perfil.getId().equals(new Long(3)) && !perfil.getId().equals(new Long(13))) {
                almoxarifados = almoxarifadoDao.listByUnidade(unidade);
            }
        } else {
            almoxarifados = null;
        }

        return "ok";
    }

    public String loadUnidades() {
        if (!orgao.getId().equals(0L)) {
            unidades = unidadeDao.listByOrgao(orgao);
        } else {
            unidades = null;
        }
        return "ok";
    }

    public String loadOrgaos() {
        orgaos = orgaoDao.listAtivados();
        return "ok";
    }

    /**
     * Tela de formulário de ativação de orgão
     */
    public String orgao() {
        orgaos = orgaoDao.listAll();
        return "ok";
    }

    /**
     * Ativação de orgão
     */
    public String ativarOrgao() {
        orgao = orgaoDao.getById(orgao.getId());
        orgao.setTemAlmoxarifado(Boolean.TRUE);
        orgaoDao.save(orgao);
        message = "O orgão " + orgao.getSigla() + " foi ativado para cadastrar almoxarifados";
        return orgao();
    }

    /**
     * Tela de formulário de ativação de orgão
     */
    public String unidade() {
        orgaos = orgaoDao.listAtivados();

        return "ok";
    }

    /**
     * Ativação de orgão
     */
    public String cadastrarUnidade() {
        orgao = orgaoDao.getById(orgao.getId());
        unidade.setOrgao(orgao);
        unidadeDao.save(unidade);
        message = "A unidade " + unidade.getNome() + " foi gravada com sucesso.";
        return unidade();
    }

    public String addUsuarioDepartamento() {
        if (usuarioDepartamentos == null) {
            usuarioDepartamentos = new ArrayList<UsuarioDepartamento>();
        }

        UsuarioDepartamento uD = new UsuarioDepartamento();
        uD.setPerfil(perfilDao.getById(perfil.getId()));
        uD.setDepartamento(departamentoDao.getById(departamento.getId()));

        if (!almoxarifado.getId().equals(0L)) {
            uD.setAlmoxarifado(almoxarifadoDao.getById(almoxarifado.getId()));
        }

        usuarioDepartamentos.add(uD);
        return "ok";
    }
    
    public void validateAddUsuarioDepartamento(ValidationErrors errors) {
    	if(!perfil.getId().equals(Perfil.CONSUMIDOR) && almoxarifado.getId().equals(0L))    		
    			errors.add(new Message("aviso", "Almoxarifado não escolhido."));
    }

    public String remUsuarioDepartamento() {
        int i = 0;
        for (UsuarioDepartamento uD : usuarioDepartamentos) {
            if (uD.getDepartamento().getId().equals(departamento.getId())) {
                break;
            }
            i++;
        }
        
        UsuarioDepartamento uD = usuarioDepartamentos.remove(i);
        uD = null;
        
        return "ok";
    }
    
    public String loadUsuarios() {    	
    	usuarios = usuarioDao.buscar(new Usuario(), unidade, desativado);
    	return "ok";
    }
    
    public String reabilitar() {
    	usuarioDao.reabilitar(usuario);
    	message = "Usuario reabilitado com sucesso, edite as informações deste se necessário.";
    	return "ok";
    }
    
}