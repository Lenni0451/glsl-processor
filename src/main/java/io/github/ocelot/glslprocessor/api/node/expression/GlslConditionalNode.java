package io.github.ocelot.glslprocessor.api.node.expression;

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
public final class GlslConditionalNode implements GlslNode {

    private GlslNode condition;
    private GlslNode first;
    private GlslNode second;

    public GlslConditionalNode(final GlslNode condition, final GlslNode first, final GlslNode second) {
        this.condition = condition;
        this.first = first;
        this.second = second;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.CONDITIONAL;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitCondition(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.first.stream(), this.second.stream()));
    }

}
