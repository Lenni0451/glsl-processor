package io.github.ocelot.glslprocessor.api.node.expression;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.*;
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
public final class GlslCompareNode implements GlslNode {

    private GlslNode first;
    private GlslNode second;
    private Operand operand;

    public GlslCompareNode(final GlslNode first, final GlslNode second, final Operand operand) {
        this.first = first;
        this.second = second;
        this.operand = operand;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.COMPARE;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitCompare(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.first.stream(), this.second.stream()));
    }


    @Getter
    @RequiredArgsConstructor
    public enum Operand {
        EQUAL("=="),
        NOT_EQUAL("!="),
        LESS("<"),
        GREATER(">"),
        LEQUAL("<="),
        GEQUAL(">=");

        private final String delimiter;
    }

}
