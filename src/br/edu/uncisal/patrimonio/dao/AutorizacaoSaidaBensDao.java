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

package br.edu.uncisal.patrimonio.dao;

import org.hibernate.Session;

import br.edu.uncisal.almoxarifado.dao.Dao;
import br.edu.uncisal.patrimonio.model.AutorizacaoSaidaBens;

/**
 *
 * @author augusto
 */
public class AutorizacaoSaidaBensDao extends Dao<AutorizacaoSaidaBens> {

    public AutorizacaoSaidaBensDao(Session session) {
        super(session, AutorizacaoSaidaBens.class);
    }

}