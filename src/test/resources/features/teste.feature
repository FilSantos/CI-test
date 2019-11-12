@login
Feature: This is a sample feature file


  Background: 
    Given scenario data

@login1
  Scenario Outline: This is a scenario to test datadriven test on Cucumber JVM.
    Given scenario data
    When executed from Runner Class.
    Then UserName and Password shows on console from Examples "<UserName>" and "<Password>"

    Examples: 
      | UserName        | Password        |
      ##@externaldata@./src/test/resources/data/TestData.xlsx@Sheet1
|teste99|pwd1|
|teste100|pwd2|

@login2
  Scenario Outline: This is a scenario to test datadriven test on Cucumber JVM - diferente.
    Given scenario data
    When executed from Runner Class.
    Then UserName and Password shows on console from Examples "<UserName>" and "<Password>"

    Examples: 
      | UserName        | Password        |
      ##@externaldata@./src/test/resources/data/TestData.xlsx@Sheet2
|teste4|pwd4|
|teste5|pwd5|
|teste6|pwd6|
