/**
 * Classe abstrata que representa um produto qualquer do estoque.
 *
 * Nao pode ser instanciada: cada tipo concreto de produto decide como o seu
 * valor total e calculado (metodo abstrato calcularValorTotal()).
 */
public abstract class Product implements Vendavel {

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                    "Preco invalido para \"" + nome + "\": " + preco + " (nao pode ser negativo).");
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    "Quantidade invalida para \"" + nome + "\": " + quantidade + " (nao pode ser negativa).");
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    /** Cada subclasse define a sua propria regra de calculo. */
    public abstract double calcularValorTotal();

    public String getDescricao() {
        return String.format("%s | preco unitario: R$ %.2f | quantidade: %d", nome, preco, quantidade);
    }

    /**
     * Implementacao da interface Vendavel: se nao houver estoque suficiente,
     * a venda e recusada com ProdutoIndisponivelException.
     */
    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(
                    "Estoque insuficiente de \"" + nome + "\": pedido " + quantidadeDesejada
                            + ", disponivel " + quantidade + ".");
        }
        quantidade -= quantidadeDesejada;
    }

    /* ---------- Sobrecarga: polimorfismo estatico ---------- */

    /** Aplica um desconto percentual sobre o preco unitario. */
    public void aplicarDesconto(double percentual) {
        preco -= preco * (percentual / 100.0);
    }

    /**
     * Mesma ideia, mas o valor descontado nunca passa de descontoMaximo
     * (em reais). Mesmo nome, assinatura diferente: o compilador escolhe a
     * versao certa pela lista de argumentos.
     */
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        double desconto = preco * (percentual / 100.0);
        if (desconto > descontoMaximo) {
            desconto = descontoMaximo;
        }
        preco -= desconto;
    }

    /* ---------- Acesso aos atributos encapsulados ---------- */

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
