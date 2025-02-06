package com.gapsi.app.response;

import lombok.Getter;
import lombok.Setter;

public class DTOErrorResponse {

	@Setter
	@Getter
	private String mensaje;
	@Setter
	@Getter
	private String estatus;

	public DTOErrorResponse() {

	}

	public DTOErrorResponse(String mensaje, String estatus) {
		this.mensaje = mensaje;
		this.estatus = estatus;

	}

}
