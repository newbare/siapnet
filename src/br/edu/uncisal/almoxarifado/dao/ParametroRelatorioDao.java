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

import java.util.List;

import org.hibernate.Session;

import br.edu.uncisal.almoxarifado.model.ParametroRelatorio;
import br.edu.uncisal.almoxarifado.model.Relatorio;

/**
 *
 * @author Augusto Oliveira
 */
public class ParametroRelatorioDao extends Dao<ParametroRelatorio> {

    ParametroRelatorioDao(Session session) {
        super(session, ParametroRelatorio.class);
    }

    @SuppressWarnings("unchecked")
    public ParametroRelatorio getByNome(ParametroRelatorio pr) {
        StringBuffer sql = new StringBuffer(" SELECT ");
        sql.append("pr.id, ");              // 0
        sql.append("pr.nome, ");            // 1
        sql.append("pr.tipo, ");            // 2
        sql.append("pr.titulo, ");          // 3
        sql.append("relatorio_id, ");       // 4
        sql.append("rel.arquivojasper, ");  // 5
        sql.append("rel.titulo ");          // 6
        sql.append(" FROM almoxarifado.Parametro_Relatorio pr, almoxarifado.relatorio rel ");
        sql.append(" WHERE pr.nome LIKE '" + pr.getNome() + "' and rel.arquivoJasper like '" + pr.getRelatorio().getArquivoJasper() + "' and pr.relatorio_id = rel.id");
        List resultado = session.createSQLQuery(sql.toString()).list();
        Relatorio rel = new Relatorio();
        ParametroRelatorio pr1 = new ParametroRelatorio();
        for (int i = 0; i < resultado.size(); i++) {
            //Retorna cada linha da tabela
            Object[] linha = (Object[]) resultado.get(i);
            rel = new Relatorio();
            rel.setId(new Long(linha[4].toString()));
            rel.setArquivoJasper(linha[5].toString());
            rel.setTitulo(linha[6].toString());
            pr1 = new ParametroRelatorio(linha[1].toString(), rel);
            pr1.setId(new Long(linha[0].toString()));
            pr1.setTipo(linha[2].toString());
            pr1.setTitulo(linha[3].toString());
        }
        return pr1;
    }
}