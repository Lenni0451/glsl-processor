package io.github.ocelot.glslprocessor.api.node.branch;

import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import io.github.ocelot.glslprocessor.api.visitor.GlslSwitchVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Switch statement.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslSwitchNode implements GlslNode {

    private GlslNode condition;
    private final GlslNodeList branches;

    public GlslSwitchNode(final GlslNode condition, final Collection<GlslNode> branches) {
        this.condition = condition;
        this.branches = new GlslNodeList(branches);
    }

    /**
     * Replaces all branches with the specified values.
     *
     * @param branches The new branches
     */
    public GlslSwitchNode setBranches(final GlslNode... branches) {
        return this.setBranches(Arrays.asList(branches));
    }

    /**
     * Replaces all branches with the specified values.
     *
     * @param branches The new branches
     */
    public GlslSwitchNode setBranches(final Collection<GlslNode> branches) {
        this.branches.clear();
        this.branches.addAll(branches);
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.SWITCH;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        GlslSwitchVisitor switchVisitor = visitor.visitSwitch(this);
        if (switchVisitor != null) {
            GlslNodeVisitor nodeVisitor = null;
            for (GlslNode branch : this.branches) {
                if (branch instanceof GlslCaseLabelNode node) {
                    nodeVisitor = switchVisitor.visitLabel(node);
                } else if (nodeVisitor != null) {
                    branch.visit(nodeVisitor);
                }
            }
            switchVisitor.visitSwitchEnd(this);
        }
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.concat(Stream.of(this), Stream.concat(this.condition.stream(), this.branches.stream().flatMap(GlslNode::stream)));
    }

}
