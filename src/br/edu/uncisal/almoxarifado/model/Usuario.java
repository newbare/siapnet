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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author Igor Cavalcante
 */
@Entity
@SequenceGenerator(name = "SEQ_CLOG", sequenceName = "sq_usuario")
@SQLDelete(sql="UPDATE almoxarifado.usuario SET deleted = 'true' WHERE id = ?")
@Where(clause="deleted <> 'true'")
public class Usuario extends Domain implements Serializable {

    private static final long serialVersionUID = 355675681886731320L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLOG")
    private Long id;
    @Column(length = 100)
    private String nome;
    @Column(length = 50)
    private String funcao;
    @Column(length = 100)
    private String email;
    @Column(length = 15)
    private String cpf;
    @Column(length = 40)
    private String senha;
    @Transient
    @Column(length = 15)
    private String ip;
    /**
     * Um usuário tem acesso a vários departamentos, mas estes departamentos estão relacionados aos perfis
     * então esta classe associativa foi criada.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<UsuarioDepartamento> usuarioDepartamentos;

    /**
     * Em que departamento e com qual perfil o usuário está logado.
     */
    @Transient
    private UsuarioDepartamento usuarioDepartamentoAtivo;
    
    /** Numero de matrícula do servidor */
    @Column(name = "numero_matricula", length = 15)
    private String numeroMatricula;

    public Usuario() {
        super();
    }

    public Usuario(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setUsuarioDepartamentoAtivo(UsuarioDepartamento usuarioDepartamentoAtivo) {
        this.usuarioDepartamentoAtivo = usuarioDepartamentoAtivo;
    }

    /**
     * Deve-se procurar ele em usuarioDepartamentos por causa do lazy-loading.
     * @return O departamento e perfil que o usuário está representando no momento.
     */
    public UsuarioDepartamento getUsuarioDepartamentoAtivo() {
        return usuarioDepartamentoAtivo;
    }

    public List<UsuarioDepartamento> getUsuarioDepartamentos() {
        return usuarioDepartamentos;
    }

    public void setUsuarioDepartamentos(List<UsuarioDepartamento> usuarioDepartamentos) {    	
        this.usuarioDepartamentos = usuarioDepartamentos;
    }

    @Override
    public String toString() {
        return "[id: " + id + ", nome: " + nome + " email: " + email + " ip: " + ip + "]";
    }
    
    public static Collection<Usuario> listByAlmoxarifado(List<Usuario> usuarios, Almoxarifado almoxarifado) {
        List<Usuario> uSelecionados = new ArrayList<Usuario>();
        for(Usuario u: usuarios) {
            boolean selected = false;
            for(UsuarioDepartamento uD: u.getUsuarioDepartamentos()) {
                for(Almoxarifado a: uD.getDepartamento().getAlmoxarifados()) {
                    if(a.getId().equals(almoxarifado.getId()) && !selected ) {
                        uSelecionados.add(u);
                        selected = true;
                    }
                }
            }
        }
        return usuarios;
    }
    
    public Collection<Usuario> list(Collection<Usuario> usuarios, Almoxarifado almoxarifado) {
        Collection<Usuario> usuariosAux = new ArrayList<Usuario>();
        for(Usuario u: usuarios) {
            boolean selected = false;
            for(UsuarioDepartamento uD: u.getUsuarioDepartamentos())
                for(Almoxarifado a: uD.getDepartamento().getAlmoxarifados())
                    if(a.getId().equals(almoxarifado.getId()) && !selected ) {
                        usuariosAux.add(u);
                        selected = true;
                    }
        }
        return usuariosAux;
    }
    
}
