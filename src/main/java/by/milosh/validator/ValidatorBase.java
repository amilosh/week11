package by.milosh.validator;


import org.springframework.core.GenericTypeResolver;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class ValidatorBase<T> implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(GenericTypeResolver.resolveTypeArgument(this.getClass(), ValidatorBase.class));
    }

    @Override
    public void validate(Object o, Errors errors) {
        T object = (T) o;
        validateObject(object, errors);
    }

    public abstract void validateObject(T object, Errors errors);
}
