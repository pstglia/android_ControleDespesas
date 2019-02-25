# android_ControleDespesas
Um app pessoal como exercício de aprendizado. O objetivo registrar despesas

Detalhe: O código não segue os padrões e práticas que atualmente se exigem.
Reconheço que ele pode ser um exemplo de "como não fazer". Mas é o que temos para hoje.

O que o app faz:
================

- Registrar o valor de uma despesa, informando a data em que ela foi gerada, uma categoria principal e uma secundária:
(Ex: 
Categoria principal: CARRO
Categoria secundária: COMBUSTIVEL
Valor: 100,00

- Consultar as despesas lançadas: É possível filtrar por período (data início, data fim) e por categoria principal
  Acima da lista de resultados, é exibida a soma dos valores

- Criar e apagar categorias (obs: Atualmente não é validado se ela está associada a um gasto)

O que o app não faz:
====================

- Salvar os dados em um servidor externo. Tudo fica em uma base sqlite local.
  Logo, se você desinstalar o app, trocar de aparelho você perde seu histórico.

- Não há qualquer integração com bancos ou outros apps (há alguns app's avançados que fazem isso)

O que talvez ainda seja implementado:
=====================================

- Funcionalidade de export/import dos dados


