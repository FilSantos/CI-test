package br.com.testfilipe.core.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class StringFormat {

	public static String substituiTextoPosicao (String texto, Object conteudo, int inicio, int tamanho) {
		
		String conteudoConvertido = String.valueOf(conteudo).trim();	
		
		if ( conteudo instanceof  String == false) {
			conteudoConvertido = StringUtils.repeat("0", tamanho - conteudoConvertido.trim().length()) + conteudoConvertido.trim();
		} else {
			if (conteudoConvertido.trim().length() < tamanho) {
				conteudoConvertido = conteudoConvertido.trim() + StringUtils.repeat(" ", tamanho - conteudoConvertido.trim().length()) ;
			} else {
				conteudoConvertido = conteudoConvertido.substring(0, tamanho);
			}
		}

		texto = texto.substring(0,inicio-1) + conteudoConvertido + texto.substring(inicio + tamanho, texto.length());
		return texto;
	}
	
	public static String completaTexto (char[] conteudo, int tamanhoLinha) {
		
		
		
		return null;
	}
	
	public static String formatString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }
}
