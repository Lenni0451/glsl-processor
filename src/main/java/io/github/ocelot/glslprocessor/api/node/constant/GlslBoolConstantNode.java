package io.github.ocelot.glslprocessor.api.node.constant;

import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslBoolConstantNode implements GlslConstantNode {

    private boolean value;

    @ApiStatus.Internal
    public GlslBoolConstantNode(final boolean value) {
        this.value = value;
    }

    @Override
    public Number numberValue() {
        return this.value ? 1 : 0;
    }

    @Override
    public boolean booleanValue() {
        return this.value;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public void set(final Number value) {
        this.value = value.longValue() == 1;
    }

    @Override
    public void set(final boolean value) {
        this.value = value;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.BOOL_CONSTANT;
    }

}
