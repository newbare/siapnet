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

import java.util.Comparator;

/**
 * Faz a comparação utilizando as classes de movimento nota de entrada e nota de saída.
 * @author Igor Cavalcante
 *
 */
public class MovimentoComparator implements Comparator<Datable> {

	@Override
	public int compare(Datable o1, Datable o2) {
		return o1.getData().compareTo(o2.getData());		
	}

}
