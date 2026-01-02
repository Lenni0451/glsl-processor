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
public final class GlslGetFieldNode implements GlslNode {

    private GlslNode expression;
    private String fieldSelection;

    public GlslGetFieldNode(final GlslNode expression, final String fieldSelection) {
        this.expression = expression;
        this.fieldSelection = fieldSelection;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitGetField(this);
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.GET_FIELD;
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), this.expression.stream());
    }

}
