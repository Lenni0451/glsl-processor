package io.github.ocelot.glslprocessor.api.grammar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Information about a function definition, not including a body.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslFunctionHeader {

    private String name;
    private GlslSpecifiedType returnType;
    private final List<GlslParameterDeclaration> parameters;

    public GlslFunctionHeader(final String name, final GlslType returnType, final Collection<GlslParameterDeclaration> parameters) {
        this(name, returnType, new ArrayList<>(parameters));
    }

    private GlslFunctionHeader(final String name, final GlslType returnType, final List<GlslParameterDeclaration> parameters) {
        this.name = name;
        this.returnType = returnType.asSpecifiedType();
        this.parameters = parameters;
    }

    public GlslFunctionHeader setReturnType(final GlslType returnType) {
        this.returnType = returnType.asSpecifiedType();
        return this;
    }

    /**
     * Sets the parameters in this function.
     *
     * @param parameters The new parameters to use
     */
    public GlslFunctionHeader setParameters(final GlslParameterDeclaration... parameters) {
        return this.setParameters(Arrays.asList(parameters));
    }

    /**
     * Sets the parameters in this function.
     *
     * @param parameters The new parameters to use
     */
    public GlslFunctionHeader setParameters(final Collection<GlslParameterDeclaration> parameters) {
        this.parameters.clear();
        this.parameters.addAll(parameters);
        return this;
    }

    /**
     * @return A deep copy of this function header
     */
    public GlslFunctionHeader copy() {
        List<GlslParameterDeclaration> declarations = new ArrayList<>();
        for (GlslParameterDeclaration parameter : this.parameters) {
            declarations.add(parameter.copy());
        }
        return new GlslFunctionHeader(this.name, this.returnType, declarations);
    }

}
