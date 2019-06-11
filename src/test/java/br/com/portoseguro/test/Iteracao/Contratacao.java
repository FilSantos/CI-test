package br.com.portoseguro.test.Iteracao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import br.com.portoseguro.core.selenium.pageobject.BaseWebPage;
import br.com.portoseguro.pageobject.salesforce.ContratacaoPO;

public class Contratacao extends BaseWebPage {

	
	public Contratacao(WebDriver webDriver) {
		super(webDriver);
		
		// TODO Auto-generated constructor stub
	}


	private ContratacaoPO Contract;

			
		
		@Override
		public boolean isDisplayed() {
			return false;
		}
		
			
		/**
		 * Validar seleção de canal - Corretor
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void selecaoCorretor() throws Exception {
					
			WebElement corretor = Contract.canalSelecionadoCorretor();
			command.click(corretor);
			WebElement susep = Contract.campoSusep();
			command.send(susep, "0075");
			command.screenshot();
		}
		
		/**
		 * Validar seleção de canal - Cliente
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void selecaoCliente() throws Exception {
					
			WebElement cliente = Contract.canalSelecionadoClientes();
			command.click(cliente);
			WebElement cpf = Contract.campoSusep();
			command.send(cpf, "41223244404");
			command.screenshot();
		}
		
		/**
		 * Botao Entrar
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void btnEntrar() throws Exception {
					
			WebElement entrar = Contract.portoButton();
			command.click(entrar);
			
		}
		
		/**
		 * Validar selecao plano para voce
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoVoce() throws Exception {
					
			WebElement voce = Contract.typedContractForYou();
			command.click(voce);
			
		}
		
		/**
		 * Validar selecao plano para familia
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoFamilia() throws Exception {
					
			WebElement familia = Contract.typedContractForFamily();
			command.click(familia);
			
		}
		
		/**
		 * Validar selecao plano para todos
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoTodos() throws Exception {
					
			WebElement todos = Contract.typedContractForAll();
			command.click(todos);
			
		}
		
		/**
		 * Validar botao proxima etapa
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void proximaEtapa() throws Exception {
					
			WebElement next = Contract.nextStage();
			command.click(next);
			
		}
		
		
		/**
		 * Validar campo nome completo
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void nomeCompleto() throws Exception {
					
			WebElement nome = Contract.completo_Nome();
			command.send(nome, "Bruno Silva");
			
		}
		
		
		/**
		 * Validar campo data de nascimento
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void dataNascimento() throws Exception {
					
			WebElement nascimento = Contract.nascimento();
			command.send(nascimento, "07081992");
			
		}
		
		/**
		 * Validar campo de selecao sexo
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void sexoMasculino() throws Exception {
					
			WebElement masculino = Contract.sexoMasculino();
			command.click(masculino);
			
		}
		
		/**
		 * Validar campo de selecao feminino
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void sexoFeminino() throws Exception {
					
			WebElement feminino = Contract.sexoFeminino();
			command.click(feminino);
			
		}
		
		/**
		 * Validar campo de selecao fumante
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void fumanteSim() throws Exception {
					
			WebElement fumanteSim = Contract.fumanteSim();
			command.click(fumanteSim);
			
		}
		
		/**
		 * Validar campo de selecao fumante
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void fumanteNao() throws Exception {
					
			WebElement fumanteNao = Contract.fumanteNao();
			command.click(fumanteNao);
			
		}
		
		/**
		 * Validar campo de selecao Estado Civil - Solteiro
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void estadoSolteiro() throws Exception {
					
			WebElement solteiro = Contract.estadoSolteiro();
			command.click(solteiro);
			
		}
		
		
		/** 
		 * Validar campo de selecao Estado Civil - Casado
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void estadoCasado() throws Exception {
					
			WebElement casado = Contract.estadoCasado();
			command.click(casado);
			
		}
		
		/** 
		 * Validar campo de selecao Estado Civil - Divorciado
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void estadoDivorciado() throws Exception {
					
			WebElement divorciado = Contract.estadoDivorciado();
			command.click(divorciado);
			
		}
		
		
		/** 
		 * Validar campo de selecao Estado Civil - Viuvo
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void estadoViuvo() throws Exception {
					
			WebElement viuvo = Contract.estadoViuvo();
			command.click(viuvo);
			
		}
		
		
		/** 
		 * Validar campo de informacao Profissao
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void profissao() throws Exception {
					
			WebElement profissao = Contract.profissao();
			command.click(profissao);
			
		}
		
		/** 
		 * Validar botao voltar
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void btnVoltar() throws Exception {
					
			WebElement voltar = Contract.btnVoltar();
			command.click(voltar);
			
		}
		
		/** 
		 * Validar campo de selecao Renda - 4000
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void renda4000() throws Exception {
					
			WebElement renda4000 = Contract.renda4000();
			command.click(renda4000);
			
		}
		
		
		/** 
		 * Validar campo de selecao Renda - 4000,01 a 8000
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void renda8000() throws Exception {
					
			WebElement renda8000 = Contract.renda4001();
			command.click(renda8000);
			
		}
		
		
		/** 
		 * Validar campo de selecao Renda - 8000,01 a 12000
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void renda12000() throws Exception {
					
			WebElement renda12000 = Contract.renda8001();
			command.click(renda12000);
			
		}
		
		/** 
		 * Validar campo de selecao Renda - 12000,01 a 16000
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void renda16000() throws Exception {
					
			WebElement renda16000 = Contract.renda12001();
			command.click(renda16000);
			
		}
		
		
		/** 
		 * Validar campo de selecao Renda - acima 16000
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void acima16000() throws Exception {
					
			WebElement acima16000 = Contract.rendaAcima16000();
			command.click(acima16000);
			
		}
		
		/** 
		 * Validar botao enviar
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void btnEnviar() throws Exception {
					
			WebElement enviar = Contract.btnEnviar();
			command.click(enviar);
			
		}
		
		
		/** 
		 * Validar plano Viva Tranquilo Inferior
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoVivaInferior() throws Exception {
					
			WebElement vivaInferior = Contract.planoInferior();
			command.click(vivaInferior);
			
		}
		
		/** 
		 * Validar plano Viva Tranquilo Medio
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoVivaMedio() throws Exception {
					
			WebElement vivaMedio = Contract.planoMedio();
			command.click(vivaMedio);
			
		}
		
		
		/** 
		 * Validar plano Viva Tranquilo Superior
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void planoVivaSuperior() throws Exception {
					
			WebElement vivaSuperior = Contract.planoSuperior();
			command.click(vivaSuperior);
			
		}
		
		/** 
		 * Validar plano Viva Tranquilo Inferior - Personalize
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void personalizeVivaInferior() throws Exception {
					
			WebElement personalizeVivaInferior = Contract.personalizarPlanoInferior();
			command.click(personalizeVivaInferior);
			
		}
		
		/** 
		 * Validar plano Viva Tranquilo Medio - Personalize
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void personalizeVivaMedio() throws Exception {
					
			WebElement personalizeVivaMedio = Contract.personalizarPlanoMedio();
			command.click(personalizeVivaMedio);
			
		}
		
		/** 
		 * Validar plano Viva Tranquilo Superior - Personalize
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void personalizeVivaSuperior() throws Exception {
					
			WebElement personalizeVivaSuperior = Contract.personalizarPlanoSuperior();
			command.click(personalizeVivaSuperior);
			
		}
		
		
		/** 
		 * Botao confirmar
		 * @author 634111 - Bruno Silva
		 * @param tokenPort 
		 * @throws Exception
		 */
		public void btnConfirmar() throws Exception {
					
			WebElement confirmar = Contract.btnConfirmar();
			command.click(confirmar);
			
		}
		
		
		
		
		
}