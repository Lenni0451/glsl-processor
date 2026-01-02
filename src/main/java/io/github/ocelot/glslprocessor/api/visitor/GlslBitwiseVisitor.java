package io.github.ocelot.glslprocessor.api.visitor;

import io.github.ocelot.glslprocessor.api.node.expression.GlslBitwiseNode;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@ApiStatus.Experimental
public class GlslBitwiseVisitor {

    @Nullable
    private final GlslBitwiseVisitor parent;

    public GlslBitwiseVisitor() {
        this(null);
    }

    public GlslBitwiseVisitor(@Nullable final GlslBitwiseVisitor parent) {
        this.parent = parent;
    }

    public @Nullable GlslNodeVisitor visitNode(final int index) {
        return this.parent != null ? this.parent.visitNode(index) : null;
    }

    public void visitBitwiseExpressionEnd(final GlslBitwiseNode node) {
        if (this.parent != null) {
            this.parent.visitBitwiseExpressionEnd(node);
        }
    }

}
