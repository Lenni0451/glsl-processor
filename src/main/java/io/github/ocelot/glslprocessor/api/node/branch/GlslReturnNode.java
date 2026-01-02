package io.github.ocelot.glslprocessor.api.node.branch;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public final class GlslReturnNode implements GlslNode {

    @Nullable
    private GlslNode value;

    public GlslReturnNode(@Nullable final GlslNode value) {
        this.value = value;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.RETURN;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitReturn(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), this.value.stream());
    }

}
