package io.github.ocelot.glslprocessor.api.visitor;

import io.github.ocelot.glslprocessor.api.node.branch.GlslCaseLabelNode;
import io.github.ocelot.glslprocessor.api.node.branch.GlslSwitchNode;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@ApiStatus.Experimental
public class GlslSwitchVisitor {

    @Nullable
    private final GlslSwitchVisitor parent;

    public GlslSwitchVisitor() {
        this(null);
    }

    public GlslSwitchVisitor(@Nullable final GlslSwitchVisitor parent) {
        this.parent = parent;
    }

    /**
     * Visits the specified switch label.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslNodeVisitor visitLabel(final GlslCaseLabelNode node) {
        return this.parent != null ? this.parent.visitLabel(node) : null;
    }

    public void visitSwitchEnd(final GlslSwitchNode node) {
        if (this.parent != null) {
            this.parent.visitSwitchEnd(node);
        }
    }

}
