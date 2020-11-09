package br.com.henriquecouto.domain.exception;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 6795517830145661696L;

	public FuncionarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FuncionarioNaoEncontradoException(Long funcionarioId) {
		this(String.format("Não existe um cadastro de funcionario com código %d", funcionarioId));
	}

}
