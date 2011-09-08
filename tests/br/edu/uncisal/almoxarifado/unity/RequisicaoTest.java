package br.edu.uncisal.almoxarifado.unity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.uncisal.almoxarifado.model.Almoxarifado;
import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Item;
import br.edu.uncisal.almoxarifado.model.NotaEntrada;
import br.edu.uncisal.almoxarifado.model.Requisicao;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.almoxarifado.model.UsuarioDepartamento;

public class RequisicaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequerer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarNotaSaida() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddItemRequisicao() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemItemRequisicao() {
		fail("Not yet implemented");
	}

	@Test
	public void testBloquear() {
		fail("Not yet implemented");
	}

	@Test
	public void testAutorizar() {
		fail("Not yet implemented");
	}

	@Test
	public void testEntregar() {
		fail("Not yet implemented");
	}

	@Test
	//FIXME Ainda falta colocar os valores unitários, lotes e validades na nota de entrada.
	public void testTransferir() {
		Almoxarifado aUsuario = mock(Almoxarifado.class);
		when(aUsuario.getId()).thenReturn(2L);
		UsuarioDepartamento ud = mock(UsuarioDepartamento.class);
		when(ud.getAlmoxarifado()).thenReturn(aUsuario);				
		Usuario u = mock(Usuario.class);
		when(u.getId()).thenReturn(1L);
		when(u.getUsuarioDepartamentoAtivo()).thenReturn(ud);		
		Almoxarifado a = mock(Almoxarifado.class);
		when(a.getId()).thenReturn(1L);
		Departamento d = mock(Departamento.class);
		when(d.getId()).thenReturn(1L);
		
		Requisicao r = new Requisicao();
		r.addItemRequisicao(new Item(12345L), new BigDecimal("10"));
		r.addItemRequisicao(new Item(54321L), new BigDecimal("5"));
		r.setUsuario(u);
		r.setAlmoxarifado(a);
		r.setDepartamento(d);
		
		r.gerarNotaSaida("Nota de teste");
		
		assertTrue(
				r.getItensEnviados().get(0).getQuantidade().equals(new BigDecimal("10")) &&
				r.getItensEnviados().get(1).getQuantidade().equals(new BigDecimal("5"))
		);
		
		assertTrue("O almoxarifado deve ser igual ao du usuário",
				r.getAlmoxarifadoRequisitante().getId().equals(2L)
		);
		
		
		NotaEntrada n = r.transferir();
		
		assertTrue(r.getAlmoxarifadoRequisitante() == null);
		
		assertTrue("Os itens de entrada não são os mesmos dos de saída",
			n.getItensEntrada().get(0).getItem().getId().equals(12345L) &&
			n.getItensEntrada().get(0).getQuantidade().equals(new BigDecimal(10)) &&
			n.getItensEntrada().get(1).getItem().getId().equals(54321L) &&
			n.getItensEntrada().get(1).getQuantidade().equals(new BigDecimal(5))
		);
		
		assertTrue("Almoxarifado na nota de saída não está correto",
				n.getAlmoxarifado().getId().equals(2L)
		);
		
		assertTrue("O tipo de nota de entrada não está correto",
			n.getTipoEntrada().getId().equals(4L)
		);
	}

}
