package io.github.ocelot.glslprocessor.api.grammar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Represents the version statement for a GLSL shader source.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslVersionStatement {

    private int version;
    private boolean core;

    public GlslVersionStatement() {
        this(110, true);
    }

    public GlslVersionStatement(final int version, final boolean core) {
        this.version = version;
        this.core = core;
    }

    /**
     * @return The string representation of this version
     */
    public String getVersionStatement() {
        return this.version + (this.core ? " core" : "");
    }

}
