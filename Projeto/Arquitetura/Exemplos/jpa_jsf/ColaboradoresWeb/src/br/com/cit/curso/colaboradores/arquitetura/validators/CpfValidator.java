package br.com.cit.curso.colaboradores.arquitetura.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.cit.curso.colaboradores.util.BundleUtil;
import br.com.cit.curso.colaboradores.util.Constantes;

@FacesValidator("CpfValidator")
public class CpfValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(!new CPFValidator(false).invalidMessagesFor((String) value).isEmpty()) {
			FacesMessage msg = 
					new FacesMessage(BundleUtil.getMessage(Constantes.BundleKeys.Comum.CPF_INVALIDO));
			throw new ValidatorException(msg);
		}
	}

}
