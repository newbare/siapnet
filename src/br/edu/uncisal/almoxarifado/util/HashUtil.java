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

package br.edu.uncisal.almoxarifado.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Paul Barry
 */
public class HashUtil {

    private static final char[] hexChars = {
        '0','1','2','3','4','5','6','7',
        '8','9','a','b','c','d','e','f'};

    public static String SHA(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(s.getBytes(), 0, s.getBytes().length);
            byte[] hash = md.digest();
            StringBuilder sb = new StringBuilder();
            int msb;
            int lsb = 0;
            int i;
            for (i = 0; i < hash.length; i++) {
                msb = ((int)hash[i] & 0x000000FF) / 16;
                lsb = ((int)hash[i] & 0x000000FF) % 16;
                sb.append(hexChars[msb]);
                sb.append(hexChars[lsb]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
}

