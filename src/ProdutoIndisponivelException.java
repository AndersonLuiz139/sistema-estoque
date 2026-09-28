/**
 * Lancada quando se tenta vender mais unidades do que existem em estoque.
 */
public class ProdutoIndisponivelException extends EstoqueException {

    private static final long serialVersionUID = 1L;

    public ProdutoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
