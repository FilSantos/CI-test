package automacao.framework.api;

import io.restassured.http.ContentType;

public enum APIContent {
	URLENC(ContentType.URLENC,ContentType.URLENC.toString()),
	JSON(ContentType.JSON,ContentType.JSON.toString());

	private Object[] option;

	APIContent(Object... vals) {
		option = vals;
	}

	public ContentType getContent() {
		return (ContentType) option[0];
	}

	public String getTexto() {
		return (String) option[1];
	}
}