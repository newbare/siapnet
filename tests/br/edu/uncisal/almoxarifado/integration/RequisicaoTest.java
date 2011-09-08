package br.edu.uncisal.almoxarifado.integration;


import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.ItemEstoqueDao;
import br.edu.uncisal.almoxarifado.dao.RequisicaoDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemRequisicao;
import br.edu.uncisal.almoxarifado.model.ItemSaida;
import br.edu.uncisal.almoxarifado.model.NotaEntrada;
import br.edu.uncisal.almoxarifado.model.Requisicao;
import br.edu.uncisal.almoxarifado.model.TipoSaida;
import br.edu.uncisal.almoxarifado.model.TipoStatus;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.almoxarifado.model.UsuarioDepartamento;

public class RequisicaoTest {

	private DaoFactory factory;
	private RequisicaoDao dao;
	private ItemEstoqueDao itemEstoqueDao;
	
	@Before
	public void setUp() throws Exception {
		factory = new DaoFactory("hibernate.test.cfg.xml");
		factory.beginTransaction();
		dao = factory.getRequisicaoDao();
		itemEstoqueDao = factory.getItemEstoqueDao();
	}

	@After
	public void tearDown() throws Exception {
		try {
			if(factory.hasTransaction())
				factory.commit();
        } catch (Exception e) {
        	factory.rollback();
        } finally {
            factory.close();
        }
	}
	
	private Requisicao criaRequisicao() {
		Usuario u = new Usuario(2L);
		Departamento d = new Departamento(321L);
		Almoxarifado a = new Almoxarifado(14L);
		TipoSaida ts = new TipoSaida(1L);		
		
		Requisicao r = new Requisicao();
		r.setAlmoxarifado(a);
		r.setDepartamento(d);
		r.setUsuario(u);
		r.setTipoSaida(ts);
		r.addItemRequisicao(new Item(4030L), new BigDecimal("10"));
		r.addItemRequisicao(new Item(22L), new BigDecimal("2"));
		
		return r;
	}
	
	private Requisicao getUltimaRequisicao() {
		List<Requisicao> rs = dao.listAll();
		return rs.get(rs.size() -1);
	}
	
	@Test
	public void notaSaidaGravarTest() {
		Requisicao r = criaRequisicao();
		
		r.gerarNotaSaida("Teste de integração");
		
		assertTrue(r.getItensEnviados().size() == 2);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.APROVADO));
		
		dao.add(r);
		dao.remove(r);		
	}
	
	@Test
	/**
	 * Testa uma requisicao simples.
	 */
	public void requererTest() {
		Requisicao r = criaRequisicao();
		r.requerer();
		assertTrue(r.getItensRequisitados().size() == 2);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.AGUARDANDO));
		
		dao.add(r);
	}
	
	@Test
	/**
	 * Testa uma requisicao de um almoxarife de outro almoxarifado.
	 */
	public void requererAlmoxarifadoExternoTest() {
		UsuarioDepartamento ud = new UsuarioDepartamento();
		ud.setAlmoxarifado(new Almoxarifado(1L));
		Requisicao r = criaRequisicao();
		r.getUsuario().setUsuarioDepartamentoAtivo(ud);
		r.requerer();
		assertTrue(r.getItensRequisitados().size() == 2);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.AGUARDANDO));
		assertTrue(r.getAlmoxarifadoRequisitante().getId().equals(1L));
		assertTrue(!r.getAlmoxarifadoRequisitante().getId().equals(r.getAlmoxarifado().getId()));
		
		dao.add(r);
		dao.remove(r);	
	}
	
	@Test
	public void addItemTest() {
		Requisicao r = criaRequisicao();
		r.remItemRequisicao(new Item(4030L));
		assertTrue(r.getItensRequisitados().size() == 1);
		assertTrue(!r.getItensRequisitados().contains(new ItemRequisicao(new Item(4030L))));
		assertTrue(r.getItensRequisitados().contains(new ItemRequisicao(new Item(22L))));
	}
	
	@Test
	//FIXME verificar pq a listagem de requisições não está sendo feita na ordem natural
	public void testListAguardando() {
		List<Requisicao> rs = dao.findByStatus(TipoStatus.AGUARDANDO, new Almoxarifado(1L));
		for (Requisicao r : rs) {
			assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.AGUARDANDO));
		}
		Requisicao u = getUltimaRequisicao();
		
		assertTrue(rs.contains(u));		
	}
	
	@Test
	public void testListAprovadas() {
		List<Requisicao> rs = dao.findByStatus(TipoStatus.APROVADO, new Almoxarifado(1L)); 
		for (Requisicao r : rs) {
			assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.APROVADO));
		}		
	}
	
	@Test
	public void testListEntregues() {
		List<Requisicao> rs = dao.findByStatus(TipoStatus.ENTEGUE, new Almoxarifado(1L)); 
		for (Requisicao r : rs) {
			assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.ENTEGUE));
		}		
	}
	
	@Test
	public void bloquearTest() {
		Requisicao r = criaRequisicao();
		r.requerer();
		dao.add(r);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.AGUARDANDO));
		r.bloquear("Requisição bloqueada");
		dao.update(r);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.BLOQUEADO));
		//dao.remove(r);
		
	}
	
	@Test
	public void autorizarTest() {
		Requisicao r = criaRequisicao();
		r.requerer();
		dao.add(r);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.AGUARDANDO));
		
		List<ItemSaida> ies = new ArrayList<ItemSaida>();
		for (ItemRequisicao ir : r.getItensRequisitados()) {
			ies.add(new ItemSaida(ir.getItem().getId(), ir.getQtdRequisitada()));
		}
		
		r.autorizar(ies, "Requisição autorizada");
		dao.update(r);
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.APROVADO));		
	}
	
	@Test
	public void entregarTest() {
		Requisicao r = getUltimaRequisicao();
		assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.APROVADO));
		r.entregar();
        dao.update(r);
        assertTrue(r.getStatusAtual().getTipoStatus().getId().equals(TipoStatus.ENTEGUE));
	}
	
	@Test
	public void listarAcompanhar() {
		List<Requisicao> rs = dao.listarParaAcompanhar(new Usuario(2L));
		assertTrue(rs.size() > 0);		
	}
	
	@Test
	public void transferirTest() {
		Requisicao r = criaRequisicao();
		Usuario u = r.getUsuario();
		UsuarioDepartamento ud = new UsuarioDepartamento();
		ud.setAlmoxarifado(new Almoxarifado(1L));	
		u.setUsuarioDepartamentoAtivo(ud);
		r.requerer();
		dao.add(r);
		assertTrue(r.getAlmoxarifadoRequisitante().getId().equals(1L));
				
		List<ItemSaida> ies = new ArrayList<ItemSaida>();
		for (ItemRequisicao ir : r.getItensRequisitados()) {
			ies.add(new ItemSaida(ir.getItem().getId(), ir.getQtdRequisitada()));
		}
		
		r.autorizar(ies, "Requisição autorizada");
		dao.update(r);
		
		NotaEntrada n = r.transferir();
		dao.update(r);
		assertTrue(r.getAlmoxarifadoRequisitante() == null);
		assertTrue(n.getAlmoxarifado().getId().equals(1L));
		assertTrue(n.getFornecedor().getId().equals(0L));
		assertTrue(n.getTipoEntrada().getId().equals(4L));
		assertTrue(n.getItensEntrada().size() == 2);
	}
	
	
}
