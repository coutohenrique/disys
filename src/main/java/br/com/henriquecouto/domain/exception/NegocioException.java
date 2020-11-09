package br.com.henriquecouto.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -5642530885388813584L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}
