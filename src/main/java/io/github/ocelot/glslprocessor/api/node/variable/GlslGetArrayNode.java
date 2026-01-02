package io.github.ocelot.glslprocessor.api.node.variable;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.stream.Stream;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslGetArrayNode implements GlslNode {

    private GlslNode expression;
    private GlslNode index;

    public GlslGetArrayNode(final GlslNode expression, final GlslNode index) {
        this.expression = expression;
        this.index = index;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.GET_ARRAY;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitGetArray(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.expression.stream(), this.index.stream()));
    }

}
