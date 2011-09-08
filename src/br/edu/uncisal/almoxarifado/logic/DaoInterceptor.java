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

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.Out;
import org.vraptor.view.ViewException;

import br.edu.uncisal.almoxarifado.dao.DaoFactory;

public class DaoInterceptor implements Interceptor {

    private final DaoFactory factory = new DaoFactory();

    @Override
    public void intercept(LogicFlow flow) throws LogicException, ViewException {
    	factory.beginTransaction();
    	
        flow.execute();

        try {
        	if(factory.hasTransaction())
        		factory.commit();
        } catch (Exception e) {
        	factory.rollback();
        } finally {
            factory.close();
        }
    }

    @Out(key = "br.edu.uncisal.almoxarifado.dao.DaoFactory")
    public DaoFactory getFactory() {
        return factory;
    }
}
