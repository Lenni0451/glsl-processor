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
public final class GlslVariableNode implements GlslNode {

    private String name;

    public GlslVariableNode(final String name) {
        this.name = name;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.VARIABLE;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitVariable(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.of(this);
    }

}
