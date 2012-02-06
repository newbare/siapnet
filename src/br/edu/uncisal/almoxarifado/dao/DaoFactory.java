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

package br.edu.uncisal.almoxarifado.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.edu.uncisal.almoxarifado.util.HibernateUtil;
import br.edu.uncisal.patrimonio.dao.AutorizacaoSaidaBensDao;
import br.edu.uncisal.patrimonio.dao.EstadoDepreciacaoDao;
import br.edu.uncisal.patrimonio.dao.MarcaDao;
import br.edu.uncisal.patrimonio.dao.PatrimonioDao;
import br.edu.uncisal.patrimonio.dao.TermoResponsabilidadeDao;
import br.edu.uncisal.patrimonio.dao.TransferenciaPatrimonioDao;

public class DaoFactory {

    private final Session session;
    private Transaction transaction;
    
    public DaoFactory() {
    	session = HibernateUtil.getSession("hibernate.cfg.xml");
    }

    public DaoFactory(final String path) {
        session = HibernateUtil.getSession(path);
    }

    public void beginTransaction() {
        this.transaction = this.session.beginTransaction();
    }

    public void commit() {
        this.transaction.commit();
        this.transaction = null;
    }

    public boolean hasTransaction() {
        return this.transaction != null;
    }

    public void rollback() {
        this.transaction.rollback();
        this.transaction = null;
    }

    public void close() {
        this.session.close();
    }

    public DepartamentoDao getDepartamentoDao() {
        return new DepartamentoDao(this.session);
    }

    public AlmoxarifadoDao getAlmoxarifadoDao() {
        return new AlmoxarifadoDao(this.session);
    }

    public EnderecoDao getEnderecoDao() {
        return new EnderecoDao(this.session);
    }

    public UfDao getUfDao() {
        return new UfDao(this.session);
    }

    public TipoMedidaDao getTipoMedidaDao() {
        return new TipoMedidaDao(this.session);
    }

    public TipoEntradaDao getTipoEntradaDao() {
        return new TipoEntradaDao(this.session);
    }

    public GrupoDao getGrupoDao() {
        return new GrupoDao(this.session);
    }

    public ItemDao getItemDao() {
        return new ItemDao(this.session);
    }

    public FornecedorDao getFornecedorDao() {
        return new FornecedorDao(this.session);
    }

    public RequisicaoDao getRequisicaoDao() {
        return new RequisicaoDao(this.session);
    }

    public NotaEntradaDao getNotaEntradaDao() {
        return new NotaEntradaDao(this.session);
    }

    public ItemEntradaDao getItemEntradaDao() {
        return new ItemEntradaDao(this.session);
    }

    public ItemEstoqueDao getItemEstoqueDao() {
        return new ItemEstoqueDao(this.session);
    }

    public ItemSaidaDao getItemSaidaDao() {
        return new ItemSaidaDao(this.session);
    }

    public StatusDao getStatusDao() {
        return new StatusDao(this.session);
    }

    public UsuarioDao getUsuarioDao() {
        return new UsuarioDao(this.session);
    }

    public RecursoDao getRecursoDao() {
        return new RecursoDao(this.session);
    }

    public PerfilDao getPerfilDao() {
        return new PerfilDao(this.session);
    }

    public RelatorioDao getRelatorioDao() {
        return new RelatorioDao(this.session);
    }

    public ParametroRelatorioDao getParametroRelatorioDao() {
        return new ParametroRelatorioDao(this.session);
    }

    public TipoFornecedorDao getTipoFornecedorDao() {
        return new TipoFornecedorDao(this.session);
    }

    /**  Package patrimonio.dao */
    public PatrimonioDao getPatrimonioDao() {
        return new PatrimonioDao(this.session);
    }

    public MarcaDao getMarcaDao() {
        return new MarcaDao(this.session);
    }

    public EstadoDepreciacaoDao getEstadoDepreciacaoDao() {
        return new EstadoDepreciacaoDao(this.session);
    }

    public TermoResponsabilidadeDao getTermoResponsabilidadeDao() {
        return new TermoResponsabilidadeDao(this.session);
    }

    public AutorizacaoSaidaBensDao getAutorizacaoSaidaBensDao() {
        return new AutorizacaoSaidaBensDao(this.session);
    }

    public AuditanteDao getAuditanteDao() {
        return new AuditanteDao(this.session);
    }

    public TransferenciaPatrimonioDao getTransferenciaPatrimonioDao() {
        return new TransferenciaPatrimonioDao(this.session);
    }

    public UnidadeDao getUnidadeDao() {
        return new UnidadeDao(this.session);
    }

    public TipoSaidaDao getTipoSaidaDao() {
        return new TipoSaidaDao(this.session);
    }

    public OrgaoDao getOrgaoDao() {
        return new OrgaoDao(this.session);
    }

    public UsuarioDepartamentoDao getUsuarioDepartamentoDao() {
        return new UsuarioDepartamentoDao(this.session);
    }

    public CancelamentoRequisicaoDao getCancelamentoRequisicaoDao() {
    	return new CancelamentoRequisicaoDao(this.session);
    }

}
