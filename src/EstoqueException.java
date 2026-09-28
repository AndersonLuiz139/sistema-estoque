/**
 * Excecao base da hierarquia de erros de estoque.
 * Por herdar de Exception (e nao de RuntimeException), e uma excecao
 * verificada: quem chama precisa tratar ou declarar throws.
 */
public class EstoqueException extends Exception {

    private static final long serialVersionUID = 1L;

    public EstoqueException(String mensagem) {
        super(mensagem);
    }
}
