package br.edu.uncisal.almoxarifado.integration;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.ItemEstoqueDao;
import br.edu.uncisal.almoxarifado.dao.NotaEntradaDao;
import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Fornecedor;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.ItemEntrada;
import br.edu.uncisal.almoxarifado.model.ItemEstoque;
import br.edu.uncisal.almoxarifado.model.NotaEntrada;
import br.edu.uncisal.almoxarifado.util.EstoqueException;

public class NotaEntradaTest {

	private DaoFactory factory;
	private NotaEntradaDao dao;
	private ItemEstoqueDao itemEstoqueDao;
	
	@Before
	public void setUp() throws Exception {
		factory = new DaoFactory("hibernate.test.cfg.xml");
		factory.beginTransaction();
		dao = factory.getNotaEntradaDao();
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

	@Test
	public void testAddItemEntrada() {		
		NotaEntrada n = criarNotaEntrada();
		Integer size = n.getItensEntrada().size();
		n.addItemEntrada(new ItemEntrada(new Item(100L), new BigDecimal("1"), new BigDecimal("1")));
		assertTrue(n.getItensEntrada().size() == size + 1);
		
	}

	@Test
	public void testRemItemEntrada() {
		NotaEntrada n = criarNotaEntrada();
		Integer size = n.getItensEntrada().size();
		n.remItemEntrada(new ItemEntrada(new Item(22L)));		
		assertTrue(n.getItensEntrada().size() == size - 1);
		assertTrue(n.getItensEntrada().indexOf(new ItemEntrada(new Item(22L))) == -1);
	}
	
	/**
	 * Simular valor negativo
	 * Simular notas com itens duplicados mas com lotes diferentes.
	 * @return
	 */
	private NotaEntrada criarNotaEntrada() {
		NotaEntrada n = new NotaEntrada();
		n.setAlmoxarifado(new Almoxarifado(1L));
		n.addItemEntrada(new ItemEntrada(new Item(4030L), new BigDecimal("10")));
		n.addItemEntrada(new ItemEntrada(new Item(22L), new BigDecimal("2")));
		n.addItemEntrada(new ItemEntrada(new Item(6823L), new BigDecimal("10")));
		n.setFornecedor(new Fornecedor(1L));
		n.setNumero("12345");
		
		return n;
	}
	
	private NotaEntrada getUltimaNota() {
		//Pega última nota de entrada no banco de dados;
		List<NotaEntrada> notas = dao.listByAlmoxarifado(new Almoxarifado(1L));
		return notas.get(notas.size() -1);
	}
	
	//Implementa uma operação de adição de nota de entrada no sistema.
	@Test
	public void gravarTest() {
		try {
			BigDecimal e1 = itemEstoqueDao.findByExample(new Item(4030L), new Almoxarifado(1L)).getEstoque();
			BigDecimal e2 = itemEstoqueDao.findByExample(new Item(22L), new Almoxarifado(1L)).getEstoque();
			
			assertTrue(itemEstoqueDao.findByExample(new Item(6823L), new Almoxarifado(1L)) == null);
			
			NotaEntrada n = criarNotaEntrada();
			
			List<ItemEstoque> itensEstoque = n.efetuarEntrada(null, itemEstoqueDao.listByAlmoxarifado(n.getAlmoxarifado()));
			
			//confere o valor dos itens estoque.
			assertTrue(itensEstoque.size() == 3);
			assertTrue(itensEstoque.get(0).getEstoque().equals(e1.add(new BigDecimal("10"))));
			assertTrue(itensEstoque.get(1).getEstoque().equals(e2.add(new BigDecimal("2"))));
			assertTrue(itensEstoque.get(2).getEstoque().equals(new BigDecimal("10")));
			
			for (ItemEstoque itemEstoque : itensEstoque) {
				if(itemEstoque.getId() == null)
					itemEstoqueDao.add(itemEstoque);
				else
					itemEstoqueDao.update(itemEstoque);
			}
			
			dao.add(n);			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}	
	
	//Implementa uma operação de atualização de nota de entrada no sistema.
	@Test
	public void atualizarTest() throws Exception {				
		BigDecimal e1 = itemEstoqueDao.findByExample(new Item(4030L), new Almoxarifado(1L)).getEstoque();
		BigDecimal e2 = itemEstoqueDao.findByExample(new Item(22L), new Almoxarifado(1L)).getEstoque();
		BigDecimal e3 = itemEstoqueDao.findByExample(new Item(6823L), new Almoxarifado(1L)).getEstoque();
		
		NotaEntrada n = getUltimaNota();
		
		n.setNumero("12345");
		
		n.getItensEntrada().get(0).setQuantidade(new BigDecimal("5"));			
		
		ItemEntrada ie = n.getItensEntrada().get(1);	
		n.remItemEntrada(ie);
		//itemEntradaDao.remove(ie);
		
		dao.evict(n);
		
		NotaEntrada nAntiga =  dao.getById(n.getId());
		
		List<ItemEstoque> itensEstoque = n.efetuarEntrada(nAntiga, itemEstoqueDao.listByAlmoxarifado(n.getAlmoxarifado()));
		dao.evict(nAntiga);
		nAntiga = null;
		
		//confere o valor dos itens estoque.
		assertTrue(itensEstoque.size() == 3);
		assertTrue(itensEstoque.get(0).getEstoque().equals(e1.subtract(new BigDecimal("5"))));
		assertTrue(itensEstoque.get(2).getEstoque().equals(e2.subtract(new BigDecimal("2")))); //A ordem está trocada por causa do algorítmo da Nota de Entrada.
		assertTrue(itensEstoque.get(1).getEstoque().equals(e3));
		
		for (ItemEstoque itemEstoque : itensEstoque) {
			if(itemEstoque.getId() == null)
				itemEstoqueDao.add(itemEstoque);
			else
				itemEstoqueDao.update(itemEstoque);
		}
		
		dao.update(n);
		dao.evict(n);
	}
	
	//Elaborar um cenário onde se edite uma nota de entrada e o estoque não seja suficiente.
	@Test(expected = EstoqueException.class)
	public void editEstoqueInsuficiente() throws EstoqueException {
		BigDecimal e1 = itemEstoqueDao.findByExample(new Item(4030L), new Almoxarifado(1L)).getEstoque();
		BigDecimal e2 = itemEstoqueDao.findByExample(new Item(22L), new Almoxarifado(1L)).getEstoque();
		BigDecimal e3 = itemEstoqueDao.findByExample(new Item(6823L), new Almoxarifado(1L)).getEstoque();
		
		NotaEntrada n = getUltimaNota();
		n.getItensEntrada().get(0).setQuantidade(new BigDecimal("1"));
		
		dao.evict(n);
		
		NotaEntrada nAntiga =  dao.getById(n.getId());
		
		List<ItemEstoque> ies = itemEstoqueDao.listByAlmoxarifado(n.getAlmoxarifado());
		for (ItemEstoque ie : ies) {
			ie.setEstoque(new BigDecimal("0"));
		}
		
		List<ItemEstoque> itensEstoque = n.efetuarEntrada(nAntiga, ies);		
	}	
	
	@Test
	public void buscar() {
		NotaEntrada n = criarNotaEntrada();
		Collection<NotaEntrada> notas = dao.list(n);
		assertTrue(notas.size() > 0);		
	}
	
	@Test
	public void listAll() {
		assertTrue(dao.listByAlmoxarifado(new Almoxarifado(1L)).size() > 0);		
	}
	
	@Test
	public void testGet() {
		NotaEntrada n = dao.getById(getUltimaNota().getId());
		//assertTrue(n.getItensEntrada().size() == 2);
		assertTrue(n.getAlmoxarifado().getId() == 1L);
	}
	
	@Test
	public void removeTest() throws Exception {
		BigDecimal e1 = itemEstoqueDao.findByExample(new Item(4030L), new Almoxarifado(1L)).getEstoque();
		
		NotaEntrada n = getUltimaNota();			
		
		List<ItemEstoque> itensEstoque = n.remove(itemEstoqueDao.listByAlmoxarifado(n.getAlmoxarifado()));
		
		//confere o valor dos itens estoque.			
		assertTrue(itensEstoque.get(0).getEstoque().equals(e1.subtract(new BigDecimal("5"))));
		
		dao.remove(n);
		
		//Remove também o ItemEstoque novo para os testes posteriores.
		itemEstoqueDao.remove(itemEstoqueDao.findByExample(new Item(6823L), new Almoxarifado(1L)));
	}

}
