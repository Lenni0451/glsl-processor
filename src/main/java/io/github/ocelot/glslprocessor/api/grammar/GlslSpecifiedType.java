package io.github.ocelot.glslprocessor.api.grammar;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Specifies the full operand of something in GLSL in addition to all qualifiers.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslSpecifiedType implements GlslType {

    private GlslTypeSpecifier specifier;
    private final List<GlslTypeQualifier> qualifiers;

    public GlslSpecifiedType(final GlslTypeSpecifier specifier) {
        this.specifier = specifier;
        this.qualifiers = new ArrayList<>();
    }

    public GlslSpecifiedType(final GlslTypeSpecifier specifier, final GlslTypeQualifier... qualifiers) {
        this.specifier = specifier;
        this.qualifiers = new ArrayList<>(Arrays.asList(qualifiers));
    }

    public GlslSpecifiedType(final GlslTypeSpecifier specifier, final Collection<GlslTypeQualifier> qualifiers) {
        this.specifier = specifier;
        this.qualifiers = new ArrayList<>(qualifiers);
    }

    public GlslSpecifiedType setQualifiers(final GlslTypeQualifier... qualifiers) {
        return this.setQualifiers(Arrays.asList(qualifiers));
    }

    public GlslSpecifiedType setQualifiers(final Collection<GlslTypeQualifier> qualifiers) {
        this.qualifiers.clear();
        this.qualifiers.addAll(qualifiers);
        return this;
    }

    /**
     * Adds a layout id to the qualifier list, or adds to an existing layout.
     *
     * @param identifier The name of the identifier
     * @param expression The value to assign it to
     */
    public GlslSpecifiedType addLayoutId(final String identifier, @Nullable final GlslNode expression) {
        for (int i = 0; i < this.qualifiers.size(); i++) {
            if (this.qualifiers.get(i) instanceof GlslTypeQualifier.Layout layout) {
                this.qualifiers.set(i, layout.addLayoutId(identifier, expression));
                return this;
            }
        }

        this.qualifiers.add(0, GlslTypeQualifier.layout(GlslTypeQualifier.layoutId(identifier, expression)));
        return this;
    }

    /**
     * @return A deep copy of this type
     */
    public GlslSpecifiedType copy() {
        return new GlslSpecifiedType(this.specifier, this.qualifiers);
    }

    @Override
    public GlslSpecifiedType asSpecifiedType() {
        return this;
    }

}
