/**
 * Lancada quando preco ou quantidade informados sao invalidos (negativos).
 */
public class QuantidadeInvalidaException extends EstoqueException {

    private static final long serialVersionUID = 1L;

    public QuantidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
