import java.util.ArrayList;
import java.util.List;

/**
 * O Estoque TEM UMA lista de Product (composicao) -- nao herda de Product.
 */
public class Estoque {

    private List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        produtos.add(p);
    }

    /**
     * Vende do produto que esta na posicao indicada.
     *
     * Pode falhar de tres formas, e por isso declara o tipo base EstoqueException:
     *   - indice inexistente          -> EstoqueException
     *   - quantidade menor ou igual a 0 -> QuantidadeInvalidaException
     *   - estoque insuficiente        -> ProdutoIndisponivelException (propagada de vender())
     */
    public void venderProduto(int indice, int quantidade) throws EstoqueException {
        if (indice < 0 || indice >= produtos.size()) {
            throw new EstoqueException("Nao existe produto no indice " + indice + ".");
        }
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException(
                    "Quantidade de venda invalida: " + quantidade + " (deve ser maior que zero).");
        }
        produtos.get(indice).vender(quantidade);
    }

    /**
     * Polimorfismo dinamico: o Estoque chama calcularValorTotal() sem saber
     * se o objeto e comum ou perecivel -- cada um calcula do seu jeito.
     */
    public double calcularValorTotalEstoque() {
        double total = 0.0;
        for (Product p : produtos) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            Product p = produtos.get(i);
            System.out.printf("  [%d] %s%n      -> valor total: R$ %.2f%n",
                    i, p.getDescricao(), p.calcularValorTotal());
        }
    }

    public Product getProduto(int indice) {
        return produtos.get(indice);
    }

    public int getTotalDeProdutos() {
        return produtos.size();
    }
}
