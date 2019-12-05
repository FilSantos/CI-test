# language: pt
@login
Funcionalidade: This is a sample feature file

  Contexto: 
    Dado scenario data

@login1
  Esquema do Cenario: This is a scenario to test datadriven test on Cucumber JVM.
    Dado scenario data
    Quando executed from Runner Class.
    Entao UserName and Password shows on console from Examples "<UserName>" and "<Password>"

    Exemplos: 
      | UserName        | Password        |
      ##@externaldata@./dados/TestData.xlsx@Sheet1
|teste99|1|
|teste100|2|
|teste10|2|

@login2
  Esquema do Cenario: This is a scenario to test datadriven test on Cucumber JVM - diferente.
    Dado scenario data
    Quando executed from Runner Class.
    Entao UserName and Password shows on console from Examples "<UserName>" and "<Password>"

    Exemplos: 
      | UserName        | Password        |
      ##@externaldata@./dados/TestData.xlsx@Sheet2
|teste4|pwd4|
|teste5|pwd5|
|||
|||
|||
|||
|||
