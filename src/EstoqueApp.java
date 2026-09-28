/**
 * Programa de demonstracao do sistema de estoque.
 */
public class EstoqueApp {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        // ---------- 1. Cadastro ----------
        System.out.println("=== 1. CADASTRO DE PRODUTOS ===");
        try {
            estoque.adicionarProduto(new ProdutoComum("Caneta Azul", 2.50, 100));
            estoque.adicionarProduto(new ProdutoComum("Caderno 100 folhas", 18.90, 40));
            estoque.adicionarProduto(new ProdutoPerecivel("Leite Integral 1L", 5.20, 30, 10));
            estoque.adicionarProduto(new ProdutoPerecivel("Iogurte Natural", 4.00, 25, 2));
            System.out.println("4 produtos cadastrados com sucesso.\n");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro inesperado no cadastro: " + e.getMessage());
        }

        estoque.listarProdutos();
        System.out.printf("%nValor total do estoque: R$ %.2f%n%n", estoque.calcularValorTotalEstoque());

        // ---------- 2. Cadastro invalido ----------
        System.out.println("=== 2. TENTANDO CADASTRAR PRODUTO COM QUANTIDADE NEGATIVA ===");
        try {
            estoque.adicionarProduto(new ProdutoComum("Borracha", 1.50, -10));
            System.out.println("Isto nao deveria ser impresso.");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        }
        System.out.println();

        // ---------- 3. Vendas ----------
        // Os tres catches no mesmo try: do mais especifico para o mais generico.
        // EstoqueException vem por ultimo, senao os outros ficariam inalcancaveis.
        System.out.println("=== 3. VENDAS ===");
        try {
            System.out.println("Vendendo 10 unidades de Caneta Azul (indice 0)...");
            estoque.venderProduto(0, 10);
            System.out.println("Venda realizada. Agora: " + estoque.getProduto(0).getDescricao());

            System.out.println("\nTentando vender 999 unidades de Iogurte Natural (indice 3)...");
            estoque.venderProduto(3, 999);
            System.out.println("Isto nao deveria ser impresso.");

        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException: " + e.getMessage());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("ProdutoIndisponivelException capturada: " + e.getMessage());
        } catch (EstoqueException e) {
            System.out.println("EstoqueException (generica): " + e.getMessage());
        }
        System.out.println();

        // As outras duas saidas de erro de venderProduto, so para mostrar que
        // cada catch da cadeia e realmente alcancavel.
        System.out.println("--- Outras falhas de venda ---");
        try {
            estoque.venderProduto(0, -5);
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        } catch (EstoqueException e) {
            System.out.println("EstoqueException (generica): " + e.getMessage());
        }

        try {
            estoque.venderProduto(99, 1);
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        } catch (EstoqueException e) {
            System.out.println("EstoqueException (generica) capturada: " + e.getMessage());
        }
        System.out.println();

        // ---------- 4. Sobrecarga de aplicarDesconto ----------
        System.out.println("=== 4. SOBRECARGA DE aplicarDesconto() ===");
        Product caderno = estoque.getProduto(1);
        System.out.printf("Caderno antes: R$ %.2f%n", caderno.getPreco());
        caderno.aplicarDesconto(10.0);
        System.out.printf("Depois de aplicarDesconto(10.0): R$ %.2f%n", caderno.getPreco());
        caderno.aplicarDesconto(50.0, 2.00);
        System.out.printf("Depois de aplicarDesconto(50.0, 2.00): R$ %.2f%n%n", caderno.getPreco());

        // ---------- 5. Total final ----------
        System.out.println("=== 5. VALOR TOTAL DO ESTOQUE (polimorfismo) ===");
        estoque.listarProdutos();
        System.out.printf("%nTOTAL: R$ %.2f%n", estoque.calcularValorTotalEstoque());
    }
}
