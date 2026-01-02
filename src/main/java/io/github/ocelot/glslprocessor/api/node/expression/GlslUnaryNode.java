package io.github.ocelot.glslprocessor.api.node.expression;

import io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

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
public final class GlslUnaryNode implements GlslNode {

    private GlslNode expression;
    private Operand operand;

    public GlslUnaryNode(final GlslNode expression, final Operand operand) {
        this.expression = expression;
        this.operand = operand;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.UNARY;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitUnary(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), this.expression.stream());
    }

    @Override
    public @Nullable GlslSpecifiedType getSpecifiedTypeInternal() {
        return this.expression.getSpecifiedTypeInternal();
    }


    @Getter
    @RequiredArgsConstructor
    public enum Operand {
        PRE_INCREMENT("++"),
        PRE_DECREMENT("--"),
        POST_INCREMENT("++"),
        POST_DECREMENT("--"),
        PLUS("+"),
        DASH("-"),
        BANG("!"),
        TILDE("~");

        private final String delimiter;
    }

}
