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
public final class GlslOperationNode implements GlslNode {

    private GlslNode first;
    private GlslNode second;
    private Operand operand;

    public GlslOperationNode(final GlslNode first, final GlslNode second, final Operand operand) {
        this.first = first;
        this.second = second;
        this.operand = operand;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.OPERATION;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitOperation(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.first.stream(), this.second.stream()));
    }


    public enum Operand {
        LEFT_SHIFT("<<"),
        RIGHT_SHIFT(">>"),
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        MODULO("%");

        private final String delimiter;

        Operand(String delimiter) {
            this.delimiter = delimiter;
        }

        public String getDelimiter() {
            return this.delimiter;
        }
    }
}
