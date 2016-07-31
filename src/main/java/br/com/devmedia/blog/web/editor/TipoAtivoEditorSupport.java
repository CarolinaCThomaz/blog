package br.com.devmedia.blog.web.editor;

import java.beans.PropertyEditorSupport;

import br.com.devmedia.blog.entity.TipoAtivo;

public class TipoAtivoEditorSupport extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (text.equals("AÇÃO")) {

			super.setValue(TipoAtivo.AÇÃO);

		} else {

			super.setValue(TipoAtivo.OPÇÃO);

		}
	}

}
