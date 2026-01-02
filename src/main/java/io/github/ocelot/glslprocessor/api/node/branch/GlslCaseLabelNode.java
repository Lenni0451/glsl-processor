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
public final class GlslCaseLabelNode implements GlslNode {

    @Nullable
    private GlslNode condition;

    public GlslCaseLabelNode(@Nullable final GlslNode condition) {
        this.condition = condition;
    }

    public boolean isDefault() {
        return this.condition == null;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.CASE_LABEL;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        throw new UnsupportedOperationException("Cannot call visit() on GlslCaseLabelNode");
    }

    @Override
    public Stream<GlslNode> stream() {
        if (this.condition == null) {
            return Stream.of(this);
        } else {
            return Stream.concat(Stream.of(this), this.condition.stream());
        }
    }

}
