# Sistema de Estoque de Produtos

Exercício de implementação em Java — classes abstratas, herança, interfaces,
polimorfismo, composição e exceções.

## Como compilar e executar

```bash
javac -d out src/*.java
java -cp out EstoqueApp
```

Testado com JDK 25. Compila sem erros e sem avisos (verificado com `-Xlint:all`).
A saída da execução está salva em [`saida.txt`](saida.txt).

## Estrutura

```
src/
  EstoqueException.java            exceção base (verificada)
  QuantidadeInvalidaException.java preço/quantidade negativos
  ProdutoIndisponivelException.java estoque insuficiente
  Vendavel.java                    interface com vender()
  Product.java                     classe abstrata, implements Vendavel
  ProdutoComum.java                preço x quantidade
  ProdutoPerecivel.java            20% off quando diasParaVencer <= 3
  Estoque.java                     composição: TEM UMA List<Product>
  EstoqueApp.java                  main() de demonstração
```

## Onde cada requisito foi atendido

| Requisito | Implementação |
|---|---|
| Hierarquia de exceções | `EstoqueException` → `QuantidadeInvalidaException`, `ProdutoIndisponivelException` |
| Classe abstrata com atributos `private` | `Product` (nome, preco, quantidade + getters) |
| Construtor valida preço/quantidade | `Product(...)` lança `QuantidadeInvalidaException` |
| Método abstrato | `Product.calcularValorTotal()` |
| Método concreto | `Product.getDescricao()` |
| Herança + `@Override` (polimorfismo dinâmico) | `ProdutoComum`, `ProdutoPerecivel` |
| Desconto automático de 20% | `ProdutoPerecivel.calcularValorTotal()` quando `diasParaVencer <= 3` |
| `getDescricao()` sobrescrito com a validade | `ProdutoPerecivel.getDescricao()` (usa `super.getDescricao()`) |
| Interface implementada na classe abstrata | `Product implements Vendavel` |
| Sobrecarga (polimorfismo estático) | as duas versões de `aplicarDesconto()` |
| Composição (TEM UMA) | `Estoque` com `List<Product>`, sem herdar de `Product` |
| Propagação da exceção de venda | `Estoque.venderProduto()` |
| Polimorfismo na soma do estoque | `Estoque.calcularValorTotalEstoque()` |
| Ordem dos catches | em `EstoqueApp`, `EstoqueException` sempre por último |

## Uma observação sobre a ordem dos catches

O enunciado pede os três `catch` no mesmo `try`, com `EstoqueException` por
último. Colocá-lo antes dos outros é erro de compilação
(*unreachable catch clause*) — é isso que a dica do enunciado adverte.

Há um segundo detalhe que só aparece ao compilar: o Java também recusa um
`catch` de exceção **verificada** que o corpo do `try` não pode lançar
(*exception X is never thrown in body of corresponding try statement*). Para que
os três `catch` sejam todos válidos, o `try` precisa realmente poder lançar os
três tipos. Por isso `Estoque.venderProduto()` declara `throws EstoqueException`
e tem três saídas de erro distintas:

- índice inexistente → `EstoqueException`
- quantidade menor ou igual a zero → `QuantidadeInvalidaException`
- estoque insuficiente → `ProdutoIndisponivelException`, propagada de `vender()`

## Valores da demonstração

| Produto | Preço | Qtd. | Validade | Valor total |
|---|---|---|---|---|
| Caneta Azul | R$ 2,50 | 100 | — | R$ 250,00 |
| Caderno 100 folhas | R$ 18,90 | 40 | — | R$ 756,00 |
| Leite Integral 1L | R$ 5,20 | 30 | 10 dias | R$ 156,00 |
| Iogurte Natural | R$ 4,00 | 25 | 2 dias | R$ 80,00 (20% off) |

Total inicial: **R$ 1.242,00**. O iogurte mostra o polimorfismo: 4,00 × 25 =
100,00, menos 20% = 80,00, somado pelo `Estoque` sem que ele saiba que aquele
produto é perecível.
