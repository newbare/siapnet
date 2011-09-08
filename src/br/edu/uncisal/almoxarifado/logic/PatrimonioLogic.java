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
import java.util.Date;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.In;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;
import org.vraptor.i18n.Message;
import org.vraptor.scope.ScopeType;
import org.vraptor.validator.ValidationErrors;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.DepartamentoDao;
import br.edu.uncisal.almoxarifado.dao.FornecedorDao;
import br.edu.uncisal.almoxarifado.dao.ItemEntradaDao;
import br.edu.uncisal.almoxarifado.dao.UsuarioDao;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Fornecedor;
import br.edu.uncisal.almoxarifado.model.Grupo;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEntrada;
import br.edu.uncisal.almoxarifado.model.TipoFornecedor;
import br.edu.uncisal.almoxarifado.model.TipoMedida;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.patrimonio.dao.AutorizacaoSaidaBensDao;
import br.edu.uncisal.patrimonio.dao.EstadoDepreciacaoDao;
import br.edu.uncisal.patrimonio.dao.MarcaDao;
import br.edu.uncisal.patrimonio.dao.PatrimonioDao;
import br.edu.uncisal.patrimonio.dao.TermoResponsabilidadeDao;
import br.edu.uncisal.patrimonio.dao.TransferenciaPatrimonioDao;
import br.edu.uncisal.patrimonio.model.AutorizacaoSaidaBens;
import br.edu.uncisal.patrimonio.model.EstadoDepreciacao;
import br.edu.uncisal.patrimonio.model.Marca;
import br.edu.uncisal.patrimonio.model.Patrimonio;
import br.edu.uncisal.patrimonio.model.TermoResponsabilidade;
import br.edu.uncisal.patrimonio.model.TransferenciaPatrimonio;

/**
 * Controller do gerenciamento de patrimonio
 */
@SuppressWarnings("unused")
@Component("patrimonio")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class PatrimonioLogic {

	@Parameter
	private Long[] patrimoniosId;
	
	@Out
	private List<TransferenciaPatrimonio> transferencias;
	
	@Out
	@Parameter(create = true)
	private Departamento departamento;
	
	@Parameter
	private String localizacao;
	
    @Deprecated
	@Parameter(create = true)
    @Out
    private Item item = new Item();
    
    @Parameter(create = true)
    @Out
    private ItemEntrada itemEntrada = new ItemEntrada();
    
    @Deprecated
    @Parameter
    private String q;
    
    @Deprecated
    @Out
    private List<Item> itensPermanentes;
    
    @Out
    private Collection<Usuario> servidores;
    
    @Out
    private List<ItemEntrada> itensEntradaPermanentes;
    
    @Deprecated
    @Out
    private List<TipoMedida> tiposMedida;
    
    @Deprecated
    @Out
    private List<Grupo> grupos;
    
    @Parameter(create = true)
    @Out
    private Patrimonio[] patrimonios;
    
    @Out
    private Collection<Patrimonio> itensTombados;
    
    @Parameter(create = true)
    @Out
    private Patrimonio patrimonio;
    
    @Out
    private String message;
    
    @Out
    private String msgErro;
    
    @Deprecated
    @Parameter(create = true)
    @Out(scope = ScopeType.SESSION)
    @In(scope = ScopeType.SESSION, required = false)
    private AutorizacaoSaidaBens autorizacaoSaida = new AutorizacaoSaidaBens();
    
    @Deprecated
    @Parameter(create = true)
    @Out
    private TermoResponsabilidade termoResponsabilidade = new TermoResponsabilidade();
    
    @Out
    private List<Marca> marcas;
    
    @Out
    private List<Departamento> departamentos;
    
    @Out
    private Collection<EstadoDepreciacao> estadosDepre;
    
    @Parameter
    @In(scope = ScopeType.SESSION)
    @Out(scope = ScopeType.SESSION)
    private Usuario authUser;
    
    @Deprecated
    @Out
    private Usuario servidor;
    
    @Deprecated
    @Parameter
    private String[] assistenciasTecnicaArray;
    
    @Out
    private List<Fornecedor> assistenciasTecnicas;
    
    @Parameter(create = true)
    @Out
    private TransferenciaPatrimonio transferenciaPatrimonio;
    
    @Out
    private List<Patrimonio> bens;
    
    /**
     * Número do setor do usuário na autorização de saída de bens.
     */
    @Deprecated
    @Out
    private Long usuarioSetorId;    
  
    private DepartamentoDao departamentoDao;
    private FornecedorDao fornecedorDao;
    private ItemEntradaDao itemEntradaDao;
    private UsuarioDao usuarioDao;
    private EstadoDepreciacaoDao estadoDepreciacaoDao;
    private MarcaDao marcaDao;
    private AutorizacaoSaidaBensDao autorizacaoSaidaBensDao;
    private TermoResponsabilidadeDao termoResponsabilidadeDao;
    private PatrimonioDao patrimonioDao;
    private TransferenciaPatrimonioDao transferenciaPatrimonioDao;
    
    public PatrimonioLogic(DaoFactory daoFactory, Usuario authUser) {
        
        departamentoDao = daoFactory.getDepartamentoDao();
        fornecedorDao = daoFactory.getFornecedorDao();
        itemEntradaDao = daoFactory.getItemEntradaDao();
        usuarioDao = daoFactory.getUsuarioDao();
        estadoDepreciacaoDao = daoFactory.getEstadoDepreciacaoDao();
        marcaDao = daoFactory.getMarcaDao();
        autorizacaoSaidaBensDao = daoFactory.getAutorizacaoSaidaBensDao();
        termoResponsabilidadeDao = daoFactory.getTermoResponsabilidadeDao();
        patrimonioDao = daoFactory.getPatrimonioDao();
        transferenciaPatrimonioDao = daoFactory.getTransferenciaPatrimonioDao();

        item.setGrupo(new Grupo());
        item.setTipoMedidaEntrada(new TipoMedida());
        patrimonio = new Patrimonio();
        patrimonio.setItemEntrada(itemEntrada);

        autorizacaoSaida.setBens(new ArrayList<Patrimonio>());
    }

    /**
     * Realiza o tombamento dos intens.
     */
    public String gravar() {
        itemEntrada = itemEntradaDao.getById(itemEntrada.getId());
        itemEntrada.setTombado(new Boolean(true));
        Date dataTombamento = new Date();
        try {
            for (Patrimonio p : patrimonios) {
                if ("".equals(p.getId())) {
                    p.setId(null);
                }
                p.setDataTombamento(dataTombamento);
                if (p.getMarca().getId() == null) {
                    p.setMarca(null);
                }
                if (p.getEstadoDepreciacao().getId() == null) {
                    p.setEstadoDepreciacao(null);
                }
                p.setDepartamento(departamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId()));
                p.setItemEntrada(itemEntrada);
                patrimonioDao.save(p);
            }
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            msgErro = "Número de Patrimônio, já existente para outro material tombado.";
        }
        message = "Bem(s) tombado(s) com sucesso.";
        return "ok";
    }

    //FIXME implementar método exist para validação.
    public void validateGravar(ValidationErrors errors) {
        boolean faltaTombo = false;
        boolean faltaDepreciacao = false;
        for (Patrimonio p : patrimonios) {
            if (p.getNumero() == null) {
                faltaTombo = true;
            }
            if (p.getEstadoDepreciacao().getId() == null) {
                faltaDepreciacao = true;
            }
            if (faltaTombo && faltaDepreciacao) {
                break;
            }
/*            if (manager.exist(p) && p.getId() == null) {
                errors.add(new Message("aviso",
                        "O item com o número de tombamento: " + p.getNumero() + " já existe."));
            }*/
        }

        if (faltaTombo) {
            errors.add(new Message("aviso",
                    "Existe(m) bem(bens) cujo o número de tombamento não foi informado."));
        }
        if (faltaDepreciacao) {
            errors.add(new Message("aviso",
                    "Existe(m) bem(bens) cujo o estado de depreciação não foi informado."));
        }
    }

    /**
     * Listar itens Permanentes que não foram tombados ainda.
     */
    public String listAll() {
        boolean tombados = new Boolean(false);
        itensEntradaPermanentes = itemEntradaDao.itensPermanentesTombados(tombados, authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        return "ok";
    }

    /** Lista todos os itens tombados do almoxarifado cujo o usuário pertence */
    public String listTombados() {
        itensTombados = patrimonioDao.listAll(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        return "ok";
    }    
    
    public String filtro() {
    	marcas = marcaDao.listAll();
        estadosDepre = estadoDepreciacaoDao.listAll();
        return "ok";
    }    

    //FIXME Verificar código deste método e vê se é necessário.
    /**public String buscar() {
        NotaEntrada ne = new NotaEntrada();
        try {
            if (patrimonio.getItemEntrada().getNotaEntrada()==null)
            ne.setAlmoxarifado(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
                patrimonio.getItemEntrada().setNotaEntrada(ne);
            itensTombados = manager.buscar(patrimonio);
        } catch (Exception e) {
            msgErro = "Por favor refaça os critérios da busca. Ocorreu o seguinte erro na tua busca: <br>" + e.getMessage() +  "<br>" + e.getCause();
        }
        return "ok";
    }**/

    public String get() {
        itemEntrada = itemEntradaDao.getById(itemEntrada.getId());
        itensTombados = patrimonioDao.listAll(itemEntrada);
        marcas = marcaDao.listAll();
        estadosDepre = estadoDepreciacaoDao.listAll();
        int i = 0;
        patrimonios = new Patrimonio[itensTombados.size()];
        for (Patrimonio p : itensTombados) {
            patrimonios[i] = p;
            ++i;
        }
        return "ok";
    }

    /**
     * Só desvincula um bem permanente da parte de patrimônio do sistema.
     */
    public String remove() {
        try {
            patrimonio = patrimonioDao.getById(patrimonio.getId());
            ItemEntrada ie = itemEntradaDao.getById(patrimonio.getItemEntrada().getId());
            patrimonio.setItemEntrada(null);
            patrimonioDao.remove(patrimonio);
            ie.setTombado(new Boolean(false));
            itemEntradaDao.save(ie);
            message = "O bem foi removido com sucesso.";
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            msgErro = "Não é possível remover este bem. Pois ele está associado a outros elementos.";
        }
        return "ok";
    }

    /**
     * Retorna os dados de um bem patrimônial específico.
     * @return
     */
    public String recupera() {
        marcas = marcaDao.listAll();
        estadosDepre = estadoDepreciacaoDao.listAll();
        assistenciasTecnicas = fornecedorDao.listAllbyTipoFornecedor(new TipoFornecedor(TipoFornecedor.ASSISTENCIA_TECNICA));
        patrimonio = patrimonioDao.getById(patrimonio.getId());
        return "ok";
    }

    public String enviar() {
        /*
         * adiciona os ids selecionados do listbox assistencia técnica à coleção
         * fornecedores
         */
        List<Fornecedor> assistencias = new ArrayList<Fornecedor>();
        Fornecedor assistencia;
        if (assistenciasTecnicaArray != null) {
            for (String assistenciaId : assistenciasTecnicaArray) {
                assistencia = new Fornecedor();
                assistencia.setId(new Long(assistenciaId));
                assistencia = fornecedorDao.getById(assistencia.getId());
                assistencias.add(assistencia);
            }
            patrimonio.setAssistenciasTecnica(assistencias);
        }
        if (patrimonio.getMarca().getId() == null) {
            patrimonio.setMarca(null);
        }

        try {
            patrimonioDao.save(patrimonio);
            message = "Os dados sobre o bem tombado no.: " + patrimonio.getNumero() + " foram atualizados.";
        } catch (org.hibernate.TransientObjectException e) {
            String erro = "object references an unsaved transient instance - save the transient instance before flushing:";
            if ((erro + " br.edu.uncisal.patrimonio.model.EstadoDepreciacao").equals(e.getMessage())) {
                msgErro = "O estado de depreciação do patrimônio deve ser informado.";
            }
            if ((erro + " br.edu.uncisal.patrimonio.model.Departamento").equals(e.getMessage())) {
                msgErro = "O setor onde está localizado ou será enviado o bem tombaddo deve ser informado.";
            }
        }

        return "ok";
    }

    /** ############ métodos para Ajax ############ */
    public String loadItensTombados() {
    	if(departamento.getId().equals(0L)) {
    		itensTombados = patrimonioDao.listAll(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
    	} else {
    		itensTombados = patrimonioDao.loadByDepartamento(departamento);
    	}
        return "ok";
    }
    
    public String loadUsuarios() {    	
    	servidores = usuarioDao.loadByDepartamento(departamento);
    	return "ok";
    }

    //FIXME Ajeitar isto
    public String loadItens() {
        //itensPermanentes = itemManager.loads (q);
        return "ok";
    }

    public String loadServidor() {
        servidor = new Usuario();
        servidor.setNumeroMatricula(q);
        servidor = usuarioDao.getByExample(servidor);
        return "ok";
    }

    public String loadPatrimonios() {
        patrimonio = patrimonioDao.getById(patrimonio.getId());
        return "ok";
    }

    /** ############ Emissão de Documentos ############ */
    public String prepararAutorizacao() {
        authUser = usuarioDao.get(authUser);
        autorizacaoSaida = new AutorizacaoSaidaBens();
        autorizacaoSaida.setSetorResponsavel(departamentoDao.getById(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId()));
        autorizacaoSaida.setMotivoSaida("conserto/manutenção"); // motivo de
        // saida padrão
        servidores = usuarioDao.listAll();
        return "ok";
    }

    public String prepararTermo() {
        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        return "ok";
    }

    public String autorizarSaida() {
    	authUser = usuarioDao.get(authUser);
    	usuarioSetorId = authUser.getUsuarioDepartamentoAtivo().getDepartamento().getId();
        autorizacaoSaida.setData(new Date());
        autorizacaoSaidaBensDao.save(autorizacaoSaida);
        autorizacaoSaida = autorizacaoSaidaBensDao.getByExample(autorizacaoSaida);
        return "ok";
    }

    public String addTombo() {
        patrimonio = patrimonioDao.getByExample(patrimonio);
        if (patrimonio != null) {
            if (autorizacaoSaida.getBens() == null) {
                autorizacaoSaida.setBens(new ArrayList<Patrimonio>());
            }
            autorizacaoSaida.getBens().add(patrimonio);
        } else {
            message = "Não existe bem com esse número de tombamento.";
        }
        return "ok";
    }

    public String remTombo() {
        patrimonio = patrimonioDao.getById(patrimonio.getId());
        autorizacaoSaida.getBens().remove(patrimonio);
        return "ok";
    }

    public String termoResponsabilidade() {
        TermoResponsabilidade tmp = termoResponsabilidadeDao.getByExample(termoResponsabilidade);
        if (tmp == null) {
            termoResponsabilidade.setData(new Date());
            termoResponsabilidadeDao.save(termoResponsabilidade);
            termoResponsabilidade = termoResponsabilidadeDao.getByExample(termoResponsabilidade);
        } else {
            termoResponsabilidade = tmp;
        }
        return "ok";
    }

    //Métodos de Tranferencia de bem (movimentação dos bens nos setores)
    public String preparaTransferencia() {
    	authUser = usuarioDao.get(authUser);
    	departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        bens = new ArrayList<Patrimonio>();
        
    	if(patrimonio.getId() != null) {    		
    		patrimonio = patrimonioDao.getById(patrimonio.getId());
    		bens.add(patrimonio);
    	} else if(patrimoniosId != null && patrimoniosId.length > 0) {
    		for (Long id : patrimoniosId) {
				bens.add(patrimonioDao.getById(id));
    		}
    	} else {
    		msgErro = "Selecione um bem para fazer a transferência";
    		itensTombados = patrimonioDao.listAll(authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
    		return "invalid";
    	}
        
        return "ok";
    }

    public String verTransferencia() {
        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
        estadosDepre = estadoDepreciacaoDao.listAll();
        transferenciaPatrimonio = transferenciaPatrimonioDao.getById(transferenciaPatrimonio.getId());
        bens = transferenciaPatrimonio.getBens();
        return "ok";
    }

    public String transfere() {
    	bens = new ArrayList<Patrimonio>();
    	for (Long id : patrimoniosId) {
			bens.add(patrimonioDao.getById(id));
		}
    	
    	transferenciaPatrimonio.setSetorOrigem(bens.get(0).getDepartamento());        
        transferenciaPatrimonio.setBens(bens);        
        transferenciaPatrimonio.setDataSaida(new Date());
        authUser = usuarioDao.get(authUser);
        transferenciaPatrimonio.setResponsavelEnvio(authUser);
        transferenciaPatrimonioDao.save(transferenciaPatrimonio);
        
        for (Patrimonio p : bens) {
			p.setLocalizacao(localizacao);
			p.setDepartamento(transferenciaPatrimonio.getSetorDestino());
			patrimonioDao.save(p);
		}        
        transferenciaPatrimonio = transferenciaPatrimonioDao.getById(transferenciaPatrimonio.getId());
        message = "O bem foi transferido de setor com sucesso.";
        return "ok";
    }

    public void validateTransfere(ValidationErrors errors) {
        if (transferenciaPatrimonio.getSetorDestino().getId().equals(0L)) {
            errors.add(new Message("aviso", "O setor de destino precisa ser infomado."));
        }
        
        if (transferenciaPatrimonio.getResponsavelRecebimento().getId().equals(0L)) {
            errors.add(new Message("aviso", "Um responsável pelo recebimento precisa ser informado."));
        }
        
        if(errors.size() > 0) {        
	        authUser = usuarioDao.get(authUser);
	        departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());    
	        Usuario usuario = new Usuario();
	        servidores = usuario.list(usuarioDao.listAll(), authUser.getUsuarioDepartamentoAtivo().getAlmoxarifado());
        }        
    }
    
    public String comprovanteTransferenciaForm() {
    	departamentos = departamentoDao.listByUnidade(authUser.getUsuarioDepartamentoAtivo().getDepartamento().getUnidade());
    	return "ok";
    }
    
    public String loadTransferencias() {
    	transferencias = transferenciaPatrimonioDao.listByDestino(departamento.getId());
    	return "ok";
    }

}