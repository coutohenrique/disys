package br.com.henriquecouto.domain.exception;

public class CargoNaoEncontradoException  extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = -3523634946547219147L;
	
	public CargoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
