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
public final class GlslFloatConstantNode implements GlslConstantNode {

    private float value;

    @ApiStatus.Internal
    public GlslFloatConstantNode(final float value) {
        this.value = value;
    }

    @Override
    public Number numberValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public boolean booleanValue() {
        return this.value != 0.0;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public void set(final Number value) {
        this.value = value.floatValue();
    }

    @Override
    public void set(final boolean value) {
        this.value = value ? 1 : 0;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.FLOAT_CONSTANT;
    }

}
