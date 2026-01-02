package io.github.ocelot.glslprocessor.api.grammar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Represents a single field inside a struct.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslStructField {

    private GlslSpecifiedType type;
    private String name;

    public GlslStructField(final GlslType type, final String name) {
        this(type.asSpecifiedType(), name);
    }

    public GlslStructField(final GlslSpecifiedType type, final String name) {
        this.type = type.asSpecifiedType();
        this.name = name;
    }

    /**
     * @return A deep copy of this struct field
     */
    public GlslStructField copy() {
        return new GlslStructField(this.type.copy(), this.name);
    }

}
