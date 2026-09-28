/**
 * Produto com validade curta. Quando faltam 3 dias ou menos para vencer,
 * o valor total recebe 20% de desconto automatico.
 */
public class ProdutoPerecivel extends Product {

    private static final int DIAS_LIMITE_DESCONTO = 3;
    private static final double PERCENTUAL_DESCONTO = 0.20;

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    @Override
    public double calcularValorTotal() {
        double total = getPreco() * getQuantidade();
        if (diasParaVencer <= DIAS_LIMITE_DESCONTO) {
            total -= total * PERCENTUAL_DESCONTO;
        }
        return total;
    }

    @Override
    public String getDescricao() {
        String base = super.getDescricao() + " | vence em: " + diasParaVencer + " dia(s)";
        if (diasParaVencer <= DIAS_LIMITE_DESCONTO) {
            base += " | DESCONTO DE 20% APLICADO";
        }
        return base;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }
}
