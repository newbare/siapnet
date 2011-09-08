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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.edu.uncisal.almoxarifado.model.Departamento;
import br.edu.uncisal.almoxarifado.model.Unidade;
import br.edu.uncisal.almoxarifado.model.Usuario;
import br.edu.uncisal.almoxarifado.model.UsuarioDepartamento;

@SuppressWarnings("unchecked")
public class UsuarioDao extends Dao<Usuario> {

	UsuarioDao(Session session) {
		super(session, Usuario.class);
	}

	public List<Usuario> login(Usuario vo) {
		if (!"".equals(vo.getCpf())) {
			Query query = session
					.createQuery(" select u from Usuario u where cpf=:cpf and senha=:senha ");
			query.setParameter("cpf", vo.getCpf());
			query.setParameter("senha", vo.getSenha());
			return query.list();
		}
		return new ArrayList<Usuario>();
	}

	@Override
	public List<Usuario> listAll() {
		Criteria crit = session.createCriteria(Usuario.class);
		crit.addOrder(Order.asc("nome"));
		return crit.list();
	}

	/** @TODO deixar a busca mais completa */
	public List<Usuario> buscar(Usuario user, Unidade unidade, boolean desativado) {
        String sql = "select distinct(u.*)" +
        		" from almoxarifado.Usuario u" +
        		" inner join almoxarifado.usuario_departamento ud ON (u.id = ud.usuario_id)" +
        		" inner join almoxarifado.departamento d ON (ud.departamento_id = d.id)" +
        		" where";
        
        if (desativado) sql+= " deleted = 'true'"; else sql += " deleted = 'false'"; 
        if (unidade != null) sql += " and d.unidade_id="+unidade.getId();
        if (user.getNome()!= null && !user.getNome().equals(""))sql+=" and sem_acentos(u.nome) ilike sem_acentos('%"+user.getNome()+"%')";
        if (user.getCpf()!=null && !user.getCpf().equals(""))sql+=" and u.cpf ilike '"+user.getCpf()+"'";
        if (user.getEmail()!=null && !user.getEmail().equals(""))sql+=" and u.email ilike '"+user.getEmail()+"'";
        if (user.getFuncao()!=null && !user.getFuncao().equals(""))sql+=" and sem_acentos(funcao) ilike sem_acentos('%"+user.getFuncao()+"%')";

        SQLQuery squery = session.createSQLQuery(sql);
        squery.addEntity(Usuario.class);
		return squery.list();
	}
	
	public Collection<Usuario> loadByDepartamento(Departamento d) {		
		 String sql = "select distinct(u.*)" +
 		" from almoxarifado.Usuario u" +
 		" inner join almoxarifado.usuario_departamento ud ON (u.id = ud.usuario_id)" +
 		" inner join almoxarifado.departamento d ON (ud.departamento_id = d.id)" +
 		" where deleted = 'false' and d.id = " + d.getId() +
 		" order by u.nome";
		 
		SQLQuery squery = session.createSQLQuery(sql);
	    squery.addEntity(Usuario.class);
		return squery.list();
	}
	
	public void reabilitar(Usuario u) {
		String sql = "UPDATE almoxarifado.usuario SET deleted=false WHERE id=" + u.getId();		 
		SQLQuery squery = session.createSQLQuery(sql);
	    squery.addEntity(Usuario.class);
		squery.executeUpdate();
	}
	
	public void remove(Usuario u) {
		String sql = "UPDATE almoxarifado.usuario SET deleted=true WHERE id=" + u.getId();
		SQLQuery squery = session.createSQLQuery(sql);
	    squery.addEntity(Usuario.class);
		squery.executeUpdate();
	}
	
	public Usuario get(Usuario usuario) {
		String ip = usuario.getIp();
        UsuarioDepartamento uDp = usuario.getUsuarioDepartamentoAtivo();
        Usuario usuarioNovo = (Usuario) session.get(Usuario.class, usuario.getId());
        usuarioNovo.setIp(ip);
        usuarioNovo.setUsuarioDepartamentoAtivo(uDp);
		return usuarioNovo;
	}

}
