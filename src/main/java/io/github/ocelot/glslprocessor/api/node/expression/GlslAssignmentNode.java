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
public final class GlslAssignmentNode implements GlslNode {

    private GlslNode first;
    private GlslNode second;
    private Operand operand;

    /**
     * @param first   The first operand
     * @param second  The second operand
     * @param operand The operand to perform when setting the first to the second
     */
    public GlslAssignmentNode(GlslNode first, GlslNode second, Operand operand) {
        this.first = first;
        this.second = second;
        this.operand = operand;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.ASSIGN;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitAssign(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.first.stream(), this.second.stream()));
    }

    @Override
    public @Nullable GlslSpecifiedType getSpecifiedTypeInternal() {
        return this.first.getSpecifiedTypeInternal();
    }


    @Getter
    @RequiredArgsConstructor
    public enum Operand {
        EQUAL("="),
        MUL_ASSIGN("*="),
        DIV_ASSIGN("/="),
        MOD_ASSIGN("%="),
        ADD_ASSIGN("+="),
        SUB_ASSIGN("-="),
        LEFT_ASSIGN("<<="),
        RIGHT_ASSIGN(">>="),
        AND_ASSIGN("&="),
        XOR_ASSIGN("^="),
        OR_ASSIGN("|=");

        private final String delimiter;
    }

}
