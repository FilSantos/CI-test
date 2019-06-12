package br.com.portoseguro.test.iteracao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.ContratacaoPO;
import br.com.portoseguro.test.iteracao.enums.ContratacaoPlano;
import br.com.portoseguro.test.iteracao.enums.ContratacaoPlanoVivaTranquilo;
import br.com.portoseguro.test.iteracao.enums.EstadoCivil;
import br.com.portoseguro.test.iteracao.enums.PersonalizePlanos;
import br.com.portoseguro.test.iteracao.enums.RendaMensal;

public class Contratacao extends BaseWebPage {

	public Contratacao(WebDriver webDriver) {
		super(webDriver);

		// TODO Auto-generated constructor stub
	}

	private ContratacaoPO contract;

	@Override
	public boolean isDisplayed() {
		return false;
	}

	/**
	 * Validar seleção de canal - Corretor
	 * 
	 * @author 634111 - Bruno Silva
	 * @param Corretor = true
	 * @throws Exception
	 */
	public void selecaoCanal(boolean Corretor) throws Exception {

		WebElement canal = contract.selecionarCanal();
		command.click(canal);

		WebElement selecionarCanal;
		if (Corretor) {
			selecionarCanal = contract.canalSelecionadoCorretor();
		} else {
			selecionarCanal = contract.canalSelecionadoClientes();
		}
		command.click(canal);

	}

	/**
	 * Clicar nobotao Entrar
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void btnEntrar() throws Exception {

		WebElement entrar = contract.portoButton();
		command.click(entrar);

	}

	/**
	 * Seleciona o plano para contratacao/simulacao de seguro
	 * 
	 * @author Filipe Santos (Cognizant)
	 * @param plano
	 * @throws Exception
	 */
	public void selecionarPlano(ContratacaoPlano plano) throws Exception {

		WebElement planoselecionado = null;
		switch (plano) {
		case FAMILIA:
			planoselecionado = contract.typedContractForFamily();
			break;
		case VOCE:
			planoselecionado = contract.typedContractForYou();
			break;
		case TODOS:
			planoselecionado = contract.typedContractForAll();
			break;
		}
		command.click(planoselecionado);

	}

	/**
	 * Clicar no botao proxima etapa
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void proximaEtapa() throws Exception {

		WebElement next = contract.nextStage();
		command.click(next);

	}

	/**
	 * Inserir dados no campo de Nome Completo
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void nomeCompleto(String nomeCompleto) throws Exception {

		WebElement nome = contract.completo_Nome();
		command.send(nome, nomeCompleto);

	}

	/**
	 * Inserir dados no campo data de nascimento
	 * 
	 * @author 634111 - Bruno Silva
	 * @param tokenPort
	 * @throws Exception
	 */
	public void dataNascimento(String dateNascimento) throws Exception {

		WebElement nascimento = contract.nascimento();
		command.send(nascimento, dateNascimento);

	}

	/**
	 * Selecionar campo de selecao sexo e inserir o tipo do sexo
	 * @author 634111 - Bruno Silva
	 * @param masculino = True
	 * @throws Exception
	 */
	public void sexo(boolean masculino) throws Exception {

		WebElement sexo = contract.sexo();
		command.click(sexo);

		WebElement sexoclick;
		if (masculino) {
			sexoclick = contract.sexoMasculino();
		} else {
			sexoclick = contract.sexoFeminino();
		}
		command.click(sexoclick);

	}

	/**
	 * Selecionar opção de fumante
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void fumanteSim() throws Exception {

		WebElement fumanteSim = contract.fumanteSim();
		command.click(fumanteSim);

	}

	/**
	 * Selecao de  dados no campo Estado Civil
	 * @author 634111 - Bruno Silva
	 * @param estado 
	 * @throws Exception
	 */
	public void estadoCivil(EstadoCivil estado) throws Exception {

		WebElement estadocivil = null;
		switch (estado) {
		case SOLTEIRO:
			estadocivil = contract.estadoSolteiro();
			break;
		case CASADO:
			estadocivil = contract.estadoCasado();
			break;
		case DIVORCIADO:
			estadocivil = contract.estadoDivorciado();
			break;
		case VIUVO:
			estadocivil = contract.estadoViuvo();
			break;
		}
		command.click(estadocivil);

	}

	/**
	 * Inserir dados no campo Profissao
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void profissao(String job) throws Exception {

		WebElement profissao = contract.profissao();
		command.send(profissao, job);

	}

	/**
	 * Clicar no botao voltar
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void btnVoltar() throws Exception {

		WebElement voltar = contract.btnVoltar();
		command.click(voltar);

	}

	/**
	 * Selecionar rendal mensal
	 * 
	 * @author 634111 - Bruno Silva
	 * @param renda
	 * @throws Exception
	 */
	public void rendaMensal(RendaMensal renda) throws Exception {

		WebElement rendamensal = null;
		switch (renda) {
		case ATE4000:
			rendamensal = contract.renda4000();
			break;
		case DE4001A8000:
			rendamensal = contract.renda4001();
			break;
		case DE8001A12000:
			rendamensal = contract.renda8001();
			break;
		case DE12001A16000:
			rendamensal = contract.renda12001();
			break;
		case ACIMA16000:
			rendamensal = contract.rendaAcima16000();
			break;
		
		}
		command.click(rendamensal);

	}

	

	/**
	 * Clicar no botao Enviar
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void btnEnviar() throws Exception {

		WebElement enviar = contract.btnEnviar();
		command.click(enviar);

	}

	/**
	 * Selecionar plano viva tranquilo
	 * 
	 * @author 634111 - Bruno Silva
	 * @param viva
	 * @throws Exception
	 */
	public void planoVivaTranquilo(ContratacaoPlanoVivaTranquilo viva) throws Exception {

		WebElement vivatranquilo = null;
		switch (viva) {
		case VIVATRANQUILOINFERIOR:
			vivatranquilo = contract.planoInferior();
			break;
		case VIVATRANQUILOMEDIO:
			vivatranquilo = contract.planoMedio();
			break;
		case VIVATRANQUILOSUPERIOR:
			vivatranquilo = contract.planoSuperior();
			break;
		}
		command.click(vivatranquilo);

	}


	/**
	 * Selecao da opçao personalize de planos
	 * 
	 * @author 634111 - Bruno Silva
	 * @param peronalize
	 * @throws Exception
	 */
	public void personalizeVivaInferior(PersonalizePlanos personalize) throws Exception {

		WebElement personalizeplanos = null;
		switch (personalize) {
		case PERSONALIZEPLANOINFERIOR:
			personalizeplanos = contract.personalizarPlanoInferior();
			break;
		case PERSONALIZEPLANOMEDIO:
			personalizeplanos = contract.personalizarPlanoMedio();
			break;
		case PERSONALIZEPLANOSUPERIOR:
			personalizeplanos = contract.personalizarPlanoSuperior();
			break;
		}
		command.click(personalizeplanos);

	}

	
	/**
	 * Clicar botao confirmar
	 * 
	 * @author 634111 - Bruno Silva
	 * @throws Exception
	 */
	public void btnConfirmar() throws Exception {

		WebElement confirmar = contract.btnConfirmar();
		command.click(confirmar);

	}

}