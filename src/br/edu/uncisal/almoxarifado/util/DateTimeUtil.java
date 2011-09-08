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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateTimeUtil
{

    static SimpleDateFormat dateFormatterSql = new SimpleDateFormat("yyyy-MM-dd");

    static SimpleDateFormat dateFormatterBr = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat dateTimeFormatterBr = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private DateTimeUtil()
    {
    }

    public static String formateDate(Date dt)
    {
        return dateFormatterBr.format(dt);
    }

    public static Date convertDate(String str)
    {
        if (!validateBrDate(str))
            return null;
        try
        {

            return dateFormatterBr.parse(str);
        } catch (ParseException ex)
        {
            Logger.getLogger(DateTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

      public static Date convertDateTime(String str)
    {
        if (!validateBrDateTime(str))
            return null;
        try
        {

            return dateTimeFormatterBr.parse(str);
        } catch (ParseException ex)
        {
            Logger.getLogger(DateTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String getDateSql()
    {
        return getDateSql(System.currentTimeMillis());
    }

    public static String getDateSql(long currentTimeMillis)
    {
        Date dt = new Date(currentTimeMillis);
        return dt.toString();
    }

    public static String addDaysSql(long currentTimeMillis, int days)
    {
        long day_millis = 86400000 * (long) days;
        Date dt = new Date(currentTimeMillis + day_millis);
        return dt.toString();
    }

    public static String addDaysBr(long currentTimeMillis, int days)
    {
        long day_millis = 86400000 * (long) days;
        Date dt = new Date(currentTimeMillis + day_millis);
        return sqlToBrDate(dt.toString());
    }

    public static String getDateBr()
    {
        Date dt = new Date(System.currentTimeMillis());
        return sqlToBrDate(dt.toString());
    }

    public static String getDateBr(long currentTimeMillis)
    {
        Date dt = new Date(currentTimeMillis);
        return sqlToBrDate(dt.toString());
    }

    public static String getTimeStamp()
    {
        return getTimeStamp(System.currentTimeMillis());
    }

    public static String getTimeStamp(long currentTimeMillis)
    {
        Timestamp ts = new Timestamp(currentTimeMillis);
        return ts.toString();
    }

    public static String getTime()
    {
        return getTime(System.currentTimeMillis());
    }

    public static String getTime(long currentTimeMillis)
    {
        Timestamp ts = new Timestamp(currentTimeMillis);
        String fields[] = ts.toString().split(" ")[1].split(":");
        return formatTime(fields[0] + ":" + fields[1]);
    }

    public static boolean validateDate(int ano, int mes, int dia)
    {
        boolean dv = false;
        boolean anoBissexto = false;
        dv = false;

        if (ano > 0)
        {
            if (mes == 1 | mes == 3 | mes == 5 | mes == 7 | mes == 8 | mes == 10 | mes == 12)
            {
                if (dia >= 1 & dia <= 31)
                    dv = true;
            } else
            {
                if (mes == 4 | mes == 6 | mes == 9 | mes == 11)
                {
                    if (dia >= 1 & dia <= 30)
                        dv = true;
                } else
                {
                    if (mes == 2)
                    {
                        if (dia >= 1 & dia <= 28)
                        {
                            dv = true;
                        } else
                        {
                            anoBissexto = ((ano % 4 == 0 & ano % 100 != 0) | (ano % 400 == 0));
                            if (dia == 29 & anoBissexto)
                                dv = true;
                        }
                    }
                }
            }
        }

        return dv;
    }

    public static boolean validateBrDate(String brDate)
    {
        if (brDate.length() != 10 || !brDate.substring(2, 3).equalsIgnoreCase("/") || !brDate.substring(5, 6).equalsIgnoreCase("/"))
            return false;

        int ano, mes, dia;
        try
        {
            ano = Integer.parseInt(brDate.substring(6, 10));
            mes = Integer.parseInt(brDate.substring(3, 5));
            dia = Integer.parseInt(brDate.substring(0, 2));

            return validateDate(ano, mes, dia);
        } catch (Exception e)
        {
            return false;
        }
    }

  public static boolean validateBrDateTime(String brDate)
    {
        if (brDate.length() != 16 || !brDate.substring(2, 3).equalsIgnoreCase("/") || !brDate.substring(5, 6).equalsIgnoreCase("/"))
            return false;

        int ano, mes, dia, hora, minutos;
        try
        {   // formato dd/MM/aaaa hh:mm
            //         0123456789012345
            ano = Integer.parseInt(brDate.substring(6, 10));
            mes = Integer.parseInt(brDate.substring(3, 5));
            dia = Integer.parseInt(brDate.substring(0, 2));
            hora = Integer.parseInt(brDate.substring(11, 13));
            minutos = Integer.parseInt(brDate.substring(14, 16));

            return validateDate(ano, mes, dia) && (hora < 24 && hora >=0) && (minutos < 60 && minutos >=0);
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Transforma uma data do formato BR para SQL
     * 
     * @param brDate
     *            vazio se a data for invalida ou a data em formato SQL.
     * @return
     */
    public static String brToSqlDate(String brDate)
    {
        StringBuilder sqlDate = new StringBuilder();
        if (brDate.length() == 0)
            return sqlDate.toString();
        if (!validateBrDate(brDate))
            return sqlDate.toString();

        sqlDate.append(brDate.substring(6, 10));
        sqlDate.append("-");
        sqlDate.append(brDate.substring(3, 5));
        sqlDate.append("-");
        sqlDate.append(brDate.substring(0, 2));

        return sqlDate.toString();
    }

    /**
     * Transforma uma data do formato SQL para BR
     * 
     * @param sqlDate
     *            vazio se a data for invalida ou a data em formato SQL.
     * @return
     */
    public static String sqlToBrDate(String sqlDate)
    {
        StringBuilder retDate = new StringBuilder();
        if (sqlDate.length() != 10)
        {
            retDate.append("  /  /    ");
            return retDate.toString();
        }

        try
        {
            retDate.append(sqlDate.substring(8, 10));
            retDate.append("/");
            retDate.append(sqlDate.substring(5, 7));
            retDate.append("/");
            retDate.append(sqlDate.substring(0, 4));
        } catch (Exception e)
        {
            e.printStackTrace();
            retDate = new StringBuilder("  /  /    ");
        }
        return retDate.toString();
    }

    public static boolean validateTime(String time)
    {
        String nums[] = time.trim().split(":");
        if (nums.length < 2)
            return false;

        int intsNums[] = new int[2];

        try
        {
            intsNums[0] = Integer.parseInt(nums[0]);
        } catch (Exception e)
        {
            return false;
        }

        try
        {
            intsNums[1] = Integer.parseInt(nums[1]);
        } catch (Exception e)
        {
            return false;
        }

        if (intsNums[0] < 0 || intsNums[0] > 23)
            return false;

        if (intsNums[1] < 0 || intsNums[1] > 59)
            return false;

        return true;
    }

    public static boolean validateTimeAcceptNull(String time)
    {
        String hora = time.trim();
        if (hora.length() == 0)
            return true;

        String nums[] = hora.split(":");
        if (nums.length < 2)
            return false;

        int intsNums[] = new int[2];

        try
        {
            intsNums[0] = Integer.parseInt(nums[0]);
        } catch (Exception e)
        {
            return false;
        }

        try
        {
            intsNums[1] = Integer.parseInt(nums[1]);
        } catch (Exception e)
        {
            return false;
        }

        if (intsNums[0] < 0 || intsNums[0] > 23)
            return false;

        if (intsNums[1] < 0 || intsNums[1] > 59)
            return false;

        return true;
    }

    /**
     * 
     * @param time
     *            Entrada
     * @return Hora ou vazio se for invalido.
     */
    public static String formatTime(String time)
    {
        String hora = time.trim();
        if (hora.length() == 0)
            return "";

        if (!validateTime(hora))
            return "";

        String nums[] = hora.split(":");

        int intsNums[] = new int[2];
        intsNums[0] = Integer.parseInt(nums[0]);
        intsNums[1] = Integer.parseInt(nums[1]);

        String strNums[] = new String[2];
        strNums[0] = String.valueOf(intsNums[0]);
        strNums[1] = String.valueOf(intsNums[1]);

        if (strNums[0].length() == 1)
            strNums[0] = "0" + strNums[0];
        if (strNums[1].length() == 1)
            strNums[1] = "0" + strNums[01];

        return strNums[0] + ":" + strNums[1];
    }

    /**
     * 
     * @param time
     *            Time ou TimeStamp vindo de SQL
     * @return Hora ou vazio se for invalido.
     */
    public static String sqlToBrTime(String time)
    {
        String ts = time.trim();
        if (ts.length() == 0)
            return "";

        String tmp1[] = ts.split(" ");
        String nums[] = null;

        // se for timestamp
        if (tmp1.length == 2)
            nums = tmp1[1].split(":");
        else
            nums = ts.split(":");

        if (nums[0].length() == 1)
            nums[0] = "0" + nums[0];
        if (nums[1].length() == 1)
            nums[1] = "0" + nums[1];

        return nums[0] + ":" + nums[1];
    }

    /**
     * 
     * @param time
     *            Time ou TimeStamp vindo de SQL
     * @return Data e Hora ou vazio se for invalido.
     */
    public static String sqlToBrTimeStamp(String time)
    {
        String ts = time.trim();
        if (ts.length() == 0)
            return "";

        String tmp1[] = ts.split(" ");
        String nums[] = null;
        String data = "";

        // se for timestamp
        if (tmp1.length == 2)
        {
            nums = tmp1[1].split(":");
            data = sqlToBrDate(tmp1[0]).toString();
        } else
        {
            nums = ts.split(":");
        }

        if (nums[0].length() == 1)
            nums[0] = "0" + nums[0];
        if (nums[1].length() == 1)
            nums[1] = "0" + nums[01];

        return data + " " + nums[0] + ":" + nums[1];
    }

    /**
     * 
     * @param time
     *            Time ou TimeStamp vindo de SQL
     * @return Data e Hora ou vazio se for invalido.
     */
    public static String brToSqlTimeStamp(String time)
    {
        String ts = time.trim();
        if (ts.length() == 0)
            return "";

        String tmp1[] = ts.split(" ");
        String nums[] = null;
        String data = "";

        // se for timestamp
        if (tmp1.length == 2)
        {
            nums = tmp1[1].split(":");
            data = brToSqlDate(tmp1[0]).toString();
        } else
        {
            nums = ts.split(":");
        }

        if (nums[0].length() == 1)
            nums[0] = "0" + nums[0];
        if (nums[1].length() == 1)
            nums[1] = "0" + nums[01];

        return data + " " + nums[0] + ":" + nums[1];
    }

    /**
     * 
     * @param dtSQL
     *            data de referencia no formato SQL.
     * @return qtd dias
     * @throws ParseException
     */
    public static int getDiferencaDtSQLDias(String dtSQL) throws ParseException
    {
        java.util.Date dt = dateFormatterSql.parse(dtSQL);
        long dtRef = dt.getTime();

        return Math.round((System.currentTimeMillis() - dtRef) / 86400000);
    }

    /**
     * 
     */
    public static int getLastDayOfMonth(int ano, int mes)
    {
        int dia = 31;
        while (!validateDate(ano, mes, dia))
            dia--;
        return dia;
    }

    public static int getLastDayOfMonth(String ano, String mes)
    {
        return getLastDayOfMonth(Integer.parseInt(ano), Integer.parseInt(mes));
    }

    /**
     * 
     * @param dtNascimentoBr
     *            data de nascimento no formato SQL.
     * @return Objeto com a idade
     * @throws ParseException
     */
    public static int getIdadeDtSQLDias(String dtNascimentoSQL, String dtReferenciaSQL) throws ParseException
    {
        java.util.Date dt = dateFormatterSql.parse(dtNascimentoSQL);
        java.util.Date dtRef = dateFormatterSql.parse(dtReferenciaSQL);

        return Math.round((dtRef.getTime() - dt.getTime()) / 86400000);
    }

    /**
     * Retorna de 0 a 6, domingo a sabado
     */
    @SuppressWarnings("deprecation")
    public static int getWeekDayNumberFromSqlDate(String dtSQL) throws ParseException
    {
        java.util.Date date = dateFormatterSql.parse(dtSQL);
        return date.getDay();
    }

    public static int getMonthDayNumberFromSqlDate(String dtSQL) throws Exception
    {
        return Integer.parseInt(dtSQL.substring(8, 10));
    }

    public static long trasformToTimeMillis(String dateFormat, String dateSource) throws ParseException
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        java.util.Date dt = dateFormatter.parse(dateSource);
        return dt.getTime();
    }

    public static long brDateToMillis(String date) throws ParseException
    {
        java.util.Date dt = dateFormatterBr.parse(date);
        return dt.getTime();
    }

    public static long sqlDateToMillis(String date) throws ParseException
    {
        java.util.Date dt = dateFormatterSql.parse(date);
        return dt.getTime();
    }
}
