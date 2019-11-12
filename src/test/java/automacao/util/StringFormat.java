package automacao.util;

import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

public class StringFormat {

	protected static String camposDivergentes = "";

	public static String substituiTextoPosicao(String texto, Object conteudo, int inicio, int tamanho) {

		String conteudoConvertido = String.valueOf(conteudo).trim();

		if (conteudo instanceof String == false) {
			conteudoConvertido = StringUtils.repeat("0", tamanho - conteudoConvertido.trim().length())
					+ conteudoConvertido.trim();
		} else {
			if (conteudoConvertido.trim().length() < tamanho) {
				conteudoConvertido = conteudoConvertido.trim()
						+ StringUtils.repeat(" ", tamanho - conteudoConvertido.trim().length());
			} else {
				conteudoConvertido = conteudoConvertido.substring(0, tamanho);
			}
		}

		texto = texto.substring(0, inicio - 1) + conteudoConvertido + texto.substring(inicio + tamanho, texto.length());
		return texto;
	}

	public boolean compararValores(Object esperado, Object retornado, String descricao) {

		if (retornado == null) {
			retornado = "";
		}

		if (!esperado.equals(retornado)) {
			String desc = descricao + "[E:" + String.valueOf(esperado) + " |R: " + String.valueOf(retornado) + "]";
			camposDivergentes += desc + ";";
			return false;
		}
		return true;
	}

	public String getCamposDivergentes() {
		return camposDivergentes;
	}

	public String removerAcentuacao(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("[/()-.\"]", "");
	}
}