package io.github.ocelot.glslprocessor.api.grammar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents a single parameter declaration. Includes the name and full data operand of the parameter.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslParameterDeclaration {

    private GlslSpecifiedType type;
    private String name;

    public GlslParameterDeclaration(final GlslType type, @Nullable final String name) {
        this(type.asSpecifiedType(), name);
    }

    public GlslParameterDeclaration(final GlslSpecifiedType type, @Nullable final String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Sets the data operand of this parameter.
     *
     * @param type The new operand
     */
    public GlslParameterDeclaration setType(final GlslType type) {
        this.type = type.asSpecifiedType();
        return this;
    }

    /**
     * Sets The qualifiers of this parameter operand.
     *
     * @param qualifiers The new qualifiers
     */
    public GlslParameterDeclaration setQualifiers(final GlslTypeQualifier... qualifiers) {
        this.type.setQualifiers(qualifiers);
        return this;
    }

    /**
     * Sets The qualifiers of this parameter operand.
     *
     * @param qualifiers The new qualifiers
     */
    public GlslParameterDeclaration setQualifiers(final Collection<GlslTypeQualifier> qualifiers) {
        this.type.setQualifiers(qualifiers);
        return this;
    }

    /**
     * @return A deep copy of this parameter declaration
     */
    public GlslParameterDeclaration copy() {
        return new GlslParameterDeclaration(this.type.copy(), this.name);
    }

}
