package io.github.ocelot.glslprocessor.api.node.branch;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Represents both while and do/while loops.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslWhileLoopNode implements GlslNode {

    private GlslNode condition;
    private final GlslNodeList body;
    private Type loopType;

    public GlslWhileLoopNode(final GlslNode condition, final Collection<GlslNode> body, final Type loopType) {
        this.condition = condition;
        this.body = new GlslNodeList(body);
        this.loopType = loopType;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.WHILE_LOOP;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        GlslNodeVisitor bodyVisitor = visitor.visitWhileLoop(this);
        if (bodyVisitor != null) {
            for (GlslNode node : this.body) {
                node.visit(bodyVisitor);
            }
            bodyVisitor.visitWhileLoopEnd(this);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.condition.stream(), this.body.stream()));
    }


    public enum Type {
        WHILE, DO
    }

}
