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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Represents for loops.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslForLoopNode implements GlslNode {

    private GlslNode init;
    private GlslNode condition;
    private GlslNode increment;
    private final GlslNodeList body;

    public GlslForLoopNode(final GlslNode init, final GlslNode condition, @Nullable final GlslNode increment, final Collection<GlslNode> body) {
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.body = new GlslNodeList(body);
    }

    public GlslForLoopNode setBody(final GlslNode... body) {
        return this.setBody(Arrays.asList(body));
    }

    public GlslForLoopNode setBody(final Collection<GlslNode> body) {
        this.body.clear();
        this.body.addAll(body);
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.FOR_LOOP;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        GlslNodeVisitor bodyVisitor = visitor.visitForLoop(this);
        if (bodyVisitor != null) {
            for (GlslNode node : this.body) {
                node.visit(bodyVisitor);
            }
            bodyVisitor.visitForLoopEnd(this);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(this.init.stream(), Stream.concat(this.condition.stream(), this.increment.stream()));
    }

}
