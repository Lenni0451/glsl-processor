package io.github.ocelot.glslprocessor.api.node;

import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;

import java.util.stream.Stream;

/**
 * @author Ocelot
 * @since 1.0.0
 */
public enum GlslEmptyNode implements GlslNode {
    INSTANCE;

    @Override
    public GlslNodeType getNodeType() {
        throw new AssertionError("This should never happen");
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.empty();
    }

}
