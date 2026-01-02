package io.github.ocelot.glslprocessor.api.node.function;

import io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier;
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
public final class GlslPrimitiveConstructorNode implements GlslNode {

    private GlslTypeSpecifier primitiveType;

    public GlslPrimitiveConstructorNode(final GlslTypeSpecifier primitiveType) {
        this.primitiveType = primitiveType;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.PRIMITIVE_CONSTRUCTOR;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitPrimitiveConstructor(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.of(this);
    }

}
