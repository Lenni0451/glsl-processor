package io.github.ocelot.glslprocessor.api.node.branch;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslIfVisitor;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * if/else
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslIfNode implements GlslNode {

    private GlslNode condition;
    private final GlslNodeList first;
    private final GlslNodeList second;

    public GlslIfNode(final GlslNode condition, final Collection<GlslNode> first, final Collection<GlslNode> second) {
        this.condition = condition;
        this.first = new GlslNodeList(first);
        this.second = new GlslNodeList(second);
    }

    public GlslIfNode setFirst(final Collection<GlslNode> first) {
        this.first.clear();
        this.first.addAll(first);
        return this;
    }

    public GlslIfNode setSecond(final Collection<GlslNode> first) {
        this.first.clear();
        this.first.addAll(first);
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.IF_ELSE;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        GlslIfVisitor bodyVisitor = visitor.visitIf(this);
        if (bodyVisitor != null) {
            if (!this.first.isEmpty()) {
                GlslNodeVisitor ifVisitor = bodyVisitor.visitIf();
                if (ifVisitor != null) {
                    for (GlslNode node : this.first) {
                        node.visit(ifVisitor);
                    }
                }
            }

            if (!this.second.isEmpty()) {
                GlslNodeVisitor elseVisitor = bodyVisitor.visitElse();
                if (elseVisitor != null) {
                    for (GlslNode node : this.second) {
                        node.visit(elseVisitor);
                    }
                }
            }

            bodyVisitor.visitIfEnd(this);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.first.stream().flatMap(GlslNode::stream), this.second.stream().flatMap(GlslNode::stream)));
    }

}
