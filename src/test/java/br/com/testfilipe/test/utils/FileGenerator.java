package br.com.testfilipe.test.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.testfilipe.core.utils.StringFormat;

public class FileGenerator {
	
	final static Logger logger = Logger.getLogger(FileGenerator.class);
	
	public String preencheHeader(String date, int tamanhoLinha, int versao, int tipoArquivo, String empresa, String cnpjEmpresa,
			String codigoProduto) {
		
		String header = StringUtils.repeat(" ", tamanhoLinha);
		header = StringFormat.substituiTextoPosicao(header, 0, 1, 2); //Tipo Registro
		header = StringFormat.substituiTextoPosicao(header, tipoArquivo, 3, 2); //Tipo Arquivo
		header = StringFormat.substituiTextoPosicao(header, versao, 5, 6); //Numero Sequencial do Arquivo
		header = StringFormat.substituiTextoPosicao(header, date, 11, 10); //Data de Geracao do Arquivo	
		header = StringFormat.substituiTextoPosicao(header, empresa, 21, 15); //Nome da Empresa
		header = StringFormat.substituiTextoPosicao(header,cnpjEmpresa, 36, 15); //CNPJ  da Empresa
		header = StringFormat.substituiTextoPosicao(header, codigoProduto, 51, 8); //Codigo do Produto
		return header;

		
	}
	
	public String footer(int tamanhoLinha, int numeroLinha) {
		String footer = StringUtils.repeat(" ", tamanhoLinha);
		footer = StringFormat.substituiTextoPosicao(footer, 99, 1, 2); //Tipo Registro
		footer = StringFormat.substituiTextoPosicao(footer, numeroLinha + 1, 3, 7); //Tipo Arquivo
		return footer;
	}


	public String geraParcelas(int tamanhoLinha, DateTimeFormatter dtf, int numeroLinha, int origemProposta,
								int numeroProposta, int NumeroContrato, int j, LocalDateTime venctoData) {
	
		String detail = StringUtils.repeat(" ", tamanhoLinha);
		detail = StringFormat.substituiTextoPosicao(detail, 30, 1, 2); //Tipo de Registro
		detail = StringFormat.substituiTextoPosicao(detail,numeroLinha, 3, 7); //Número sequencial de Registro
		detail = StringFormat.substituiTextoPosicao(detail, origemProposta, 10, 2); //Origem da Proposta
		detail = StringFormat.substituiTextoPosicao(detail, numeroProposta, 12, 8); //número da proposta Porto
		detail = StringFormat.substituiTextoPosicao(detail, NumeroContrato, 20, 14); //número do contrato  
		detail = StringFormat.substituiTextoPosicao(detail,j, 34, 2); //Número Parcela
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(venctoData), 36, 10); //Dt vencto parcela
		detail = StringFormat.substituiTextoPosicao(detail, 22.15, 46, 15); //Vl parcela
		detail = StringFormat.substituiTextoPosicao(detail, 22.15 , 61, 15); //Vl prêmio parcela
		return detail;
	}

	public String geraProposta(int tamanhoLinha, int parcelasQuantidade, DateTimeFormatter dtf, LocalDateTime localDate,
								int numeroLinha, int origemProposta, int numeroProposta, int NumeroContrato) {
	
		String detail = StringUtils.repeat(" ", tamanhoLinha);
		detail = StringFormat.substituiTextoPosicao(detail, 10, 1, 2); //Tipo Registro
		detail = StringFormat.substituiTextoPosicao(detail, numeroLinha, 3, 7); //Numero Sequencial
		detail = StringFormat.substituiTextoPosicao(detail, origemProposta, 10, 2); //Origem Proposta
		detail = StringFormat.substituiTextoPosicao(detail, numeroProposta, 12, 8); //NumeroProposta
		detail = StringFormat.substituiTextoPosicao(detail, NumeroContrato, 20, 14); //NumeroContrato
		detail = StringFormat.substituiTextoPosicao(detail, "", 34, 20); //Identificacao Segurado Parceiro
		detail = StringFormat.substituiTextoPosicao(detail, "", 54, 8); //Estipulante
		detail = StringFormat.substituiTextoPosicao(detail, "", 62, 5); //Filial
		detail = StringFormat.substituiTextoPosicao(detail, "", 67, 3); //número de série Sulacap
		detail = StringFormat.substituiTextoPosicao(detail, "", 70, 5); //número de Sorte Sulacap
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(localDate), 75, 10); //data da compra
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(localDate), 85, 10); //data da adesão ao seguro
		detail = StringFormat.substituiTextoPosicao(detail, 1, 95, 2); //plano contratado
		detail = StringFormat.substituiTextoPosicao(detail, parcelasQuantidade, 97, 2); //quantidade total de parcelas                               
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(localDate), 99, 10); //data do início da vigência do seguro
		detail = StringFormat.substituiTextoPosicao(detail, dtf.format(localDate), 109, 10); //data do final da vigência do seguro
		return detail;
	}

	public String geraSegurado(int tamanhoLinha, int numeroLinha, int origemProposta, int numeroProposta,
								int NumeroContrato, String cpfSegurado) {
	
		String detail = StringUtils.repeat(" ", tamanhoLinha);
		detail = StringFormat.substituiTextoPosicao(detail, 20, 1, 2); //Tipo de Registro
		detail = StringFormat.substituiTextoPosicao(detail,numeroLinha, 3, 7); //Número sequencial de Registro
		detail = StringFormat.substituiTextoPosicao(detail, origemProposta, 10, 2); //Origem da Proposta
		detail = StringFormat.substituiTextoPosicao(detail, numeroProposta, 12, 8); //número da proposta Porto
		detail = StringFormat.substituiTextoPosicao(detail, NumeroContrato, 20, 14); //número do contrato                                
		detail = StringFormat.substituiTextoPosicao(detail, cpfSegurado, 34, 14); //CPF do segurado                                       
		detail = StringFormat.substituiTextoPosicao(detail, "TESTE AUTOMATIZADO" + cpfSegurado, 48, 50); //nome do segurado                                      
		detail = StringFormat.substituiTextoPosicao(detail, "15/11/1984", 98, 10); //data de nascimento do segurado
		detail = StringFormat.substituiTextoPosicao(detail, "F", 108, 1); //Sexo do segurado
		detail = StringFormat.substituiTextoPosicao(detail, 1, 109, 1); //Estado Civil do Segurado
		detail = StringFormat.substituiTextoPosicao(detail, "PRACA ANTONIO PRADO", 110, 40); //Endereço do Segurado
		detail = StringFormat.substituiTextoPosicao(detail, 1, 150, 5); //Número do Endereço
		detail = StringFormat.substituiTextoPosicao(detail, "", 155, 15); //Complemento
		detail = StringFormat.substituiTextoPosicao(detail, 01010 , 170, 5); //CEP
		detail = StringFormat.substituiTextoPosicao(detail, 0, 175, 3); //Complemento do CEP
		detail = StringFormat.substituiTextoPosicao(detail, "São Bento", 178, 20); //Bairro
		detail = StringFormat.substituiTextoPosicao(detail, "São Paulo", 198, 20); //Cidade
		detail = StringFormat.substituiTextoPosicao(detail, "SP", 218, 2); //Estado
		detail = StringFormat.substituiTextoPosicao(detail, 1, 220, 2); //Tipo Fone 1
		detail = StringFormat.substituiTextoPosicao(detail, 11, 222, 4); //DDD Fone 1
		detail = StringFormat.substituiTextoPosicao(detail, 55555551, 226, 8); //Número Fone 1
		detail = StringFormat.substituiTextoPosicao(detail, 0, 234, 4); //Ramal Fone 1
		detail = StringFormat.substituiTextoPosicao(detail, 2, 238, 2); //Tipo Fone 2
		detail = StringFormat.substituiTextoPosicao(detail, 11, 240, 4); //DDD Fone 2
		detail = StringFormat.substituiTextoPosicao(detail, 55555552, 244, 8); //Número Fone 2
		detail = StringFormat.substituiTextoPosicao(detail, 0, 252, 4); //Ramal Fone 2
		detail = StringFormat.substituiTextoPosicao(detail, 3, 256, 2); //Tipo Fone 3
		detail = StringFormat.substituiTextoPosicao(detail, 11, 258, 4); //DDD Fone 3
		detail = StringFormat.substituiTextoPosicao(detail, 55555553, 262, 8); //Número Fone 3
		detail = StringFormat.substituiTextoPosicao(detail, 1278, 270, 4); //Ramal Fone 3
		detail = StringFormat.substituiTextoPosicao(detail, 4, 274, 2); //Tipo Fone 4
		detail = StringFormat.substituiTextoPosicao(detail, 11, 276, 4); //DDD Fone 4
		detail = StringFormat.substituiTextoPosicao(detail, 55555554, 280, 8); //Número Fone 4
		detail = StringFormat.substituiTextoPosicao(detail, 0, 288, 4); //Ramal Fone 4
		detail = StringFormat.substituiTextoPosicao(detail, "portoseguro@portoseguro.com.br", 292, 50); //E-mail
		detail = StringFormat.substituiTextoPosicao(detail,  2111, 342, 5); //Código da Profissão
		detail = StringFormat.substituiTextoPosicao(detail, 2580.33, 347, 18); //Valor da Renda Mensal
		return detail;
	}



}
