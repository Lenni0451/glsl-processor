package io.github.ocelot.glslprocessor.api.node;

import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class GlslCompoundNode implements GlslNode {

    private final GlslNodeList children;

    public GlslCompoundNode(final Collection<GlslNode> children) {
        this.children = new GlslNodeList(children);
    }

    public GlslNodeList getChildren() {
        return this.children;
    }

    public GlslCompoundNode setChildren(final GlslNode... children) {
        return this.setChildren(Arrays.asList(children));
    }

    public GlslCompoundNode setChildren(final Collection<GlslNode> children) {
        this.children.clear();
        this.children.addAll(children);
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        throw new AssertionError("This should never happen");
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        for (GlslNode node : this.children) {
            node.visit(visitor);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), this.children.stream().flatMap(GlslNode::stream));
    }

}
