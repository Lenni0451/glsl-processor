package io.github.ocelot.glslprocessor.api.node.function;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslInvokeVisitor;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
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
public final class GlslInvokeFunctionNode implements GlslNode {

    private GlslNode header;
    private final GlslNodeList parameters;

    public GlslInvokeFunctionNode(final GlslNode header, final Collection<GlslNode> parameters) {
        this.header = header;
        this.parameters = new GlslNodeList(parameters);
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.INVOKE_FUNCTION;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        GlslInvokeVisitor invokeVisitor = visitor.visitFunctionInvocation(this);
        if (invokeVisitor != null) {
            invokeVisitor.visitHeader();
            for (int i = 0; i < this.parameters.size(); i++) {
                GlslNodeVisitor parameterVisitor = invokeVisitor.visitParameter(i);
                if (parameterVisitor != null) {
                    this.parameters.get(i).visit(parameterVisitor);
                }
            }
            invokeVisitor.visitInvokeEnd(this);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), this.parameters.stream().flatMap(GlslNode::stream));
    }

}
