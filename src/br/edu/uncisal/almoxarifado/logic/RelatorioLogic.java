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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.scope.ScopeType;

import br.edu.uncisal.almoxarifado.constants.GeneralConstants;
import br.edu.uncisal.almoxarifado.dao.AlmoxarifadoDao;
import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.DepartamentoDao;
import br.edu.uncisal.almoxarifado.dao.ItemDao;
import br.edu.uncisal.almoxarifado.dao.ItemSaidaDao;
import br.edu.uncisal.almoxarifado.dao.RelatorioDao;
import br.edu.uncisal.almoxarifado.dao.RequisicaoDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDepartamentoDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemSaida;
import br.edu.uncisal.almoxarifado.model.ParametroRelatorio;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Relatorio;
import br.edu.uncisal.almoxarifado.model.Requisicao;
import br.edu.uncisal.almoxarifado.model.Usuario;

/**
 * Classe Logic(Controller) para entrada de itens no almoxarifado, com o
 * cadastro de nota de entrada e seus respectivos itens.
 * 
 * @author Augusto Oliveira
 */
@SuppressWarnings("unused")
@Component("relatorio")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class RelatorioLogic {

    @Out
    private List<ItemSaida> itensSaida;
    @Out 
    private Collection<ItemSaida> itensSaidaA;
    @Out 
    private Collection<ItemSaida> itensSaidaB;
    @Out 
    private Collection<ItemSaida> itensSaidaC;    
    @Out
    private Integer a;
    @Out
    private Integer b;
    @Out
    private Integer tamanho;
    @Parameter
    private Date dataInicio;
    @Parameter
    private Date dataFim;
    @Out
    private BigDecimal montante;
    
	@Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private Relatorio relatorio;
    @Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private Usuario authUser;
    @Parameter(create = true)
    @Out
    private ParametroRelatorio parametro;
    @Parameter(create = true)
    private Requisicao recibo;
    @Out
    private List<ParametroRelatorio> parametros;
    @Out
    private Collection<Relatorio> relatorios;
    @Parameter
    private String q;
    @Parameter(create = true)
    private Departamento departamento;
    @Out
    private List<Item> itens;
    @Out
    private List<Requisicao> recibos;
    @Out
    private List<Departamento> departamentos;
    @Out
    private Collection<Almoxarifado> almoxarifados;
    @Out
    @Parameter
    private Long almoxarifadoId;
    @Out
    private Map<String, String> tipos = new HashMap<String, String>();
    @Out
    private String msgErro;
    @Out
    private Long orgaoId;    
    
    private ItemDao iDao;
    private RequisicaoDao rDao;
    private UsuarioDepartamentoDao uDDao;
    private DepartamentoDao departamentoDao;    
    private AlmoxarifadoDao almoxarifadoDao;
    private RelatorioDao relatorioDao;
    private UsuarioDao usuarioDao;
    private ItemSaidaDao itemSaidaDao;

    public RelatorioLogic(DaoFactory daoFactory) {
    	iDao = daoFactory.getItemDao();
    	rDao = daoFactory.getRequisicaoDao();   
    	uDDao = daoFactory.getUsuarioDepartamentoDao();
    	almoxarifadoDao = daoFactory.getAlmoxarifadoDao();
    	departamentoDao = daoFactory.getDepartamentoDao();
    	relatorioDao = daoFactory.getRelatorioDao();
    	usuarioDao = daoFactory.getUsuarioDao();
    	itemSaidaDao = daoFactory.getItemSaidaDao();

        tipos.put("String", GeneralConstants.STRING);
        tipos.put("Date", GeneralConstants.DATE);
        tipos.put("Long", GeneralConstants.LONG);
        tipos.put("Integer", GeneralConstants.INTERGER);
        tipos.put("Float", GeneralConstants.FLOAT);
        tipos.put("Double", GeneralConstants.DOUBLE);
    }

    public String formulario() {
        relatorio = new Relatorio();
        relatorio.setParametros(new ArrayList<ParametroRelatorio>());

        return "ok";
    }

    public String gravar() {
        relatorioDao.save(relatorio);
        return "ok";
    }

    public String listAll() {
        relatorios = relatorioDao.listAll();
        return "ok";
    }

    public String remove() {
        relatorio.setParametros(null);
        relatorioDao.remove(relatorio);
        return "ok";
    }

    public String get() {
        relatorio = relatorioDao.getById(relatorio.getId());
        parametros = relatorio.getParametros();
        return "ok";
    }

    public String filtro() {
        relatorios = relatorioDao.listAll();
        return "ok";
    }

    public String loadParams() {
        parametros = relatorioDao.getById(relatorio.getId()).getParametros();
        return "ok";
    }

    public String loadDepartamentos() {
        departamentos = departamentoDao.listAll();
        return "ok";
    }

    public String loadItens() {
    	authUser = usuarioDao.get(authUser);
        itens = iDao.loadItensAutoComplete(q, authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        return "ok";
    }

    public String loadRecibos() {
        recibos = rDao.findByDepartamento(departamento, recibo.getAlmoxarifado());
        return "ok";
    }

    private void loadAlmoxarifado() {
    	Perfil p = authUser.getUsuarioDepartamentoAtivo().getPerfil();
        if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_GERAL)) {
            almoxarifados = almoxarifadoDao.listAll();
        } else if (authUser.getUsuarioDepartamentoAtivo().getPerfil().getId().equals(Perfil.ADMINISTRADOR_LOCAL)) {
            almoxarifados = almoxarifadoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        } else if (p.getId().equals(Perfil.ALMOXARIFE)){
        	almoxarifados = new ArrayList<Almoxarifado>();
        	almoxarifados.add(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        } else {        	
        	almoxarifados = uDDao.getById(authUser.getUsuarioDepartamentoAtivo().getId()).getDepartamento().getAlmoxarifados();
        }
        
        if (authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado() != null) {
            almoxarifadoId = authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado().getId();
        }
    }

    public String loadAlmoxarifados() {
        loadAlmoxarifado();
        return "ok";
    }

    public String balancoFinanceiro() {
        loadAlmoxarifado();
        return "ok";
    }

    public String balancoFinanceiroOrgao() {
        orgaoId = authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade().getOrgao().getId();
        return "ok";
    }
    
    public String balancoFisico() {
        loadAlmoxarifado();
        return "ok";
    }

    public String curvaABCForm() {
        loadAlmoxarifado();
        return "ok";
    }
    
    public String curvaABC() {
    	ItemSaida itemSaida = new ItemSaida();
    	itensSaida = itemSaida.loadCurvaABC(dataInicio, dataFim, itemSaidaDao.loadCurvaABC(dataInicio, dataFim, almoxarifadoId));
        
        if(itensSaida == null || itensSaida.size() < 5) {
        	msgErro = "Não foram encontrados registros suficientes para o relatório.";
        	loadAlmoxarifado();
        	return "invalid";
        }
        else {
        	tamanho = itensSaida.size();
        	a = new Integer(new Double(Math.floor(tamanho * 0.2)).intValue());
        	b = new Integer(new Double(Math.floor(tamanho * 0.5)).intValue());
        }
        
        itensSaidaA = itensSaida.subList(0, a);
        itensSaidaB = itensSaida.subList(a, b);
        itensSaidaC = itensSaida.subList(b, tamanho);
        
        montante = new BigDecimal("0");
        for (ItemSaida is : itensSaida) {
			montante.add(is.getValorConsumo());
		}
        
    	return "ok";
    }

    public String consumo() {
        loadAlmoxarifado();
        return "ok";
    }

    public String consumoItem() {
        loadAlmoxarifado();
        return "ok";
    }

    public String consumoSetor() {
        loadAlmoxarifado();
        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        return "ok";
    }

    public String consumoSetorUsuario() {
        loadAlmoxarifado();
        departamentos = departamentoDao.findByExample(authUser.getUsuarioDepartamentoAtivo().getDepartamento());
        return "ok";
    }

    public String patrimonioSetor() {
        departamentos = departamentoDao.listAll();
        loadAlmoxarifado();

        return "ok";
    }

    public String notaSaida() {
        loadAlmoxarifado();
        return "ok";
    }

    public String estoque() {
        loadAlmoxarifado();
        return "ok";
    }

    public String estoqueBaixo() {
        return estoque();
    }

    public String inventario() {
        loadAlmoxarifado();
        return "ok";
    }

    public String notaSaidaRecibo() {
        loadAlmoxarifado();
        departamentos =
                departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        return "ok";
    }

    public String notaSaidaSetor() {
        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        loadAlmoxarifado();

        return "ok";
    }

    public String notaSaidaSetorUsuario() {
        return consumoSetorUsuario();
    }

    public String notaEntrada() {
        loadAlmoxarifado();
        return "ok";
    }
    
    public String itensForaDaMedia() {  
    	almoxarifadoId = authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado().getId();
        return "ok";
    }
    
    public String catalogoItens() {
		return "ok";
	}
    
}
