# language: pt
@BRQ
Funcionalidade: Acesso no sistema Portal BRQ - Android

  Contexto: 
    Dado instalado aplicação Portal BRQ

@Acesso
  Esquema do Cenario: Validação de acesso no sitema
    Dado exibido tela incial
    Quando informar usuário "<Usuario>" e senha "<Senha>".
    Entao obtenho o seguinte resultado "<Resultado>"

    Exemplos: 
      | Usuario		| Senha | Resultado |
			|teste  		|1			| Erro      |
			|			  		|1			| Erro      |
			|teste  		|123456	| Erro      |
