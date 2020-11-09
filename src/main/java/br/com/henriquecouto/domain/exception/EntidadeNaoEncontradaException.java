package br.com.henriquecouto.domain.exception;

public class EntidadeNaoEncontradaException extends NegocioException{
	
	private static final long serialVersionUID = -3364530295954284523L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
