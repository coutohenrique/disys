package br.com.henriquecouto.domain.exception;

public class DepartamentoNaoEncontradoException extends NegocioException{

	private static final long serialVersionUID = 675260839913465965L;

	public DepartamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
