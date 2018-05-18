package br.pharma.dao;

import java.util.ArrayList;

/**
 * Interface para classes de acesso a dados
 *
 * @author Juliano
 */
public interface IDAO<Tipo> {

    public void inserir(Tipo objeto) throws Exception;

    public void atualizar(Tipo objeto) throws Exception;

    public void remover(Tipo objeto) throws Exception;

    public ArrayList<Tipo> listarTodos() throws Exception;

    public Tipo recuperar(long codigo) throws Exception;
}
