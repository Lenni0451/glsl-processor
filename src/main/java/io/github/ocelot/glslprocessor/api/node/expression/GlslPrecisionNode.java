package io.github.ocelot.glslprocessor.api.node.expression;

import io.github.ocelot.glslprocessor.api.grammar.GlslTypeQualifier;
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
public final class GlslPrecisionNode implements GlslNode {

    private GlslTypeQualifier.Precision precision;
    private GlslTypeSpecifier typeSpecifier;

    public GlslPrecisionNode(final GlslTypeQualifier.Precision precision, final GlslTypeSpecifier typeSpecifier) {
        this.precision = precision;
        this.typeSpecifier = typeSpecifier;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.PRECISION;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitPrecision(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.of(this);
    }

}
