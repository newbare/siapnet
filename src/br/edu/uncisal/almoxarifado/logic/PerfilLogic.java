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

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Parameter;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;
import br.edu.uncisal.almoxarifado.dao.PerfilDao;
import br.edu.uncisal.almoxarifado.dao.RecursoDao;
import br.edu.uncisal.almoxarifado.model.Perfil;
import br.edu.uncisal.almoxarifado.model.Recurso;
import br.edu.uncisal.almoxarifado.model.Usuario;

@SuppressWarnings("unused")
@Component("perfil")
@InterceptedBy({DaoInterceptor.class, AutorizadorInterceptor.class})
public class PerfilLogic {

    @Parameter(create = true)
    @Out
    private Perfil perfil;
    @Parameter(key = "recursosEscolhidos")
    private String[] recursosEscolhidos;
    @Out
    private Collection<Perfil> perfis;
    @Out
    private List<Recurso> recursos;

    private PerfilDao perfilDao;
    private RecursoDao recursoDao;

    public PerfilLogic(DaoFactory daoFactory, Usuario authUser) {
        perfilDao = daoFactory.getPerfilDao();
        recursoDao = daoFactory.getRecursoDao();
    }     

    public String formulario() {
        recursos = recursoDao.listarRaizes();
        return "ok";
    }

    public String gravar() {
        List<Recurso> recursos = null;
        if (recursosEscolhidos.length > 0) {
            recursos = new ArrayList<Recurso>();
            for (String r : recursosEscolhidos) {
                recursos.add(new Recurso(new Long(r)));
            }
        }

        perfil.setRecursos(recursos);
        perfilDao.save(perfil);
        return "ok";
    }

    public String listAll() {
        perfis = perfilDao.listAll();
        return "ok";
    }

    public String remove() {
        perfilDao.remove(perfil);
        return "ok";
    }

    public String get() {
        perfil = perfilDao.getById(perfil.getId());
        recursos = recursoDao.listarRaizes();
        return "ok";
    }
}