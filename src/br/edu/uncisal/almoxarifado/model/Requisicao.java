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

package br.edu.uncisal.almoxarifado.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uncisal.almoxarifado.util.EstoqueException;

/**
 * Requisição feita por um setor para o almoxarifado.
 * @author Igor Cavalcante
 * @see Requisicao
 * @see Item
 * @see Usuario
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_requisicao")
public class Requisicao extends Domain implements Serializable, Datable {

    private static final long serialVersionUID = -4217784904897171491L;
    /**
     * Tempo em DIAS que uma requisição deve permanecer no estado de espera.
     */
    public static final int TEMPO_ESPERA = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @ManyToOne
    private Almoxarifado almoxarifado;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    @JoinColumn(name = "tipo_saida_id")
    private TipoSaida tipoSaida;
    /**
     * Estado em que a requisição se encontra atualmente.
     */
    @ManyToOne
    @JoinColumn(name = "status_atual_id")
    private Status statusAtual;
    /**
     * Coleção de status ao qual a requisição já foi submetida
     * guarda os estados da requisição para um futuro acompanhamento 
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "requisicao_id")
    private Collection<Status> status;
    /**
     * Itens que o usuário fez um pedido.
     * Não é contabilizado no estoque.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "requisicao_id")
    private Collection<ItemRequisicao> itensRequisitados;
    /**
     * Itens que o almoxarife autorizou
     * É contabilizado no estoque.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "requisicao_id")
    private List<ItemSaida> itensEnviados;
    
    /**
     * Se for feita uma transferência entre almoxarifados este campo será usado.
     *
     */
    @ManyToOne
    @JoinColumn(name = "almoxarifado_requisitante_id")
    private Almoxarifado almoxarifadoRequisitante;
    
    public Requisicao() {
        itensRequisitados = new ArrayList<ItemRequisicao>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<ItemRequisicao> getItensRequisitados() {
        return itensRequisitados;
    }

    public void setItensRequisitados(Set<ItemRequisicao> itensRequisitados) {
        this.itensRequisitados = itensRequisitados;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Collection<Status> getStatus() {
        return status;
    }

    public Status getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(Status statusAtual) {
        this.statusAtual = statusAtual;
    }

    public void setItensEnviados(List<ItemSaida> itensEnviados) {
        this.itensEnviados = itensEnviados;
    }

    public List<ItemSaida> getItensEnviados() {
        return itensEnviados;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setAlmoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
    }

    public Almoxarifado getAlmoxarifado() {
        return almoxarifado;
    }

    public TipoSaida getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    public void addStatus(Status s) {
    	if(this.getStatus() == null)
    		this.status = new ArrayList<Status>();
    	this.status.add(s);    	
    }

    @Override
    public String toString() {
        return "[id: " + id + " data: " + dataCadastro + " almoxarifado: " + almoxarifado + " usuario: " + usuario + " status atual: " + statusAtual + " status: " + status + " tipo saida: " + (tipoSaida != null ? tipoSaida.getId() : null) + "-" + (tipoSaida != null ? tipoSaida.getNome() : null) + " itens requisitados: " + itensRequisitados + " itens enviados: " + itensEnviados + "]";
    }
    
    public void requerer() {
    	Status s = new Status("Envio de requisição", TipoStatus.AGUARDANDO);
    	this.addStatus(s);
    	this.statusAtual = s;
    	if(usuario.getUsuarioDepartamentoAtivo() != null && usuario.getUsuarioDepartamentoAtivo().getAlmoxarifado() != null) {
	    	if(!usuario.getUsuarioDepartamentoAtivo().getAlmoxarifado().equals(almoxarifado)) {
	    		almoxarifadoRequisitante = usuario.getUsuarioDepartamentoAtivo().getAlmoxarifado();
	    		setTipoSaida(new TipoSaida(2L));
	    	}
    	}
    }
    
    public void gerarNotaSaida(String comentario) {
    	this.requerer();
    	
        List<ItemSaida> is = new ArrayList<ItemSaida>();
        for (ItemRequisicao ir : getItensRequisitados()) {
            ItemSaida i = new ItemSaida();
            i.setItem(ir.getItem());
            i.setQuantidade(ir.getQuantidadeSaida());
            is.add(i);
        }
        
        autorizar(is, comentario, this.getDataCadastro());
    }
    
    public void addItemRequisicao(Item i, BigDecimal qtd) {
    	if(itensRequisitados == null)
    		itensRequisitados = new ArrayList<ItemRequisicao>();
    	
    	ItemRequisicao ir = new ItemRequisicao(i, qtd);    	
    	itensRequisitados.add(ir);
    }
    
    public void remItemRequisicao(Item i) {
        ItemRequisicao irRemovido = null;
        for (ItemRequisicao irNativo : this.getItensRequisitados())
            if(irNativo.getItem().equals(i))
                irRemovido = irNativo;                  

        if (irRemovido == null) {
            throw new InvalidParameterException("Este item não existe.");
        }

        this.getItensRequisitados().remove(irRemovido);    	
    }
    
    /**
     * Método utilitário que muda o status atual da classe.
     */
    private void avaliar(Long aval, String comentario) {
    	Status s = new Status(comentario, aval);
    	this.addStatus(s);
    	this.setStatusAtual(s);
    }
    
    private void avaliar(Long aval, String comentario, Date data) {
    	Status s = new Status(comentario, aval, data);
    	this.addStatus(s);
    	this.setStatusAtual(s);
    }
    
    public void bloquear(String comentario) {
    	avaliar(TipoStatus.BLOQUEADO, comentario);
    }    
    
    public void autorizar(List<ItemSaida> itensEnviados, String comentario) {    	
    	setItensEnviados(itensEnviados);
    	avaliar(TipoStatus.APROVADO, comentario);    	
    }
    
    public void autorizar(List<ItemSaida> itensEnviados, String comentario, Date data) {    	
    	setItensEnviados(itensEnviados);
    	avaliar(TipoStatus.APROVADO, comentario, data);    	
    }
    
    public void entregar() {
    	if(statusAtual.getComentario() == null || statusAtual.getComentario().equals(""))
        	avaliar(TipoStatus.ENTEGUE, "Requisição entregue ao requisitante.");
    	else
    		avaliar(TipoStatus.ENTEGUE, statusAtual.getComentario());
    }
    
    public Almoxarifado getAlmoxarifadoRequisitante() {
		return almoxarifadoRequisitante;
	}
    
    /**
     * Faz uma transferência entre almoxarifados.
     * FIXME ao adicionar o item entrada preencher o valor com o valor real do item e não com valor nulo.
     */
    public NotaEntrada transferir() {
    	NotaEntrada n = new NotaEntrada();
    	n.setAlmoxarifado(almoxarifadoRequisitante);
    	n.setData(new Date());
    	n.setObservacao("Transferência automática de nota de entrada.");
    	n.setFornecedor(new Fornecedor(0L));
    	n.setTipoEntrada(new TipoEntrada(4L));   	
    	
    	for (ItemSaida is : getItensEnviados()) {
			n.addItemEntrada(new ItemEntrada(is.getItem(), is.getQuantidade(), is.getValorUnitario()));
		}
    	
    	almoxarifadoRequisitante = null;
    	return n;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Requisicao other = (Requisicao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	//Este método está vazio somente para preencher os pre-requisitos da interface datable.
	@Override
	public void setData(Date date) {		
	}
	
	@Override
	//Ele tá peagndo a data do status atual para a ver em que momento a requisição foi avaliada pelo almoxarife.
	public Date getData() {
		return getStatusAtual().getDataCadastro();
	}
	
	/**
	 * Cancela uma requisicao depois de aprovada.
	 * @throws EstoqueException 
	 */
	public Collection<Item> cancelar() {
		Collection<Item> itens = new ArrayList<Item>();
		for (ItemSaida isaida : itensEnviados) {
			itens.add(isaida.getItem());
		}
		
		return itens;
	}
	
	/**
	 * Retornar verdadeiro caso a requisição já possui o tipo de status passado.
	 */
	public Boolean verificaStatus(Long tipoStatusId) {
		for (Status s : getStatus()) {
			if(s.getTipoStatus().getId().equals(tipoStatusId))
				return true;
		}
		return false;		
	}
    
}
